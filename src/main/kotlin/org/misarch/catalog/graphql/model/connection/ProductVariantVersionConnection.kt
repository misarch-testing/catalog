package org.misarch.catalog.graphql.model.connection

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.dsl.ComparableExpression
import com.querydsl.sql.SQLQuery
import org.misarch.catalog.graphql.model.ProductVariantVersion
import org.misarch.catalog.graphql.model.connection.base.BaseConnection
import org.misarch.catalog.graphql.model.connection.base.BaseOrder
import org.misarch.catalog.graphql.model.connection.base.BaseOrderField
import org.misarch.catalog.graphql.model.connection.base.OrderDirection
import org.misarch.catalog.persistence.model.ProductVariantVersionEntity
import org.misarch.catalog.persistence.repository.ProductVariantVersionRepository

/**
 * A GraphQL connection for [ProductVariantVersion]s.
 *
 * @param first The maximum number of items to return
 * @param skip The number of items to skip
 * @param predicate The predicate to filter the items by
 * @param order The order to sort the items by
 * @param repository The repository to fetch the items from
 * @param applyJoin A function to apply a join to the query
 */
@GraphQLDescription("A connection to a list of `ProductVariantVersion` values.")
class ProductVariantVersionConnection(
    first: Int?,
    skip: Int?,
    predicate: Predicate?,
    order: ProductVariantVersionOrder?,
    repository: ProductVariantVersionRepository,
    applyJoin: (query: SQLQuery<*>) -> SQLQuery<*> = { it }
) : BaseConnection<ProductVariantVersion, ProductVariantVersionEntity>(
    first,
    skip,
    predicate,
    (order ?: ProductVariantVersionOrder.DEFAULT).toOrderSpecifier(),
    repository,
    ProductVariantVersionEntity.ENTITY,
    applyJoin
) {

    override val primaryKey: ComparableExpression<*> get() = ProductVariantVersionEntity.ENTITY.id

    override fun toDto(value: ProductVariantVersionEntity): ProductVariantVersion {
        return value.toDTO()
    }
}

@GraphQLDescription("ProductVariantVersion order fields")
enum class ProductVariantVersionOrderField(override vararg val expressions: ComparableExpression<*>) : BaseOrderField {
    @GraphQLDescription("Order productVariantVersions by their id")
    ID(ProductVariantVersionEntity.ENTITY.id)
}

@GraphQLDescription("ProductVariantVersion order")
class ProductVariantVersionOrder(direction: OrderDirection, field: ProductVariantVersionOrderField) :
    BaseOrder<ProductVariantVersionOrderField>(direction, field) {

    companion object {
        val DEFAULT = ProductVariantVersionOrder(OrderDirection.ASC, ProductVariantVersionOrderField.ID)
    }
}