package com.amedmoore.ktor.validate.utils

import kotlin.collections.Map

fun Any?.hasSize(min: Int = 0, max: Int? = null): Boolean {
    var n = 0
    when (this) {
        is Iterable<*> -> n = this.count()
        is Map<*, *> -> n = this.size
        is String -> n = this.length
    }
    return n >= min && (max == null || n <= max)
}
