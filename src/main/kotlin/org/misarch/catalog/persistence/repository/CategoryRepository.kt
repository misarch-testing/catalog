package org.misarch.catalog.persistence.repository

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository
import org.misarch.catalog.persistence.model.CategoryEntity
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Repository for [CategoryEntity]s
 */
@Repository
interface CategoryRepository : QuerydslR2dbcRepository<CategoryEntity, UUID>