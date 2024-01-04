package org.misarch.catalog.graphql.model.connection.base

import com.querydsl.core.types.dsl.ComparableExpression

/**
 * A GraphQL order field
 */
interface BaseOrderField {
    /**
     * The expressions to order by
     */
    val expressions: Array<out ComparableExpression<*>>
}