package org.misarch.catalog.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.scalars.ID
import java.util.*

@GraphQLDescription("An object with an ID.")
abstract class Node(
    internal val id: UUID
) {

    fun id(): ID {
        return ID(id.toString())
    }

}