plugins {
    id("net.neoforged.moddev") version "2.0.140"
    id("neoforge-mutex")
    id("me.modmuss50.mod-publish-plugin") version "2.2.0"
}

version = "${property("mod.version")}+${sc.current.version}-neoforge"
base.archivesName = property("mod.id") as String

val requiredJava = when {
    sc.current.parsed >= "26.1" -> JavaVersion.VERSION_25
    sc.current.parsed >= "1.20.5" -> JavaVersion.VERSION_21
    sc.current.parsed >= "1.18" -> JavaVersion.VERSION_17
    sc.current.parsed >= "1.17" -> JavaVersion.VERSION_16
    else -> JavaVersion.VERSION_1_8
}

repositories {
    /**
     * Restricts dependency search of the given [groups] to the [maven URL][url],
     * improving the setup speed.
     */
    fun strictMaven(url: String, alias: String, vararg groups: String) = exclusiveContent {
        forRepository { maven(url) { name = alias } }
        filter { groups.forEach(::includeGroup) }
    }
    strictMaven("https://www.cursemaven.com", "CurseForge", "curse.maven")
    strictMaven("https://api.modrinth.com/maven", "Modrinth", "maven.modrinth")
    maven("https://thedarkcolour.github.io/KotlinForForge/")
    maven("https://maven.quiltmc.org/repository/release/")
    maven("https://api.modrinth.com/maven")
    maven("https://maven.terraformersmc.com/releases/")
    maven("https://gitlab.com/api/v4/projects/21830712/packages/maven")
    maven("https://maven.isxander.dev/releases")
    maven("https://dl.cloudsmith.io/public/tslat/sbl/maven/")
    maven("https://maven.parchmentmc.org")
    maven("https://maven.shedaniel.me/")
    maven("https://repo.lucko.me/")
}

dependencies {
    // Architectury
//    runtimeOnly("dev.architectury:architectury-neoforge:${property("deps.architectury")}")

    // Cloth Config
    runtimeOnly("me.shedaniel.cloth:cloth-config-neoforge:${property("deps.cloth_config")}")

    // Debug Utils
    implementation("io.github.flemmli97:debugutils:${property("deps.debugutils")}-neoforge")

    // Kotlin For Forge
//    runtimeOnly("maven.modrinth:kotlin-for-forge:${property("deps.kotlin_for_forge")}")

    // NBT Autocomplete
    runtimeOnly("maven.modrinth:nbt-autocomplete:${property("deps.nbt_autocomplete")}-neoforge,1.21.1")

    // Observable
//    runtimeOnly("maven.modrinth:observable:${property("deps.observable")}+neoforge")

    // Smart Brain Lib
    implementation("net.tslat.smartbrainlib:SmartBrainLib-neoforge-${sc.current.version}:${property("deps.sbl")}")

    // Spark
//    runtimeOnly("me.lucko:fabric-permissions-api:${property("deps.fabric_permissions_api")}")
//    runtimeOnly("maven.modrinth:spark:${property("deps.spark")}-neoforge")

    // Suggestion Tweaker
    runtimeOnly("maven.modrinth:suggestion-tweaker:${property("deps.suggestion_tweaker")}+neoforge")

    // Yet Another Config Lib
    implementation("dev.isxander:yet-another-config-lib:${property("deps.yacl")}-neoforge")
}

neoForge {
    version = property("deps.neo_loader") as String

    val atFile = rootProject.file("src/main/resources/META-INF/accesstransformer.cfg")
    accessTransformers.from(sc.process(atFile, "build/processed.cfg"))

    parchment {
        minecraftVersion = sc.current.version
        mappingsVersion = property("deps.parchment") as String
    }

    mods {
        register("fowlplay") {
            sourceSet(sourceSets.main.get())
        }
    }

    runs {
        register("client") {
            gameDirectory = file("../../run/")
            client()
        }

        register("server") {
            gameDirectory = file("../../run/")
            server()
        }
    }
}

java {
    withSourcesJar()
    targetCompatibility = requiredJava
    sourceCompatibility = requiredJava
}

tasks {
    processResources {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        fun MutableMap<String, String>.register(key: String, property: String) {
            val value: String = sc.properties[property]
            inputs.property(key, value)
            set(key, value)
        }

        val props = buildMap {
            register("id", "mod.id")
            register("name", "mod.name")
            register("desc", "mod.desc")
            register("version", "mod.version")
            register("minecraft", "mod.mc_compat")
            register("neoforge", "deps.neo_loader")
            register("sbl", "deps.sbl")
            register("yacl", "deps.yacl")
        }

        filesMatching("META-INF/neoforge.mods.toml") { expand(props) }

        val mixinJava = "JAVA_${requiredJava.majorVersion}"
        filesMatching("*.mixins.json") { expand("java" to mixinJava) }

        exclude("fabric.mod.json", "*.ct", "*.classtweaker")
    }

    named("createMinecraftArtifacts") {
        dependsOn("stonecutterGenerate")
    }

    register<Copy>("buildAndCollect") {
        group = "build"
        description = "Builds mod jars and copies results to `build/libs/{mod version}/`"

        inputs.property("version", project.property("mod.version"))
        from(jar.flatMap { it.archiveFile }, named<Jar>("sourcesJar").flatMap { it.archiveFile })
        into(rootProject.layout.buildDirectory.file("libs/${project.property("mod.version")}"))
    }
}

publishMods {
    maxRetries.set(5)
    displayName = "${property("mod.name")} ${property("mod.version")}"
    file = tasks.jar.flatMap { it.archiveFile }
    changelog = providers.fileContents(
        rootProject.layout.projectDirectory.file(
            "RELEASE_CHANGELOG.md"
        )
    ).asText
    type = STABLE
    modLoaders.add("neoforge")

    curseforge {
        projectId = providers.environmentVariable("CURSEFORGE_ID")
            .orElse("0")
        accessToken = providers.environmentVariable("CURSEFORGE_TOKEN")
        minecraftVersions.add(sc.current.version)
        client = true
        server = true
        requires(
            "smartbrainlib",
            "yacl"
        )
    }
    modrinth {
        projectId = providers.environmentVariable("MODRINTH_ID")
            .orElse("0")
        accessToken = providers.environmentVariable("MODRINTH_TOKEN")
        minecraftVersions.add(sc.current.version)
        environment = CLIENT_AND_SERVER
        requires(
            "smartbrainlib",
            "yacl"
        )
    }
}
