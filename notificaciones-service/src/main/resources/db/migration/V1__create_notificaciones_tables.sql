-- V1__create_notificaciones_tables.sql
CREATE TYPE tipo_notif AS ENUM (
    'EMAIL', 'PUSH', 'SMS'
);

CREATE TYPE estado_notif AS ENUM (
    'PENDIENTE', 'ENVIADA', 'FALLIDA'
);

CREATE TABLE IF NOT EXISTS notificaciones (
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id   UUID NOT NULL,
    tipo         tipo_notif NOT NULL,
    asunto       VARCHAR(200),
    mensaje      TEXT NOT NULL,
    estado       estado_notif NOT NULL DEFAULT 'PENDIENTE',
    referencia   VARCHAR(200),        -- ID del pedido, envío, etc.
    intentos     INT NOT NULL DEFAULT 0,
    enviada_en   TIMESTAMP,
    created_at   TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_notif_usuario ON notificaciones(usuario_id);
CREATE INDEX idx_notif_estado  ON notificaciones(estado);
