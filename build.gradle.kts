import java.io.BufferedReader
import java.io.FileReader

plugins {
    kotlin("jvm") version "1.9.23"
    java
}
val pluginName = "NewProfile"
group = "kr.doka.lab.newprofile"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://jitpack.io")
}

dependencies {
    testImplementation(kotlin("test"))
    compileOnly("io.papermc.paper:paper-api:1.20.6-R0.1-SNAPSHOT")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    // https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc
    compileOnly("org.xerial:sqlite-jdbc:3.46.0.0")

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
tasks {
    processResources {
        val file = File("/home/doka/IdeaProjects/NewProfile/version.txt")
        println(file.absolutePath)
        val reader = BufferedReader(FileReader(file, Charsets.UTF_8))

        var minor_version = 0
        var major_version = 1
        reader.lines().forEach {
            if (it.startsWith("major")) {
                major_version = it.split("=")[1].toInt()
            } else if (it.startsWith("minor")) {
                minor_version = it.split("=")[1].toInt()
            }
        }
        file.printWriter(Charsets.UTF_8).use {
            it.println("major=${major_version}")
            it.println("minor=${minor_version+1}")
        }
        println("$major_version.$minor_version")
        val placeholders = mapOf(
            "version" to "$major_version.$minor_version",
            "apiVersion" to "1.20",
            "kotlinVersion" to project.properties["kotlinVersion"],
        )
        filteringCharset = "UTF-8"
        filesMatching("paper-plugin.yml") {
            expand(placeholders)
        }
        filesMatching("plugin.yml") {
            expand(placeholders)
        }

    }
    jar {
        destinationDirectory = File("~/workspace/minecraft-server/plugins")
        archiveFileName.set("$pluginName.jar")
    }
}