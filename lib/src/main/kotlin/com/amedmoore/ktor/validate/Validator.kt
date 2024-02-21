package com.amedmoore.ktor.validate

abstract class Validator {
    abstract fun validate(value: Any?, annotation: Annotation?): ValidatorResult
}

sealed class ValidatorResult {
    data object Valid : ValidatorResult()

    data class Invalid(val message: String) : ValidatorResult()

    fun isValid() = this is Valid

    fun isInvalid() = this is Invalid
}
