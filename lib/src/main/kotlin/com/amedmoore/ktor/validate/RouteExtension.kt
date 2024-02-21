package com.amedmoore.ktor.validate

import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.routing.*

inline fun <reified T : Any> Routing.validate() = install(RequestValidation) { validate<T>() }

inline fun <reified T : Any> Route.validate() = install(RequestValidation) { validate<T>() }
