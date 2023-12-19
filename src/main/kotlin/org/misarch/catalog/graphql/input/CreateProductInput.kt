package org.misarch.catalog.graphql.input

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.scalars.ID

@GraphQLDescription("Input for the createProduct mutation")
class CreateProductInput(
    @GraphQLDescription("An internal name to identify the product, not visible to customers.")
    val internalName: String,
    @GraphQLDescription("If true, the product is visible to customers.")
    val isPubliclyVisible: Boolean,
    @GraphQLDescription("The default variant of the product.")
    val defaultVariant: ProductVariantInput,
    @GraphQLDescription("The categories this product is associated with.")
    val categories: List<ID>,
)