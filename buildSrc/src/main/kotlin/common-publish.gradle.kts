import me.modmuss50.mpp.PlatformDependency
import me.modmuss50.mpp.platforms.curseforge.CurseforgeDependency
import me.modmuss50.mpp.platforms.modrinth.ModrinthDependency

plugins {
    id("me.modmuss50.mod-publish-plugin")
}

val publishing = extensions.create<PlatformPublishingExtension>("platformPublish")

publishMods {
    maxRetries.set(5)
    displayName = "${property("mod.name")} ${property("mod.version")}"
    file = publishing.file
    modLoaders.addAll(publishing.modLoaders)
    changelog = providers.fileContents(
        rootProject.layout.projectDirectory.file("RELEASE_CHANGELOG.md")
    ).asText
    type = STABLE

    curseforge {
        projectId = variable("CURSEFORGE_ID").orElse("0")
        accessToken = variable("CURSEFORGE_TOKEN")
        minecraftVersions.addAll(publishing.minecraftVersions)
        client = true
        server = true
        dependencies.addAll(publishing.requiredDependencies.map { slugs ->
            slugs.map { slug ->
                objects.newInstance(CurseforgeDependency::class.java).apply {
                    this.slug.set(slug)
                    type.set(PlatformDependency.DependencyType.REQUIRED)
                }
            }
        })
    }

    modrinth {
        projectId = variable("MODRINTH_ID").orElse("0")
        accessToken = variable("MODRINTH_TOKEN")
        minecraftVersions.addAll(publishing.minecraftVersions)
        environment = CLIENT_AND_SERVER
        dependencies.addAll(publishing.requiredDependencies.map { slugs ->
            slugs.map { slug ->
                objects.newInstance(ModrinthDependency::class.java).apply {
                    this.slug.set(slug)
                    type.set(PlatformDependency.DependencyType.REQUIRED)
                }
            }
        })
    }

    dryRun = shouldDryRun()
}

fun variable(name: String) = providers.environmentVariable(name)

fun shouldDryRun(): Boolean {
    return variable("CURSEFORGE_TOKEN").orNull == null
        || variable("MODRINTH_TOKEN").orNull == null
}
