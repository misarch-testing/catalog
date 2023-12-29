package org.misarch.catalog.graphql.model.connection

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.dsl.ComparableExpression
import com.querydsl.sql.SQLQuery
import org.misarch.catalog.graphql.model.Category
import org.misarch.catalog.graphql.model.connection.base.BaseConnection
import org.misarch.catalog.graphql.model.connection.base.BaseOrder
import org.misarch.catalog.graphql.model.connection.base.BaseOrderField
import org.misarch.catalog.graphql.model.connection.base.OrderDirection
import org.misarch.catalog.persistance.model.CategoryEntity
import org.misarch.catalog.persistance.repository.CategoryRepository

/**
 * A GraphQL connection for [Category]s.
 *
 * @param first The maximum number of items to return
 * @param skip The number of items to skip
 * @param predicate The predicate to filter the items by
 * @param order The order to sort the items by
 * @param repository The repository to fetch the items from
 * @param applyJoin A function to apply a join to the query
 */
@GraphQLDescription("A connection to a list of `Category` values.")
class CategoryConnection(
    first: Int?,
    skip: Int?,
    predicate: Predicate?,
    order: CategoryOrder?,
    repository: CategoryRepository,
    applyJoin: (query: SQLQuery<*>) -> SQLQuery<*> = { it }
) : BaseConnection<Category, CategoryEntity>(
    first,
    skip,
    predicate,
    (order ?: CategoryOrder.DEFAULT).toOrderSpecifier(),
    repository,
    CategoryEntity.ENTITY,
    applyJoin
) {

    override val primaryKey: ComparableExpression<*> get() = CategoryEntity.ENTITY.id

    override fun toDto(value: CategoryEntity): Category {
        return value.toDTO()
    }
}

@GraphQLDescription("Category order fields")
enum class CategoryOrderField(override vararg val expressions: ComparableExpression<*>) : BaseOrderField {
    @GraphQLDescription("Order categories by their id")
    ID(CategoryEntity.ENTITY.id)
}

@GraphQLDescription("Category order")
class CategoryOrder(direction: OrderDirection, field: CategoryOrderField) :
    BaseOrder<CategoryOrderField>(direction, field) {

    companion object {
        val DEFAULT = CategoryOrder(OrderDirection.ASC, CategoryOrderField.ID)
    }
}