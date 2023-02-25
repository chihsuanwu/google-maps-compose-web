plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")

    id("maven-publish")
}

group = "com.github.chihsuanwu"

fun kotlinw(target: String): String = "org.jetbrains.kotlin-wrappers:kotlin-$target"

val kotlinWrappersVersion = "1.0.0-pre.490"

kotlin {
    jvmToolchain(11)

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

                implementation(compose.web.core)
                implementation(compose.runtime)
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

tasks.withType<GenerateModuleMetadata> {
    // The value 'enforced-platform' is provided in the validation
    // error message you got
    suppressedValidationErrors.add("enforced-platform")
}
