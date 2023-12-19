package org.misarch.catalog.graphql

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Mutation
import org.misarch.catalog.graphql.input.*
import org.misarch.catalog.graphql.model.*
import org.misarch.catalog.service.*
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional(propagation = Propagation.REQUIRES_NEW)
class Mutation(
    private val productService: ProductService,
    private val productVariantService: ProductVariantService,
    private val productVariantVersionService: ProductVariantVersionService,
    private val categoryService: CategoryService,
    private val categoryCharacteristicService: CategoryCharacteristicService
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

    @GraphQLDescription("Create a new category")
    suspend fun createCategory(
        @GraphQLDescription("Input for the createCategory mutation")
        input: CreateCategoryInput
    ): Category {
        val category = categoryService.createCategory(input)
        return category.toDTO()
    }

    @GraphQLDescription("Create a new categorical category characteristic")
    suspend fun createCategoricalCategoryCharacteristic(
        @GraphQLDescription("Input for the createCategoricalCategoryCharacteristic mutation")
        input: CreateCategoricalCategoryCharacteristicInput
    ): CategoricalCategoryCharacteristic {
        val categoricalCategoryCharacteristic =
            categoryCharacteristicService.createCategoricalCategoryCharacteristic(input)
        return categoricalCategoryCharacteristic.toDTO() as CategoricalCategoryCharacteristic
    }

    @GraphQLDescription("Create a new numerical category characteristic")
    suspend fun createNumericalCategoryCharacteristic(
        @GraphQLDescription("Input for the createNumericalCategoryCharacteristic mutation")
        input: CreateNumericalCategoryCharacteristicInput
    ): NumericalCategoryCharacteristic {
        val numericalCategoryCharacteristic = categoryCharacteristicService.createNumericalCategoryCharacteristic(input)
        return numericalCategoryCharacteristic.toDTO() as NumericalCategoryCharacteristic
    }

}