package org.misarch.catalog.service

import kotlinx.coroutines.reactor.awaitSingle
import org.misarch.catalog.graphql.input.CreateCategoryInput
import org.misarch.catalog.persistance.model.CategoryCharacteristicEntity
import org.misarch.catalog.persistance.model.CategoryEntity
import org.misarch.catalog.persistance.repository.CategoryRepository
import org.misarch.catalog.util.duplicates
import org.springframework.stereotype.Service

/**
 * Service for [CategoryEntity]s
 *
 * @param repository the provided repository
 * @property categoryCharacteristicService service for [CategoryCharacteristicEntity]s
 */
@Service
class CategoryService(
    repository: CategoryRepository, private val categoryCharacteristicService: CategoryCharacteristicService
) : BaseService<CategoryEntity, CategoryRepository>(repository) {

    /**
     * Creates a category, also creates the characteristics
     *
     * @param input defines the category (and characteristics) to be created
     * @return the created category
     */
    suspend fun createCategory(input: CreateCategoryInput): CategoryEntity {
        val category = CategoryEntity(
            name = input.name, description = input.description, id = null
        )
        val savedCategory = repository.save(category).awaitSingle()
        val id = savedCategory.id!!
        val characteristicNames =
            input.categoricalCharacteristics.map { it.name } + input.numericalCharacteristics.map { it.name }
        val duplicates = characteristicNames.duplicates()
        if (duplicates.isNotEmpty()) {
            throw IllegalArgumentException("Characteristic names must be unique. Duplicates: $duplicates")
        }
        input.categoricalCharacteristics.forEach {
            categoryCharacteristicService.createCategoricalCategoryCharacteristicInternal(it, id)
        }
        input.numericalCharacteristics.forEach {
            categoryCharacteristicService.createNumericalCategoryCharacteristicInternal(it, id)
        }
        return savedCategory
    }

}