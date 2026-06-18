-- Flyway Migration: V2__create_expense_type_table.sql

CREATE TABLE IF NOT EXISTS expense_type (
    id          BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    created_at  TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    icon_name CHARACTER VARYING(100),
    name        CHARACTER VARYING(100) NOT NULL,
    updated_at  TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL DEFAULT NOW()
);

-- Insert Combustibil
INSERT INTO expense_type (created_at, icon_name, name, updated_at)
VALUES (NOW(), 'fuel', 'Combustibil', NOW());

-- Insert Parcare
INSERT INTO expense_type (created_at, icon_name, name, updated_at)
VALUES (NOW(), 'square-parking', 'Parcare', NOW());

-- Insert Spălătorie
INSERT INTO expense_type (created_at, icon_name, name, updated_at)
VALUES (NOW(), 'droplets', 'Spălătorie', NOW());

-- Insert Reparații&Piese
INSERT INTO expense_type (created_at, icon_name, name, updated_at)
VALUES (NOW(), 'wrench', 'Reparații&Piese', NOW());

-- Insert Anvelope&Schimburi
INSERT INTO expense_type (created_at, icon_name, name, updated_at)
VALUES (NOW(), 'circle-dot', 'Anvelope&Schimburi', NOW());

-- Insert Amenzi
INSERT INTO expense_type (created_at, icon_name, name, updated_at)
VALUES (NOW(), 'triangle-alert', 'Amenzi', NOW());

-- Insert RCA
INSERT INTO expense_type (created_at, icon_name, name, updated_at)
VALUES (NOW(), 'shield', 'RCA', NOW());

-- Insert CASCO
INSERT INTO expense_type (created_at, icon_name, name, updated_at)
VALUES (NOW(), 'shield-check', 'CASCO', NOW());

-- Insert ITP
INSERT INTO expense_type (created_at, icon_name, name, updated_at)
VALUES (NOW(), 'car', 'ITP', NOW());

-- Insert Rovinietă
INSERT INTO expense_type (created_at, icon_name, name, updated_at)
VALUES (NOW(), 'road', 'Rovinietă', NOW());

-- Insert Impozit Auto
INSERT INTO expense_type (created_at, icon_name, name, updated_at)
VALUES (NOW(), 'receipt', 'Impozit Auto', NOW());

-- Insert Extinctor
INSERT INTO expense_type (created_at, icon_name, name, updated_at)
VALUES (NOW(), 'fire-extinguisher', 'Extinctor', NOW());

-- Insert Trusă medicală
INSERT INTO expense_type (created_at, icon_name, name, updated_at)
VALUES (NOW(), 'cross', 'Trusă medicală', NOW());

-- Insert Alte cheltuieli
INSERT INTO expense_type (created_at, icon_name, name, updated_at)
VALUES (NOW(), 'person-standing', 'Alte cheltuieli', NOW());