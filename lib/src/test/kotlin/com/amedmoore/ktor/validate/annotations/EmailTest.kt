package com.amedmoore.ktor.validate.annotations

import com.amedmoore.ktor.validate.validate
import io.ktor.server.plugins.requestvalidation.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertIs

class EmailTest {
    private val testData = listOf(
        // Valid emails
        "valid.email@example.com" to true,
        "user.name123@company.co.uk" to true,
        "john.doe@sub.domain.example" to true,
        "alice+test@email.provider" to true,
        "alice-test@email.provider" to true,
        "alice_test@email.provider" to true,
        "john.q.public@example.com" to true,
        "mary@x.test" to true,
        "jdoe@example.org" to true,
        "one@y.test" to true,
        "boss@nil.test" to true,
        "sysservices@example.net" to true,

        // Invalid emails
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
    fun testEmailValidator() {
        data class RequestBody(@Email val email: String)

        testData.forEach {
            val body = RequestBody(it.first)
            val res = validate<RequestBody>(body)
            if (it.second) {
                assertIs<ValidationResult.Valid>(res, "Expected '${it.first}' to be valid")
            } else {
                assertIs<ValidationResult.Invalid>(res, "Expected '${it.first}' to be invalid")
                assertContains(res.reasons, Email().message)
            }
        }
    }
}
