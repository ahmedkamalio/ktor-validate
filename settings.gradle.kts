@file:Suppress("SpellCheckingInspection")

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "ktor-validate"

include("lib")
project(":lib").name = rootProject.name
