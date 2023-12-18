package org.misarch.catalog.graphql

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Mutation
import org.misarch.catalog.graphql.input.CreateProductInput
import org.misarch.catalog.graphql.input.CreateProductVariantInput
import org.misarch.catalog.graphql.input.CreateProductVariantVersionInput
import org.misarch.catalog.graphql.model.Product
import org.misarch.catalog.graphql.model.ProductVariant
import org.misarch.catalog.graphql.model.ProductVariantVersion
import org.misarch.catalog.service.ProductService
import org.misarch.catalog.service.ProductVariantService
import org.misarch.catalog.service.ProductVariantVersionService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
class Mutation(
    private val productService: ProductService,
    private val productVariantService: ProductVariantService,
    private val productVariantVersionService: ProductVariantVersionService
) : Mutation {

    @GraphQLDescription("Create a new product")
    suspend fun createProduct(
        @GraphQLDescription("Input for the createProduct mutation")
        input: CreateProductInput
    ): Product {
        val product = productService.createProduct(input)
        return product.toDTO()
    }

    @GraphQLDescription("Create a new product variant")
    suspend fun createProductVariant(
        @GraphQLDescription("Input for the createProductVariant mutation")
        input: CreateProductVariantInput
    ): ProductVariant {
        val productVariant = productVariantService.createProductVariant(input)
        return productVariant.toDTO()
    }

    @GraphQLDescription("Create a new product variant version")
    suspend fun createProductVariantVersion(
        @GraphQLDescription("Input for the createProductVariantVersion mutation")
        input: CreateProductVariantVersionInput
    ): ProductVariantVersion {
        val product = productVariantVersionService.createProductVariantVersion(input)
        return product.toDTO()
    }

}