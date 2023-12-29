package org.misarch.catalog.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import kotlinx.coroutines.reactor.awaitSingle
import org.misarch.catalog.graphql.model.connection.CategoryConnection
import org.misarch.catalog.graphql.model.connection.CategoryOrder
import org.misarch.catalog.graphql.model.connection.ProductVariantConnection
import org.misarch.catalog.graphql.model.connection.ProductVariantOrder
import org.misarch.catalog.persistence.model.CategoryEntity
import org.misarch.catalog.persistence.model.ProductToCategoryEntity
import org.misarch.catalog.persistence.model.ProductVariantEntity
import org.misarch.catalog.persistence.repository.CategoryRepository
import org.misarch.catalog.persistence.repository.ProductVariantRepository
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@GraphQLDescription("A product.")
class Product(
    id: UUID,
    @GraphQLDescription("An internal name to identify the Product, not visible to customers.")
    val internalName: String,
    @GraphQLDescription("If true, the Product is visible to customers.")
    val isPubliclyVisible: Boolean, private val defaultVariantId: UUID
) : Node(id) {

    @GraphQLDescription("The default variant of the product.")
    suspend fun defaultVariant(
        @Autowired
        @GraphQLIgnore
        productVariantRepository: ProductVariantRepository
    ): ProductVariant {
        return productVariantRepository.findById(defaultVariantId).awaitSingle().toDTO()
    }

    @GraphQLDescription("Get all associated ProductVariants")
    suspend fun variants(
        @GraphQLDescription("Number of items to return")
        first: Int? = null,
        @GraphQLDescription("Number of items to skip")
        skip: Int? = null,
        @GraphQLDescription("Ordering")
        orderBy: ProductVariantOrder? = null,
        @GraphQLIgnore
        @Autowired
        productVariantRepository: ProductVariantRepository
    ): ProductVariantConnection {
        return ProductVariantConnection(
            first, skip, ProductVariantEntity.ENTITY.productId.eq(id), orderBy, productVariantRepository
        )
    }

    @GraphQLDescription("Get all associated Categories")
    suspend fun categories(
        @GraphQLDescription("Number of items to return")
        first: Int? = null,
        @GraphQLDescription("Number of items to skip")
        skip: Int? = null,
        @GraphQLDescription("Ordering")
        orderBy: CategoryOrder? = null,
        @GraphQLIgnore
        @Autowired
        categoryRepository: CategoryRepository
    ): CategoryConnection {
        return CategoryConnection(
            first, skip, ProductToCategoryEntity.ENTITY.productId.eq(id), orderBy, categoryRepository
        ) {
            it.innerJoin(ProductToCategoryEntity.ENTITY)
                .on(ProductToCategoryEntity.ENTITY.categoryId.eq(CategoryEntity.ENTITY.id))
        }
    }

}