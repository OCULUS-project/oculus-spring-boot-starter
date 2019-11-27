package pl.poznan.put.oculus.boot.config

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.DefaultResponseErrorHandler
import org.springframework.web.client.RestTemplate

@Configuration
class RestTemplateConfig {

    /**
     * Provides default [RestTemplate] for Oculus services. The template won't throw exception for 4xx statuses
     */
    @Bean
    @ConditionalOnMissingBean
    fun restTemplate(): RestTemplate = RestTemplateBuilder()
        .errorHandler(Handler())
        .build()

    companion object {
        private class Handler : DefaultResponseErrorHandler() {
            override fun hasError(response: ClientHttpResponse) = when (response.statusCode.series()) {
                HttpStatus.Series.SERVER_ERROR -> true
                else -> false
            }
        }
    }
}
