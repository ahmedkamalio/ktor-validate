@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.gradle.maven.publish)
    alias(libs.plugins.nmcp)
    `java-library`
}

group = property("GROUP").toString()
version = property("VERSION_NAME").toString()
description = property("DESCRIPTION").toString()
val artifactId = property("POM_ARTIFACT_ID").toString()

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

nmcp {
    publishAllPublications {
        username = System.getenv("MAVEN_USERNAME")
        password = System.getenv("MAVEN_PASSWORD")
        publicationType = "AUTOMATIC"
    }
}
