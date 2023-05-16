plugins {
    id("chatlab.common-conventions")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

dependencies {

    implementation("me.fixeddev:commandflow-bukkit:0.5.2")
    implementation("team.unnamed:inject:1.0.1")
    compileOnly("org.spongepowered:configurate-hocon:4.0.0")

}

tasks {
    shadowJar {

        archiveFileName.set("ChatLab-${project.version}.jar")

        relocate("me.fixeddev", "${project.group}.chatlab.libs.commandflow")
        relocate("team.unnamed.inject", "${project.group}.chatlab.libs.inject")

    }


}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
