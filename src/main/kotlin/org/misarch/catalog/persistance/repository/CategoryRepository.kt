package org.misarch.catalog.persistance.repository

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository
import org.misarch.catalog.persistance.model.CategoryEntity
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CategoryRepository : QuerydslR2dbcRepository<CategoryEntity, UUID>