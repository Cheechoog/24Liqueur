-- V1__create_catalogo_tables.sql
CREATE TABLE IF NOT EXISTS categorias (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre      VARCHAR(100) NOT NULL UNIQUE,
    descripcion TEXT,
    activa      BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS productos (
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre        VARCHAR(200) NOT NULL,
    descripcion   TEXT,
    categoria_id  UUID REFERENCES categorias(id),
    marca         VARCHAR(100),
    precio        DECIMAL(10,2) NOT NULL,
    precio_oferta DECIMAL(10,2),
    graduacion    DECIMAL(4,1),        -- % alcohol
    volumen_ml    INT,                 -- 250, 500, 750, 1000
    imagen_url    VARCHAR(500),
    activo        BOOLEAN NOT NULL DEFAULT TRUE,
    created_at    TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMP NOT NULL DEFAULT NOW()
);

INSERT INTO categorias (nombre, descripcion) VALUES
  ('Cervezas', 'Cervezas nacionales e importadas'),
  ('Vinos', 'Vinos tintos, blancos y rosados'),
  ('Aguardiente', 'Aguardientes y anisados'),
  ('Whiskies', 'Whiskies nacionales e importados'),
  ('Ron', 'Rones y cocteles'),
  ('Vodka', 'Vodkas y mezclas'),
  ('Gin', 'Gins artesanales e importados'),
  ('Otros', 'Licores y bebidas especiales');

CREATE INDEX idx_productos_categoria ON productos(categoria_id);
CREATE INDEX idx_productos_activo ON productos(activo);
