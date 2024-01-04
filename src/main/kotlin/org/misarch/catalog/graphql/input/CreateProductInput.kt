package org.misarch.catalog.graphql.input

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.scalars.ID

@GraphQLDescription("Input for the createProduct mutation")
class CreateProductInput(
    @GraphQLDescription("An internal name to identify the Product, not visible to customers.")
    val internalName: String,
    @GraphQLDescription("If true, the Product is visible to customers.")
    val isPubliclyVisible: Boolean,
    @GraphQLDescription("The default ProductVariant of the Product.")
    val defaultVariant: ProductVariantInput,
    @GraphQLDescription("The Categories this product is associated with.")
    val categoryIds: List<ID>,
)