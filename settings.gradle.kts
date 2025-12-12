rootProject.name = "cmp-sdui"

include(":app")
include(":sdui-common")
include(":sdui")
include(":app:composeApp")

project(":app").projectDir = file("packages/app")
project(":sdui-common").projectDir = file("packages/sdui-common")
project(":sdui").projectDir = file("packages/sdui")
project(":app:composeApp").projectDir = file("packages/app/composeApp")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
                includeGroupByRegex("android.*")
            }
        }
    }
    plugins {
        kotlin("jvm") version "2.2.20-RC" apply false
        kotlin("plugin.serialization") version "2.2.10"
        kotlin("multiplatform") version "2.2.20-RC" apply false
        id("com.android.application") version "8.13.0-rc01" apply false
        id("com.android.library") version "8.13.0-rc01" apply false
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0" apply false
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}