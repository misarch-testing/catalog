package org.misarch.catalog.graphql.model.connection

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository
import com.querydsl.core.types.Predicate
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.domain.Sort
import java.util.*

@GraphQLIgnore
abstract class BaseConnection<T, D>(
    internal val first: Int?,
    internal val skip: Int?,
    internal val predicate: Predicate?,
    internal val sort: Sort,
    internal val repository: QuerydslR2dbcRepository<D, *>
) {

    @GraphQLDescription("The total amount of items in this connection")
    suspend fun totalCount(): Int {
        return if (predicate != null) {
            repository.count(predicate)
        } else {
            repository.count()
        }.awaitSingle().toInt()
    }

    @GraphQLDescription("The resulting .")
    suspend fun nodes(): List<T> {
        return if (predicate != null) {
            repository.findAll(predicate, sort)
        } else {
            repository.findAll(sort)
        }.skip(skip?.toLong() ?: 0).take(first?.toLong() ?: Long.MAX_VALUE).collectList().awaitSingle()
            .map { toDto(it) }
    }

    @GraphQLDescription("Whether this connection has a next page")
    suspend fun hasNextPage(): Boolean {
        if (first == null) {
            return false
        }
        return if (predicate != null) {
            repository.findAll(predicate)
        } else {
            repository.findAll()
        }.skip((first + (skip ?: 0)).toLong()).hasElements().awaitSingle()
    }

    internal abstract fun toDto(value: D): T

}

enum class OrderDirection(internal val sortDirection: Sort.Direction) {
    @GraphQLDescription("Ascending order")
    ASC(Sort.Direction.ASC),

    @GraphQLDescription("Descending order")
    DESC(Sort.Direction.DESC);
}

@GraphQLIgnore
abstract class BaseOrder<T : Enum<*>>(
    @GraphQLDescription("The direction to order by")
    val direction: OrderDirection,
    @GraphQLDescription("The field to order by")
    val field: T
) {

    internal fun toSort(): Sort {
        return Sort.by(direction.sortDirection, field.name.lowercase(Locale.getDefault()))
    }

}