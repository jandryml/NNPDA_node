package cz.edu.upce.fei.service

import cz.edu.upce.fei.config.NodeProperties
import cz.edu.upce.fei.model.LoginObject
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.*
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForEntity
import java.net.URI
import java.time.Duration

@Service
class DataSendingService(
    private val restTemplate: RestTemplate,
    private val nodeProperties: NodeProperties
) {
    private var tokenJWT: String = ""

    @Scheduled(cron = "0 0 */12 * * *")
    fun login() {
        val response: ResponseEntity<String> =
            restTemplate.postForEntity(
                "http://${nodeProperties.serverUrl}/api/user/login",
                LoginObject(nodeProperties.serverUsername, nodeProperties.serverPassword)
            )
        extractJWTFromResponse(response)
    }

    fun <T> postData(body: T?, apiPath: String): ResponseEntity<String> {
        val entity = HttpEntity<Any?>(body ?: "", getHeaders())
        return restTemplate.exchange(
            URI("http://${nodeProperties.serverUrl}$apiPath"),
            HttpMethod.POST,
            entity,
            String::class.java
        )
    }

    protected fun getHeaders(contentType: MediaType =  MediaType.APPLICATION_JSON): HttpHeaders {
        val headers = HttpHeaders()
        headers.contentType = contentType
        headers.set("Authorization", tokenJWT)
        return headers
    }

    //refreshing JWT token
    protected fun extractJWTFromResponse(response: ResponseEntity<String>) {
        if (response.statusCode != HttpStatus.OK) {
            throw RuntimeException("Failed to login with status: ${response.statusCode}, $response")
        }
        tokenJWT = response.headers.getFirst("authorization")
            ?: throw RuntimeException("No token in response to api login: $response")
    }

}

@Configuration
class RestTemplateConfig {
    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate {
        return builder
            .setConnectTimeout(Duration.ofMillis(5000))
            .setReadTimeout(Duration.ofMillis(5000))
            .build()
    }
}