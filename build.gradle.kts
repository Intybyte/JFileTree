plugins {
    java
    application
    id("com.gradleup.shadow") version "9.2.2"
    kotlin("jvm") version "2.2.0"
}

group = "me.vaan"
version = "1.0.0"

java {
}

repositories {
    mavenCentral()

    maven {
        url = uri("https://central.sonatype.com/repository/maven-snapshots/")
        mavenContent {
            snapshotsOnly()
        }
    }
}


dependencies {
    implementation(platform("dev.tamboui:tamboui-bom:0.6.0-SNAPSHOT"))

    implementation("dev.tamboui:tamboui-toolkit")
    implementation("dev.tamboui:tamboui-image")

    implementation("dev.tamboui:tamboui-jline3-backend")
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = application.mainClass.get()
    }
}

application {
    mainClass.set("me.vaan.jfiletree.Main")
}

kotlin {
    jvmToolchain(21)
}