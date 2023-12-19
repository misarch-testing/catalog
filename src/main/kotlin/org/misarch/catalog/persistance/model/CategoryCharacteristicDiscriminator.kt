package org.misarch.catalog.persistance.model

/**
 * Discriminator to distinguish between categorical and numerical characteristics
 */
enum class CategoryCharacteristicDiscriminator {
    /**
     * Categorical characteristic
     */
    CATEGORICAL,

    /**
     * Numerical characteristic
     */
    NUMERICAL
}