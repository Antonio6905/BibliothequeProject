-- Creation de la base de donnees
CREATE DATABASE bibliotheque
    WITH 
    OWNER = postgres;

-- Connexion à la base de donnees
\c bibliotheque

-- Creation des tables


-- Table Type Adherent
CREATE TABLE Type_Adherent (
    Id SERIAL PRIMARY KEY,
    Nom_type VARCHAR(50) NOT NULL
);

-- Table Utilisateur
CREATE TABLE Utilisateur (
    Id SERIAL PRIMARY KEY,
    Id_type_adherent INTEGER REFERENCES Type_Adherent(Id),
    Nom VARCHAR(50) NOT NULL,
    Prenom VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    Date_naissance DATE
);

-- Table Configuration Prêt Livre
CREATE TABLE Configuration_Pret (
    Id SERIAL PRIMARY KEY,
    Id_type_adherent INTEGER REFERENCES Type_Adherent(Id),
    Nombre_livre_quota INTEGER NOT NULL,
    Nombre_prolongement_quota INTEGER NOT NULL,
    Nombre_reservation_quota INTEGER NOT NULL,
    Duree_pret INTEGER NOT NULL,
    duree_prolongement INTEGER NOT NULL,
    duree_reservation INTEGER NOT NULL DEFAULT 5
);

CREATE TABLE jour_ferie(
    Id SERIAL PRIMARY KEY,
    date_ferie DATE NOT NULL
);

-- Table Type Livre
CREATE TABLE Type_Livre (
    Id SERIAL PRIMARY KEY,
    Nom_type VARCHAR(50) NOT NULL
);

-- Table Categorie
CREATE TABLE Categorie (
    Id SERIAL PRIMARY KEY,
    Nom_categorie VARCHAR(50) NOT NULL
);

-- Table Livre
CREATE TABLE Livre (
    Id SERIAL PRIMARY KEY,
    Id_type_livre INTEGER REFERENCES Type_Livre(Id),
    Nom VARCHAR(100) NOT NULL,
    Description TEXT,
    Date_edition DATE
);

CREATE TABLE categorie_livre(
    id SERIAL PRIMARY KEY,
    id_livre INTEGER REFERENCES livre(Id),
    id_categorie INTEGER REFERENCES categorie(Id),
    UNIQUE (id_livre, id_categorie)
);

-- Table Exemplaire
CREATE TABLE Exemplaire (
    Id SERIAL PRIMARY KEY,
    Id_Livre INTEGER REFERENCES Livre(Id),
    Date_ajout DATE DEFAULT CURRENT_DATE
);

-- Table Prêt de Livre
CREATE TABLE Pret (
    Id SERIAL PRIMARY KEY,
    Id_Utilisateur INTEGER REFERENCES Utilisateur(Id),
    Id_Exemplaire INTEGER REFERENCES Exemplaire(Id),
    Date_pret TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Date_retour_prevue DATE NOT NULL
);


-- Table Prolongement Prêt
CREATE TABLE Prolongement (
    Id SERIAL PRIMARY KEY,
    Id_pret_livre INTEGER REFERENCES Pret(Id),
    Date_debut_prolongement DATE NOT NULL,
    Date_fin_prolongement DATE NOT NULL
);

