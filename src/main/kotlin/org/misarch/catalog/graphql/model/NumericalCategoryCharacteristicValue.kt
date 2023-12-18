package org.misarch.catalog.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLDescription

@GraphQLDescription("A numerical value of a numerical category characteristic.")
class NumericalCategoryCharacteristicValue(
    value: Double
) : CategoryCharacteristicValue<Double>(value) {
}