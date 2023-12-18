package org.misarch.catalog.graphql.input

import com.expediagroup.graphql.generator.annotations.GraphQLDescription

@GraphQLDescription("Input for creating a product variant")
open class ProductVariantInput(
    @GraphQLDescription("If true, the variant is visible to customers.")
    val isPubliclyVisible: Boolean,
    @GraphQLDescription("The initial version of the product variant.")
    val initialVersion: ProductVariantVersionInput
)