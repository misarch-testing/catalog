package org.misarch.catalog.service

import kotlinx.coroutines.reactor.awaitSingle
import org.misarch.catalog.graphql.input.CreateProductVariantInput
import org.misarch.catalog.graphql.input.ProductVariantInput
import org.misarch.catalog.persistance.model.ProductVariantEntity
import org.misarch.catalog.persistance.repository.ProductRepository
import org.misarch.catalog.persistance.repository.ProductVariantRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductVariantService(
    repository: ProductVariantRepository,
    private val productVariantVersionService: ProductVariantVersionService,
    private val productRepository: ProductRepository
) : BaseService<ProductVariantEntity, ProductVariantRepository>(repository) {

    suspend fun createProductVariant(input: CreateProductVariantInput): ProductVariantEntity {
        if (!productRepository.existsById(UUID.fromString(input.productId.value)).awaitSingle()) {
            throw IllegalArgumentException("Product with id ${input.productId} does not exist.")
        }
        val productVariant = createProductVariantInternal(input, UUID.fromString(input.productId.value))
        return productVariant
    }

    suspend fun createProductVariantInternal(input: ProductVariantInput, productId: UUID): ProductVariantEntity {
        val productVariant = ProductVariantEntity(
            isPubliclyVisible = input.isPubliclyVisible,
            productId = productId,
            currentVersion = null,
            id = null
        )
        val savedProductVariant = repository.save(productVariant).awaitSingle()
        val initialVersion = productVariantVersionService.createProductVariantVersionInternal(input.initialVersion, savedProductVariant.id!!)
        savedProductVariant.currentVersion = initialVersion.id!!
        return repository.save(savedProductVariant).awaitSingle()
    }

}