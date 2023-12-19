package org.misarch.catalog.persistance.repository

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository
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
            INSERT INTO CategoryCharacteristicValueEntity (categoryCharacteristicId, productVariantVersionId, stringValue, doubleValue)
            VALUES (:categoryCharacteristicId, :productVariantVersionId, :stringValue, :doubleValue)
            ON CONFLICT (categoryCharacteristicId, productVariantVersionId)
            DO UPDATE SET stringValue = :stringValue, doubleValue = :doubleValue
        """
    )
    fun upsert(
        @Param("categoryCharacteristicId")
        categoryCharacteristicId: UUID,
        @Param("productVariantVersionId")
        productVariantVersionId: UUID,
        @Param("stringValue")
        stringValue: String?,
        @Param("doubleValue")
        doubleValue: Double?
    )

}