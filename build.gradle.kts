import java.util.Optional
import java.util.function.BiConsumer
import java.util.function.Consumer
import java.util.function.Predicate

// Baseline code. Minimal edits necessary.
// TODO acknowledge that you add plugins here.
plugins {
    `maven-publish`
    kotlin("jvm") version "1.9.22"
    //id("fabric-loom") // Leaving this here if you want to swap loom.
    id("dev.architectury.loom")
    //id("dev.kikugie.j52j") // Recommended by kiku if using swaps in json5.
    id("me.modmuss50.mod-publish-plugin")
}
// Leave this alone unless adding more dependencies.
// TODO acknowledge that you add dependency repositories here.
repositories {
    mavenCentral()
    exclusiveContent {
        forRepository { maven("https://www.cursemaven.com") { name = "CurseForge" } }
        filter { includeGroup("curse.maven") }
    }
    exclusiveContent {
        forRepository { maven("https://api.modrinth.com/maven") { name = "Modrinth" } }
        filter { includeGroup("maven.modrinth") }
    }
    maven("https://maven.neoforged.net/releases/")
    maven("https://maven.architectury.dev/")
    maven("https://modmaven.dev/")
    maven("https://panel.ryuutech.com/nexus/repository/maven-releases/")
}

fun bool(str: String): Boolean {
    return str.lowercase().startsWith("t")
}

fun boolProperty(key: String): Boolean {
    if(!hasProperty(key)) {
        return false
    }
    return bool(property(key).toString())
}

fun listProperty(key: String): ArrayList<String> {
    if(!hasProperty(key)) {
        return arrayListOf()
    }
    val str = property(key).toString()
    if(str == "UNSET") {
        return arrayListOf()
    }
    return ArrayList(str.split(" "))
}

fun optionalStrProperty(key: String): Optional<String> {
    if(!hasProperty(key)) {
        return Optional.empty()
    }
    val str = property(key).toString()
    if(str == "UNSET") {
        return Optional.empty()
    }
    return Optional.of(str)
}

class VersionRange(val min: String, val max: String) {
    fun asForgelike(): String {
        return "${if(min.isEmpty()) "(" else "["}${min},${max}${if(max.isEmpty()) ")" else "]"}"
    }

    fun asFabric(): String {
        var out = ""
        if(min.isNotEmpty()) {
            out += ">=$min"
        }
        if(max.isNotEmpty()) {
            if(out.isNotEmpty()) {
                out += " "
            }
            out += "<=$max"
        }
        return out
    }
}

/**
 * Creates a VersionRange from a listProperty
 */
fun versionProperty(key: String): VersionRange {
    if(!hasProperty(key)) {
        return VersionRange("", "")
    }
    val list = listProperty(key)
    for(i in 0 until list.size) {
        if(list[i] == "UNSET") {
            list[i] = ""
        }
    }
    return if(list.isEmpty()) {
        VersionRange("", "")
    }
    else if(list.size == 1) {
        VersionRange(list[0], "")
    }
    else {
        VersionRange(list[0], list[1])
    }
}

/**
 * Creates a VersionRange unless the value is UNSET
 */
fun optionalVersionProperty(key: String): Optional<VersionRange> {
    val str = optionalStrProperty(key)
    if(!hasProperty(key)) {
        return Optional.empty()
    }
    if(!str.isPresent) {
        return Optional.empty()
    }
    return Optional.of(versionProperty(key))
}

enum class EnvType {
    FABRIC,
    FORGE,
    NEOFORGE
}

/**
 * Stores core dependency and environment information.
 */
class Env {
    val archivesBaseName = property("archives_base_name").toString()
    val mcVersion = versionProperty("deps.core.mc.version_range")
    val loader = property("loom.platform").toString()
    val isFabric = loader == "fabric"
    val isForge = loader == "forge"
    val isNeo = loader == "neoforge"
    val isCommon = project.parent!!.name == "common"
    val isApi = project.parent!!.name == "api"
    val type = if(isFabric) EnvType.FABRIC else if(isForge) EnvType.FORGE else EnvType.NEOFORGE

