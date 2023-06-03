plugins {
    id("java")
    kotlin("jvm") version "1.8.20"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.minepalm"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://papermc.io/repo/repository/maven-public/")}
    maven { url = uri("https://maven.enginehub.org/repo/") }
    maven {
        name = "minepalm-snapshots"
        url = uri("https://nexus.minepalm.com/repository/maven-snapshots")
        credentials {
            username = project.properties["myNexusUsername"].toString()
            password = project.properties["myNexusPassword"].toString()
        }
    }
    maven { url = uri("https://repo.aikar.co/content/groups/aikar/") }
    mavenLocal()
    //jitpack
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    compileOnly(kotlin("stdlib"))
    compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")
    compileOnly("com.minepalm:GUIPreset:1.0-SNAPSHOT")
    implementation(group = "com.minepalm", name = "arkarangutils-bukkit", version = "1.3-SNAPSHOT")
    implementation("co.aikar:acf-bukkit:0.5.1-SNAPSHOT")
    implementation("net.milkbowl.vault:Vault:1.7.3")
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.compileKotlin {
    kotlinOptions {
        jvmTarget = "17"
    }
}