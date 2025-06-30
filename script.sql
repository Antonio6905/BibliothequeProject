\c postgres; -- Connexion à la base de données PostgreSQL
DROP DATABASE IF EXISTS bibliotheque;

CREATE DATABASE bibliotheque;
\c bibliotheque; -- Connexion à la base de données bibliotheque
-- Création de la table type_livre
CREATE TABLE type_livre (
    id_type_livre BIGINT PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(100) NOT NULL,
    UNIQUE (libelle)
);

-- Création de la table livre
CREATE TABLE livre (
    id_livre BIGINT PRIMARY KEY AUTO_INCREMENT,
    titre VARCHAR(255) NOT NULL,
    isbn VARCHAR(13) UNIQUE,
    edition VARCHAR(100),
    auteur VARCHAR(100) NOT NULL,
    date_sortie DATE,
    tag VARCHAR(50),
    id_type_livre BIGINT NOT NULL,
    description TEXT,
    nombre_exemplaires INT DEFAULT 0,
    date_ajout DATE,
    FOREIGN KEY (id_type_livre) REFERENCES type_livre (id_type_livre)
);

-- Création de la table type_pret
CREATE TABLE type_pret (
    id_type_pret BIGINT PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(100) NOT NULL,
    duree_max INT,
    UNIQUE (libelle)
);

-- Création de la table type_adherant
CREATE TABLE type_adherant (
    id_type_adherant BIGINT PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(100) NOT NULL,
    UNIQUE (libelle)
);

-- Création de la table adherant
CREATE TABLE adherant (
    id_adherant BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_type_adherant BIGINT NOT NULL,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    mot_de_passe VARCHAR(255) NOT NULL,
    date_de_naissance DATE,
    adresse TEXT,
    telephone VARCHAR(20),
    date_inscription DATE,
    actif BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id_type_adherant) REFERENCES type_adherant (id_type_adherant)
);

-- Création de la table configuration_type_adherant_type_livre
CREATE TABLE configuration_type_adherant_type_livre (
    id_configuration BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_type_adherant BIGINT NOT NULL,
    id_type_livre BIGINT NOT NULL,
    FOREIGN KEY (id_type_adherant) REFERENCES type_adherant (id_type_adherant),
    FOREIGN KEY (id_type_livre) REFERENCES type_livre (id_type_livre),
    UNIQUE (
        id_type_adherant,
        id_type_livre
    )
);

-- Création de la table configuration_type_adherant_type_pret
CREATE TABLE configuration_type_adherant_type_pret (
    id_configuration BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_type_adherant BIGINT NOT NULL,
    id_type_pret BIGINT NOT NULL,
    nombre_de_livres INT NOT NULL,
    duree_max_emprunt INT,
    FOREIGN KEY (id_type_adherant) REFERENCES type_adherant (id_type_adherant),
    FOREIGN KEY (id_type_pret) REFERENCES type_pret (id_type_pret),
    UNIQUE (
        id_type_adherant,
        id_type_pret
    )
);

-- Création de la table exemplaire
CREATE TABLE exemplaire (
    id_exemplaire BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_livre BIGINT NOT NULL,
    exemplaire_disponible BOOLEAN DEFAULT TRUE,
    date_ajout DATE,
    etat VARCHAR(50),
    FOREIGN KEY (id_livre) REFERENCES livre (id_livre)
);

-- Création de la table statut_pret
CREATE TABLE statut_pret (
    id_statut_pret BIGINT PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(100) NOT NULL,
    UNIQUE (libelle)
);

-- Création de la table pret
CREATE TABLE pret (
    id_pret BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_livre BIGINT NOT NULL,
    id_exemplaire BIGINT NOT NULL,
    id_type_pret BIGINT NOT NULL,
    id_adherant BIGINT NOT NULL,
    id_statut_pret BIGINT NOT NULL,
    date_debut TIMESTAMP NOT NULL,
    duree INT,
    date_fin_prevue TIMESTAMP,
    FOREIGN KEY (id_livre) REFERENCES livre (id_livre),
    FOREIGN KEY (id_exemplaire) REFERENCES exemplaire (id_exemplaire),
    FOREIGN KEY (id_type_pret) REFERENCES type_pret (id_type_pret),
    FOREIGN KEY (id_adherant) REFERENCES adherant (id_adherant),
    FOREIGN KEY (id_statut_pret) REFERENCES statut_pret (id_statut_pret)
);

