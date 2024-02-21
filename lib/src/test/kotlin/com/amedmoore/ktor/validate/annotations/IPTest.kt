package com.amedmoore.ktor.validate.annotations

import com.amedmoore.ktor.validate.validate
import io.ktor.server.plugins.requestvalidation.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertIs

class IPTest {
    private val testData = listOf(
        // Valid IP addresses
        "192.168.1.1" to true,
        "10.0.0.1" to true,
        "172.16.254.1" to true,
        "fe80::1" to true,
        "2001:db8::ff00:42:8329" to true,
        "::1" to true,
        "192.0.2.1" to true,
        "198.51.100.42" to true,
        "203.0.113.0" to true,
        "172.0.0.1" to true,

        // Invalid IP addresses
        "" to false,
        " " to false,
        "invalid.email" to false,
        "missing.at.symbol.com" to false,
        "user@domain.with.space .com" to false,
        "user@in@valid.com" to false,
        "user@.invalid.starting.dot.com" to false,
        "user@invalid.ending.dot." to false,
        "@invalid.starting.at.com" to false,
        "user@invalid.ending.at@" to false,
        "very-long-${"address".repeat(35)}@email.provider" to false,
    )

    @Test
    fun testIPValidator() {
        data class RequestBody(@IP val ip: String)

        testData.forEach {
            val body = RequestBody(it.first)
            val res = validate<RequestBody>(body)
            if (it.second) {
                assertIs<ValidationResult.Valid>(res, "Expected '${it.first}' to be valid")
            } else {
                assertIs<ValidationResult.Invalid>(res, "Expected '${it.first}' to be invalid")
                assertContains(res.reasons, IP().message)
            }
        }
    }
}
