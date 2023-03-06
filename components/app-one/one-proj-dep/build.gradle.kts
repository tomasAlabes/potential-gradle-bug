plugins {
    kotlin("jvm") version "1.8.0"
    application
}

version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("app-two:two-proj:two-proj-in") // <- resolves dependency but doesn't compile
}

kotlin {
    jvmToolchain(11)
}

application {
    mainClass.set("MainKt")
}