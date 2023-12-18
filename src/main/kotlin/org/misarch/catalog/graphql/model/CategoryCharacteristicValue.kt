package org.misarch.catalog.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLIgnore

@GraphQLIgnore
abstract class CategoryCharacteristicValue<T>(
    @GraphQLDescription("The value of the characteristic.")
    val value: T
) {
}