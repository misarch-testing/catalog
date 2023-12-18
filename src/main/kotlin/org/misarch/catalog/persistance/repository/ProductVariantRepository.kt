package org.misarch.catalog.persistance.repository

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository
import org.misarch.catalog.persistance.model.ProductVariantEntity
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProductVariantRepository : QuerydslR2dbcRepository<ProductVariantEntity, UUID>