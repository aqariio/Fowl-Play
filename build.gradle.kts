plugins {
    id("dev.isxander.modstitch.base") version "0.5.12"
}

val loader = when {
    modstitch.isLoom -> "fabric"
    modstitch.isModDevGradleRegular -> "neoforge"
    else -> throw IllegalStateException("Unsupported loader")
}

val mcVersion = property("deps.minecraft") as String

fun prop(name: String, consumer: (prop: String) -> Unit) {
    (findProperty(name) as? String?)
        ?.let(consumer)
}

modstitch {
    minecraftVersion = mcVersion

    // Alternatively use stonecutter.eval if you have a lot of versions to target.
    // https://stonecutter.kikugie.dev/stonecutter/guide/setup#checking-versions
    javaTarget = when(mcVersion) {
        "1.20.1" -> 17
        "1.21.1" -> 21
        else -> throw IllegalArgumentException("Please store the java version for ${property("deps.minecraft")} in build.gradle.kts!")
    }

    // If parchment doesnt exist for a version yet you can safely
    // omit the "deps.parchment" property from your versioned gradle.properties
    parchment {
        prop("deps.parchment") { mappingsVersion = it }
    }

    // This metadata is used to fill out the information inside
    // the metadata files found in the templates folder.
    metadata {
        modId = "fowlplay"
        modName = "Fowl Play"
        modVersion = "1.2.1"
        modGroup = "aqario.fowlplay"
        modAuthor = "aqario"
        modDescription = "Just smile and wave, boys. Smile and wave."
        modLicense = "MIT"

        fun <K, V> MapProperty<K, V>.populate(block: MapProperty<K, V>.() -> Unit) {
            block()
        }

        replacementProperties.populate {
            // You can put any other replacement properties/metadata here that
            // modstitch doesn't initially support. Some examples below.
            put("mod_issue_tracker", "https://github.com/aqariio/Fowl-Play/issues")
            put(
                "pack_format", when(property("deps.minecraft")) {
                    "1.20.1" -> 15
                    "1.21.1" -> 34
                    else -> throw IllegalArgumentException("Please store the resource pack version for ${property("deps.minecraft")} in build.gradle.kts! https://minecraft.wiki/w/Pack_format")
                }.toString()
            )
        }

        loom {
            prop("deps.fabric_loader") { fabricLoaderVersion = it }

            configureLoom {
                runs {
                    all {
                        runDir = "../../run"
                        ideConfigGenerated(true)
                    }

                    if(loader == "fabric") {
                        create("datagenClient") {
                            client()
                            name = "Data Generation Client"
                            vmArg("-Dfabric-api.datagen")
                            vmArg(
                                "-Dfabric-api.datagen.output-dir=" + project.rootDir.toPath()
                                    .resolve("src/main/generated")
                            )
                            vmArg("-Dfabric-api.datagen.modid=sounds")
                            runDir = "build/datagen"
                        }
                    }
                }
            }
        }

        moddevgradle {
            enable {
                prop("deps.neoforge") { neoForgeVersion = it }
            }

            defaultRuns()
            configureNeoforge {
                runs.all {
//                    disableIdeRun()
                    gameDirectory = file("../../run")
                }
            }
        }
    }

    mixin {
        // You do not need to specify mixins in any mods.json/toml file if this is set to
        // true, it will automatically be generated.
        addMixinsToModManifest = true

        configs.register("fowlplay")

        // Most of the time you wont ever need loader specific mixins.
        // If you do, simply make the mixin file and add it like so for the respective loader:
        // if (isLoom) configs.register("fowlplay-fabric")
        // if (isModDevGradleRegular) configs.register("fowlplay-neoforge")
        // if (isModDevGradleLegacy) configs.register("fowlplay-forge")
    }
}

// Stonecutter constants for mod loaders.
// See https://stonecutter.kikugie.dev/stonecutter/guide/comments#condition-constants
var constraint: String = name.split("-")[1]
stonecutter {
    consts(
        "fabric" to constraint.equals("fabric"),
        "neoforge" to constraint.equals("neoforge"),
        "forge" to constraint.equals("forge")
    )
//    replacements.string(current.parsed >= "1.21") {
//        replace("io.github.me", "dev.me")
//        // ...additional replacements
//    }
}

// All dependencies should be specified through modstitch's proxy configuration.
// Wondering where the "repositories" block is? Go to "stonecutter.gradle.kts"
// If you want to create proxy configurations for more source sets, such as client source sets,
// use the modstitch.createProxyConfigurations(sourceSets["client"]) function.
dependencies {
    modstitch.loom {
        // Fabric API
        modstitchModImplementation("net.fabricmc.fabric-api:fabric-api:${property("deps.fabric_api")}")

        // Fabric Language Kotlin
        modstitchModRuntimeOnly("net.fabricmc:fabric-language-kotlin:${property("deps.fabric_language_kotlin")}")

        // Mod Menu
        modstitchModImplementation("com.terraformersmc:modmenu:${property("deps.modmenu")}")
    }

    modstitch.moddevgradle {
        // Kotlin For Forge
        modstitchModRuntimeOnly("maven.modrinth:kotlin-for-forge:${property("deps.kotlin_for_forge")}")
    }

    // Cloth Config
    modstitchModRuntimeOnly("me.shedaniel.cloth:cloth-config-${loader}:${property("deps.cloth_config")}")

    // Debug Utils
    modstitchModImplementation("io.github.flemmli97:debugutils:${property("deps.debugutils")}-${loader}")

    // NBT Autocomplete
    modstitchModRuntimeOnly("maven.modrinth:nbt-autocomplete:${property("deps.nbt_autocomplete")}-${loader},1.21.1")

    // Observable
    modstitchModRuntimeOnly("maven.modrinth:observable:${property("deps.observable")}+${loader}")

    // Smart Brain Lib
    modstitchModImplementation("net.tslat.smartbrainlib:SmartBrainLib-${loader}-${mcVersion}:${property("deps.sbl")}")

    // Spark
    modstitchModRuntimeOnly("me.lucko:fabric-permissions-api:${property("deps.fabric_permissions_api")}")
    modstitchModRuntimeOnly("maven.modrinth:spark:${property("deps.spark")}-${loader}")

    // Suggestion Tweaker
    modstitchModRuntimeOnly("maven.modrinth:suggestion-tweaker:${property("deps.suggestion_tweaker")}+${loader}")

    // Yet Another Config Lib
    modstitchModImplementation("dev.isxander:yet-another-config-lib:${property("deps.yacl")}-${loader}")
}

sourceSets {
    main {
        resources {
            srcDir(file("src/main/generated"))
        }
    }
}