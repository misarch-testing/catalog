package org.misarch.catalog.graphql

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.scalars.ID
import com.expediagroup.graphql.server.operations.Query
import kotlinx.coroutines.reactor.awaitSingle
import org.misarch.catalog.graphql.model.Category
import org.misarch.catalog.graphql.model.Product
import org.misarch.catalog.graphql.model.connection.CategoryConnection
import org.misarch.catalog.graphql.model.connection.CategoryOrder
import org.misarch.catalog.graphql.model.connection.ProductConnection
import org.misarch.catalog.graphql.model.connection.ProductOrder
import org.misarch.catalog.persistence.repository.CategoryRepository
import org.misarch.catalog.persistence.repository.ProductRepository
import org.misarch.catalog.util.uuid
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

    @GraphQLDescription("Get a product by id")
    suspend fun product(
        @GraphQLDescription("The id of the product")
        id: ID
    ): Product {
        return productRepository.findById(id.uuid).awaitSingle().toDTO()
    }

    @GraphQLDescription("Get a category by id")
    suspend fun category(
        @GraphQLDescription("The id of the category")
        id: ID
    ): Category {
        return categoryRepository.findById(id.uuid).awaitSingle().toDTO()
    }

}