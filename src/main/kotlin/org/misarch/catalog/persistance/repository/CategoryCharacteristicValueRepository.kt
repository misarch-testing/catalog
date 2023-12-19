package org.misarch.catalog.persistance.repository

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository
import org.misarch.catalog.persistance.model.CategoryCharacteristicDiscriminator
import org.misarch.catalog.persistance.model.CategoryCharacteristicEntity
import org.misarch.catalog.persistance.model.CategoryCharacteristicValueEntity
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Repository for [CategoryCharacteristicValueEntity]s
 */
@Repository
interface CategoryCharacteristicValueRepository : QuerydslR2dbcRepository<CategoryCharacteristicValueEntity, UUID> {

    /**
     * Upserts [CategoryCharacteristicValueEntity] with given [categoryCharacteristicId] and [productVariantVersionId]
     * If [CategoryCharacteristicValueEntity] with given [categoryCharacteristicId] and [productVariantVersionId] already exists, it will be updated
     * Otherwise, new [CategoryCharacteristicValueEntity] will be created
     *
     * @param categoryCharacteristicId id of the referenced characteristic
     * @param productVariantVersionId id of the referenced product variant version owning the value
     * @param stringValue [String] value to upsert
     * @param doubleValue [Double] value to upsert
     */
    @Modifying
    @Query(
        """
            INSERT INTO CategoryCharacteristicValueEntity (categoryCharacteristicId, productVariantVersionId, stringValue, doubleValue, discriminator)
            VALUES (:categoryCharacteristicId, :productVariantVersionId, :stringValue, :doubleValue, :discriminator)
            ON CONFLICT (categoryCharacteristicId, productVariantVersionId)
            DO UPDATE SET stringValue = :stringValue, doubleValue = :doubleValue
        """
    )
    suspend fun upsert(
        @Param("categoryCharacteristicId")
        categoryCharacteristicId: UUID,
        @Param("productVariantVersionId")
        productVariantVersionId: UUID,
        @Param("stringValue")
        stringValue: String?,
        @Param("doubleValue")
        doubleValue: Double?,
        @Param("discriminator")
        discriminator: CategoryCharacteristicDiscriminator
    )

    /**
     * Takes a list of category characteristic ids and a product variant version id
     * and returns only the [CategoryCharacteristicEntity]s which are compatible with the product variant version,
     * meaning that the associated product has the associated characteristic
     *
     * @param productVariantVersionId the id of the product variant
     * @param categoryCharacteristicIds the ids of the category characteristics
     * @return the valid [CategoryCharacteristicEntity]s
     */
    @Query(
        """
            SELECT DISTINCT cc.*
            FROM ProductVariantVersionEntity pvve
            JOIN ProductVariantEntity pve ON pvve.productVariantId = pve.id
            JOIN ProductEntity pe ON pve.productId = pe.id
            JOIN ProductToCategoryEntity pce ON pe.id = pce.productId
            JOIN CategoryEntity ce ON pce.categoryId = ce.id
            JOIN CategoryCharacteristicEntity cc ON ce.id = cc.categoryId
            WHERE pvve.id = :productVariantVersionId
              AND cc.id IN (:categoryCharacteristicIds);
        """
    )
    suspend fun findValidCategoryCharacteristics(
        @Param("productVariantVersionId")
        productVariantVersionId: UUID,
        @Param("categoryCharacteristicIds")
        categoryCharacteristicIds: List<UUID>
    ): List<CategoryCharacteristicEntity>

}