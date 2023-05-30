import org.gradle.configurationcache.extensions.capitalized


plugins {
    id("chatlab.common-conventions")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

dependencies {
    compileOnly("team.unnamed:inject:1.0.1")
    compileOnly("org.spongepowered:configurate-hocon:4.0.0")
    compileOnly("me.fixeddev:commandflow-bukkit:0.5.2")

}

tasks {
    shadowJar {
        archiveBaseName.set(rootProject.name.capitalized())

        arrayOf(
            "me.fixeddev",
            "javax",
            "team.unnamed.inject"
        ).forEach {
            relocate(it, "${project.group}.chatlab.libs.${it}")
        }
    }

    build {
        dependsOn(shadowJar)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
