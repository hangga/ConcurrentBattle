import id.hangga.makeHttpRequestAsync
import id.hangga.performDataProcessingKotlin
import id.hangga.performSingleFileOperation
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.util.concurrent.CompletableFuture
import kotlin.time.Duration.Companion.milliseconds

class ConcurrentUnitTest {
    @Test
    fun `Test coroutin in write file`() {
        val kotlinTotalTime = performSingleFileOperation()
        println("Java : $kotlinTotalTime milliseconds")
    }

    @Test
    fun `Test virtual thread in write file`() {
        val javaTotalTime = ConcurrentJava().performSingleFileOperationJava()
        println("Java : $javaTotalTime milliseconds")
    }

    @Test
    fun `Test coroutine in data process`() = runBlocking {
        val kotlinTotalTime = performDataProcessingKotlin()
        println("Kotlin : $kotlinTotalTime milliseconds")
    }

    @Test
    fun `Test virtual thread  data process`() = runBlocking {
        val javaTotalTime = ConcurrentJava().performDataProcessingJava()
        println("Java : $javaTotalTime milliseconds")
    }

    @Test
    fun `Test coroutine  in httpRequest`(): Unit = runBlocking {
        val (result, executionTime) = makeHttpRequestAsync()
        println("Data Processing")
        println("=================================")
        //println("Response: $result")
        println("Kotlin Execution Time: $executionTime ms")
    }

    @Test
    fun `Test virtual thread in httpRequest`(): Unit = runBlocking {
        val completableFuture: CompletableFuture<Pair<String, Long>> = ConcurrentJava().makeHttpRequestAsync()

        // Blocking until the CompletableFuture is completed
        val response: Pair<String, Long> = completableFuture.join()

        //println("Response: ${response.first}")
        println("Java Execution Time: ${response.second.milliseconds }")
    }
}