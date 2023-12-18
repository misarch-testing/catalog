package org.misarch.catalog.graphql

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.server.operations.Query
import org.misarch.catalog.graphql.model.connection.ProductConnection
import org.misarch.catalog.graphql.model.connection.ProductOrder
import org.misarch.catalog.persistance.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.relational.core.mapping.NamingStrategy
import org.springframework.stereotype.Component

@Component
class Query(
    private val productRepository: ProductRepository
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

}