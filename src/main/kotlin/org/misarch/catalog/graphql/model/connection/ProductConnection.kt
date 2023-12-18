package org.misarch.catalog.graphql.model.connection

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.querydsl.core.types.Predicate
import org.misarch.catalog.graphql.model.Product
import org.misarch.catalog.persistance.model.ProductEntity
import org.misarch.catalog.persistance.repository.ProductRepository

@GraphQLDescription("A connection to a list of `Product` values.")
class ProductConnection(
    first: Int?,
    skip: Int?,
    predicate: Predicate?,
    order: ProductOrder?,
    repository: ProductRepository
) : BaseConnection<Product, ProductEntity>(
    first,
    skip,
    predicate,
    (order ?: ProductOrder.DEFAULT).toSort(),
    repository
) {

    override fun toDto(value: ProductEntity): Product {
        return value.toDTO()
    }
}

@GraphQLDescription("Product order fields")
enum class ProductOrderField {
    @GraphQLDescription("Order products by their id")
    ID
}

@GraphQLDescription("Product order")
class ProductOrder(direction: OrderDirection, field: ProductOrderField) : BaseOrder<ProductOrderField>(direction, field) {

    companion object {
        val DEFAULT = ProductOrder(OrderDirection.ASC, ProductOrderField.ID)
    }
}