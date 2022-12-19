plugins {
    id("java-library")
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.unnamed.team/repository/unnamed-public/")
    mavenLocal()
}

dependencies{
    compileOnly("io.papermc.paper:paper-api:1.19.3-R0.1-SNAPSHOT")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}