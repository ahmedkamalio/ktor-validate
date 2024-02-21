package com.amedmoore.ktor.validate

import io.ktor.server.plugins.requestvalidation.*

inline fun <reified T : Any> validate(body: T): ValidationResult {
    T::class.java.declaredFields.forEach { field ->
        field.annotations.forEach { annotation ->
            val validator = validatorsRegistry[annotation.annotationClass]
            if (validator != null) {
                field.isAccessible = true
                val result = validator.validate(field.get(body), annotation)
                if (result is ValidatorResult.Invalid) {
                    return ValidationResult.Invalid(result.message)
                }
            }
        }
    }
    return ValidationResult.Valid
}