    // TODO: if MC requires higher JVMs in future updates change this controller.
    val javaVer = if(atMost("1.16.5")) 8 else if(atMost("1.20.4")) 17 else 21
    val yarnVersion = property("deps.mappings.yarn.version").toString()
    val yarnPatchVersion = property("deps.mappings.yarn_patch.version").toString()
    val fabricLoaderVersion = versionProperty("deps.core.fabric.loader.version_range")
    val forgeMavenVersion = versionProperty("deps.core.forge.version_range")
    val forgeVersion = VersionRange(extractForgeVer(forgeMavenVersion.min), extractForgeVer(forgeMavenVersion.max))
    val neoforgeVersion = versionProperty("deps.core.neoforge.version_range")

    // The modloader system is separate from the API in Neo
    val neoforgeLoaderVersion = versionProperty("deps.core.neoforge.loader.version_range")

    fun atLeast(version: String) = stonecutter.compare(mcVersion.min, version) >= 0
    fun atMost(version: String) = stonecutter.compare(mcVersion.min, version) <= 0
    fun isNot(version: String) = stonecutter.compare(mcVersion.min, version) != 0
    fun isExact(version: String) = stonecutter.compare(mcVersion.min, version) == 0

    private fun extractForgeVer(str: String): String {
        val split = str.split("-")
        if(split.size == 1) {
            return split[0]
        }
        if(split.size > 1) {
            return split[1]
        }
        return ""
    }
}

val env = Env()

enum class DepType {
    API,

    // Optional API
    API_OPTIONAL {
        override fun isOptional(): Boolean {
            return true
        }
    },

    // Implementation
    IMPL,

    // Forge Runtime Library
    FRL {
        override fun includeInDepsList(): Boolean {
            return false
        }
    },

    // Implementation and Included in output jar.
    INCLUDE {
        override fun includeInDepsList(): Boolean {
            return false
        }
    };

    open fun isOptional(): Boolean {
        return false
    }

    open fun includeInDepsList(): Boolean {
        return true
    }
}

class APIModInfo(val modid: String?, val curseSlug: String?, val rinthSlug: String?) {
    constructor () : this(null, null, null)
    constructor (modid: String) : this(modid, modid, modid)
    constructor (modid: String, slug: String) : this(modid, slug, slug)
}

/**
 * APIs must have a maven source.
 * If the version range is not present then the API will not be used.
 * If modid is null then the API will not be declared as a dependency in uploads.
 * The enable condition determines whether the API will be used for this version.
 */
class APISource(
    val type: DepType,
    val modInfo: APIModInfo,
    val mavenLocation: String,
    val versionRange: Optional<VersionRange>,
    private val enableCondition: Predicate<APISource>
) {
    val enabled = this.enableCondition.test(this)
}

/**
 * APIs with hardcoded support for convenience. These are optional.
 */
//TODO add any hardcoded apis here. Hardcoded APIs should be used in most if not all your versions.
val apis = arrayListOf(
    APISource(
        DepType.API,
        APIModInfo(if(env.atMost("1.16.5")) "fabric" else "fabric-api", "fabric-api"),
        "net.fabricmc.fabric-api:fabric-api",
        optionalVersionProperty("deps.api.fabric")
    ) { src ->
        src.versionRange.isPresent && env.isFabric
    },
    APISource(
        DepType.API,
        APIModInfo("architectury", "architectury-api"),
        "${if(env.atLeast("1.18.0")) "dev.architectury" else "me.shedaniel"}:architectury-${env.loader}",
        optionalVersionProperty("deps.api.architectury")
    )
    { src ->
        src.versionRange.isPresent
    }
)

// Stores information about the mod itself.
class ModProperties {
    val id = property("mod.id").toString()
    val displayName = property("mod.display_name").toString()
    val version = property("version").toString()
    val description = optionalStrProperty("mod.description").orElse("")
    val authors = property("mod.authors").toString()
    val icon = property("mod.icon").toString()
    val issueTracker = optionalStrProperty("mod.issue_tracker").orElse("")
    val license = optionalStrProperty("mod.license").orElse("")
    val sourceUrl = optionalStrProperty("mod.source_url").orElse("")
    val generalWebsite = optionalStrProperty("mod.general_website").orElse(sourceUrl)
}

