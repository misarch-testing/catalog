package org.misarch.catalog.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import kotlinx.coroutines.reactor.awaitSingle
import org.misarch.catalog.persistence.repository.CategoryRepository
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@GraphQLDescription("A characteristic of a Category.")
abstract class CategoryCharacteristic(
    id: UUID,
    @GraphQLDescription("The name of the CategoryCharacteristic.")
    val name: String,
    @GraphQLDescription("The description of the CategoryCharacteristic.")
    val description: String, private val categoryId: UUID
) : Node(id) {

    @GraphQLDescription("The Category this item belongs to.")
    suspend fun category(
        @Autowired
        @GraphQLIgnore
        categoryRepository: CategoryRepository
    ): Category {
        return categoryRepository.findById(categoryId).awaitSingle().toDTO()
    }

}