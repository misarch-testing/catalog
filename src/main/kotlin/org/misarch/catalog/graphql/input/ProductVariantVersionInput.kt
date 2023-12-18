package org.misarch.catalog.graphql.input

import com.expediagroup.graphql.generator.annotations.GraphQLDescription

@GraphQLDescription("Input for creating a product variant version")
open class ProductVariantVersionInput(
    @GraphQLDescription("The name of the product variant.")
    val name: String,
    @GraphQLDescription("The description of the product variant.")
    val description: String,
    @GraphQLDescription("The retail price of the product variant.")
    val retailPrice: Int,
    @GraphQLDescription("The amount of days for which an instance of the product variant can be returned after purchase")
    val canBeReturnedForDays: Double? = null
)