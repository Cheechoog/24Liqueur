-- V1__create_usuarios_table.sql
CREATE TABLE IF NOT EXISTS usuarios (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre      VARCHAR(100) NOT NULL,
    apellido    VARCHAR(100) NOT NULL,
    email       VARCHAR(150) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    telefono    VARCHAR(20),
    fecha_nac   DATE NOT NULL,          -- requerido para verificar mayoría de edad
    rol         VARCHAR(20) NOT NULL DEFAULT 'CLIENTE',   -- CLIENTE | ADMIN | TIENDA
    activo      BOOLEAN NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS direcciones (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id  UUID NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    alias       VARCHAR(50),
    calle       VARCHAR(200) NOT NULL,
    ciudad      VARCHAR(100) NOT NULL,
    departamento VARCHAR(100) NOT NULL,
    codigo_postal VARCHAR(20),
    lat         DECIMAL(10,8),
    lng         DECIMAL(11,8),
    principal   BOOLEAN NOT NULL DEFAULT FALSE,
    created_at  TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_usuarios_email ON usuarios(email);
CREATE INDEX idx_direcciones_usuario ON direcciones(usuario_id);
