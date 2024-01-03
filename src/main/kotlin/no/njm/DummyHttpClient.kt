package no.njm

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class DummyHttpClient(
    @Value("\${BACKEND_URL}")
    private val url: String,
) {
    val log = getLogger()

    fun getUrlPort(): Int {
        log.debug("Using backendUrl: $url")
        return url.split(":").last().toInt()
    }
}
