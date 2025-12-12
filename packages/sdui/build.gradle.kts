
plugins {
    id("org.springframework.boot") version "3.5.5"
    id("io.spring.dependency-management") version "1.1.7"

    id("com.android.application") apply false
    kotlin("multiplatform") apply false

    kotlin("jvm")
    kotlin("plugin.serialization")
}

group = "me.vxti"
version = "1.0"

dependencies {
    implementation(libs.kotlinx.serialization.json)

    implementation(platform(libs.bom))
    implementation(libs.dynamodb.enhanced)

    implementation(libs.spring.boot.starter.web)
    implementation(libs.jakarta.persistence.api)

    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.stdlib.jdk8)

    implementation(libs.commons.compress)

    implementation(libs.jackson.databind)
    implementation(libs.gson)
    implementation(libs.commons.dbcp2)
    implementation(libs.jbcrypt)

    implementation(kotlin("stdlib-jdk8"))

    implementation(project(":sdui-common")) {
        attributes {
            attribute(
                org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType.attribute,
                org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType.jvm
            )
        }
        capabilities {
            requireCapability("me.vxti:sdui-common")
        }
    }
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    enabled = true
}



kotlin {
    jvmToolchain(23)
}

sourceSets {
    named("main") {
        resources {
            srcDir("src/main/java")
            include("**/*.json")
            srcDir("src/main/resources")
        }
    }
}

springBoot {
    mainClass.set("me.vxti.Main")
}
