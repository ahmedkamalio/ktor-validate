package com.amedmoore.ktor.validate.annotations

import com.amedmoore.ktor.validate.Validator
import com.amedmoore.ktor.validate.ValidatorResult
import com.amedmoore.ktor.validate.utils.isByteLength
import com.amedmoore.ktor.validate.utils.isString

@Target(AnnotationTarget.FIELD)
annotation class DomainName(
    val message: String = "Invalid domain name",
    val allowIpAddress: Boolean = false,
)

class DomainNameValidator : Validator() {
    override fun validate(value: Any?, annotation: Annotation?): ValidatorResult {
        if (annotation !is DomainName) return ValidatorResult.Valid

        if (!value.isString()) {
            return ValidatorResult.Invalid(annotation.message)
        }

        val domain = value.toString()

        if (annotation.allowIpAddress && IPValidator().validate(value, IP()).isValid()) {
            return ValidatorResult.Valid
        }

        val parts = domain.split('.')
        if (parts.size < 2) {
            return ValidatorResult.Invalid(annotation.message)
        }

        val tld = parts.last()

        if (tld.matches(whiteSpaceRegex)) {
            return ValidatorResult.Invalid(annotation.message)
        }

        if (tld.matches(numericRegex)) {
            return ValidatorResult.Invalid(annotation.message)
        }

        for (part in parts) {
            if (!part.isByteLength(min = 1, max = 63)) {
                return ValidatorResult.Invalid(annotation.message)
            }

            if (!part.matches(a)) {
                return ValidatorResult.Invalid(annotation.message)
            }

            if (part.matches(fullWidthCharsRegex)) {
                return ValidatorResult.Invalid(annotation.message)
            }

            if (part.matches(hyphenRegex)) {
                return ValidatorResult.Invalid(annotation.message)
            }
        }

        return ValidatorResult.Valid
    }

    companion object {
        private val whiteSpaceRegex = Regex("\\s")
        private val numericRegex = Regex("^\\d+\$")
        private val a = Regex("^[a-z_\\u00a1-\\uffff0-9-]+$", RegexOption.IGNORE_CASE)
        private val fullWidthCharsRegex = Regex("[\\uff01-\\uff5e]")
        private val hyphenRegex = Regex("^-|-$")
    }
}
