package org.misarch.catalog.graphql.model.connection

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.querydsl.core.types.Predicate
import org.misarch.catalog.graphql.model.ProductVariantVersion
import org.misarch.catalog.persistance.model.ProductVariantVersionEntity
import org.misarch.catalog.persistance.repository.ProductVariantVersionRepository

@GraphQLDescription("A connection to a list of `ProductVariantVersion` values.")
class ProductVariantVersionConnection(
    first: Int?,
    skip: Int?,
    predicate: Predicate?,
    order: ProductVariantVersionOrder?,
    repository: ProductVariantVersionRepository
) : BaseConnection<ProductVariantVersion, ProductVariantVersionEntity>(
    first,
    skip,
    predicate,
    (order ?: ProductVariantVersionOrder.DEFAULT).toSort(),
    repository
) {

    override fun toDto(value: ProductVariantVersionEntity): ProductVariantVersion {
        return value.toDTO()
    }
}

@GraphQLDescription("ProductVariantVersion order fields")
enum class ProductVariantVersionOrderField {
    @GraphQLDescription("Order productVariantVersions by their id")
    ID
}

@GraphQLDescription("ProductVariantVersion order")
class ProductVariantVersionOrder(direction: OrderDirection, field: ProductVariantVersionOrderField) : BaseOrder<ProductVariantVersionOrderField>(direction, field) {

    companion object {
        val DEFAULT = ProductVariantVersionOrder(OrderDirection.ASC, ProductVariantVersionOrderField.ID)
    }
}