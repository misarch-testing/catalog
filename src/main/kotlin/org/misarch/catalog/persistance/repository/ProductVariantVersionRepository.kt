package org.misarch.catalog.persistance.repository

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository
import org.misarch.catalog.persistance.model.CategoryCharacteristicEntity
import org.misarch.catalog.persistance.model.ProductVariantVersionEntity
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*


/**
 * Repository for [ProductVariantVersionEntity]s
 */
@Repository
interface ProductVariantVersionRepository : QuerydslR2dbcRepository<ProductVariantVersionEntity, UUID> {

    /**
     * Finds the maximum version of a product variant
     *
     * @param productVariantId the id of the product variant
     * @return the maximum version of the product variant, null if there are no versions
     */
    @Query("SELECT MAX(p.version) FROM ProductVariantVersionEntity p WHERE p.productVariantId = :productVariantId")
    suspend fun findMaxVersionByProductVariantId(
        @Param("productVariantId")
        productVariantId: UUID
    ): Int?

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
            SELECT DISTINCT cc.* AS categoryCharacteristicId
            FROM ProductVariantVersionEntity pvve
            JOIN ProductVariantEntity pve ON pvve.productVariantId = pve.id
            JOIN ProductEntity pe ON pve.productId = pe.id
            JOIN ProductToCategoryEntity pce ON pe.id = pce.productId
            JOIN CategoryEntity ce ON pce.categoryId = ce.id
            JOIN CategoryCharacteristicEntity cc ON ce.id = cc.categoryId
            JOIN CategoryCharacteristicValueEntity ccve ON cc.id = ccve.categoryCharacteristicId
            WHERE pvve.id = :productVariantVersionId
              AND cc.id IN (:categoryCharacteristicIds);
        """
    )
    suspend fun findValidCategoryCharacteristics(
        @Param("productVariantVersionId") productVariantVersionId: UUID?,
        @Param("categoryCharacteristicIds") categoryCharacteristicIds: List<UUID?>?
    ): List<CategoryCharacteristicEntity>

}