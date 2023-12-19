package org.misarch.catalog.service

import kotlinx.coroutines.reactor.awaitSingle
import org.misarch.catalog.graphql.input.CreateProductVariantVersionInput
import org.misarch.catalog.graphql.input.ProductVariantVersionInput
import org.misarch.catalog.persistance.model.CategoryCharacteristicValueEntity
import org.misarch.catalog.persistance.model.ProductVariantEntity
import org.misarch.catalog.persistance.model.ProductVariantVersionEntity
import org.misarch.catalog.persistance.repository.ProductVariantRepository
import org.misarch.catalog.persistance.repository.ProductVariantVersionRepository
import org.misarch.catalog.util.uuid
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.*

/**
 * Service for [ProductVariantVersionEntity]s
 *
 * @param repository the provided repository
 * @property productVariantRepository repository for [ProductVariantEntity]s
 * @property categoryCharacteristicValueService service for [CategoryCharacteristicValueEntity]s
 */
@Service
class ProductVariantVersionService(
    repository: ProductVariantVersionRepository,
    private val productVariantRepository: ProductVariantRepository,
    private val categoryCharacteristicValueService: CategoryCharacteristicValueService
) : BaseService<ProductVariantVersionEntity, ProductVariantVersionRepository>(repository) {

    /**
     * Creates a product variant version
     * Also checks if the product variant exists
     *
     * @param input defines the product variant version to be created
     */
    suspend fun createProductVariantVersion(
        input: CreateProductVariantVersionInput
    ): ProductVariantVersionEntity {
        if (!productVariantRepository.existsById(input.productVariantId.uuid).awaitSingle()) {
            throw IllegalArgumentException("Product variant with id ${input.productVariantId} does not exist.")
        }
        val productVariantVersion = createProductVariantVersionInternal(input, input.productVariantId.uuid)
        return productVariantVersion
    }

    /**
     * Creates a product variant version without checking if the product variant exists
     *
     * @param input defines the product variant version to be created
     * @param productVariantId the id of the product variant
     * @return the created product variant version
     */
    suspend fun createProductVariantVersionInternal(
        input: ProductVariantVersionInput, productVariantId: UUID
    ): ProductVariantVersionEntity {
        val version = repository.findMaxVersionByProductVariantId(productVariantId)?.plus(1) ?: 1
        val productVariantVersion = ProductVariantVersionEntity(
            name = input.name,
            description = input.description,
            version = version,
            retailPrice = input.retailPrice,
            createdAt = OffsetDateTime.now(),
            canBeReturnedForDays = input.canBeReturnedForDays,
            productVariantId = productVariantId,
            id = null
        )
        val savedProductVariantVersion = repository.save(productVariantVersion).awaitSingle()
        categoryCharacteristicValueService.upsertCategoryCharacteristicValues(
            input.categoricalCharacteristicValues,
            input.numericalCharacteristicValues,
            savedProductVariantVersion.id!!
        )
        return savedProductVariantVersion
    }

}