plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
}

allprojects {
    tasks.withType<AbstractTestTask> {
        testLogging {
            showStandardStreams = true
            events("passed", "failed")
        }
    }
}
