plugins {
    id("dev.kikugie.stonecutter")
}
stonecutter active "1.21.1-fabric"

stonecutter registerChiseled tasks.register("chiseledBuild", stonecutter.chiseled) {
    group = "project"
    ofTask("build")
}

allprojects {
    repositories {
        mavenCentral()
        mavenLocal()
        maven("https://maven.neoforged.net/releases")
        maven("https://maven.fabricmc.net/")
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
//            content {
//                includeModule 'me.lucko', 'spark-api'
//            }
    }
}