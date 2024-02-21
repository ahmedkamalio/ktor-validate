package com.amedmoore.ktor.validate

import com.amedmoore.ktor.validate.annotations.*
import kotlin.reflect.KClass

val validatorsRegistry = hashMapOf<KClass<*>, Validator>(
    Email::class to EmailValidator(),
    IP::class to IPValidator(),
    DomainName::class to DomainNameValidator(),
    Min::class to MinValidator(),
    Max::class to MaxValidator(),
    Length::class to LengthValidator(),
)

inline fun <reified TClass : KClass<*>, TValidator : Validator> registerValidator(validator: TValidator) {
    validatorsRegistry[TClass::class] = validator
}
