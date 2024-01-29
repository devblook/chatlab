import org.gradle.configurationcache.extensions.capitalized


plugins {
    id("chatlab.common-conventions")
    id("com.github.johnrengelman.shadow") version "7.1.2"

}

repositories{
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.unnamed.team/repository/unnamed-public/")
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("team.unnamed:inject:1.0.1")
    compileOnly("org.spongepowered:configurate-hocon:4.0.0")
    compileOnly("me.fixeddev:commandflow-bukkit:0.6.0")
    compileOnly("io.github.miniplaceholders:miniplaceholders-api:2.1.0")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
}

tasks {
    shadowJar {
        archiveBaseName.set(rootProject.name.capitalized())
    }

    build {
        dependsOn(shadowJar)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
