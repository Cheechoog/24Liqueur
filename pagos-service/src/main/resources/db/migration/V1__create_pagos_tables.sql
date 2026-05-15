-- V1__create_pagos_tables.sql
CREATE TYPE estado_pago AS ENUM (
    'PENDIENTE', 'PROCESANDO', 'APROBADO', 'RECHAZADO', 'REEMBOLSADO'
);

CREATE TYPE metodo_pago AS ENUM (
    'TARJETA_CREDITO', 'TARJETA_DEBITO', 'PSE', 'NEQUI',
    'DAVIPLATA', 'EFECTIVO', 'MERCADOPAGO'
);

CREATE TABLE IF NOT EXISTS pagos (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    pedido_id       UUID NOT NULL UNIQUE,
    usuario_id      UUID NOT NULL,
    monto           DECIMAL(12,2) NOT NULL,
    metodo          metodo_pago NOT NULL,
    estado          estado_pago NOT NULL DEFAULT 'PENDIENTE',
    referencia_ext  VARCHAR(200),   -- ID del proveedor (Stripe, MercadoPago, etc.)
    datos_extra     JSONB,          -- metadata del proveedor
    created_at      TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_pagos_pedido   ON pagos(pedido_id);
CREATE INDEX idx_pagos_usuario  ON pagos(usuario_id);
CREATE INDEX idx_pagos_estado   ON pagos(estado);
