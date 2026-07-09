CREATE DATABASE IF NOT EXISTS serviceconnect
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE serviceconnect;

-- ============================================================
-- Table: users
-- ============================================================
CREATE TABLE IF NOT EXISTS users (
    id              VARCHAR(36)     NOT NULL PRIMARY KEY,
    email           VARCHAR(100)    NOT NULL UNIQUE,
    mot_de_passe    VARCHAR(255)    NOT NULL,
    nom             VARCHAR(50)     NOT NULL,
    prenom          VARCHAR(50)     NOT NULL,
    role            VARCHAR(20)     NOT NULL,
    specialite      VARCHAR(100)    NULL,
    date_naissance  DATE            NULL,
    numero_tel      VARCHAR(20)     NULL,
    adresse         VARCHAR(255)    NULL,
    ville           VARCHAR(100)    NULL,
    enabled         TINYINT(1)      NOT NULL DEFAULT 1,
    created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      DATETIME        NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    INDEX idx_users_email (email),
    INDEX idx_users_role (role)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- Table: provider_profiles
-- ============================================================
CREATE TABLE IF NOT EXISTS provider_profiles (
    id                  VARCHAR(36)     NOT NULL PRIMARY KEY,
    user_id             VARCHAR(36)     NOT NULL UNIQUE,
    note                DOUBLE          NOT NULL DEFAULT 0,
    missions_realisees  INT             NOT NULL DEFAULT 0,
    missions_total      INT             NOT NULL DEFAULT 0,
    taux_satisfaction   DOUBLE          NOT NULL DEFAULT 0,
    revenu_mensuel      DOUBLE          NOT NULL DEFAULT 0,
    revenu_total        DOUBLE          NOT NULL DEFAULT 0,
    competences         TEXT            NULL,
    description         TEXT            NULL,
    certifications      TEXT            NULL,
    disponible          TINYINT(1)      NOT NULL DEFAULT 1,
    pays                VARCHAR(100)    NULL,
    date_inscription    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_provider_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- Table: service_entities
-- ============================================================
CREATE TABLE IF NOT EXISTS service_entities (
    id          VARCHAR(36)     NOT NULL PRIMARY KEY,
    nom         VARCHAR(100)    NOT NULL,
    description TEXT            NULL,
    categorie   VARCHAR(100)    NULL,
    prix        DOUBLE          NOT NULL,
    duree       INT             NULL,
    image_url   VARCHAR(500)    NULL,
    created_at  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- Table: reservations
-- ============================================================
CREATE TABLE IF NOT EXISTS reservations (
    id                  VARCHAR(36)     NOT NULL PRIMARY KEY,
    reference           VARCHAR(20)     NOT NULL UNIQUE,
    client_id           VARCHAR(36)     NOT NULL,
    prestataire_id      VARCHAR(36)     NOT NULL,
    service_id          VARCHAR(36)     NULL,
    service_key         VARCHAR(50)     NULL,
    service_name        VARCHAR(100)    NULL,
    date_reservation    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_intervention   DATE            NULL,
    heure_slot          VARCHAR(50)     NULL,
    adresse             VARCHAR(255)    NULL,
    description         TEXT            NULL,
    montant             DOUBLE          NOT NULL,
    statut              VARCHAR(20)     NOT NULL DEFAULT 'PENDING',
    payment_method      VARCHAR(30)     NULL,
    annulable           TINYINT(1)      NOT NULL DEFAULT 1,

    CONSTRAINT fk_res_client FOREIGN KEY (client_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_res_prestataire FOREIGN KEY (prestataire_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_res_service FOREIGN KEY (service_id) REFERENCES service_entities(id) ON DELETE SET NULL,

    INDEX idx_res_client (client_id),
    INDEX idx_res_prestataire (prestataire_id),
    INDEX idx_res_statut (statut)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- Table: payments
-- ============================================================
CREATE TABLE IF NOT EXISTS payments (
    id              VARCHAR(36)     NOT NULL PRIMARY KEY,
    reservation_id  VARCHAR(36)     NOT NULL,
    user_id         VARCHAR(36)     NOT NULL,
    montant         DOUBLE          NOT NULL,
    commission      DOUBLE          NOT NULL DEFAULT 0,
    net             DOUBLE          NOT NULL,
    methode         VARCHAR(20)     NULL,
    statut          VARCHAR(20)     NOT NULL DEFAULT 'PENDING',
    date_paiement   DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_pay_reservation FOREIGN KEY (reservation_id) REFERENCES reservations(id) ON DELETE CASCADE,
    CONSTRAINT fk_pay_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- Table: revenues
-- ============================================================
CREATE TABLE IF NOT EXISTS revenues (
    id              VARCHAR(36)     NOT NULL PRIMARY KEY,
    provider_id     VARCHAR(36)     NOT NULL,
    date            DATE            NOT NULL,
    libelle         VARCHAR(200)    NOT NULL,
    mission_id      VARCHAR(36)     NULL,
    client_nom      VARCHAR(100)    NULL,
    client_prenom   VARCHAR(100)    NULL,
    montant         DOUBLE          NOT NULL,
    commission      DOUBLE          NOT NULL DEFAULT 0,
    net             DOUBLE          NOT NULL,
    statut          VARCHAR(20)     NOT NULL DEFAULT 'PAID',

    CONSTRAINT fk_rev_provider FOREIGN KEY (provider_id) REFERENCES users(id) ON DELETE CASCADE,

    INDEX idx_rev_provider (provider_id),
    INDEX idx_rev_date (date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- Table: notifications
-- ============================================================
CREATE TABLE IF NOT EXISTS notifications (
    id              VARCHAR(36)     NOT NULL PRIMARY KEY,
    user_id         VARCHAR(36)     NOT NULL,
    titre           VARCHAR(200)    NOT NULL,
    message         TEXT            NOT NULL,
    date            DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    lu              TINYINT(1)      NOT NULL DEFAULT 0,
    type            VARCHAR(30)     NOT NULL DEFAULT 'INFO',
    mission_id      VARCHAR(36)     NULL,
    emetteur_nom    VARCHAR(100)    NULL,

    CONSTRAINT fk_notif_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,

    INDEX idx_notif_user (user_id),
    INDEX idx_notif_lu (lu)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- Table: planning_slots
-- ============================================================
CREATE TABLE IF NOT EXISTS planning_slots (
    id              VARCHAR(36)     NOT NULL PRIMARY KEY,
    provider_id     VARCHAR(36)     NOT NULL,
    date            DATE            NOT NULL,
    heure_debut     TIME            NOT NULL,
    heure_fin       TIME            NOT NULL,
    mission_id      VARCHAR(36)     NULL,
    mission_titre   VARCHAR(200)    NULL,
    client_nom      VARCHAR(100)    NULL,
    adresse         VARCHAR(255)    NULL,
    type            VARCHAR(20)     NOT NULL DEFAULT 'DISPONIBLE',

    CONSTRAINT fk_planning_provider FOREIGN KEY (provider_id) REFERENCES users(id) ON DELETE CASCADE,

    INDEX idx_planning_provider (provider_id),
    INDEX idx_planning_date (date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- Table: reviews
-- ============================================================
CREATE TABLE IF NOT EXISTS reviews (
    id              VARCHAR(36)     NOT NULL PRIMARY KEY,
    client_id       VARCHAR(36)     NOT NULL,
    prestataire_id  VARCHAR(36)     NOT NULL,
    reservation_id  VARCHAR(36)     NULL,
    note            INT             NOT NULL,
    commentaire     TEXT            NULL,
    date            DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_review_client FOREIGN KEY (client_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_review_prestataire FOREIGN KEY (prestataire_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_review_reservation FOREIGN KEY (reservation_id) REFERENCES reservations(id) ON DELETE SET NULL,

    INDEX idx_review_prestataire (prestataire_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- Insertion de services par défaut
-- ============================================================
INSERT INTO service_entities (id, nom, description, categorie, prix, duree) VALUES
    (UUID(), 'Plomberie', 'Réparation et installation de plomberie', 'Entretien', 15000, 120),
    (UUID(), 'Électricité', 'Installation et dépannage électrique', 'Entretien', 20000, 120),
    (UUID(), 'Ménage', 'Service de nettoyage professionnel', 'Entretien', 10000, 180),
    (UUID(), 'Jardinage', 'Entretien de jardin et espaces verts', 'Entretien', 12000, 120),
    (UUID(), 'Peinture', 'Travaux de peinture intérieure et extérieure', 'Rénovation', 25000, 240),
    (UUID(), 'Déménagement', 'Service de déménagement complet', 'Transport', 50000, 480),
    (UUID(), 'Cours particuliers', 'Soutien scolaire et cours à domicile', 'Éducation', 8000, 60),
    (UUID(), 'Informatique', 'Dépannage et installation informatique', 'Technologie', 15000, 120);
