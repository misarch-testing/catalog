package org.misarch.catalog.service

import kotlinx.coroutines.reactor.awaitSingle
import org.misarch.catalog.graphql.input.CreateProductInput
import org.misarch.catalog.persistance.model.ProductEntity
import org.misarch.catalog.persistance.repository.ProductRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductService(
    repository: ProductRepository, private val productVariantService: ProductVariantService
) : BaseService<ProductEntity, ProductRepository>(repository) {

    suspend fun createProduct(input: CreateProductInput): ProductEntity {
        val product = ProductEntity(
            internalName = input.internalName,
            isPubliclyVisible = input.isPubliclyVisible,
            defaultVariantId = null,
            id = null
        )
        val savedProduct = repository.save(product).awaitSingle()
        val productVariant = productVariantService.createProductVariantInternal(input.defaultVariant, savedProduct.id!!)
        savedProduct.defaultVariantId = productVariant.id
        return repository.save(savedProduct).awaitSingle()
    }

}