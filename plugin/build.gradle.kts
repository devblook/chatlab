plugins{
    id("chatlab.common-conventions")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

dependencies{
    implementation("me.fixeddev:commandflow-bukkit:0.5.2")
    implementation("team.unnamed:inject:1.0.1")
    implementation ("net.kyori:adventure-platform-bukkit:4.2.0")
    implementation(project(":api"))

    testRuntimeOnly(project(":plugin"))
    testRuntimeOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
}

tasks {
    shadowJar {
        archiveFileName.set("ChatLab-${project.version}.jar")

        relocate("me.fixeddev", "${project.group}.chatlab.libs.commandflow")
        relocate("team.unnamed.inject", "${project.group}.chatlab.libs.inject")

    }
}