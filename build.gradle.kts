plugins {
    id("java")
    id("java-library")
    id("com.diffplug.spotless") version "7.0.4"
    id("com.gradleup.shadow") version "8.3.6"
    eclipse
}

java { sourceCompatibility = JavaVersion.VERSION_17 }

group = "net.trueog.spawntp"

version = "1.0"

val apiVersion = "1.19"

tasks.named<ProcessResources>("processResources") {
    val props = mapOf("version" to version, "apiVersion" to apiVersion)
    inputs.properties(props)
    filesMatching("plugin.yml") { expand(props) }
    from("LICENSE") { into("/") }
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven { url = uri("https://repo.purpurmc.org/snapshots") }
}

dependencies {
    compileOnly("org.purpurmc.purpur:purpur-api:1.19.4-R0.1-SNAPSHOT")
    compileOnly("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")
}

tasks.withType<AbstractArchiveTask>().configureEach {
    isPreserveFileTimestamps = false
    isReproducibleFileOrder = true
}

tasks.shadowJar {
    exclude("io.github.miniplaceholders.*")
    archiveClassifier.set("")
    minimize()
}

tasks.build {
    dependsOn(tasks.spotlessApply)
    dependsOn(tasks.shadowJar)
}

tasks.jar { archiveClassifier.set("part") }

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("-parameters")
    options.compilerArgs.add("-Xlint:deprecation")
    options.encoding = "UTF-8"
    options.isFork = true
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
        vendor = JvmVendorSpec.GRAAL_VM
    }
}

spotless {
    java {
        removeUnusedImports()
        palantirJavaFormat()
    }
    kotlinGradle {
        ktfmt().kotlinlangStyle().configure { it.setMaxWidth(120) }
        target("build.gradle.kts", "settings.gradle.kts")
    }
}
