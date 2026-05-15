-- V1__create_envios_tables.sql
CREATE TYPE estado_envio AS ENUM (
    'ASIGNADO', 'RECOGIDO', 'EN_CAMINO', 'ENTREGADO', 'FALLIDO'
);

CREATE TABLE IF NOT EXISTS envios (
    id               UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    pedido_id        UUID NOT NULL UNIQUE,
    repartidor_id    UUID,
    estado           estado_envio NOT NULL DEFAULT 'ASIGNADO',
    direccion_origen TEXT NOT NULL,
    direccion_destino TEXT NOT NULL,
    lat_destino      DECIMAL(10,8),
    lng_destino      DECIMAL(11,8),
    tiempo_estimado  INT,              -- minutos estimados
    codigo_confirm   VARCHAR(10),      -- código que confirma el cliente
    entregado_en     TIMESTAMP,
    created_at       TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS repartidores (
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre     VARCHAR(200) NOT NULL,
    telefono   VARCHAR(20) NOT NULL,
    vehiculo   VARCHAR(50),
    placa      VARCHAR(20),
    activo     BOOLEAN NOT NULL DEFAULT TRUE,
    lat_actual DECIMAL(10,8),
    lng_actual DECIMAL(11,8),
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_envios_pedido      ON envios(pedido_id);
CREATE INDEX idx_envios_repartidor  ON envios(repartidor_id);
CREATE INDEX idx_envios_estado      ON envios(estado);
