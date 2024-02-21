@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.jvm)
    `java-library`
}

group = "com.amedmoore"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    api(libs.ktor.server.request.validation)
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useKotlinTest("1.9.20")
        }
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}
