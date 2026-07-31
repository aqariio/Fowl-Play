import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty

abstract class PlatformPublishingExtension {
    abstract val file: RegularFileProperty
    abstract val modLoaders: ListProperty<String>
    abstract val minecraftVersions: ListProperty<String>
    abstract val requiredDependencies: ListProperty<String>
}
