package no.njm

import okhttp3.mockwebserver.MockResponse
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType

class FirstTest : IntegrationTest() {

    @Autowired
    private lateinit var dummyService: DummyService

    @Test
    fun fetchDummy() {
        val mockResponse =
            MockResponse()
                .setBody(Dummy(1, "First").toJson())
                .setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        mockWebServer.enqueue(mockResponse)

        val dummy = dummyService.fetchDummy(1)

        dummy?.apply {
            id `should be equal to` 1
            title `should be equal to` "First"
        }

        // Omitting calling takeRequest(), so the request in this test will be returned when consecutive tests calls
        // takeRequest() when the same instance of mockWebServer.
    }
}
