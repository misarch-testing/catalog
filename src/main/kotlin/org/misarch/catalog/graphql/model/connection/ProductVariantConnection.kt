package org.misarch.catalog.graphql.model.connection

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.dsl.ComparableExpression
import com.querydsl.sql.SQLQuery
import org.misarch.catalog.graphql.model.ProductVariant
import org.misarch.catalog.graphql.model.connection.base.BaseConnection
import org.misarch.catalog.graphql.model.connection.base.BaseOrder
import org.misarch.catalog.graphql.model.connection.base.BaseOrderField
import org.misarch.catalog.graphql.model.connection.base.OrderDirection
import org.misarch.catalog.persistance.model.ProductVariantEntity
import org.misarch.catalog.persistance.repository.ProductVariantRepository

/**
 * A GraphQL connection for [ProductVariant]s.
 *
 * @param first The maximum number of items to return
 * @param skip The number of items to skip
 * @param predicate The predicate to filter the items by
 * @param order The order to sort the items by
 * @param repository The repository to fetch the items from
 * @param applyJoin A function to apply a join to the query
 */
@GraphQLDescription("A connection to a list of `ProductVariant` values.")
class ProductVariantConnection(
    first: Int?,
    skip: Int?,
    predicate: Predicate?,
    order: ProductVariantOrder?,
    repository: ProductVariantRepository,
    applyJoin: (query: SQLQuery<*>) -> SQLQuery<*> = { it }
) : BaseConnection<ProductVariant, ProductVariantEntity>(
    first,
    skip,
    predicate,
    (order ?: ProductVariantOrder.DEFAULT).toOrderSpecifier(),
    repository,
    ProductVariantEntity.ENTITY,
    applyJoin
) {

    override val primaryKey: ComparableExpression<*> get() = ProductVariantEntity.ENTITY.id

    override fun toDto(value: ProductVariantEntity): ProductVariant {
        return value.toDTO()
    }
}

@GraphQLDescription("ProductVariant order fields")
enum class ProductVariantOrderField(override vararg val expressions: ComparableExpression<*>) : BaseOrderField {
    @GraphQLDescription("Order productVariants by their id")
    ID(ProductVariantEntity.ENTITY.id)
}

@GraphQLDescription("ProductVariant order")
class ProductVariantOrder(direction: OrderDirection, field: ProductVariantOrderField) :
    BaseOrder<ProductVariantOrderField>(direction, field) {

    companion object {
        val DEFAULT = ProductVariantOrder(OrderDirection.ASC, ProductVariantOrderField.ID)
    }
}