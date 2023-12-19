CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE CategoryEntity (
    id UUID PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE CategoryCharacteristicEntity (
    id UUID PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    discriminator VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    unit VARCHAR(255),
    categoryId UUID,
    FOREIGN KEY (categoryId) REFERENCES CategoryEntity(id)
);

CREATE TABLE ProductEntity (
    id UUID PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    internalName VARCHAR(255) NOT NULL,
    isPubliclyVisible BOOLEAN NOT NULL,
    defaultVariantId UUID NULL
);

CREATE TABLE ProductVariantEntity (
    id UUID PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    isPubliclyVisible BOOLEAN NOT NULL,
    productId UUID,
    currentVersion UUID NULL,
    FOREIGN KEY (productId) REFERENCES ProductEntity(id)
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
    FOREIGN KEY (productVariantId) REFERENCES ProductVariantEntity(id)
);

CREATE TABLE CategoryCharacteristicValueEntity (
    id UUID PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    discriminator VARCHAR(255) NOT NULL,
    stringValue VARCHAR(255),
    doubleValue DOUBLE PRECISION,
    categoryCharacteristicId UUID,
    productVariantVersionId UUID,
    FOREIGN KEY (categoryCharacteristicId) REFERENCES CategoryCharacteristicEntity(id),
    FOREIGN KEY (productVariantVersionId) REFERENCES ProductVariantVersionEntity(id)
);

CREATE TABLE ProductToCategoryEntity (
    id UUID PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    productId UUID,
    categoryId UUID,
    FOREIGN KEY (productId) REFERENCES ProductEntity(id),
    FOREIGN KEY (categoryId) REFERENCES CategoryEntity(id),
    UNIQUE (productId, categoryId)
);

ALTER TABLE ProductEntity
ADD CONSTRAINT fk_product_default_variant
FOREIGN KEY (defaultVariantId) REFERENCES ProductVariantEntity(id);

ALTER TABLE ProductVariantEntity
ADD CONSTRAINT fk_product_variant_version
FOREIGN KEY (currentVersion) REFERENCES ProductVariantVersionEntity(id);