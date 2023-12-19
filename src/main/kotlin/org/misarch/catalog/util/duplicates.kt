package org.misarch.catalog.util

fun <T> Iterable<T>.duplicates(): Set<T> {
    return groupingBy { it }.eachCount().filter { it.value > 1 }.keys
}