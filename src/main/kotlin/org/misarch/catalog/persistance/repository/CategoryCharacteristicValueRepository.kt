package org.misarch.catalog.persistance.repository

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository
import org.misarch.catalog.persistance.model.CategoryCharacteristicValueEntity
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CategoryCharacteristicValueRepository : QuerydslR2dbcRepository<CategoryCharacteristicValueEntity, UUID>