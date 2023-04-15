pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        kotlin("multiplatform") version "1.8.20"
        id("org.jetbrains.compose") version "1.4.0"
    }
}

rootProject.name = "google-maps-compose-web"

include(":maps-compose-web", ":example")
