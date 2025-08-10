plugins {
    id("java-library")
}

group = "de.allycraft"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.hypera.dev/snapshots/") // luckperms-minestom

}

dependencies {
    compileOnly("net.minestom:minestom:2025.07.30-1.21.8")
    api("dev.lu15:luckperms-minestom:5.5-SNAPSHOT")
    implementation("org.spongepowered:configurate-hocon:3.7.3")

    // luckperms runtime dependencies
    runtimeOnly("com.zaxxer:HikariCP:6.3.0")
    runtimeOnly("com.h2database:h2:2.1.214")
    runtimeOnly("org.postgresql:postgresql:42.7.6")
    runtimeOnly("redis.clients:jedis:5.2.0")

    // demo
    testImplementation("net.minestom:minestom:2025.07.30-1.21.8")
    testImplementation("org.slf4j:slf4j-simple:2.0.17")
}

tasks.register<JavaExec>("runDemo") {
    mainClass.set("de.allycraft.minestom.perms.demo.DemoServer")
    classpath = sourceSets["test"].runtimeClasspath
    workingDir = file("run/")
}