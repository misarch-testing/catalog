package org.misarch.catalog.persistance.repository

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository
import org.misarch.catalog.persistance.model.ProductVariantVersionEntity
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProductVariantVersionRepository : QuerydslR2dbcRepository<ProductVariantVersionEntity, UUID> {

    @Query("SELECT MAX(p.version) FROM ProductVariantVersionEntity p WHERE p.productVariantId = :productVariantId")
    suspend fun findMaxVersionByProductVariantId(
        @Param("productVariantId")
        productVariantId: UUID
    ): Int?

}