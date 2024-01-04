package org.misarch.catalog.graphql.input

import com.expediagroup.graphql.generator.annotations.GraphQLDescription

@GraphQLDescription("Input for creating a product variant")
open class ProductVariantInput(
    @GraphQLDescription("If true, the ProductVariant is visible to customers.")
    val isPubliclyVisible: Boolean,
    @GraphQLDescription("The initial ProductVariantVersion of the ProductVariant.")
    val initialVersion: ProductVariantVersionInput
)