/**
 * Stores information specifically for fabric.
 * Fabric requires that the mod's client and common main() entry points be included in the fabric.mod.json file.
 */
class ModFabric {
    val commonEntry = "${group}.${env.archivesBaseName}.fabric.${property("mod.fabric.entry.common").toString()}"
    val clientEntry = "${group}.${env.archivesBaseName}.fabric.${property("mod.fabric.entry.client").toString()}"
}

/**
 * Provides access to the mixins for specific environments.
 * All environments are provided the vanilla mixin if it is enabled.
 */
class ModMixins {
    val enableVanillaMixin = boolProperty("mixins.vanilla.enable")
    val enableFabricMixin = boolProperty("mixins.fabric.enable")
    val enableForgeMixin = boolProperty("mixins.forge.enable")
    val enableNeoforgeMixin = boolProperty("mixins.neoforge.enable")
    val vanillaMixin = "${mod.id}.mixins.json"
    val fabricMixin = "${mod.id}.fabric.mixins.json"
    val forgeMixin = "${mod.id}.forge.mixins.json"
    val neoForgeMixin = "${mod.id}.neoforge.mixins.json"
    val extraMixins = listProperty("mixins.extras")

    /**
     * Modify this method if you need better control over the mixin list.
     */
    fun getMixins(env: EnvType): List<String> {
        val out = arrayListOf<String>()
        if(enableVanillaMixin) out.add(vanillaMixin)
        when(env) {
            EnvType.FABRIC -> if(enableFabricMixin) out.add(fabricMixin)
            EnvType.FORGE -> if(enableForgeMixin) out.add(forgeMixin)
            EnvType.NEOFORGE -> if(enableNeoforgeMixin) out.add(neoForgeMixin)
        }
        return out
    }
}
//TODO acknowledge this controller and the relevant API tokens if you intend to auto-publish (HIGHLY RECOMMENDED)
//TODO acknowledge that with high version count Modrinth will probably rate limit you. If this is the case you should email them to ask for assistance.
/**
 * Controls publishing. For publishing to work dryRunMode must be false.
 * Modrinth and Curseforge project tokens are publicly accessible, so it is safe to include them in files.
 * Do not include your API keys in your project!
 *
 * The Modrinth API token should be stored in the MODRINTH_TOKEN environment variable.
 * The curseforge API token should be stored in the CURSEFORGE_TOKEN environment variable.
 */
class ModPublish {
    val mcTargets = arrayListOf<String>()
    val modrinthProjectToken = property("publish.token.modrinth").toString()
    val curseforgeProjectToken = property("publish.token.curseforge").toString()
    val mavenURL = optionalStrProperty("publish.maven.url")
    val dryRunMode = boolProperty("publish.dry_run")

    init {
        val tempmcTargets = listProperty("publish_acceptable_mc_versions")
        if(tempmcTargets.isEmpty()) {
            mcTargets.add(env.mcVersion.min)
        }
        else {
            mcTargets.addAll(tempmcTargets)
        }
    }
}

val modPublish = ModPublish()

/**
 * These dependencies will be added to the fabric.mods.json, META-INF/neoforge.mods.toml, and META-INF/mods.toml file.
 */
class ModDependencies {
    val loadBefore = listProperty("deps.before")
    fun forEachAfter(cons: BiConsumer<String, VersionRange>) {
        forEachRequired(cons)
        forEachOptional(cons)
    }

    fun forEachBefore(cons: Consumer<String>) {
        loadBefore.forEach(cons)
    }

    fun forEachOptional(cons: BiConsumer<String, VersionRange>) {
        apis.forEach { src ->
            if(src.enabled && src.type.isOptional() && src.type.includeInDepsList()) src.versionRange.ifPresent { ver ->
                src.modInfo.modid?.let {
                    cons.accept(it, ver)
                }
            }
        }
    }

    fun forEachRequired(cons: BiConsumer<String, VersionRange>) {
        cons.accept("minecraft", env.mcVersion)
        if(env.isForge) {
            cons.accept("forge", env.forgeVersion)
        }
        if(env.isNeo) {
            cons.accept("neoforge", env.neoforgeVersion)
        }
        if(env.isFabric) {
            cons.accept("fabric", env.fabricLoaderVersion)
        }
        apis.forEach { src ->
            if(src.enabled && !src.type.isOptional() && src.type.includeInDepsList()) src.versionRange.ifPresent { ver ->
                src.modInfo.modid?.let {
                    cons.accept(it, ver)
                }
            }
        }
    }
}

