package org.misarch.catalog.service

import kotlinx.coroutines.reactor.awaitSingle
import org.misarch.catalog.graphql.input.CategoricalCategoryCharacteristicInput
import org.misarch.catalog.graphql.input.CreateCategoricalCategoryCharacteristicInput
import org.misarch.catalog.graphql.input.CreateNumericalCategoryCharacteristicInput
import org.misarch.catalog.graphql.input.NumericalCategoryCharacteristicInput
import org.misarch.catalog.persistance.model.CategoryCharacteristicEntity
import org.misarch.catalog.persistance.repository.CategoryCharacteristicRepository
import org.misarch.catalog.persistance.repository.CategoryRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CategoryCharacteristicService(
    repository: CategoryCharacteristicRepository, private val categoryRepository: CategoryRepository
) : BaseService<CategoryCharacteristicEntity, CategoryCharacteristicRepository>(repository) {

    suspend fun createCategoricalCategoryCharacteristic(input: CreateCategoricalCategoryCharacteristicInput): CategoryCharacteristicEntity {
        val categoryId = UUID.fromString(input.categoryId.value)
        checkCategoryExists(categoryId)
        return createCategoricalCategoryCharacteristicInternal(input, categoryId)
    }

    suspend fun createCategoricalCategoryCharacteristicInternal(
        input: CategoricalCategoryCharacteristicInput, categoryId: UUID
    ): CategoryCharacteristicEntity {
        val categoryCharacteristic = CategoryCharacteristicEntity(
            discriminator = CategoryCharacteristicEntity.Discriminator.CATEGORICAL,
            name = input.name,
            description = input.description,
            unit = null,
            categoryId = categoryId,
            id = null
        )
        return repository.save(categoryCharacteristic).awaitSingle()
    }

    suspend fun createNumericalCategoryCharacteristic(input: CreateNumericalCategoryCharacteristicInput): CategoryCharacteristicEntity {
        val categoryId = UUID.fromString(input.categoryId.value)
        checkCategoryExists(categoryId)
        return createNumericalCategoryCharacteristicInternal(input, categoryId)
    }

    suspend fun createNumericalCategoryCharacteristicInternal(
        input: NumericalCategoryCharacteristicInput, categoryId: UUID
    ): CategoryCharacteristicEntity {
        val categoryCharacteristic = CategoryCharacteristicEntity(
            discriminator = CategoryCharacteristicEntity.Discriminator.NUMERICAL,
            name = input.name,
            description = input.description,
            unit = input.unit,
            categoryId = categoryId,
            id = null
        )
        return repository.save(categoryCharacteristic).awaitSingle()
    }

    private suspend fun checkCategoryExists(categoryId: UUID) {
        if (!categoryRepository.existsById(categoryId).awaitSingle()) {
            throw IllegalArgumentException("Category with id $categoryId does not exist.")
        }
    }

}