plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.sqlDelight)
    alias(libs.plugins.skie)
}

kotlin {
    androidTarget()
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
        val androidUnitTest by getting {
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

android {
    namespace = "com.russhwolf.todo.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
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
    analytics {
        // Disabled so people who clone don't accidentally send analytics without knowing
        // TODO Remove this line to support SKIE development by sending usage analytics
        // See https://skie.touchlab.co/Analytics for more details
        enabled = false
    }
}
