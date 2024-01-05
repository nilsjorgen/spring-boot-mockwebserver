package no.njm

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource

class SecondTest : IntegrationTest() {

    @Autowired
    private lateinit var dummyService: DummyService

    companion object {
        var mockWebServer =
            MockWebServer().apply {
                dispatcher = MockDispatcher
            }

        @DynamicPropertySource
        @JvmStatic
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("BACKEND_URL") {
                "http://localhost:${mockWebServer.port}"
            }
        }

        init {
            println("Started MockWebServer on port: ${mockWebServer.port}")
        }
    }

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

        val request = mockWebServer.takeRequest()
        request.path `should be equal to` "/dummy/2"
        request.method `should be equal to` "GET"
    }
}
