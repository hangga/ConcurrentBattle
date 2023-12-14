import id.hangga.makeHttpRequestAsync
import id.hangga.performDataProcessingKotlin
import id.hangga.performSingleFileOperation
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.time.Duration.Companion.milliseconds

class ConcurrentUnitTest {
    @Test
    fun `Test coroutin in write file`() {
        val kotlinTotalTime = performSingleFileOperation()
        println("write file | Kotlin : $kotlinTotalTime milliseconds")
    }

    @Test
    fun `Test virtual thread in write file`() {
        //val javaTotalTime = ConcurrentJava().performSingleFileOperationJava()
        val javaTotalTime = ConcurrentJava().performSingleFileOperationVirtualThread()
        println("write file | Java : $javaTotalTime milliseconds")
    }

    @Test
    fun `Test coroutine in data process`() = runBlocking {
        val kotlinTotalTime = performDataProcessingKotlin()
        println("data process | Kotlin : $kotlinTotalTime milliseconds")
    }

    @Test
    fun `Test virtual thread  data process`() = runBlocking {
        //val javaTotalTime = ConcurrentJava().performDataProcessingJava()
        val javaTotalTime = ConcurrentJava().performDataProcessingVirtualThread()
        println("data process | Java : $javaTotalTime milliseconds")
    }

    @Test
    fun `Test coroutine  in httpRequest`(): Unit = runBlocking {
        /*val (_, executionTime) = makeHttpRequestAsync()
        println("Data Processing")
        println("=================================")
        //println("Response: $result")
        println("Kotlin Execution Time: $executionTime ms")*/
        val executionTime = makeHttpRequestAsync()
        println("HttpRequest | Kotlin : $executionTime ms")
    }

    @Test
    fun `Test virtual thread in httpRequest`() = runBlocking {
        val result: Pair<String, Long> = ConcurrentJava().makeHttpRequest()
        println("HttpRequest | Java : ${result.second.milliseconds}")
    }

}