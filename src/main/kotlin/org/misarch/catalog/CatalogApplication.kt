package org.misarch.catalog

import com.infobip.spring.data.r2dbc.EnableQuerydslR2dbcRepositories
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableQuerydslR2dbcRepositories
class CatalogApplication

fun main(args: Array<String>) {
    runApplication<CatalogApplication>(*args)
}