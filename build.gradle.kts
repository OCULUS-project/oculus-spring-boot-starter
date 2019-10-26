import com.jfrog.bintray.gradle.BintrayExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.50"
    kotlin("plugin.spring") version "1.3.50"

    `maven-publish`
    id("com.jfrog.bintray") version "1.8.4"
    id("org.jetbrains.dokka") version "0.10.0"

    id("org.springframework.boot") version "2.2.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
}

group = "pl.poznan.put.oculus.boot"
version = "0.2.1"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
    jcenter()
    maven("http://oss.jfrog.org/artifactory/oss-snapshot-local/")
}

val swaggerVersion = "3.0.0-SNAPSHOT"

dependencies {
    // kotlin
    api(kotlin("reflect"))
    api(kotlin("stdlib-jdk8"))

    // spring
    api("org.springframework.boot:spring-boot-starter-web")
    api("org.springframework.boot:spring-boot-starter-hateoas")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // jackson
    api("com.fasterxml.jackson.module:jackson-module-kotlin")

//    // swagger / springfox
    api("io.springfox:springfox-swagger2:$swaggerVersion")
    api("io.springfox:springfox-swagger-ui:$swaggerVersion")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")
    testImplementation("io.mockk:mockk:1.9.3")
}

val dokkaJar by tasks.creating(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    setProperty("classifier", "javadoc")
    from(tasks.dokka)
}

val sourcesJar by tasks.creating(Jar::class) {
    group = JavaBasePlugin.BUILD_TASK_NAME
    dependsOn(JavaPlugin.CLASSES_TASK_NAME)
    setProperty("classifier", "sources")
    from(project.sourceSets["main"].allSource)
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
    }

    withType<Test> {
        useJUnitPlatform()
    }

    withType<Jar> {
        enabled = true
    }

    dokka {
        outputFormat = "javadoc"
        outputDirectory = "${project.buildDir}/javadoc"
//        jdkVersion = 8
//        reportUndocumented = false
//        sourceDirs = files("src/main/kotlin")
    }
}

artifacts {
    archives(sourcesJar)
    archives(dokkaJar)
    archives(tasks.jar)
}

val publication = "oculus-spring-boot-starter"

publishing {
    publications {
        create<MavenPublication>(publication) {
            from(project.components["kotlin"])
            groupId = project.group.toString()
            artifactId = publication
            version = project.version.toString()
            setArtifacts(listOf(tasks.jar.get(), sourcesJar, dokkaJar))
        }
    }
}

bintray {
    user = System.getenv("BINTRAY_USER")
    key = System.getenv("BINTRAY_KEY")
    override = true
    publish = true
    setPublications(publication)
    pkg(delegateClosureOf<BintrayExtension.PackageConfig> {
        repo = "oculus"
        name = publication
        userOrg = "jakubriegel"
        websiteUrl = ""
        githubRepo = "OCULUS-project/oculus-spring-boot-starter"
        vcsUrl = "https://github.com/OCULUS-project/oculus-spring-boot-starter.git"
        description = publication
        setLabels("kotlin", "spring", "boot", "swagger", "put")
        setLicenses("mit")
        desc = description
    })
}
