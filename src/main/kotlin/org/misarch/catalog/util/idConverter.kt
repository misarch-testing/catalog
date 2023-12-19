package org.misarch.catalog.util

import com.expediagroup.graphql.generator.scalars.ID
import java.util.*

/**
 * Converts a [ID] to a [uuid]
 */
val UUID.graphQLID: ID
    get() = ID(this.toString())

/**
 * Converts a [uuid] to a [ID]
 */
val ID.uuid: UUID
    get() = UUID.fromString(this.value)