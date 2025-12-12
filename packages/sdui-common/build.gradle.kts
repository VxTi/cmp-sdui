plugins {
    kotlin("multiplatform")
    id("com.android.library")

    // Required for Serialization
    kotlin("jvm") apply false
    kotlin("plugin.serialization")
}

group = "me.vxti"
version = "1.0"

kotlin {
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    jvm()
    androidTarget {}

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.serialization.json)
                api(libs.jackson.annotations)
            }
        }
    }
}

android {
    namespace = "me.vxti.sdui.common"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        testOptions {
            targetSdk = 34
        }
        lint {
            targetSdk = 34
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
