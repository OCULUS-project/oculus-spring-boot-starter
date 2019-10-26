package pl.poznan.put.oculus.boot

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
internal class AppRunnerTest {

    @SpringBootApplication
    internal class OculusTestApplication

    internal fun main(args: Array<String>) {
        runApplication<OculusTestApplication>(*args)
    }

    @Test
    fun `should start context`() {
    }
}

