buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.30")
        classpath("com.android.tools.build:gradle:7.0.0-alpha08")
        classpath("com.squareup.sqldelight:gradle-plugin:1.4.4")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter {
            content {
                includeModule("org.jetbrains.kotlinx", "kotlinx-collections-immutable-jvm")
            }
        }
    }
}
