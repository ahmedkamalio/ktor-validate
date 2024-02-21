package com.amedmoore.ktor.validate.annotations

import com.amedmoore.ktor.validate.Validator
import com.amedmoore.ktor.validate.ValidatorResult
import com.amedmoore.ktor.validate.utils.isString

@Target(AnnotationTarget.FIELD)
annotation class IP(
    val message: String = "Invalid IP",
    val version: IPVersion = IPVersion.Any,
)

enum class IPVersion { V4, V6, Any }

class IPValidator : Validator() {
    override fun validate(value: Any?, annotation: Annotation?): ValidatorResult {
        if (annotation !is IP) return ValidatorResult.Valid

        if (!value.isString()) {
            return ValidatorResult.Invalid(annotation.message)
        }

        val ip = value.toString()

        if ((annotation.version in setOf(IPVersion.Any, IPVersion.V4) && ip.matches(ipV4AddressRegExp)) ||
            (annotation.version in setOf(IPVersion.Any, IPVersion.V6) && ip.matches(ipV6AddressRegExp))
        ) {
            return ValidatorResult.Valid
        }

        return ValidatorResult.Invalid(annotation.message)
    }

    companion object {
        private const val IP_V4_SEGMENT_FORMAT = "(?:[0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])"
        private const val IPV4_ADDRESS_FORMAT = "($IP_V4_SEGMENT_FORMAT[.]){3}$IP_V4_SEGMENT_FORMAT"
        private val ipV4AddressRegExp = Regex("^$IPV4_ADDRESS_FORMAT$")

        private const val IPV6_SEGMENT_FORMAT = "(?:[0-9a-fA-F]{1,4})"

        @Suppress("RegExpUnnecessaryNonCapturingGroup")
        private val ipV6AddressRegExp = Regex(
            "^(" +
                    "(?:$IPV6_SEGMENT_FORMAT:){7}(?:$IPV6_SEGMENT_FORMAT|:)|" +
                    "(?:$IPV6_SEGMENT_FORMAT:){6}(?:$IPV4_ADDRESS_FORMAT|:$IPV6_SEGMENT_FORMAT|:)|" +
                    "(?:$IPV6_SEGMENT_FORMAT:){5}(?::$IPV4_ADDRESS_FORMAT|(:$IPV6_SEGMENT_FORMAT){1,2}|:)|" +
                    "(?:$IPV6_SEGMENT_FORMAT:){4}(?:(:$IPV6_SEGMENT_FORMAT){0,1}:$IPV4_ADDRESS_FORMAT|(:$IPV6_SEGMENT_FORMAT){1,3}|:)|" +
                    "(?:$IPV6_SEGMENT_FORMAT:){3}(?:(:$IPV6_SEGMENT_FORMAT){0,2}:$IPV4_ADDRESS_FORMAT|(:$IPV6_SEGMENT_FORMAT){1,4}|:)|" +
                    "(?:$IPV6_SEGMENT_FORMAT:){2}(?:(:$IPV6_SEGMENT_FORMAT){0,3}:$IPV4_ADDRESS_FORMAT|(:$IPV6_SEGMENT_FORMAT){1,5}|:)|" +
                    "(?:$IPV6_SEGMENT_FORMAT:){1}(?:(:$IPV6_SEGMENT_FORMAT){0,4}:$IPV4_ADDRESS_FORMAT|(:$IPV6_SEGMENT_FORMAT){1,6}|:)|" +
                    "(?::((?::$IPV6_SEGMENT_FORMAT){0,5}:$IPV4_ADDRESS_FORMAT|(?::$IPV6_SEGMENT_FORMAT){1,7}|:))" +
                    ")(%[0-9a-zA-Z-.:]+)?$"
        )
    }
}
