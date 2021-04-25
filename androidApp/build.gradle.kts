plugins {
    id("com.android.application")
    kotlin("android")
}

val composeVersion = "1.0.0-beta01"

dependencies {
    implementation(project(":shared"))
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")

    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")

    implementation("androidx.compose.foundation:foundation:$composeVersion")

    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.material:material-icons-core:$composeVersion")
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")

    implementation("androidx.activity:activity-compose:1.3.0-alpha07")

    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")

}

android {
    compileSdkVersion(30)
    defaultConfig {
        applicationId = "com.russhwolf.todo.androidApp"
        minSdkVersion(24)
        targetSdkVersion(30)
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "$composeVersion"
    }
}
