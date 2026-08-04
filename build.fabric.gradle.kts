plugins {
    // This plugin applies the correct loom variant based on the Minecraft version
    id("dev.kikugie.loom-back-compat")
    id("me.modmuss50.mod-publish-plugin") version "2.2.0"
}

// DO NOT set group = ...!
version = "${property("mod.version")}+${sc.current.version}-fabric"
base.archivesName = property("mod.id") as String

val requiredJava: JavaVersion = when {
    sc.current.parsed >= "26.1" -> JavaVersion.VERSION_25
    sc.current.parsed >= "1.20.5" -> JavaVersion.VERSION_21
    sc.current.parsed >= "1.18" -> JavaVersion.VERSION_17
    sc.current.parsed >= "1.17" -> JavaVersion.VERSION_16
    else -> JavaVersion.VERSION_1_8
}

// This can be used for publishing on Modrinth and Curseforge
val compatibleVersions: List<String> = sc.properties.rawOrNull("mod", "mc_releases")
    ?.asList().orEmpty().map { it.toString() }

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
    /**
     * Fetches only the required Fabric API modules to not waste time downloading all of them for each version.
     * @see <a href="https://github.com/FabricMC/fabric">List of Fabric API modules</a>
     */
    fun fapi(vararg modules: String) {
        for(it in modules) modImplementation(fabricApi.module(it, sc.properties["deps.fabric_api"]))
    }

    minecraft("com.mojang:minecraft:${sc.current.version}")
    mappings(
        loom.layered {
            officialMojangMappings()
            parchment("org.parchmentmc.data:parchment-${sc.current.version}:${property("deps.parchment")}@zip")
        }
    )

    // Use `mod{dependency type}` even on 26.1+ - loom-back-compat converts them

    // Fabric Loader
    modImplementation("net.fabricmc:fabric-loader:${property("deps.fabric_loader")}")

    // Fabric API
    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("deps.fabric_api")}")

    // Architectury
    modImplementation("dev.architectury:architectury-fabric:${property("deps.architectury")}")

    // Cloth Config
    modRuntimeOnly("me.shedaniel.cloth:cloth-config-fabric:${property("deps.cloth_config")}")

    // Debug Utils
    modImplementation("io.github.flemmli97:debugutils:${property("deps.debugutils")}-fabric")

    // Fabric Language Kotlin
    modRuntimeOnly("net.fabricmc:fabric-language-kotlin:${property("deps.fabric_language_kotlin")}")

    // Mod Menu
    modImplementation("com.terraformersmc:modmenu:${property("deps.modmenu")}")

    // NBT Autocomplete
    modRuntimeOnly("maven.modrinth:nbt-autocomplete:${property("deps.nbt_autocomplete")}-fabric,1.20.1")

    // Observable
    modRuntimeOnly("maven.modrinth:observable:${property("deps.observable")}+fabric") {
        isTransitive = false
    }

    // Smart Brain Lib
    modImplementation("net.tslat.smartbrainlib:SmartBrainLib-fabric-${sc.current.version}:${property("deps.sbl")}")

    // Spark
    modRuntimeOnly("me.lucko:fabric-permissions-api:${property("deps.fabric_permissions_api")}") {
        isTransitive = false
    }
    modRuntimeOnly("maven.modrinth:spark:${property("deps.spark")}-fabric") {
        isTransitive = false
    }

    // Suggestion Tweaker
    modRuntimeOnly("maven.modrinth:suggestion-tweaker:${property("deps.suggestion_tweaker")}+fabric")

    // Yet Another Config Lib
    modImplementation("dev.isxander:yet-another-config-lib:${property("deps.yacl")}-fabric")
}

fabricApi {
    configureDataGeneration {
        client = true
    }
}

loom {
    fabricModJsonPath = rootProject.file("src/main/resources/fabric.mod.json")
    accessWidenerPath = rootProject.file("src/main/resources/fowlplay.accesswidener")

    decompilerOptions.named("vineflower") {
        options.put("mark-corresponding-synthetics", "1") // Adds names to lambdas - useful for mixins
    }

    runConfigs.all {
        preferGradleTask = true
        generateRunConfig = true
        runDirectory = rootProject.file("run") // Shares the run directory between versions
        jvmArguments.add("-Dmixin.debug.export=true") // Exports transformed classes for debugging
    }
}

java {
    withSourcesJar()
    targetCompatibility = requiredJava
    sourceCompatibility = requiredJava

    toolchain {
        vendor = JvmVendorSpec.ADOPTIUM
        languageVersion = JavaLanguageVersion.of(requiredJava.majorVersion)
    }
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
            register("loader", "deps.fabric_loader")
            register("fapi", "deps.fabric_api")
            register("arch", "deps.architectury")
            register("sbl", "deps.sbl")
            register("yacl", "deps.yacl")
        }

        filesMatching("fabric.mod.json") { expand(props) }

        val mixinJava = "JAVA_${requiredJava.majorVersion}"
        filesMatching("*.mixins.json") { expand("java" to mixinJava) }

        exclude("META-INF/mods.toml")
    }

    register<Copy>("buildAndCollect") {
        group = "build"
        description = "Builds mod jars and copies results to `build/libs/{mod version}/`"

        inputs.property("version", project.property("mod.version"))
        // loomx.mod(Sources)Jar returns the jar task for the applied loom variant
        from(loomx.modJar.flatMap { it.archiveFile }, loomx.modSourcesJar.flatMap { it.archiveFile })
        into(rootProject.layout.buildDirectory.file("libs/${project.property("mod.version")}"))
    }
}

publishMods {
    maxRetries.set(5)
    displayName = "${property("mod.name")} ${property("mod.version")}"
    file = loomx.modJar.flatMap { it.archiveFile }
    changelog = providers.fileContents(
        rootProject.layout.projectDirectory.file(
            "RELEASE_CHANGELOG.md"
        )
    ).asText
    type = STABLE
    modLoaders.add("fabric")

    curseforge {
        projectId = providers.environmentVariable("CURSEFORGE_ID")
            .orElse("0")
        accessToken = providers.environmentVariable("CURSEFORGE_TOKEN")
        minecraftVersions.add(sc.current.version)
        client = true
        server = true
        requires(
            "architectury-api",
            "fabric-api",
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
            "architectury-api",
            "fabric-api",
            "smartbrainlib",
            "yacl"
        )
    }
}