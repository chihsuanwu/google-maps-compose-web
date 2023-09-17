plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

fun kotlinw(target: String): String = "org.jetbrains.kotlin-wrappers:kotlin-$target"

val kotlinWrappersVersion = "1.0.0-pre.624"

kotlin {
    js(IR) {
        browser {
            testTask {
                testLogging.showStandardStreams = true
                useKarma {
                    useChromeHeadless()
                    useFirefox()
                }
            }
        }
        binaries.executable()
    }
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
                implementation(kotlinw("js"))

                implementation(compose.html.core)
                implementation(compose.runtime)

                implementation("app.softwork:routing-compose:0.2.12")

                implementation(project(":maps-compose-web"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}

dependencies {
    "jsMainImplementation"(enforcedPlatform(kotlinw("wrappers-bom:$kotlinWrappersVersion")))
}