val dependencies = ModDependencies()

/**
 * These values will change between versions and mod loaders. Handles generation of specific entries in mods.toml and neoforge.mods.toml
 */
class SpecialMultiversionedConstants {
    private val mandatoryIndicator = if(env.isNeo) "required" else "mandatory"
    val mixinField =
        if(env.atMost("1.20.4") && env.isNeo) neoForgeMixinField() else if(env.isFabric) fabricMixinField() else ""
    val forgelikeLoaderVer = if(env.isForge) env.forgeVersion.asForgelike() else env.neoforgeLoaderVersion.asForgelike()
    val forgelikeAPIVer = if(env.isForge) env.forgeVersion.asForgelike() else env.neoforgeVersion.asForgelike()
    val dependenciesField = if(env.isFabric) fabricDependencyList() else forgelikeDependencyField()
    val excludes = excludes0()
    private fun excludes0(): List<String> {
        val out = arrayListOf<String>()
        if(!env.isForge) {
            // NeoForge before 1.21 still uses the forge mods.toml :/ One of those goofy changes between versions.
            if(!env.isNeo || !env.atLeast("1.20.6")) {
                out.add("META-INF/mods.toml")
            }
        }
        if(!env.isFabric) {
            out.add("fabric.mod.json")
        }
        if(!env.isNeo) {
            out.add("META-INF/neoforge.mods.toml")
        }
        return out
    }

    private fun neoForgeMixinField(): String {
        var out = ""
        for(mixin in modMixins.getMixins(EnvType.NEOFORGE)) {
            out += "[[mixins]]\nconfig=\"${mixin}\"\n"
        }
        return out
    }

    private fun fabricMixinField(): String {
        val list = modMixins.getMixins(EnvType.FABRIC)
        if(list.isEmpty()) {
            return ""
        }
        else {
            var out = "  \"mixins\" : [\n"
            for((index, mixin) in list.withIndex()) {
                out += "    \"${mixin}\""
                if(index < list.size - 1) {
                    out += ","
                }
                out += "\n"
            }
            return "$out  ],"
        }
    }

    private fun fabricDependencyList(): String {
        var out = "  \"depends\":{"
        var useComma = false
        dependencies.forEachRequired { modid, ver ->
            if(useComma) {
                out += ","
            }
            out += "\n"
            out += "    \"${modid}\": \"${ver.asFabric()}\""
            useComma = true
        }
        return "$out\n  }"
    }

    private fun forgelikeDependencyField(): String {
        var out = ""
        dependencies.forEachBefore { modid ->
            out += forgedep(modid, VersionRange("", ""), "BEFORE", false)
        }
        dependencies.forEachOptional { modid, ver ->
            out += forgedep(modid, ver, "AFTER", false)
        }
        dependencies.forEachRequired { modid, ver ->
            out += forgedep(modid, ver, "AFTER", true)
        }
        return out
    }

    private fun forgedep(modid: String, versionRange: VersionRange, order: String, mandatory: Boolean): String {
        return "[[dependencies.${mod.id}]]\n" +
            "modId=\"${modid}\"\n" +
            "${mandatoryIndicator}=${mandatory}\n" +
            "versionRange=\"${versionRange.asForgelike()}\"\n" +
            "ordering=\"${order}\"\n" +
            "side=\"BOTH\"\n"
    }
}

val mod = ModProperties()
val modFabric = ModFabric()
val modMixins = ModMixins()
val dynamics = SpecialMultiversionedConstants()
//TODO: change this if you want your upload version format to be different (this is a highly recommended format)
version = "${mod.version}+${env.mcVersion.min}+${env.loader}"
group = property("group").toString()
// Adds both optional and required dependencies to stonecutter version checking.
dependencies.forEachAfter { mid, ver ->
    stonecutter.dependency(mid, ver.min)
}
apis.forEach { src ->
    src.modInfo.modid?.let {
        stonecutter.const(it, src.enabled)
        src.versionRange.ifPresent { ver ->
            stonecutter.dependency(it, ver.min)
        }
    }
}
//TODO: Add more stonecutter consts here.
stonecutter.const("fabric", env.isFabric)
stonecutter.const("forge", env.isForge)
stonecutter.const("neoforge", env.isNeo)

