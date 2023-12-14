package id.hangga

import Const
import kotlinx.coroutines.*
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption
import java.util.*
import kotlin.system.measureTimeMillis

fun performSingleFileOperation(): Long {
    return measureTimeMillis {
        runBlocking(Dispatchers.Default) {
            val job = async {
                // Simulasi latensi operasi file dengan penulisan ke file
                val filePath = Path.of("example.txt")
                Files.write(
                    filePath, Const.LOREM_IPSUM.toByteArray(), StandardOpenOption.CREATE, StandardOpenOption.APPEND
                )
                //println("File operation completed by coroutine")
            }
            job.await()
        }
    }
}

// Kotlin menggunakan Coroutine
suspend fun performDataProcessingKotlin(): Long {
    val startTime = System.currentTimeMillis()

    val dataList = listOf("dummy_data1", "dummy_data2", "dummy_data3", "dummy_data4", "dummy_data5")

    coroutineScope {
        val jobs = dataList.map { data ->
            async {
                // Simulasi pemrosesan data
                val result = data.replace("_data", "-").uppercase(Locale.getDefault())
                println("Coroutine : Data processed: $result")
            }
        }
        jobs.awaitAll()
    }

    val endTime = System.currentTimeMillis()
    return endTime - startTime
}

suspend fun makeHttpRequestAsync(): Long = withContext(Dispatchers.IO) {
    val uri = URI.create(Const.DUMMY_API)
    val client = HttpClient.newHttpClient()
    val request = HttpRequest.newBuilder(uri).build()

    return@withContext try {
        var responseString: String
        measureTimeMillis {
            val response = client.send(request, HttpResponse.BodyHandlers.ofString())
            responseString = response.body()
            // Do something with the response if needed
        }
    } catch (e: Exception) {
        e.printStackTrace()
        0L
    } finally {
        // Tidak perlu menutup HttpClient di sini, karena HttpClient.newHttpClient() tidak membuat sumber daya yang perlu ditutup secara manual.
    }
}


/*suspend fun makeHttpRequestAsync(): Pair<String, Long> = withContext(Dispatchers.IO) {
    val uri = URI.create(Const.DUMMY_API)
    val client = HttpClient.newHttpClient()
    val request = HttpRequest.newBuilder(uri).build()

    return@withContext try {
        var responseString: String
        val executionTime = measureTimeMillis {
            val response = client.send(request, HttpResponse.BodyHandlers.ofString())
            responseString = response.body()
            //println("Request completed in ${executionTime}ms")
        }
        Pair(responseString, executionTime)
    } catch (e: Exception) {
        e.printStackTrace()
        Pair("", 0L)
    } finally {
        // Tidak perlu menutup HttpClient di sini, karena HttpClient.newHttpClient() tidak membuat sumber daya yang perlu ditutup secara manual.
    }
}*/
