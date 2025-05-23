import com.android.build.api.dsl.androidLibrary
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.sqlDelight)
    alias(libs.plugins.skie)
}

kotlin {
    androidLibrary {
        namespace = "com.russhwolf.todo.shared"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        withHostTest {
        }

        compilations.configureEach {
            compilerOptions.configure {
                jvmTarget = JvmTarget.JVM_17
            }
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64(),
        iosX64()
    ).forEach {
        it.binaries.framework {
            baseName = "Shared"
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.sqlDelight.coroutines)
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))

                implementation(libs.kotlinx.coroutines.test)
                implementation(libs.turbine)
            }
        }
        androidMain {
            dependencies {
                implementation(libs.sqlDelight.android)
            }
        }
        val androidHostTest by getting {
            dependencies {
                implementation(libs.sqlDelight.jvm)
            }
        }
        iosMain {
            dependencies {
                implementation(libs.sqlDelight.native)
            }
        }
    }
}

sqldelight {
    databases {
        create("ToDoDatabase") {
            packageName.set("com.russhwolf.todo.shared.db")
        }
    }
}

skie {
    features {
        enableSwiftUIObservingPreview = true
    }
    analytics {
        // Disabled so people who clone don't accidentally send analytics without knowing
        // TODO Remove this line to support SKIE development by sending usage analytics
        // See https://skie.touchlab.co/Analytics for more details
        enabled = false
    }
}
