package org.misarch.catalog.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import org.misarch.catalog.graphql.model.connection.CategoryCharacteristicConnection
import org.misarch.catalog.graphql.model.connection.CategoryCharacteristicOrder
import org.misarch.catalog.graphql.model.connection.ProductConnection
import org.misarch.catalog.graphql.model.connection.ProductOrder
import org.misarch.catalog.persistence.model.CategoryCharacteristicEntity
import org.misarch.catalog.persistence.model.ProductEntity
import org.misarch.catalog.persistence.model.ProductToCategoryEntity
import org.misarch.catalog.persistence.repository.CategoryCharacteristicRepository
import org.misarch.catalog.persistence.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@GraphQLDescription("A category")
class Category(
    id: UUID,
    @GraphQLDescription("The name of the category.")
    val name: String,
    @GraphQLDescription("The description of the category.")
    val description: String,
) : Node(id) {

    @GraphQLDescription("Get all associated products")
    suspend fun products(
        @GraphQLDescription("Number of items to return")
        first: Int? = null,
        @GraphQLDescription("Number of items to skip")
        skip: Int? = null,
        @GraphQLDescription("Ordering")
        orderBy: ProductOrder? = null,
        @GraphQLIgnore
        @Autowired
        productRepository: ProductRepository
    ): ProductConnection {
        return ProductConnection(
            first, skip, ProductToCategoryEntity.ENTITY.categoryId.eq(id), orderBy, productRepository
        ) {
            it.innerJoin(ProductToCategoryEntity.ENTITY)
                .on(ProductToCategoryEntity.ENTITY.productId.eq(ProductEntity.ENTITY.id))
        }
    }

    @GraphQLDescription("Get characteristics for the category")
    suspend fun characteristics(
        @GraphQLDescription("Number of items to return")
        first: Int? = null,
        @GraphQLDescription("Number of items to skip")
        skip: Int? = null,
        @GraphQLDescription("Ordering")
        orderBy: CategoryCharacteristicOrder? = null,
        @GraphQLIgnore
        @Autowired
        categoryCharacteristicsRepository: CategoryCharacteristicRepository
    ): CategoryCharacteristicConnection {
        return CategoryCharacteristicConnection(
            first,
            skip,
            CategoryCharacteristicEntity.ENTITY.categoryId.eq(id),
            orderBy,
            categoryCharacteristicsRepository
        )
    }

}