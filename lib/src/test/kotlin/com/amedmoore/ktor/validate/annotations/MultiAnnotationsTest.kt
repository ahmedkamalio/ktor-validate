package com.amedmoore.ktor.validate.annotations

import com.amedmoore.ktor.validate.validate
import io.ktor.server.plugins.requestvalidation.*
import java.util.*
import kotlin.test.Test
import kotlin.test.assertIs

class MultiAnnotationsTest {

    data class TestUser(
        @Length(36)
        val id: String,

        @Min(2)
        @Max(12)
        val name: String,

        @Email
        val email: String,

        @IP
        val ipAddress: String,
    )

    @Test
    fun `data class with multiple validation annotations and valid values`() {
        val user = TestUser(
            id = UUID.randomUUID().toString(),
            name = "John",
            email = "john@example.com",
            ipAddress = "192.168.0.1",
        )
        val res = validate<TestUser>(user)
        assertIs<ValidationResult.Valid>(res)
    }

    @Test
    fun `data class with multiple validation annotations and invalid values`() {
        val user = TestUser(
            id = UUID.randomUUID().toString() + "x",
            name = "J",
            email = "invalid_email",
            ipAddress = "invalid_ip",
        )
        val res = validate<TestUser>(user)
        assertIs<ValidationResult.Invalid>(res)
    }

    @Test
    fun `data class with multiple validation annotations and invalid values (2)`() {
        val user = TestUser(
            id = "xyz-123",
            name = "John the second",
            email = "invalid_email",
            ipAddress = "invalid_ip",
        )
        val res = validate<TestUser>(user)
        assertIs<ValidationResult.Invalid>(res)
    }

}
