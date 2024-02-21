package com.amedmoore.ktor.validate.annotations

import com.amedmoore.ktor.validate.validate
import io.ktor.server.plugins.requestvalidation.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertIs

class DomainNameTest {

    private val testData = listOf(
        // Valid emails
        "example.com" to true,
        "company.co.uk" to true,
        "sub.domain.example" to true,
        "email.provider" to true,
        "email.provider" to true,
        "email.provider" to true,
        "example.com" to true,
        "x.test" to true,
        "example.org" to true,
        "y.test" to true,
        "nil.test" to true,
        "example.net" to true,

        // Invalid emails
        "" to false,
        " " to false,
        "172.0.0.1" to false,
        "domain.with.space .com" to false,
        ".invalid.starting.dot.com" to false,
        "invalid.ending.dot." to false,
    )

    @Test
    fun testDomainNameValidator() {
        data class RequestBody(@DomainName val email: String)

        testData.forEach {
            val body = RequestBody(it.first)
            val res = validate<RequestBody>(body)
            if (it.second) {
                assertIs<ValidationResult.Valid>(res, "Expected '${it.first}' to be valid")
            } else {
                assertIs<ValidationResult.Invalid>(res, "Expected '${it.first}' to be invalid")
                assertContains(res.reasons, DomainName().message)
            }
        }
    }

}
