package org.misarch.catalog.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import kotlinx.coroutines.reactor.awaitSingle
import org.misarch.catalog.persistence.repository.CategoryCharacteristicRepository
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@GraphQLDescription("A value for a CategoryCharacteristic.")
abstract class CategoryCharacteristicValue(
    private val characteristicId: UUID
) {

    @GraphQLDescription("The associated CategoryCharacteristic this is a value of.")
    suspend fun characteristic(
        @Autowired
        @GraphQLIgnore
        categoryCharacteristicRepository: CategoryCharacteristicRepository
    ): CategoryCharacteristic {
        return categoryCharacteristicRepository.findById(characteristicId).awaitSingle().toDTO()
    }

}