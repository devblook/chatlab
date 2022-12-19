plugins{
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}
group = "me.bryang.chatlab"
version = project.version
repositories{
    mavenCentral()
    mavenLocal()
    maven("https://repo.unnamed.team/repository/unnamed-public/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
}
dependencies{
    compileOnly("io.papermc.paper:paper-api:1.19.3-R0.1-SNAPSHOT")
    implementation("me.fixeddev:commandflow-bukkit:0.5.2")
    implementation("me.fixeddev:commandflow-universal:0.5.2")
    implementation("team.unnamed:inject:1.0.1")

}