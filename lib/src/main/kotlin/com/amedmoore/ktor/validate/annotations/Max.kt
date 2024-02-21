package com.amedmoore.ktor.validate.annotations

import com.amedmoore.ktor.validate.Validator
import com.amedmoore.ktor.validate.ValidatorResult
import com.amedmoore.ktor.validate.utils.hasSize

@Target(AnnotationTarget.FIELD)
annotation class Max(
    val max: Int,
    val message: String = "Value exceeded the maximum length",
)

class MaxValidator : Validator() {
    override fun validate(value: Any?, annotation: Annotation?): ValidatorResult {
        if (annotation !is Max) return ValidatorResult.Valid

        if (!value.hasSize(max = annotation.max)) {
            return ValidatorResult.Invalid(annotation.message)
        }

        return ValidatorResult.Valid
    }
}