loom {
    silentMojangMappingsLicense()
    if(env.isForge) forge {
        for(mixin in modMixins.getMixins(EnvType.FORGE)) {
            mixinConfigs(
                mixin
            )
        }
    }

    decompilers {
        get("vineflower").apply { // Adds names to lambdas - useful for mixins
            options.put("mark-corresponding-synthetics", "1")
        }
    }

    runConfigs.all {
        ideConfigGenerated(stonecutter.current.isActive)
        vmArgs("-Dmixin.debug.export=true")
        runDir = "../../run"
    }
}
base { archivesName.set(env.archivesBaseName) }

dependencies {
    minecraft("com.mojang:minecraft:${env.mcVersion.min}")
    // TODO do you really want to use yarn though? Like what convenience does it even give you smh?
//    mappings(loom.officialMojangMappings())
    if(env.isFabric) {
        mappings("net.fabricmc:yarn:${env.yarnVersion}:v2")
        modImplementation("net.fabricmc:fabric-loader:${env.fabricLoaderVersion.min}")
    }
    if(env.isForge) {
        mappings("net.fabricmc:yarn:${env.yarnVersion}:v2")
        "forge"("net.minecraftforge:forge:${env.forgeMavenVersion.min}")
    }
    if(env.isNeo) {
        mappings(loom.layered {
            mappings("net.fabricmc:yarn:${env.yarnVersion}:v2")
            env.yarnPatchVersion.takeUnless { it.startsWith('[') }?.let {
                mappings("dev.architectury:yarn-mappings-patch-neoforge:$it")
            }
        })
        "neoForge"("net.neoforged:neoforge:${env.neoforgeVersion.min}")
    }

    apis.forEach { src ->
        if(src.enabled) {
            src.versionRange.ifPresent { ver ->
                if(src.type == DepType.API || src.type == DepType.API_OPTIONAL) {
                    modApi("${src.mavenLocation}:${ver.min}")
                }
                if(src.type == DepType.IMPL) {
                    modImplementation("${src.mavenLocation}:${ver.min}")
                }
                if(src.type == DepType.FRL && env.isForge) {
                    "forgeRuntimeLibrary"("${src.mavenLocation}:${ver.min}")
                }
                if(src.type == DepType.INCLUDE) {
                    modImplementation("${src.mavenLocation}:${ver.min}")
                    include("${src.mavenLocation}:${ver.min}")
                }
            }
        }
    }

    vineflowerDecompilerClasspath("org.vineflower:vineflower:1.10.1")
}

java {
    withSourcesJar()
    //TODO update this is newer java is ever required.
    val java =
        if(env.javaVer == 8) JavaVersion.VERSION_1_8 else if(env.javaVer == 17) JavaVersion.VERSION_17 else JavaVersion.VERSION_21
    targetCompatibility = java
    sourceCompatibility = java
}

/**
 * Replaces the normal copy task and post-processes the files.
 * Effectively renames datapack directories due to depluralization past 1.20.4.
 * TODO: acknowledge that you should not pluralize the directories listed in targets.
 */
abstract class ProcessResourcesExtension : ProcessResources() {
    @get:Input
    val autoPluralize = arrayListOf(
        "/data/minecraft/tags/block",
        "/data/minecraft/tags/entity_type",
        "/data/minecraft/tags/item",
        "/data/fowlplay/loot_table",
        "/data/fowlplay/recipe",
        "/data/fowlplay/tags/block",
        "/data/fowlplay/tags/entity_type",
        "/data/fowlplay/tags/item",
    )

    override fun copy() {
        super.copy()
        val root = destinationDir.absolutePath
        autoPluralize.forEach { path ->
            val file = File(root.plus(path))
            if(file.exists()) {
                file.copyRecursively(File(file.absolutePath.plus("s")), true)
                file.deleteRecursively()
            }
        }
    }
}

if(env.atMost("1.20.6")) {
    tasks.replace("processResources", ProcessResourcesExtension::class)
}

