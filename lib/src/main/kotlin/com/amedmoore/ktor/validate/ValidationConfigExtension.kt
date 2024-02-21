package com.amedmoore.ktor.validate

import io.ktor.server.plugins.requestvalidation.*

inline fun <reified T : Any> RequestValidationConfig.validate() = validate<T> { validate<T>(it) }
