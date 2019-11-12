package pl.poznan.put.oculus.boot.config

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.hateoas.client.LinkDiscoverers
import org.springframework.hateoas.mediatype.hal.HalLinkDiscoverer
import org.springframework.hateoas.server.LinkRelationProvider
import org.springframework.hateoas.server.core.DelegatingLinkRelationProvider
import org.springframework.hateoas.server.core.EvoInflectorLinkRelationProvider
import org.springframework.plugin.core.SimplePluginRegistry
import org.springframework.plugin.core.support.PluginRegistryFactoryBean
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


/**
 * Annotated controller is exposed to the public api in Swagger
 *
 * @see SwaggerConfig
 * */
@Target(AnnotationTarget.CLASS)
annotation class PublicAPI

/**
 * Provides universal configuration for swagger endpoint and UI.
 *
 * To add controller to Swagger use [PublicAPI] annotation
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
        logger.info("initializing Swagger bean")
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

    // TODO: remove these beans after Springfox gets fixed for Spring HATEOAS
    @Bean
    fun discoverers(): LinkDiscoverers {
        val plugins = listOf(HalLinkDiscoverer())
        return LinkDiscoverers(SimplePluginRegistry.create(plugins))
    }

    @Bean
    fun provider(): LinkRelationProvider {
        return EvoInflectorLinkRelationProvider()
    }

    @Primary
    @Bean
    fun relProviderPluginRegistry2(): PluginRegistryFactoryBean<LinkRelationProvider, LinkRelationProvider.LookupContext> {
        val factory = PluginRegistryFactoryBean<LinkRelationProvider, LinkRelationProvider.LookupContext>()
        factory.setType(LinkRelationProvider::class.java)
        factory.setExclusions(arrayOf(DelegatingLinkRelationProvider::class.java))
        return factory
    }

    companion object {
        private val logger = LoggerFactory.getLogger(SwaggerConfig::class.java)
    }
}
