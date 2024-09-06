plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
}

dependencies {
    implementation(project(":shared"))

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.tooling)

    implementation(libs.androidx.compose.foundation)

    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.materialIcons)
    implementation(libs.androidx.compose.materialIconsExtended)

    implementation(libs.androidx.activityCompose)

    androidTestImplementation(libs.androidx.compose.test)

}

android {
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    namespace = "com.russhwolf.todo.androidApp"
    defaultConfig {
        applicationId = "com.russhwolf.todo.androidApp"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}
