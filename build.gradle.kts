import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("application")
    id("com.github.johnrengelman.shadow") version "7.1.1"
}

group = "com.michaelthesad.sadbot"
version = "1.3.1"

repositories {
    mavenCentral()
}

dependencies {
    //Change 'implementation' to 'compile' in old Gradle versions
    implementation("net.dv8tion:JDA:5.0.0-beta.22")
    implementation("io.github.cdimascio:dotenv-java:3.0.0")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("com.michaelthesad.sadbot.sadbot") }

tasks.withType<ShadowJar> {
    manifest{
        attributes(mapOf("Main-Class" to "com.michaelthesad.sadbot.sadbot"))

    }

}
