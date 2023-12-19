package org.misarch.catalog.graphql.model.connection

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.dsl.ComparableExpression
import com.querydsl.sql.SQLQuery
import org.misarch.catalog.graphql.model.CategoryCharacteristicValue
import org.misarch.catalog.graphql.model.connection.base.BaseConnection
import org.misarch.catalog.graphql.model.connection.base.BaseOrder
import org.misarch.catalog.graphql.model.connection.base.BaseOrderField
import org.misarch.catalog.graphql.model.connection.base.OrderDirection
import org.misarch.catalog.persistance.model.CategoryCharacteristicValueEntity
import org.misarch.catalog.persistance.repository.CategoryCharacteristicValueRepository

/**
 * A GraphQL connection for [CategoryCharacteristicValue]s.
 *
 * @param first The maximum number of items to return
 * @param skip The number of items to skip
 * @param predicate The predicate to filter the items by
 * @param order The order to sort the items by
 * @param repository The repository to fetch the items from
 * @param applyJoin A function to apply a join to the query
 */
@GraphQLDescription("A connection to a list of `CategoryCharacteristicValue` values.")
class CategoryCharacteristicValueConnection(
    first: Int?,
    skip: Int?,
    predicate: Predicate?,
    order: CategoryCharacteristicValueOrder?,
    repository: CategoryCharacteristicValueRepository,
    applyJoin: (query: SQLQuery<*>) -> SQLQuery<*> = { it }
) : BaseConnection<CategoryCharacteristicValue, CategoryCharacteristicValueEntity>(
    first,
    skip,
    predicate,
    (order ?: CategoryCharacteristicValueOrder.DEFAULT).toOrderSpecifier(),
    repository,
    CategoryCharacteristicValueEntity.ENTITY,
    applyJoin
) {

    override val primaryKey: ComparableExpression<*> get() = CategoryCharacteristicValueEntity.ENTITY.id

    override fun toDto(value: CategoryCharacteristicValueEntity): CategoryCharacteristicValue {
        return value.toDTO()
    }
}

@GraphQLDescription("CategoryCharacteristicValue order fields")
enum class CategoryCharacteristicValueOrderField(override vararg val expressions: ComparableExpression<*>) : BaseOrderField {
    @GraphQLDescription("Order categoryCharacteristicValues by their id")
    ID(CategoryCharacteristicValueEntity.ENTITY.id)
}

@GraphQLDescription("CategoryCharacteristicValue order")
class CategoryCharacteristicValueOrder(direction: OrderDirection, field: CategoryCharacteristicValueOrderField) :
    BaseOrder<CategoryCharacteristicValueOrderField>(direction, field) {

    companion object {
        val DEFAULT = CategoryCharacteristicValueOrder(OrderDirection.ASC, CategoryCharacteristicValueOrderField.ID)
    }
}