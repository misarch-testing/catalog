package org.misarch.catalog.graphql.model.connection.base

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository
import com.querydsl.core.types.Ops.AggOps
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.dsl.ComparableExpression
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.sql.RelationalPathBase
import com.querydsl.sql.SQLQuery
import kotlinx.coroutines.reactor.awaitSingle

/**
 * A GraphQL connection that is backed by a QueryDSL repository
 *
 * @param T The type of the node
 * @param D The type of the entity
 * @property first The amount of items to fetch
 * @property skip The amount of items to skip
 * @property predicate The predicate to filter the items
 * @property order The order to sort the items
 * @property repository The repository to fetch the items from
 * @property entity The entity to fetch the items from
 * @property applyJoin A function to apply one or more joins to the query
 */
@GraphQLIgnore
abstract class BaseConnection<T, D>(
    private val first: Int?,
    private val skip: Int?,
    private val predicate: Predicate?,
    private val order: Array<OrderSpecifier<*>>,
    private val repository: QuerydslR2dbcRepository<D, *>,
    private val entity: RelationalPathBase<*>,
    private val applyJoin: (query: SQLQuery<*>) -> SQLQuery<*> = { it }
) {

    /**
     * The primary key of the entity
     */
    protected abstract val primaryKey: ComparableExpression<*>

    @GraphQLDescription("The total amount of items in this connection")
    @Suppress("UNCHECKED_CAST")
    suspend fun totalCount(): Int {
        return repository.query {
            val baseQuery =
                it.select(Expressions.numberOperation(Long::class.javaObjectType, AggOps.COUNT_AGG, primaryKey))
                    .from(entity)
            val joinedQuery = applyJoin(baseQuery) as SQLQuery<Long>
            if (predicate != null) {
                joinedQuery.where(predicate)
            } else {
                joinedQuery
            }
        }.first().awaitSingle().toInt()
    }

    @GraphQLDescription("The resulting .")
    @Suppress("UNCHECKED_CAST")
    suspend fun nodes(): List<T> {
        return repository.query {
            val baseQuery = it.select(repository.entityProjection()).from(entity)
            val joinedQuery = applyJoin(baseQuery) as SQLQuery<D>
            if (predicate != null) {
                joinedQuery.where(predicate)
            } else {
                joinedQuery
            }.orderBy(*order).offset(skip?.toLong() ?: 0).limit(first?.toLong() ?: Long.MAX_VALUE)
        }.all().collectList().awaitSingle().map { toDto(it) }
    }

    @GraphQLDescription("Whether this connection has a next page")
    suspend fun hasNextPage(): Boolean {
        if (first == null) {
            return false
        }
        return repository.query {
            val baseQuery = it.select(repository.entityProjection()).from(entity)
            val joinedQuery = applyJoin(baseQuery)
            if (predicate != null) {
                joinedQuery.where(predicate)
            } else {
                joinedQuery
            }.offset(first.toLong() + (skip ?: 0)).limit(1)
        }.all().hasElements().awaitSingle()
    }

    internal abstract fun toDto(value: D): T

}

