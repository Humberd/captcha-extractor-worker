import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    id("org.jetbrains.compose") version "1.2.1"
    application
    id("org.bytedeco.gradle-javacpp-platform") version "1.5.8"
}

ext {
    set("javacppPlatform", "windows-x86_64")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(compose.desktop.currentOs)
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}

kotlin {
    dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
        implementation("io.github.cdimascio:dotenv-kotlin:6.4.0")
        implementation("com.squareup.retrofit2:retrofit:2.9.0")
        implementation("com.squareup.retrofit2:converter-jackson:2.9.0")
        implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
        implementation("org.ktorm:ktorm-core:3.5.0")
        implementation("org.ktorm:ktorm-support-postgresql:3.5.0")
        implementation("io.github.microutils:kotlin-logging-jvm:3.0.2")
        implementation("org.slf4j:slf4j-simple:2.0.5")
        implementation("org.bytedeco:javacv-platform:1.5.8")
        implementation("org.bytedeco:opencv-platform-gpu:4.6.0-1.5.8")
        implementation("org.bytedeco:ffmpeg-platform-gpl:5.1.2-1.5.8")
    }
}

//compose.desktop {
//    application {
//        mainClass = "ComposeMainKt"
//    }
//}
