@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.jvm)
    `java-library`
    `maven-publish`
}

group = "com.amedmoore"
version = "0.0.1"
description = "Adds validation annotations support for Ktor and Request Validation plugin."

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

publishing {
    repositories {
        maven {
            name = rootProject.name
            url = uri("https://maven.pkg.github.com/amedmoore/${rootProject.name}")
            credentials {
                username = System.getenv("MAVEN_USERNAME")
                password = System.getenv("MAVEN_PASSWORD")
            }
        }
    }

    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}
