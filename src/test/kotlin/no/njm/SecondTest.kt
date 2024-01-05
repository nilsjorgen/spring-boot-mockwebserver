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

        // Uses a common MockWebServer instance, but the port is not updated for each test since the instantiation is
        // places in the abstract test class. Annotating the FirstTest with @DirtiesContext, which caused the Spring
        // context to be reloaded so the correct port is used.
        val secondRequest = mockWebServer.takeRequest()
        secondRequest.path `should be equal to` "/dummy/2"
        secondRequest.method `should be equal to` "GET"
    }
}