CREATE TABLE historique_pret (
    id_historique BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_pret BIGINT NOT NULL,
    date_historique_pret TIMESTAMP NOT NULL,
    id_statut BIGINT NOT NULL,
    commentaire TEXT,
    FOREIGN KEY (id_pret) REFERENCES pret (id_pret),
    FOREIGN KEY (id_statut) REFERENCES statut_pret (id_statut_pret)
);

-- Création de la table retour
CREATE TABLE retour (
    id_retour BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_pret BIGINT NOT NULL,
    date_retour TIMESTAMP NOT NULL,
    etat_exemplaire VARCHAR(50),
    commentaire TEXT,
    FOREIGN KEY (id_pret) REFERENCES pret (id_pret),
    UNIQUE (id_pret)
);

-- Création de la table penalite
CREATE TABLE penalite (
    id_penalite BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_adherant BIGINT NOT NULL,
    nombre_jours_penalite INT NOT NULL,
    date_debut TIMESTAMP,
    date_fin TIMESTAMP,
    motif TEXT,
    FOREIGN KEY (id_adherant) REFERENCES adherant (id_adherant)
);

-- Création de la table inscription
CREATE TABLE inscription (
    id_inscription BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_adherant BIGINT NOT NULL,
    date_inscription DATE NOT NULL,
    date_expiration DATE,
    etat VARCHAR(50) DEFAULT 'En attente',
    commentaire TEXT,
    FOREIGN KEY (id_adherant) REFERENCES adherant (id_adherant)
);

-- Création de la table bibliothecaire
CREATE TABLE bibliothecaire (
    id_bibliothecaire BIGINT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    mot_de_passe VARCHAR(255) NOT NULL
);

-- Création de la table statut_reservation
CREATE TABLE statut_reservation (
    id_statut_reservation BIGINT PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(100) NOT NULL,
    UNIQUE (libelle)
);

-- Création de la table reservation
CREATE TABLE reservation (
    id_reservation BIGINT PRIMARY KEY AUTO_INCREMENT,
    date_reservation TIMESTAMP NOT NULL,
    id_adherant BIGINT NOT NULL,
    id_bibliothecaire BIGINT NOT NULL,
    id_exemplaire BIGINT NOT NULL,
    id_statut_reservation BIGINT NOT NULL,
    date_expiration TIMESTAMP,
    FOREIGN KEY (id_adherant) REFERENCES adherant (id_adherant),
    FOREIGN KEY (id_bibliothecaire) REFERENCES bibliothecaire (id_bibliothecaire),
    FOREIGN KEY (id_exemplaire) REFERENCES exemplaire (id_exemplaire)
);

CREATE TABLE historique_reservation(
    id_historique_reservation BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_reservation BIGINT NOT NULL,
    date_historique TIMESTAMP NOT NULL,
    id_statut_reservation BIGINT NOT NULL,
    commentaire TEXT,
    FOREIGN KEY (id_reservation) REFERENCES reservation (id_reservation),
    FOREIGN KEY (id_statut_reservation) REFERENCES statut_reservation (id_statut_reservation)
);

-- Création de la table jour_ferie (pour gérer les jours fériés)
CREATE TABLE jour_ferie (
    id_jour_ferie BIGINT PRIMARY KEY AUTO_INCREMENT,
    day INT NOT NULL,
    month INT NOT NULL,
    description VARCHAR(255),
    UNIQUE (date_ferie)
);

-- Création de la table configuration_reservation (pour les réservations paramétrables)
CREATE TABLE configuration_reservation (
    id_configuration BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_type_adherant BIGINT NOT NULL,
    nombre_max_reservations INT,
    duree_max_reservation INT,
    description TEXT,
    FOREIGN KEY (id_type_adherant) REFERENCES type_adherant (id_type_adherant)
);