-- Table Suivi Statut Prolongement
CREATE TABLE Suivi_Statut_Prolongement (
    Id SERIAL PRIMARY KEY,
    Id_prolongement INTEGER REFERENCES Prolongement(Id),
    statut VARCHAR(20) CHECK(statut  IN ('non traite','valide','non valide')),
    Date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table Retour
CREATE TABLE Retour (
    Id SERIAL PRIMARY KEY,
    Id_pret_livre INTEGER REFERENCES Pret(Id),
    Date_remise_effective TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table Sanction
CREATE TABLE Sanction (
    Id SERIAL PRIMARY KEY,
    Id_Utilisateur INTEGER REFERENCES Utilisateur(Id),
    Id_pret_livre INTEGER REFERENCES Pret(Id),
    Date_debut_sanction DATE NOT NULL,
    Date_fin_sanction DATE NOT NULL,
    Motif VARCHAR(100)
);

-- Table Sanction par Type Adherent
CREATE TABLE Sanction_Type_Adherent (
    Id SERIAL PRIMARY KEY,
    Id_type_adherent INTEGER REFERENCES Type_Adherent(Id),
    Duree_sanction INTEGER NOT NULL,
    Description_sanction TEXT,
    UNIQUE (id_type_adherent)
);

-- Table Type Livre Autorise
CREATE TABLE Type_Livre_Autorise (
    Id SERIAL PRIMARY KEY,
    Id_type_adherent INTEGER REFERENCES Type_Adherent(Id),
    Id_type_livre INTEGER REFERENCES Type_Livre(Id),
    UNIQUE (Id_type_adherent, Id_type_livre)
);

CREATE TABLE abonnement(
    id SERIAL PRIMARY KEY,
    Id_utilisateur INTEGER REFERENCES Utilisateur(id),
    date_debut DATE DEFAULT CURRENT_DATE,
    date_fin DATE NOT NULL
);

-- Table Reservation Livre
CREATE TABLE Reservation (
    Id SERIAL PRIMARY KEY,
    Id_utilisateur INTEGER REFERENCES Utilisateur(Id),
    Id_exemplaire INTEGER REFERENCES Exemplaire(Id),
    Date_demande DATE DEFAULT CURRENT_DATE,
    Date_fin DATE NOT NULL,
    Date_expiration DATE
);

-- Table Suivi Statut Reservation
CREATE TABLE  Suivi_Statut_Reservation (
    Id SERIAL PRIMARY KEY,
    Id_reservation INTEGER REFERENCES Reservation(Id),
    statut VARCHAR(20) CHECK(statut  IN ('non traite','valide','non valide')),
    Date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE OR REPLACE VIEW vue_livres_disponibles AS
SELECT 
    l.Id AS Livre_id,
    l.Nom AS Titre,
    tl.Nom_type AS Type_livre,
    e.Id AS Exemplaire_id,
    e.Date_ajout
FROM 
    Livre l
JOIN 
    Type_Livre tl ON l.Id_type_livre = tl.Id
JOIN 
    Exemplaire e ON l.Id = e.Id_Livre
WHERE 
    e.Id NOT IN (
        SELECT Id_Exemplaire FROM Pret 
        WHERE Id NOT IN (
            SELECT Id_pret_livre FROM Retour
        )
    );

CREATE OR REPLACE VIEW vue_emprunts_en_cours AS
SELECT 
    p.Id AS Pret_id,
    
    u.Nom AS Nom_emprunteur,
    u.Prenom AS Prenom_emprunteur,
    ta.Nom_type AS Type_adherent,
    l.Nom AS Titre_livre,
    e.Id AS Exemplaire_id,
    p.Date_pret,
    p.Date_retour_prevue,
    u.Id AS User_id,
    CASE 
        WHEN CURRENT_DATE > p.Date_retour_prevue THEN 'En retard'
        ELSE 'Dans les temps'
    END AS Etat_retour
FROM 
    Pret p
JOIN 
    Utilisateur u ON p.Id_Utilisateur = u.Id
JOIN 
    Type_Adherent ta ON u.Id_type_adherent = ta.Id
JOIN 
    Exemplaire e ON p.Id_Exemplaire = e.Id
JOIN 
    Livre l ON e.Id_Livre = l.Id

WHERE 
    p.Id NOT IN (SELECT Id_pret_livre FROM Retour) AND p.date_pret<=CURRENT_TIMESTAMP;

CREATE OR REPLACE VIEW vue_reservations_actives AS
SELECT 
    r.Id AS Reservation_id,
    u.Nom AS Nom_reservant,
    u.Prenom AS Prenom_reservant,
    e.id AS exemplaire_id,
    r.Date_demande,
    r.Date_expiration
FROM 
    Reservation r
JOIN 
    Utilisateur u ON r.Id_utilisateur = u.Id
JOIN 
    Exemplaire e ON r.Id_exemplaire = e.Id
JOIN 
    Suivi_Statut_Reservation ssr ON r.Id = ssr.Id_reservation
WHERE 
    ssr.statut='valide' AND
    r.Date_expiration >= CURRENT_DATE;

CREATE OR REPLACE VIEW vue_sanctions_en_cours AS
SELECT 
    s.Id AS Sanction_id,
    u.id AS user_id,
    u.Nom AS Nom_sanctionne,
    u.Prenom AS Prenom_sanctionne,
    ta.Nom_type AS Type_adherent,
    l.Nom AS Titre_livre,
    p.Date_pret,
    s.Date_debut_sanction,
    s.Date_fin_sanction,
    s.Motif,
    CASE 
        WHEN CURRENT_DATE BETWEEN s.Date_debut_sanction AND s.Date_fin_sanction THEN 'En cours'
        WHEN CURRENT_DATE > s.Date_fin_sanction THEN 'Terminee'
        ELSE 'À venir'
    END AS Etat_sanction
FROM 
    Sanction s
JOIN 
    Utilisateur u ON s.Id_Utilisateur = u.Id
JOIN 
    Type_Adherent ta ON u.Id_type_adherent = ta.Id
LEFT JOIN 
    Pret p ON s.Id_pret_livre = p.Id
LEFT JOIN 
    Exemplaire e ON p.Id_Exemplaire = e.Id
LEFT JOIN 
    Livre l ON e.Id_Livre = l.Id
WHERE 
    CURRENT_DATE <= s.Date_fin_sanction;

CREATE OR REPLACE VIEW vue_reservations_non_traites AS
SELECT 
    r.Id AS reservation_id
FROM 
    reservation r
JOIN 
    (
        SELECT 
            Id_reservation, 
            statut, 
            Date_modification,
            ROW_NUMBER() OVER (PARTITION BY Id_reservation ORDER BY Date_modification DESC) AS rn
        FROM 
            Suivi_Statut_Reservation
    ) ssr ON r.Id = ssr.Id_reservation AND ssr.rn = 1
WHERE 
    ssr.statut = 'non traite'  -- ou 'en cours' selon votre logique métier
    AND r.Date_expiration >= CURRENT_DATE;

CREATE OR REPLACE VIEW vue_prolongements_non_traites AS
SELECT 
p.Id AS prolongement_id
FROM 
    Prolongement p
JOIN Pret pr ON p.Id_pret_livre = pr.id
JOIN 
    (
        SELECT 
            Id_prolongement, 
            statut, 
            Date_modification,
            ROW_NUMBER() OVER (PARTITION BY Id_prolongement ORDER BY Date_modification DESC) AS rn
        FROM 
            Suivi_Statut_Prolongement
    ) s ON p.Id = s.Id_prolongement AND s.rn = 1
WHERE 
    s.statut = 'non traite'
    AND pr.Date_retour_prevue>=CURRENT_DATE;


INSERT INTO Type_Adherent (Nom_type) VALUES 
('Etudiant'),
('Enseignant'),
('Professeur');

INSERT INTO Utilisateur (Id_type_adherent, Nom, Prenom, password, Date_naissance) VALUES 
(1, 'Bensaid', 'Amine', 'password123', NULL),
(1, 'El Khattabi', 'Sarah', 'password123', NULL),
(1, 'Moujahid', 'Youssef', 'password123', NULL),
(2, 'Benali', 'Nadia', 'password123', NULL),
(2, 'Haddadi', 'Karim', 'password123', NULL),
(2, 'Touhami', 'Salima', 'password123', NULL),
(3, 'El Mansouri', 'Rachid', 'password123', NULL),
(3, 'Zerouali', 'Amina', 'password123', NULL);

INSERT INTO Configuration_Pret (Id_type_adherent, Nombre_livre_quota, Nombre_prolongement_quota, Nombre_reservation_quota, Duree_pret, duree_prolongement, duree_reservation) VALUES 
(1, 2, 3, 1, 7, 7, 5),
(2, 3, 5, 2, 9, 9, 5),
(3, 4, 7, 3, 12, 12, 5);

INSERT INTO jour_ferie (date_ferie) VALUES 
-- Regular holidays from Excel
('2025-07-19'),
('2025-07-26'),

-- Sundays from Excel (Dimanche)
('2025-07-13'),  -- Already included as a holiday
('2025-07-20'),  -- Already included as a holiday
('2025-07-27'),
('2025-08-03'),
('2025-08-10'),
('2025-08-17');

INSERT INTO Type_Livre (Nom_type) VALUES 
('Littérature classique'),
('Philosophie'),
('Jeunesse/Fantastique');

INSERT INTO Categorie (Nom_categorie) VALUES 
('Littérature classique'),
('Philosophie'),
('Jeunesse'),
('Fantastique');

INSERT INTO Livre (Id_type_livre, Nom, Description, Date_edition) VALUES 
(1, 'Les Miserables', 'Roman classique de Victor Hugo', NULL),
(2, 'Etranger', 'Roman philosophique Albert Camus', NULL),
(3, 'Harry Potter ecole des sorciers', 'Premier tome de la serie Harry Potter', NULL);

INSERT INTO categorie_livre (id_livre, id_categorie) VALUES 
(1, 1),
(2, 2),
(3, 3),
(3, 4);

INSERT INTO Exemplaire (Id_Livre, Date_ajout) VALUES 
(1, CURRENT_DATE),
(1, CURRENT_DATE),
(1, CURRENT_DATE),
(2, CURRENT_DATE),
(2, CURRENT_DATE),
(3, CURRENT_DATE);

INSERT INTO abonnement (Id_utilisateur, date_debut, date_fin) VALUES 
(1, '2025-02-01', '2025-07-24'),
(2, '2025-02-01', '2025-07-01'),
(3, '2025-04-01', '2025-12-01'),
(4, '2025-07-01', '2026-07-01'),
(5, '2025-08-01', '2026-05-01'),
(6, '2025-07-01', '2026-06-01'),
(7, '2025-06-01', '2025-12-01'),
(8, '2024-10-01', '2025-06-01');

INSERT INTO Sanction_Type_Adherent (Id_type_adherent, Duree_sanction, Description_sanction) VALUES 
(1, 10, 'Sanction pour étudiant'),
(2, 9, 'Sanction pour enseignant'),
(3, 8, 'Sanction pour professionnel');

INSERT INTO Type_Livre_Autorise (Id_type_adherent, Id_type_livre) VALUES 
(1, 1),
(1, 2),
(1, 3),
(2, 1),
(2, 2),
(2, 3),
(3, 1),
(3, 2),
(3, 3);