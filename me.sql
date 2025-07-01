-- Creation de la base de donnees
CREATE DATABASE bibliotheque
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;

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
    Duree_pret INTEGER NOT NULL,
    duree_prolongement INTEGER NOT NULL
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

-- Table Statut (pour les prêts, reservations et prolongements)
CREATE TABLE Statut (
    Id SERIAL PRIMARY KEY,
    Libelle VARCHAR(50) NOT NULL CHECK (Libelle IN ('Valide', 'Refuse', 'Retard', 'Actif', 'Annule', 'Expire'))
);

-- Table Prêt de Livre
CREATE TABLE Pret (
    Id SERIAL PRIMARY KEY,
    Id_Utilisateur INTEGER REFERENCES Utilisateur(Id),
    Id_Exemplaire INTEGER REFERENCES Exemplaire(Id),
    Date_pret TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Date_retour_prevue DATE NOT NULL
);

-- Table Suivi Statut Prêt
CREATE TABLE Suivi_Statut_Pret (
    Id SERIAL PRIMARY KEY,
    Id_pret INTEGER REFERENCES Pret(Id),
    Id_statut INTEGER REFERENCES Statut(Id),
    Date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP
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
    Id_statut INTEGER REFERENCES Statut(Id),
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
    Description_sanction TEXT
);

-- Table Type Livre Autorise
CREATE TABLE Type_Livre_Autorise (
    Id SERIAL PRIMARY KEY,
    Id_type_adherent INTEGER REFERENCES Type_Adherent(Id),
    Id_type_livre INTEGER REFERENCES Type_Livre(Id),
    UNIQUE (Id_type_adherent, Id_type_livre)
);

-- Table Reservation Livre
CREATE TABLE Reservation (
    Id SERIAL PRIMARY KEY,
    Id_utilisateur INTEGER REFERENCES Utilisateur(Id),
    Id_livre INTEGER REFERENCES Livre(Id),
    Date_demande TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Date_expiration DATE
);

-- Table Suivi Statut Reservation
CREATE TABLE Suivi_Statut_Reservation (
    Id SERIAL PRIMARY KEY,
    Id_reservation INTEGER REFERENCES Reservation(Id),
    Id_statut INTEGER REFERENCES Statut(Id),
    Date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insertion des donnees de base

-- Insertion des types d'adherents
INSERT INTO Type_Adherent (Nom_type) VALUES 
('elève'), ('Professeur'), ('Admin'), ('Adherent simple');

-- Insertion des statuts possibles
INSERT INTO Statut (Libelle) VALUES 
 ('Valide'), ('Refuse'), ('Retard'), 
('Actif'), ('Annule'), ('Expire') ;

-- Insertion des types de livres
INSERT INTO Type_Livre (Nom_type) VALUES 
('Simple'), ('Reserve'), ('Rare');



-- Insertion des categories
INSERT INTO Categorie (Nom_categorie) VALUES 
('Litterature'), ('Sciences'), ('Jeunesse'), ('Arts'), 
('Histoire-Geographie'), ('Philosophie'), ('Informatique');

-- Insertion des configurations de prêt par type d'adherent
INSERT INTO Configuration_Pret (Id_type_adherent, Nombre_livre_quota, Duree_pret) VALUES 
(1, 5, 15),  -- elève
(2, 10, 30), -- Professeur
(3, 20, 60), -- Admin
(4, 3, 15); -- Adherent simple

-- Insertion des sanctions par type d'adherent
INSERT INTO Sanction_Type_Adherent (Id_type_adherent, Duree_sanction, Description_sanction) VALUES 
(1, 7, 'Retard de moins de 7 jours'), 
(1, 14, 'Retard de plus de 7 jours ou livre endommage'),
(2, 3, 'Retard pour professeur'),
(4, 10, 'Retard pour adherent simple');



CREATE OR REPLACE VIEW vue_livres_disponibles AS
SELECT 
    l.Id AS Livre_id,
    l.Nom AS Titre,
    tl.Nom_type AS Type_livre,
    c.Nom_categorie AS Categorie,
    e.Id AS Exemplaire_id,
    e.Date_ajout
FROM 
    Livre l
JOIN 
    Type_Livre tl ON l.Id_type_livre = tl.Id
JOIN 
    Categorie c ON l.Id_Categorie = c.Id
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
    s.Libelle AS Statut,
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
JOIN 
    Suivi_Statut_Pret ssp ON p.Id = ssp.Id_pret
JOIN 
    Statut s ON ssp.Id_statut = s.Id
WHERE 
    s.Libelle = 'Actif' AND
    p.Id NOT IN (SELECT Id_pret_livre FROM Retour);

CREATE OR REPLACE VIEW vue_reservations_actives AS
SELECT 
    r.Id AS Reservation_id,
    u.Nom AS Nom_reservant,
    u.Prenom AS Prenom_reservant,
    l.Nom AS Titre_livre,
    r.Date_demande,
    r.Date_expiration,
    s.Libelle AS Statut
FROM 
    Reservation r
JOIN 
    Utilisateur u ON r.Id_utilisateur = u.Id
JOIN 
    Livre l ON r.Id_livre = l.Id
JOIN 
    Suivi_Statut_Reservation ssr ON r.Id = ssr.Id_reservation
JOIN 
    Statut s ON ssr.Id_statut = s.Id
WHERE 
    s.Libelle IN ('Valide', 'Actif') AND
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