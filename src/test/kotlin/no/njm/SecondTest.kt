package no.njm

import okhttp3.mockwebserver.MockResponse
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType

class SecondTest : IntegrationTest() {

    @Autowired
    private lateinit var dummyService: DummyService

    @Test
    fun fetchDummy() {
        val mockResponse =
            MockResponse()
                .setBody(Dummy(2, "First").toJson())
                .setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        mockWebServer.enqueue(mockResponse)

        val dummy = dummyService.fetchDummy(2)

        dummy?.apply {
            id `should be equal to` 2
            title `should be equal to` "First"
        }

        // Taking the request from FirstTest.
        val firstRequest = mockWebServer.takeRequest()
        firstRequest.path `should be equal to` "/dummy/1"
        firstRequest.method `should be equal to` "GET"

        // Taking the request from this test, which would fail if the request from FirstTest was not taken.
        val secondRequest = mockWebServer.takeRequest()
        secondRequest.path `should be equal to` "/dummy/2"
        secondRequest.method `should be equal to` "GET"
    }
}
