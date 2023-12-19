package org.misarch.catalog.service

import kotlinx.coroutines.reactor.awaitSingle
import org.misarch.catalog.graphql.input.CreateProductVariantInput
import org.misarch.catalog.graphql.input.ProductVariantInput
import org.misarch.catalog.persistance.model.ProductEntity
import org.misarch.catalog.persistance.model.ProductVariantEntity
import org.misarch.catalog.persistance.model.ProductVariantVersionEntity
import org.misarch.catalog.persistance.repository.ProductRepository
import org.misarch.catalog.persistance.repository.ProductVariantRepository
import org.springframework.stereotype.Service
import java.util.*

/**
 * Service for [ProductVariantEntity]s
 *
 * @param repository the provided repository
 * @property productVariantVersionService service for [ProductVariantVersionEntity]s
 * @property productRepository repository for [ProductEntity]s

 */
@Service
class ProductVariantService(
    repository: ProductVariantRepository,
    private val productVariantVersionService: ProductVariantVersionService,
    private val productRepository: ProductRepository
) : BaseService<ProductVariantEntity, ProductVariantRepository>(repository) {

    /**
     * Creates a product variant, also creates the initial version
     * Also checks if the product exists
     *
     * @param input defines the product variant (and initial version) to be created
     * @return the created product variant
     */
    suspend fun createProductVariant(input: CreateProductVariantInput): ProductVariantEntity {
        if (!productRepository.existsById(UUID.fromString(input.productId.value)).awaitSingle()) {
            throw IllegalArgumentException("Product with id ${input.productId} does not exist.")
        }
        val productVariant = createProductVariantInternal(input, UUID.fromString(input.productId.value))
        return productVariant
    }

    /**
     * Creates a product variant without checking if the product exists
     *
     * @param input defines the product variant (and initial version) to be created
     * @param productId the id of the product
     * @return the created product variant
     */
    suspend fun createProductVariantInternal(input: ProductVariantInput, productId: UUID): ProductVariantEntity {
        val productVariant = ProductVariantEntity(
            isPubliclyVisible = input.isPubliclyVisible,
            productId = productId,
            currentVersion = null,
            id = null
        )
        val savedProductVariant = repository.save(productVariant).awaitSingle()
        val initialVersion = productVariantVersionService.createProductVariantVersionInternal(
            input.initialVersion,
            savedProductVariant.id!!
        )
        savedProductVariant.currentVersion = initialVersion.id!!
        return repository.save(savedProductVariant).awaitSingle()
    }

}