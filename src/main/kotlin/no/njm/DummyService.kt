package no.njm

import org.springframework.stereotype.Service

@Service
class DummyService(
    private val httpClient: DummyHttpClient,
) {

    fun fetchDummy(id: Int): Dummy? {
        return httpClient.fetchDummy(id = id)
    }
}
