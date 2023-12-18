package org.misarch.catalog.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.scalars.ID
import kotlinx.coroutines.reactor.awaitSingle
import org.misarch.catalog.persistance.repository.ProductVariantRepository
import org.springframework.beans.factory.annotation.Autowired
import java.time.OffsetDateTime
import java.util.UUID

@GraphQLDescription("A version of a product variant.")
class ProductVariantVersion(
    id: UUID,
    @GraphQLDescription("The name of the product variant.")
    val name: String,
    @GraphQLDescription("The description of the product variant.")
    val description: String,
    @GraphQLDescription("The version of the product variant.")
    val version: Int,
    @GraphQLDescription("The retail price of the product variant.")
    val retailPrice: Int,
    @GraphQLDescription("The date when the product variant version was created.")
    val createdAt: OffsetDateTime,
    @GraphQLDescription("The amount of days for which an instance of the product variant can be returned after purchase")
    val canBeReturnedForDays: Double?,
    private val productVariantId: UUID
) : Node(id) {

    @GraphQLDescription("The product variant this is a version of.")
    suspend fun productVariant(
        @Autowired
        @GraphQLIgnore
        productVariantRepository: ProductVariantRepository
    ): ProductVariant {
        return productVariantRepository.findById(productVariantId).awaitSingle().toDTO()
    }

}