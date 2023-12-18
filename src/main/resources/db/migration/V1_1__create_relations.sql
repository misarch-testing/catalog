CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE CategoryCharacteristicEntity (
    id UUID PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    discriminator VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    unit VARCHAR(255),
    UNIQUE (id)
);

CREATE TABLE CategoryEntity (
    id UUID PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    UNIQUE (id)
);

CREATE TABLE ProductEntity (
    id UUID PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    internalName VARCHAR(255) NOT NULL,
    isPubliclyVisible BOOLEAN NOT NULL,
    defaultVariantId UUID NULL,
    UNIQUE (id)
);

CREATE TABLE ProductVariantEntity (
    id UUID PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    isPubliclyVisible BOOLEAN NOT NULL,
    productId UUID,
    currentVersion UUID NULL,
    UNIQUE (id)
);

CREATE TABLE ProductVariantVersionEntity (
    id UUID PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    version INT NOT NULL,
    retailPrice INT NOT NULL,
    createdAt TIMESTAMPTZ NOT NULL,
    canBeReturnedForDays DOUBLE PRECISION,
    productVariantId UUID,
    UNIQUE (id)
);

CREATE TABLE CategoryCharacteristicValueEntity (
    id UUID PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    discriminator VARCHAR(255) NOT NULL,
    stringValue VARCHAR(255),
    doubleValue DOUBLE PRECISION,
    categoryCharacteristicId UUID,
    productVariantVersionId UUID,
    UNIQUE (id)
);

ALTER TABLE CategoryCharacteristicValueEntity
ADD CONSTRAINT fk_category_characteristic_value_category_characteristic
FOREIGN KEY (categoryCharacteristicId) REFERENCES CategoryCharacteristicEntity(id);

ALTER TABLE CategoryCharacteristicValueEntity
ADD CONSTRAINT fk_category_characteristic_value_product_variant_version
FOREIGN KEY (productVariantVersionId) REFERENCES ProductVariantVersionEntity(id);

ALTER TABLE ProductEntity
ADD CONSTRAINT fk_product_default_variant
FOREIGN KEY (defaultVariantId) REFERENCES ProductVariantEntity(id);

ALTER TABLE ProductVariantEntity
ADD CONSTRAINT fk_product_variant_product
FOREIGN KEY (productId) REFERENCES ProductEntity(id);

ALTER TABLE ProductVariantEntity
ADD CONSTRAINT fk_product_variant_version
FOREIGN KEY (currentVersion) REFERENCES ProductVariantVersionEntity(id);

ALTER TABLE ProductVariantVersionEntity
ADD CONSTRAINT fk_product_variant_version_product_variant
FOREIGN KEY (productVariantId) REFERENCES ProductVariantEntity(id);
