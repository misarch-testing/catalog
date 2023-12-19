package org.misarch.catalog.service

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository
import java.util.*

/**
 * Base service for all services
 */
abstract class BaseService<T, R : QuerydslR2dbcRepository<T, UUID>>(protected val repository: R)