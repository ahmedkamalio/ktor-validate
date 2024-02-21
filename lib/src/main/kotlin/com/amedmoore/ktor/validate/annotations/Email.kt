package com.amedmoore.ktor.validate.annotations

import com.amedmoore.ktor.validate.Validator
import com.amedmoore.ktor.validate.ValidatorResult
import com.amedmoore.ktor.validate.utils.isByteLength
import com.amedmoore.ktor.validate.utils.isString
import com.amedmoore.ktor.validate.utils.toUrlDecoded

@Target(AnnotationTarget.FIELD)
annotation class Email(
    val message: String = "Invalid email address",
    val maxLength: Int = DEFAULT_MAX_EMAIL_LENGTH,
    val allowIpAddress: Boolean = false,
    val allowUtf8LocalPart: Boolean = false,
) {
    companion object {
        const val DEFAULT_MAX_EMAIL_LENGTH = 254
    }
}

class EmailValidator : Validator() {
    override fun validate(value: Any?, annotation: Annotation?): ValidatorResult {
        if (annotation !is Email) return ValidatorResult.Valid

        if (!value.isString()) {
            return ValidatorResult.Invalid(annotation.message)
        }

        val email = value.toString()
        if (!email.isByteLength(max = annotation.maxLength)) {
            return ValidatorResult.Invalid(annotation.message)
        }

        val parts = email.toUrlDecoded().split('@')
        if (parts.size != 2) {
            return ValidatorResult.Invalid(annotation.message)
        }

        val username = parts.first()
        val domain = parts.last()

        val userParts = username.split('.')
        if (userParts.isEmpty() || userParts.any { it.isBlank() }) {
            return ValidatorResult.Invalid(annotation.message)
        }

        for (part in userParts) {
            if (!part.matches(usernameRegex) && (annotation.allowUtf8LocalPart && !part.matches(usernameUtf8Regex))) {
                return ValidatorResult.Invalid(annotation.message)
            }
        }

        if (DomainNameValidator().validate(domain, DomainName()).isInvalid()) {
            if (annotation.allowIpAddress && IPValidator().validate(domain, IP()).isInvalid()) {
                return ValidatorResult.Invalid(annotation.message)
            }
            return ValidatorResult.Invalid(annotation.message)
        }

        return ValidatorResult.Valid
    }

    companion object {
        private val usernameRegex = Regex("^[a-z\\d!#$%&'*+\\-/=?^_`{|}~]+$", RegexOption.IGNORE_CASE)

        private val usernameUtf8Regex = Regex(
            "^[a-z\\d!#$%&'*+\\-/=?^_`{|}~\u00A1-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+$",
            RegexOption.IGNORE_CASE,
        )
    }
}
