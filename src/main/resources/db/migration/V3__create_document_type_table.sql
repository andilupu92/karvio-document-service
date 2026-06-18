-- Flyway Migration: V3__create_document_type_table.sql

CREATE TABLE IF NOT EXISTS document_type (
    id          BIGINT GENERATED ALWAYS AS IDENTITY,
    created_at  TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    category CHARACTER VARYING(100) NOT NULL,
    icon_name CHARACTER VARYING(100) NOT NULL,
    name        CHARACTER VARYING(100) NOT NULL,
    updated_at  TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    expense_type_id BIGINT,

    CONSTRAINT document_type_pkey PRIMARY KEY (id),
    CONSTRAINT uk_document_type_expense_type UNIQUE NULLS DISTINCT (expense_type_id),
    CONSTRAINT fk_document_type_expense_type FOREIGN KEY (expense_type_id)
        REFERENCES expense_type (id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

-- Insert RCA
INSERT INTO document_type (created_at, category, icon_name, name, updated_at, expense_type_id)
VALUES (NOW(), 'car', 'shield', 'RCA', NOW(), 7);

-- Insert CASCO
INSERT INTO document_type (created_at, category, icon_name, name, updated_at, expense_type_id)
VALUES (NOW(), 'car', 'shield-check', 'CASCO', NOW(), 8);

-- Insert ITP
INSERT INTO document_type (created_at, category, icon_name, name, updated_at, expense_type_id)
VALUES (NOW(), 'car', 'car', 'ITP', NOW(), 9);

-- Insert Rovinietă
INSERT INTO document_type (created_at, category, icon_name, name, updated_at, expense_type_id)
VALUES (NOW(), 'car', 'road', 'Rovinietă', NOW(), 10);

-- Insert Revizie
INSERT INTO document_type (created_at, category, icon_name, name, updated_at, expense_type_id)
VALUES (NOW(), 'car', 'gauge', 'Revizie', NOW(), null);

-- Insert Impozit Auto
INSERT INTO document_type (created_at, category, icon_name, name, updated_at, expense_type_id)
VALUES (NOW(), 'car', 'receipt', 'Impozit Auto', NOW(), 11);

-- Insert Extinctor
INSERT INTO document_type (created_at, category, icon_name, name, updated_at, expense_type_id)
VALUES (NOW(), 'car', 'fire-extinguisher', 'Extinctor', NOW(), 12);

-- Insert Trusă medicală
INSERT INTO document_type (created_at, category, icon_name, name, updated_at, expense_type_id)
VALUES (NOW(), 'car', 'cross', 'Trusă medicală', NOW(), 13);

-- Insert Permis
INSERT INTO document_type (created_at, category, icon_name, name, updated_at, expense_type_id)
VALUES (NOW(), 'personal', 'id-card', 'Permis de conducere', NOW(), null);

-- Insert Carte de identitate
INSERT INTO document_type (created_at, category, icon_name, name, updated_at, expense_type_id)
VALUES (NOW(), 'personal', 'id-card', 'Carte de identitate', NOW(), null);