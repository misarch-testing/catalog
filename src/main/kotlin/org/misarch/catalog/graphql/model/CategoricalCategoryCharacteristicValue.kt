package org.misarch.catalog.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import java.util.*

@GraphQLDescription("A possible value for a categorical characteristic.")
class CategoricalCategoryCharacteristicValue(
    characteristicId: UUID,
    @GraphQLDescription("The value of the characteristic.")
    val value: String,
) : CategoryCharacteristicValue(characteristicId)