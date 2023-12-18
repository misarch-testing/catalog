package org.misarch.catalog.graphql

import com.expediagroup.graphql.generator.hooks.SchemaGeneratorHooks
import graphql.scalars.datetime.DateTimeScalar
import graphql.schema.GraphQLType
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.OffsetDateTime
import kotlin.reflect.KType

@Configuration
class GraphQLConfiguration {

    @Bean
    fun schemaGeneratorHooks() = object : SchemaGeneratorHooks {
        override fun willGenerateGraphQLType(type: KType): GraphQLType? {
            return when(type.classifier) {
                OffsetDateTime::class -> DateTimeScalar.INSTANCE
                else -> null
            }
        }
    }

}