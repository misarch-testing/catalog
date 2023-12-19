package org.misarch.catalog.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import kotlinx.coroutines.reactor.awaitSingle
import org.misarch.catalog.persistance.repository.CategoryRepository
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@GraphQLDescription("A characteristic of a category.")
abstract class CategoryCharacteristic(
    id: UUID,
    @GraphQLDescription("The name of the characteristic.")
    val name: String,
    @GraphQLDescription("The description of the characteristic.")
    val description: String, private val categoryId: UUID
) : Node(id) {

    @GraphQLDescription("The category this item belongs to.")
    suspend fun category(
        @Autowired
        @GraphQLIgnore
        categoryRepository: CategoryRepository
    ): Category {
        return categoryRepository.findById(categoryId).awaitSingle().toDTO()
    }

}