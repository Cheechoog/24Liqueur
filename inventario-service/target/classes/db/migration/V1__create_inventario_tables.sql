-- V1__create_inventario_tables.sql
CREATE TABLE IF NOT EXISTS inventario (
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tienda_id    UUID NOT NULL,
    producto_id  UUID NOT NULL,
    stock        INT NOT NULL DEFAULT 0 CHECK (stock >= 0),
    stock_min    INT NOT NULL DEFAULT 5,   -- alerta de stock bajo
    updated_at   TIMESTAMP NOT NULL DEFAULT NOW(),
    UNIQUE(tienda_id, producto_id)
);

CREATE TABLE IF NOT EXISTS movimientos_inventario (
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    inventario_id UUID NOT NULL REFERENCES inventario(id),
    tipo         VARCHAR(20) NOT NULL,   -- ENTRADA | SALIDA | AJUSTE
    cantidad     INT NOT NULL,
    motivo       VARCHAR(200),
    referencia   VARCHAR(200),          -- ID de pedido, proveedor, etc.
    created_at   TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_inventario_tienda   ON inventario(tienda_id);
CREATE INDEX idx_inventario_producto ON inventario(producto_id);
