package org.misarch.catalog.graphql.input

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.scalars.ID

@GraphQLDescription("Input for the createProductVariantVersion mutation")
class CreateProductVariantVersionInput(
    val productVariantId: ID,
    name: String,
    description: String,
    retailPrice: Int,
    canBeReturnedForDays: Double? = null,
    categoricalCharacteristicValues: List<CategoricalCategoryCharacteristicValueInput>,
    numericalCharacteristicValues: List<NumericalCategoryCharacteristicValueInput>
) : ProductVariantVersionInput(
    name,
    description,
    retailPrice,
    canBeReturnedForDays,
    categoricalCharacteristicValues,
    numericalCharacteristicValues
)