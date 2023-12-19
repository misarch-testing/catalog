package org.misarch.catalog.graphql.model.connection

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.dsl.ComparableExpression
import com.querydsl.sql.SQLQuery
import org.misarch.catalog.graphql.model.CategoryCharacteristic
import org.misarch.catalog.graphql.model.connection.base.BaseConnection
import org.misarch.catalog.graphql.model.connection.base.BaseOrder
import org.misarch.catalog.graphql.model.connection.base.BaseOrderField
import org.misarch.catalog.graphql.model.connection.base.OrderDirection
import org.misarch.catalog.persistance.model.CategoryCharacteristicEntity
import org.misarch.catalog.persistance.repository.CategoryCharacteristicRepository

/**
 * A GraphQL connection for [CategoryCharacteristic]s.
 *
 * @param first The maximum number of items to return
 * @param skip The number of items to skip
 * @param predicate The predicate to filter the items by
 * @param order The order to sort the items by
 * @param repository The repository to fetch the items from
 * @param applyJoin A function to apply a join to the query
 */
@GraphQLDescription("A connection to a list of `CategoryCharacteristic` values.")
class CategoryCharacteristicConnection(
    first: Int?,
    skip: Int?,
    predicate: Predicate?,
    order: CategoryCharacteristicOrder?,
    repository: CategoryCharacteristicRepository,
    applyJoin: (query: SQLQuery<*>) -> SQLQuery<*> = { it }
) : BaseConnection<CategoryCharacteristic, CategoryCharacteristicEntity>(
    first,
    skip,
    predicate,
    (order ?: CategoryCharacteristicOrder.DEFAULT).toOrderSpecifier(),
    repository,
    CategoryCharacteristicEntity.ENTITY,
    applyJoin
) {

    override val primaryKey: ComparableExpression<*> get() = CategoryCharacteristicEntity.ENTITY.id

    override fun toDto(value: CategoryCharacteristicEntity): CategoryCharacteristic {
        return value.toDTO()
    }
}

@GraphQLDescription("CategoryCharacteristic order fields")
enum class CategoryCharacteristicOrderField(override vararg val expressions: ComparableExpression<*>) : BaseOrderField {
    @GraphQLDescription("Order categoryCharacteristics by their id")
    ID(CategoryCharacteristicEntity.ENTITY.id)
}

@GraphQLDescription("CategoryCharacteristic order")
class CategoryCharacteristicOrder(direction: OrderDirection, field: CategoryCharacteristicOrderField) :
    BaseOrder<CategoryCharacteristicOrderField>(direction, field) {

    companion object {
        val DEFAULT = CategoryCharacteristicOrder(OrderDirection.ASC, CategoryCharacteristicOrderField.ID)
    }
}