val ktorVersion: String by project
val kotlinVersion: String by project
val kotlinWrappersVersion: String by project
val serializationVersion: String by project
val koinVersion: String by project
val kmongoVersion: String by project
val logbackVersion: String by project

plugins {
    application
    kotlin("multiplatform") version "1.8.10"
    kotlin("plugin.serialization") version "1.8.10"
}

group = "com.tsato"
version = "0.0.1"

kotlin {
    jvm {
        withJava()
    }
    js(IR) {
        browser {
            binaries.executable()
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val jvmMain by getting {
            dependencies {
                // Ktor
                implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
                implementation("io.ktor:ktor-server-auth-jvm:$ktorVersion")
                implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktorVersion")
                implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
                implementation("io.ktor:ktor-server-call-logging-jvm:$ktorVersion")
                implementation("io.ktor:ktor-server-cors:$ktorVersion")
                implementation("io.ktor:ktor-server-html-builder:$ktorVersion")
                implementation("io.ktor:ktor-server-compression:$ktorVersion")
                implementation("io.ktor:ktor-server-call-id-jvm:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")

                // log
                implementation("ch.qos.logback:logback-classic:$logbackVersion")

                // Koin core features
                implementation("io.insert-koin:koin-ktor:$koinVersion")
                implementation("io.insert-koin:koin-logger-slf4j:$koinVersion")

                // MongoDB
                implementation("org.litote.kmongo:kmongo:$kmongoVersion")
                implementation("org.litote.kmongo:kmongo-coroutine:$kmongoVersion")
                implementation("org.litote.kmongo:kmongo-coroutine-serialization:$kmongoVersion")

                // Apache
                implementation("commons-codec:commons-codec:1.15")
            }
        }

        val jvmTest by getting {
            dependencies {
//                testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
//                testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
            }
        }

        val jsMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-js:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation(project.dependencies.enforcedPlatform("org.jetbrains.kotlin-wrappers:kotlin-wrappers-bom:$kotlinWrappersVersion"))
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion")
            }
        }
    }
}

application {
    mainClass.set("com.tsato.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

tasks.named<Copy>("jvmProcessResources") {
    val jsBrowserDistribution = tasks.named("jsBrowserDistribution")
    from(jsBrowserDistribution)
}

tasks.named<JavaExec>("run") {
    dependsOn(tasks.named<Jar>("jvmJar"))
    classpath(tasks.named<Jar>("jvmJar"))
}