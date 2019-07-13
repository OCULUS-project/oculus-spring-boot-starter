# oculus-spring-boot-starter
[![](https://jitpack.io/v/OCULUS-project/oculus-spring-boot-starter.svg)](https://jitpack.io/#OCULUS-project/oculus-spring-boot-starter)

Starter dependency for OCULUS services. 
Includes `spring-boot-web-starter`, `OculusException`, swagger and logging configuration as well as some basic properties 

## add to project

Just add these to `build.gradle.kts`:
```kotlin
repositories {
    maven(url = "https://jitpack.io")
}

dependencies {
    implementation("com.github.OCULUS-project:oculus-spring-boot-starter:<TAG>")
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
