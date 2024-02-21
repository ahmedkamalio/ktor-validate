package com.amedmoore.ktor.validate.annotations

import com.amedmoore.ktor.validate.Validator
import com.amedmoore.ktor.validate.ValidatorResult
import com.amedmoore.ktor.validate.utils.hasSize

@Target(AnnotationTarget.FIELD)
annotation class Length(
    val length: Int,
    val message: String = "Value is not in the valid length range",
)

class LengthValidator : Validator() {
    override fun validate(value: Any?, annotation: Annotation?): ValidatorResult {
        if (annotation !is Length) return ValidatorResult.Valid

        if (!value.hasSize(annotation.length, annotation.length)) {
            return ValidatorResult.Invalid(annotation.message)
        }

        return ValidatorResult.Valid
    }
}
