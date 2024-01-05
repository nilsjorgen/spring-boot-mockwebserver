package no.njm

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.QueueDispatcher
import okhttp3.mockwebserver.RecordedRequest
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType

@SpringBootTest(classes = [Application::class])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IntegrationTest {

    companion object {
        val mockWebServer: MockWebServer =
            MockWebServer().apply {
                System.setProperty("BACKEND_URL", "http://localhost:$port")
                dispatcher = MockDispatcher
            }

        init {
            println("Started mockWebServer on port: ${mockWebServer.port}")
        }
    }
}

object MockDispatcher : QueueDispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        fun withContentTypeApplicationJson(createMockResponse: () -> MockResponse): MockResponse =
            createMockResponse().addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)

        if (responseQueue.peek() != null) {
            return withContentTypeApplicationJson { responseQueue.take() }
        }

        return MockResponse().setResponseCode(404)
    }
}
