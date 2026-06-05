-- V1__create_tiendas_tables.sql
CREATE TABLE IF NOT EXISTS tiendas (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre          VARCHAR(200) NOT NULL,
    descripcion     TEXT,
    propietario_id  UUID NOT NULL,          -- referencia al usuarios-service
    nit             VARCHAR(20) UNIQUE,
    telefono        VARCHAR(20),
    email           VARCHAR(150),
    direccion       VARCHAR(300) NOT NULL,
    ciudad          VARCHAR(100) NOT NULL,
    lat             DECIMAL(10,8),
    lng             DECIMAL(11,8),
    logo_url        VARCHAR(500),
    activa          BOOLEAN NOT NULL DEFAULT TRUE,
    horario_inicio  TIME NOT NULL DEFAULT '00:00',  -- 24/7 por defecto
    horario_fin     TIME NOT NULL DEFAULT '23:59',
    radio_envio_km  INT NOT NULL DEFAULT 5,
    created_at      TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_tiendas_ciudad   ON tiendas(ciudad);
CREATE INDEX idx_tiendas_activa   ON tiendas(activa);
CREATE INDEX idx_tiendas_propietario ON tiendas(propietario_id);
