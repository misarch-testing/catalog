package org.misarch.catalog.graphql

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import org.misarch.catalog.graphql.model.connection.CategoryConnection
import org.misarch.catalog.graphql.model.connection.CategoryOrder
import org.misarch.catalog.graphql.model.connection.ProductConnection
import org.misarch.catalog.graphql.model.connection.ProductOrder
import org.misarch.catalog.persistance.repository.CategoryRepository
import org.misarch.catalog.persistance.repository.ProductRepository
import org.springframework.stereotype.Component

/**
 * Defines GraphQL queries
 *
 * @property productRepository repository for products
 * @property categoryRepository repository for categories
 */
@Component
class Query(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository
) : Query {

    @GraphQLDescription("Get all products")
    suspend fun products(
        @GraphQLDescription("Number of items to return")
        first: Int? = null,
        @GraphQLDescription("Number of items to skip")
        skip: Int? = null,
        @GraphQLDescription("Ordering")
        orderBy: ProductOrder? = null
    ): ProductConnection {
        return ProductConnection(first, skip, null, orderBy, productRepository)
    }

    @GraphQLDescription("Get all categories")
    suspend fun categories(
        @GraphQLDescription("Number of items to return")
        first: Int? = null,
        @GraphQLDescription("Number of items to skip")
        skip: Int? = null,
        @GraphQLDescription("Ordering")
        orderBy: CategoryOrder? = null
    ): CategoryConnection {
        return CategoryConnection(first, skip, null, orderBy, categoryRepository)
    }

}