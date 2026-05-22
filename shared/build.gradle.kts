import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.sqlDelight)
}

kotlin {
    android {
        namespace = "com.russhwolf.todo.shared"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        withHostTest {
        }

        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }

    iosArm64()
    iosSimulatorArm64()
    iosX64()

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

    swiftExport {
        moduleName = "Shared"
        flattenPackage = "com.russhwolf.todo.shared"

        configure {
            freeCompilerArgs.add("-Xexpect-actual-classes")
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
