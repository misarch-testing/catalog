package org.misarch.catalog.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLDescription

@GraphQLDescription("A categorical value of a categorical category characteristic.")
class CategoricalCategoryCharacteristicValue(value: String) : CategoryCharacteristicValue<String>(value) {
}