package pl.poznan.put.oculus.boot.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.ForwardedHeaderFilter

@Configuration
class HeaderFilterConfig {

    /**
     * Enables HATEOAS links to use x-forwarded headers for building urls
     */
    @Bean
    fun forwardedHeaderFilter() = ForwardedHeaderFilter()
}
