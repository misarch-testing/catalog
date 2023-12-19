package org.misarch.catalog.graphql.model.connection

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.dsl.ComparableExpression
import com.querydsl.sql.SQLQuery
import org.misarch.catalog.graphql.model.Product
import org.misarch.catalog.graphql.model.connection.base.BaseConnection
import org.misarch.catalog.graphql.model.connection.base.BaseOrder
import org.misarch.catalog.graphql.model.connection.base.BaseOrderField
import org.misarch.catalog.graphql.model.connection.base.OrderDirection
import org.misarch.catalog.persistance.model.ProductEntity
import org.misarch.catalog.persistance.repository.ProductRepository

/**
 * A GraphQL connection for [Product]s.
 *
 * @param first The maximum number of items to return
 * @param skip The number of items to skip
 * @param predicate The predicate to filter the items by
 * @param order The order to sort the items by
 * @param repository The repository to fetch the items from
 * @param applyJoin A function to apply a join to the query
 */
@GraphQLDescription("A connection to a list of `Product` values.")
class ProductConnection(
    first: Int?,
    skip: Int?,
    predicate: Predicate?,
    order: ProductOrder?,
    repository: ProductRepository,
    applyJoin: (query: SQLQuery<*>) -> SQLQuery<*> = { it }
) : BaseConnection<Product, ProductEntity>(
    first,
    skip,
    predicate,
    (order ?: ProductOrder.DEFAULT).toOrderSpecifier(),
    repository,
    ProductEntity.ENTITY,
    applyJoin
) {

    override val primaryKey: ComparableExpression<*> get() = ProductEntity.ENTITY.id

    override fun toDto(value: ProductEntity): Product {
        return value.toDTO()
    }
}

@GraphQLDescription("Product order fields")
enum class ProductOrderField(override vararg val expressions: ComparableExpression<*>) : BaseOrderField {
    @GraphQLDescription("Order products by their id")
    ID(ProductEntity.ENTITY.id)
}

@GraphQLDescription("Product order")
class ProductOrder(direction: OrderDirection, field: ProductOrderField) :
    BaseOrder<ProductOrderField>(direction, field) {

    companion object {
        val DEFAULT = ProductOrder(OrderDirection.ASC, ProductOrderField.ID)
    }
}