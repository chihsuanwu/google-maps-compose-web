pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        kotlin("multiplatform") version "1.9.10"
        id("org.jetbrains.compose") version "1.5.1"
    }
}

rootProject.name = "google-maps-compose-web"

include(":maps-compose-web", ":example")
