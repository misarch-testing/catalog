package org.misarch.catalog.graphql.model.connection.base

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.querydsl.core.types.OrderSpecifier

/**
 * A GraphQL order, consisting of a field and a direction
 *
 * @param T The type of the field
 * @property direction The direction to order by
 * @property field The field to order by
 */
@GraphQLIgnore
abstract class BaseOrder<T : BaseOrderField>(
    @GraphQLDescription("The direction to order by")
    val direction: OrderDirection,
    @GraphQLDescription("The field to order by")
    val field: T
) {

    fun toOrderSpecifier(): Array<OrderSpecifier<*>> {
        return field.expressions.map { OrderSpecifier(direction.direction, it) }.toTypedArray()
    }

}