tasks.processResources {
    val map = mapOf<String, String>(
        "modid" to mod.id,
        "id" to mod.id,
        "name" to mod.displayName,
        "display_name" to mod.displayName,
        "version" to mod.version,
        "description" to mod.description,
        "authors" to mod.authors,
        "github_url" to mod.sourceUrl,
        "source_url" to mod.sourceUrl,
        "website" to mod.generalWebsite,
        "icon" to mod.icon,
        "fabric_common_entry" to modFabric.commonEntry,
        "fabric_client_entry" to modFabric.clientEntry,
        "mc_min" to env.mcVersion.min,
        "mc_max" to env.mcVersion.max,
        "issue_tracker" to mod.issueTracker,
        "java_ver" to env.javaVer.toString(),
        "forgelike_loader_ver" to dynamics.forgelikeLoaderVer,
        "forgelike_api_ver" to dynamics.forgelikeAPIVer,
        "loader_id" to env.loader,
        "license" to mod.license,
        "mixin_field" to dynamics.mixinField,
        "dependencies_field" to dynamics.dependenciesField
    )
    map.forEach { (key, value) ->
        inputs.property(key, value)
    }
    dynamics.excludes.forEach { file ->
        exclude(file)
    }
    filesMatching("pack.mcmeta") { expand(map) }
    filesMatching("fabric.mod.json") { expand(map) }
    filesMatching("META-INF/mods.toml") { expand(map) }
    filesMatching("META-INF/neoforge.mods.toml") { expand(map) }
    modMixins.getMixins(env.type).forEach { str ->
        filesMatching(str) { expand(map) }
    }
}
//TODO: Enable auto-publishing.
publishMods {
    file = tasks.remapJar.get().archiveFile
    additionalFiles.from(tasks.remapSourcesJar.get().archiveFile)
    displayName = "${mod.displayName} ${mod.version} for ${env.mcVersion.min}"
    version = mod.version
    changelog = rootProject.file("CHANGELOG.md").readText()
    type = STABLE
    modLoaders.add(env.loader)

    dryRun = modPublish.dryRunMode

    modrinth {
        projectId = modPublish.modrinthProjectToken
        // Get one here: https://modrinth.com/settings/pats, enable read, write, and create Versions ONLY!
        accessToken = providers.environmentVariable("MODRINTH_TOKEN")
        minecraftVersions.addAll(modPublish.mcTargets)
        apis.forEach { src ->
            if(src.enabled) src.versionRange.ifPresent { ver ->
                if(src.type.isOptional()) {
                    src.modInfo.rinthSlug?.let {
                        optional {
                            slug = it
                            version = ver.min
                        }
                    }
                }
                else {
                    src.modInfo.rinthSlug?.let {
                        requires {
                            slug = it
                            version = ver.min
                        }
                    }
                }
            }
        }
    }
    /*curseforge {
        projectId = modPublish.curseforgeProjectToken
        // Get one here: https://legacy.curseforge.com/account/api-tokens
        accessToken = providers.environmentVariable("CURSEFORGE_TOKEN")
        minecraftVersions.addAll(modPublish.mcTargets)
        apis.forEach{ src ->
            if(src.enabled) src.versionRange.ifPresent{ ver ->
                if(src.type.isOptional()){
                    src.modInfo.curseSlug?.let {
                        optional {
                            slug = it
                            version = ver.min

                        }
                    }
                }
                else{
                    src.modInfo.curseSlug?.let {
                        requires {
                            slug = it
                            version = ver.min
                        }
                    }
                }
            }
        }
    }*/
}
// TODO Disable if not uploading to a maven
/*
publishing {
    repositories {
        // TODO this is an example of how I recommend you do this.
        if(modPublish.mavenURL.isPresent) {
            maven {
                url = uri(modPublish.mavenURL.get())
                credentials {
                    username = System.getenv("MVN_NAME")
                    password = System.getenv("MVN_KEY")
                }
            }
        }
    }
    publications {
        create<MavenPublication>("mavenJava"){
            groupId = project.group.toString()
            artifactId = env.archivesBaseName
            version = project.version.toString()
            from(components["java"])
        }
    }
}*/