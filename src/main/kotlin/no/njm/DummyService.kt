package no.njm

import org.springframework.stereotype.Service

@Service
class DummyService(
    private val httpClient: DummyHttpClient,
) {
    fun getUrlPort(): Int {
        return httpClient.getUrlPort()
    }
}
