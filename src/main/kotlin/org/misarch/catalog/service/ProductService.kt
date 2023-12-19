package org.misarch.catalog.service

import kotlinx.coroutines.reactor.awaitSingle
import org.misarch.catalog.graphql.input.CreateProductInput
import org.misarch.catalog.persistance.model.ProductEntity
import org.misarch.catalog.persistance.model.ProductToCategoryEntity
import org.misarch.catalog.persistance.repository.CategoryRepository
import org.misarch.catalog.persistance.repository.ProductRepository
import org.misarch.catalog.persistance.repository.ProductToCategoryRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductService(
    repository: ProductRepository,
    private val productVariantService: ProductVariantService,
    private val productToCategoryRepository: ProductToCategoryRepository,
    private val categoryRepository: CategoryRepository
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
        addCategories(savedProduct, input.categories.map { UUID.fromString(it.value) })
        return repository.save(savedProduct).awaitSingle()
    }

    private suspend fun addCategories(product: ProductEntity, categoryIds: List<UUID>) {
        checkCategoriesExist(categoryIds)
        productToCategoryRepository.saveAll(
            categoryIds.map { categoryId ->
                ProductToCategoryEntity(
                    productId = product.id!!,
                    categoryId = categoryId,
                    id = null
                )
            }
        ).collectList().awaitSingle()
    }

    private suspend fun checkCategoriesExist(categoryIds: List<UUID>) {
        categoryIds.forEach { categoryId ->
            if (!categoryRepository.existsById(categoryId).awaitSingle()) {
                throw IllegalArgumentException("Category with id $categoryId does not exist.")
            }
        }
    }

}