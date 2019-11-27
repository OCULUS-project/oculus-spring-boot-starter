# oculus-spring-boot-starter
[![library](https://api.bintray.com/packages/jakubriegel/oculus/oculus-spring-boot-starter/images/download.svg)](https://bintray.com/jakubriegel//kotlin-shell/kotlin-shell-core/_latestVersion)

Starter dependency for OCULUS services. 
Includes `spring-boot-web-starter`, `OculusException`, swagger and logging configuration as well as some basic properties 

## add to project

Just add these to `build.gradle.kts`:
```kotlin
plugins {
    id("org.springframework.boot") version "2.2.1.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    kotlin("jvm") version "1.3.60"
    kotlin("plugin.spring") version "1.3.60"
}

repositories {
    mavenCentral()
    maven("https://dl.bintray.com/jakubriegel/oculus")
}

dependencies {
    // oculus
    implementation("pl.poznan.put.oculus.boot:oculus-spring-boot-starter:VERSION")
}
```

The most recent repository tag is always the preferred version.

## necessary configurations
Default properties can be seen in `main/kotlin/resources/config` directory.
Some of them need to be trimmed to the use case. Custom configurations should be added in `main/kotlin/resources` directory. Below are the most important to be set.

in `application.properties`:
```properties
# swagger
swagger.name=<name-of-the-service>
swagger.description=<description-of-the-service>
swagger.author=<author-of-the-service>
swagger.email=<contact-email-of-the-service>
swagger.version=<version-of-the-service>
```

in `application-*.properties`:
```properties
# service
server.port=<port>

# mongodb
spring.data.mongodb.host=<mongo-db-host-url[or container name]>
spring.data.mongodb.database=<database-name>
```
