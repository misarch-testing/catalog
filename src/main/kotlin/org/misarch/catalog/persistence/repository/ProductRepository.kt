package org.misarch.catalog.persistence.repository

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository
import org.misarch.catalog.persistence.model.ProductEntity
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Repository for [ProductEntity]s
 */
@Repository
interface ProductRepository : QuerydslR2dbcRepository<ProductEntity, UUID>