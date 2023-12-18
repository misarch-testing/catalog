package org.misarch.catalog.service

import kotlinx.coroutines.reactor.awaitSingle
import org.misarch.catalog.graphql.input.CreateProductVariantVersionInput
import org.misarch.catalog.graphql.input.ProductVariantVersionInput
import org.misarch.catalog.persistance.model.ProductVariantVersionEntity
import org.misarch.catalog.persistance.repository.ProductVariantRepository
import org.misarch.catalog.persistance.repository.ProductVariantVersionRepository
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.UUID

@Service
class ProductVariantVersionService(
    repository: ProductVariantVersionRepository,
    private val productVariantRepository: ProductVariantRepository
): BaseService<ProductVariantVersionEntity, ProductVariantVersionRepository>(repository) {

    suspend fun createProductVariantVersion(
        input: CreateProductVariantVersionInput
    ): ProductVariantVersionEntity {
        if (!productVariantRepository.existsById(UUID.fromString(input.productVariantId.value)).awaitSingle()) {
            throw IllegalArgumentException("Product variant with id ${input.productVariantId} does not exist.")
        }
        val productVariantVersion = createProductVariantVersionInternal(input, UUID.fromString(input.productVariantId.value))
        return productVariantVersion
    }

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
        return repository.save(productVariantVersion).awaitSingle()
    }

}