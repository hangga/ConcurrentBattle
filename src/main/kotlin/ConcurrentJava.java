import kotlin.Pair;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;


// https://chat.openai.com/share/9b09b6a2-8ea7-4e1a-9b00-140103c598e0


public class ConcurrentJava {

    public long performSingleFileOperationVirtualThread() {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 5; i++) {
            Thread.ofVirtual().start(() -> {
                try {
                    // Simulate disk latency with file writing
                    Path filePath = Path.of("example.txt");
                    byte[] data = Const.LOREM_IPSUM.getBytes();
                    Files.write(filePath, data, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                    // //System.out.println("File operation completed by virtual thread");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    public long performDataProcessingVirtualThread() {
        long startTime = System.currentTimeMillis();

        List<String> dataList = Arrays.asList("dummy_data1", "dummy_data2", "dummy_data3", "dummy_data4", "dummy_data5");

        for (String data : dataList) {
            Thread.ofVirtual().start(() -> {
                // Simulasi pemrosesan data
                String result = data.replace("_data", "-").toUpperCase();
                ////System.out.println("Virtual Thread Data processed: " + result);
            });
        }

        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    public Pair<String, Long> makeHttpRequest() {
        CompletableFuture<Pair<String, Long>> futureResult = new CompletableFuture<>();

        Thread.ofVirtual().start(() -> {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(Const.DUMMY_API)).build();

            long startTime = System.currentTimeMillis();

            try {
                HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

                long endTime = System.currentTimeMillis();
                long executionTime = endTime - startTime;

                futureResult.complete(new Pair<>(response.body(), executionTime));
            } catch (Exception e) {
                System.err.println("Error occurred: " + e.getMessage());
                futureResult.completeExceptionally(e);
            }
        });

        // Menggunakan join untuk menunggu hasil (secara virtual)
        CompletableFuture.runAsync(Thread::onSpinWait).join();

        try {
            return futureResult.get();
        } catch (Exception e) {
            System.err.println("Error occurred while getting result: " + e.getMessage());
            return new Pair<>("", 0L);
        }
    }

}
