package org.misarch.catalog.service

import kotlinx.coroutines.reactor.awaitSingle
import org.misarch.catalog.graphql.input.CreateProductInput
import org.misarch.catalog.persistence.model.CategoryEntity
import org.misarch.catalog.persistence.model.ProductEntity
import org.misarch.catalog.persistence.model.ProductToCategoryEntity
import org.misarch.catalog.persistence.model.ProductVariantEntity
import org.misarch.catalog.persistence.repository.CategoryRepository
import org.misarch.catalog.persistence.repository.ProductRepository
import org.misarch.catalog.persistence.repository.ProductToCategoryRepository
import org.misarch.catalog.util.uuid
import org.springframework.stereotype.Service
import java.util.*

/**
 * Service for [ProductEntity]s
 *
 * @param repository the provided repository
 * @property productVariantService service for [ProductVariantEntity]s
 * @property productToCategoryRepository repository for [ProductToCategoryEntity]s
 * @property categoryRepository repository for [CategoryEntity]s
 */
@Service
class ProductService(
    repository: ProductRepository,
    private val productVariantService: ProductVariantService,
    private val productToCategoryRepository: ProductToCategoryRepository,
    private val categoryRepository: CategoryRepository
) : BaseService<ProductEntity, ProductRepository>(repository) {

    /**
     * Creates a product, also creates the default variant (and their versions) and adds the categories
     *
     * @param input defines the product (and default variant) to be created
     * @return the created product
     */
    suspend fun createProduct(input: CreateProductInput): ProductEntity {
        val product = ProductEntity(
            internalName = input.internalName,
            isPubliclyVisible = input.isPubliclyVisible,
            defaultVariantId = null,
            id = null
        )
        val savedProduct = repository.save(product).awaitSingle()
        addCategories(savedProduct, input.categoryIds.map { it.uuid })
        val productVariant = productVariantService.createProductVariantInternal(input.defaultVariant, savedProduct.id!!)
        savedProduct.defaultVariantId = productVariant.id
        return repository.save(savedProduct).awaitSingle()
    }

    /**
     * Adds categories to a product
     *
     * @param product the product where the categories should be added
     * @param categoryIds the ids of the categories
     */
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

    /**
     * Checks if the categories exist
     *
     * @param categoryIds the ids of the categories
     * @throws IllegalArgumentException if a category does not exist
     */
    private suspend fun checkCategoriesExist(categoryIds: List<UUID>) {
        categoryIds.forEach { categoryId ->
            if (!categoryRepository.existsById(categoryId).awaitSingle()) {
                throw IllegalArgumentException("Category with id $categoryId does not exist.")
            }
        }
    }

}