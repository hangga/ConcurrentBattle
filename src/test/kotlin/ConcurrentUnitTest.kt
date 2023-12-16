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
        println("write file | Kotlin : $kotlinTotalTime ms")
    }

    @Test
    fun `Test virtual thread in write file`() {
        //val javaTotalTime = ConcurrentJava().performSingleFileOperationJava()
        val javaTotalTime = ConcurrentJava().performSingleFileOperationVirtualThread()
        println("write file | Java : $javaTotalTime ms")
    }

    @Test
    fun `Test coroutine in data process`() = runBlocking {
        val kotlinTotalTime = performDataProcessingKotlin()
        println("data process | Kotlin : $kotlinTotalTime ms")
    }

    @Test
    fun `Test virtual thread  data process`() = runBlocking {
        //val javaTotalTime = ConcurrentJava().performDataProcessingJava()
        val javaTotalTime = ConcurrentJava().performDataProcessingVirtualThread()
        println("data process | Java : $javaTotalTime ms")
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
        println(String.format("%-25s %-15s %-20s %15s", "Task", "Lang", "Approach", "Execution Time"))
    }

    @Test
    fun `Test all method`() = runBlocking{
        println(String.format("%-25s %-15s %-20s %15s", "Task", "Lang", "Approach", "Execution Time"))
        println("-------------------------------------------------------------------------------------")

        val kotlinTotalTime = performSingleFileOperation()
        println(String.format("%-25s %-15s %-20s %15s", "Write File", "Kotlin", "Coroutine", "$kotlinTotalTime ms"))

        val javaTotalTime = ConcurrentJava().performSingleFileOperationVirtualThread()
        println(String.format("%-25s %-15s %-20s %15s", "Write File", "Java", "Thread.ofVirtual()", "$javaTotalTime ms"))

        val dataProcesskotlinTotalTime = performDataProcessingKotlin()
        println(String.format("%-25s %-15s %-20s %15s", "Data Processing", "Kotlin", "Coroutine", "$dataProcesskotlinTotalTime ms"))

        val dataProcessjavaTotalTime = ConcurrentJava().performDataProcessingVirtualThread()
        println(String.format("%-25s %-15s %-20s %15s", "Data Processing", "Java", "Thread.ofVirtual()", "$dataProcessjavaTotalTime ms"))

        val httpexecutionTime = makeHttpRequestAsync()
        println(String.format("%-25s %-15s %-20s %15s", "Http Request", "Kotlin", "Coroutine", "$httpexecutionTime ms"))

        val result: Pair<String, Long> = ConcurrentJava().makeHttpRequest()
        //println("HttpRequest | Java : ${result.second.ms}")
        println(String.format("%-25s %-15s %-20s %15s", "Http Request", "Java", "Thread.ofVirtual()", "${result.second} ms"))
    }

}