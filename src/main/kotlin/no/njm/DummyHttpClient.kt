package no.njm

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class DummyHttpClient(
    private val restTemplate: RestTemplate,
    @Value("\${BACKEND_URL}")
    private val url: String,
) {

    private final val log = getLogger()

    fun fetchDummy(id: Int): Dummy? {
        log.debug("Using backendUrl: $url")
        return restTemplate.getForObject("$url/dummy/$id", Dummy::class.java)
    }
}

data class Dummy(
    val id: Int,
    val title: String,
)
