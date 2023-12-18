package org.misarch.catalog.persistance.repository

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository
import org.misarch.catalog.persistance.model.CategoryCharacteristicEntity
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CategoryCharacteristicRepository : QuerydslR2dbcRepository<CategoryCharacteristicEntity, UUID>