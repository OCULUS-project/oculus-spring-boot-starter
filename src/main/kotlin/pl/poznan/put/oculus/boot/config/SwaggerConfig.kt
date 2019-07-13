package pl.poznan.put.oculus.boot.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

/** Annotated controller is exposed to the public api */
@Target(AnnotationTarget.CLASS)
annotation class PublicAPI

/**
 * Provides universal configuration for swagger endpoint and UI
 */
@Configuration
@EnableSwagger2
class SwaggerConfig (
    @Value("\${swagger.name}")
    val name: String,
    @Value("\${swagger.description}")
    val description: String,
    @Value("\${swagger.author}")
    val author: String,
    @Value("\${swagger.email}")
    val email: String,
    @Value("\${swagger.version}")
    val version: String
) {
    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false)
            .select()
            .apis(RequestHandlerSelectors.withClassAnnotation(PublicAPI::class.java))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(info())
    }

    private fun info(): ApiInfo {
        return ApiInfoBuilder().title(name)
            .description(description)
            .contact(Contact(author, "www.cie.put.poznan.pl", email))
            .license("MIT")
            .licenseUrl("#")
            .version(version)
            .build()
    }
}
