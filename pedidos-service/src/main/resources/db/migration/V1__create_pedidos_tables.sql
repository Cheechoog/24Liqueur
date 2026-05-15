-- V1__create_pedidos_tables.sql
CREATE TYPE estado_pedido AS ENUM (
    'PENDIENTE', 'CONFIRMADO', 'EN_PREPARACION',
    'EN_CAMINO', 'ENTREGADO', 'CANCELADO'
);

CREATE TABLE IF NOT EXISTS pedidos (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id      UUID NOT NULL,
    tienda_id       UUID NOT NULL,
    estado          estado_pedido NOT NULL DEFAULT 'PENDIENTE',
    subtotal        DECIMAL(12,2) NOT NULL,
    descuento       DECIMAL(12,2) NOT NULL DEFAULT 0,
    total           DECIMAL(12,2) NOT NULL,
    direccion_envio TEXT NOT NULL,
    notas           TEXT,
    created_at      TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS items_pedido (
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    pedido_id    UUID NOT NULL REFERENCES pedidos(id) ON DELETE CASCADE,
    producto_id  UUID NOT NULL,
    nombre_prod  VARCHAR(200) NOT NULL,    -- snapshot del nombre
    cantidad     INT NOT NULL CHECK (cantidad > 0),
    precio_unit  DECIMAL(10,2) NOT NULL,
    subtotal     DECIMAL(12,2) NOT NULL
);

CREATE INDEX idx_pedidos_usuario ON pedidos(usuario_id);
CREATE INDEX idx_pedidos_tienda  ON pedidos(tienda_id);
CREATE INDEX idx_pedidos_estado  ON pedidos(estado);
CREATE INDEX idx_items_pedido    ON items_pedido(pedido_id);
