package no.njm

import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource

class SecondServiceTest : BaseIntegrationTest() {
    @Autowired
    private lateinit var dummyService: DummyService

    companion object {
        @DynamicPropertySource
        @JvmStatic
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("BACKEND_URL") {
                "http://localhost:${3000}"
            }
        }
    }

    @Test
    fun testService() {
        dummyService.getUrlPort() `should be equal to` 3000
    }
}
