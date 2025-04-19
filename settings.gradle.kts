import dev.kikugie.stonecutter.settings.StonecutterSettings

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.architectury.dev")
        maven("https://maven.minecraftforge.net/")
        maven("https://maven.neoforged.net/releases/")
        maven("https://repo.spongepowered.org/maven")
        maven("https://maven.kikugie.dev/snapshots")
        maven("https://maven.kikugie.dev/releases")
    }
    plugins {
        kotlin("jvm") version "1.9.24"
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.6-beta.1"
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
extensions.configure<StonecutterSettings> {
    kotlinController = true
    centralScript = "build.gradle.kts"
    // Disable any versions you don't want to support anb enable any versions you want to support
    // The versions listed here, commented out or otherwise, all have pre-made gradle.properties.
    shared {
        /*vers("1.16.5-fabric","1.16.5")
        vers("1.16.5-forge","1.16.5")
        vers("1.18.2-fabric","1.18.2")
        vers("1.18.2-forge","1.18.2")
        vers("1.19.2-fabric","1.19.2")
        vers("1.19.2-forge","1.19.2")
        vers("1.19.4-fabric","1.19.4")
        vers("1.19.4-forge","1.19.4")*/
        vers("1.20.1-fabric", "1.20.1")
        vers("1.20.1-forge", "1.20.1")
        /*vers("1.20.4-fabric","1.20.4")
        vers("1.20.4-forge","1.20.4")
        vers("1.20.4-neoforge","1.20.4")
        vers("1.20.6-fabric","1.20.6")
        vers("1.20.6-neoforge","1.20.6")
        vers("1.21-fabric","1.21")
        vers("1.21-neoforge","1.21")*/
        vers("1.21.1-fabric", "1.21.1")
        vers("1.21.1-neoforge", "1.21.1")
        /*vers("1.21.2+3-fabric","1.21.2")
        vers("1.21.2+3-neoforge","1.21.2")*/
        vcsVersion = "1.21.1-fabric"
    }
    create(rootProject)
}
// project display name
rootProject.name = "Fowl-Play"




