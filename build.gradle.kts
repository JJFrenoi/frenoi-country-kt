val logback_version: String by project
val prometheus_version: String by project
val mongodb_version: String by project
val koin_version: String by project

plugins {
    kotlin("jvm") version "2.0.20"
    id("io.ktor.plugin") version "3.0.0-rc-2"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.20"
    id("org.graalvm.buildtools.native") version "0.10.3"
}

group = "com.frenoi.country"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.cio.EngineMain")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-cors-jvm")
    implementation("io.ktor:ktor-server-hsts-jvm")
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-cio-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-config-yaml")
    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:$mongodb_version")
    implementation("io.ktor:ktor-server-metrics-micrometer")
    implementation("io.micrometer:micrometer-registry-prometheus:$prometheus_version")
    implementation("io.insert-koin:koin-ktor:$koin_version")
}

graalvmNative {
    binaries {
        named("main") {
            fallback.set(false)
            verbose.set(true)
            buildArgs.apply {
                add("--initialize-at-build-time=ch.qos.logback")
                add("--initialize-at-build-time=io.ktor,kotlin")
                add("--initialize-at-build-time=kotlinx.io")
                add("--initialize-at-build-time=kotlinx.serialization")
                add("--initialize-at-build-time=org.slf4j.LoggerFactory")
                add("--initialize-at-build-time=org.slf4j.helpers.Reporter")
                add("-H:+InstallExitHandlers")
                add("-H:+ReportUnsupportedElementsAtRuntime")
                add("-H:+ReportExceptionStackTraces")
            }
            imageName.set("frenoi-country")
        }
    }
}

