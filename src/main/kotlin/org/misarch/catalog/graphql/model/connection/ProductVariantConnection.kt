package org.misarch.catalog.graphql.model.connection

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.querydsl.core.types.Predicate
import org.misarch.catalog.graphql.model.ProductVariant
import org.misarch.catalog.persistance.model.ProductVariantEntity
import org.misarch.catalog.persistance.repository.ProductVariantRepository

@GraphQLDescription("A connection to a list of `ProductVariant` values.")
class ProductVariantConnection(
    first: Int?,
    skip: Int?,
    predicate: Predicate?,
    order: ProductVariantOrder?,
    repository: ProductVariantRepository
) : BaseConnection<ProductVariant, ProductVariantEntity>(
    first,
    skip,
    predicate,
    (order ?: ProductVariantOrder.DEFAULT).toSort(),
    repository
) {

    override fun toDto(value: ProductVariantEntity): ProductVariant {
        return value.toDTO()
    }
}

@GraphQLDescription("ProductVariant order fields")
enum class ProductVariantOrderField {
    @GraphQLDescription("Order productVariants by their id")
    ID
}

@GraphQLDescription("ProductVariant order")
class ProductVariantOrder(direction: OrderDirection, field: ProductVariantOrderField) : BaseOrder<ProductVariantOrderField>(direction, field) {

    companion object {
        val DEFAULT = ProductVariantOrder(OrderDirection.ASC, ProductVariantOrderField.ID)
    }
}