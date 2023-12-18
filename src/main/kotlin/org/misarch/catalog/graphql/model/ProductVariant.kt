package org.misarch.catalog.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import kotlinx.coroutines.reactor.awaitSingle
import org.misarch.catalog.graphql.model.connection.ProductVariantVersionConnection
import org.misarch.catalog.graphql.model.connection.ProductVariantVersionOrder
import org.misarch.catalog.persistance.model.ProductVariantVersionEntity
import org.misarch.catalog.persistance.repository.ProductRepository
import org.misarch.catalog.persistance.repository.ProductVariantVersionRepository
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@GraphQLDescription("A variant of a product.")
class ProductVariant(
    id: UUID,
    @GraphQLDescription("If true, the variant is visible to customers.")
    val isPubliclyVisible: Boolean, private val productId: UUID, private val currentVersion: UUID
) : Node(id) {

    @GraphQLDescription("The product this is a variant of.")
    suspend fun product(
        @Autowired
        @GraphQLIgnore
        productRepository: ProductRepository
    ): Product {
        return productRepository.findById(productId).awaitSingle().toDTO()
    }

    @GraphQLDescription("The current version of the product variant.")
    suspend fun currentVersion(
        @Autowired
        @GraphQLIgnore
        productVariantVersionRepository: ProductVariantVersionRepository
    ): ProductVariantVersion {
        return productVariantVersionRepository.findById(currentVersion).awaitSingle().toDTO()
    }

    @GraphQLDescription("Get all associated versions")
    suspend fun versions(
        @GraphQLDescription("Number of items to return")
        first: Int? = null,
        @GraphQLDescription("Number of items to skip")
        skip: Int? = null,
        @GraphQLDescription("Ordering")
        orderBy: ProductVariantVersionOrder? = null,
        @GraphQLIgnore
        @Autowired
        productVariantVersionRepository: ProductVariantVersionRepository
    ): ProductVariantVersionConnection {
        return ProductVariantVersionConnection(
            first,
            skip,
            ProductVariantVersionEntity.ENTITY.productVariantId.eq(id),
            orderBy,
            productVariantVersionRepository
        )
    }

}