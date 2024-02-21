package com.amedmoore.ktor.validate.annotations

import com.amedmoore.ktor.validate.Validator
import com.amedmoore.ktor.validate.ValidatorResult
import com.amedmoore.ktor.validate.utils.hasSize

@Target(AnnotationTarget.FIELD)
annotation class Min(
    val min: Int,
    val message: String = "Value is less than the minimum length",
)

class MinValidator : Validator() {
    override fun validate(value: Any?, annotation: Annotation?): ValidatorResult {
        if (annotation !is Min) return ValidatorResult.Valid

        if (!value.hasSize(annotation.min)) {
            return ValidatorResult.Invalid(annotation.message)
        }

        return ValidatorResult.Valid
    }
}
