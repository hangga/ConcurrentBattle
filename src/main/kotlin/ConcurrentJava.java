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


public class ConcurrentJava {
    public long performSingleFileOperationJava() {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 5; i++) {
            CompletableFuture.runAsync(() -> {
                try {
                    // Simulate disk latency with file writing
                    Path filePath = Path.of("example.txt");
                    byte[] data = Const.LOREM_IPSUM.getBytes();
                    Files.write(filePath, data, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                    //System.out.println("File operation completed by virtual thread");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    // Java menggunakan CompletableFuture
    public long performDataProcessingJava() {
        long startTime = System.currentTimeMillis();

        List<String> dataList = Arrays.asList("dummy_data1", "dummy_data2", "dummy_data3", "dummy_data4", "dummy_data5");

        CompletableFuture<Void> allOf = CompletableFuture.allOf(dataList.stream().map(data -> CompletableFuture.runAsync(() -> {
            // Simulasi pemrosesan data
            String result = data.replace("_data", "-").toUpperCase();
            System.out.println("Java Data processed: " + result);
        })).toArray(CompletableFuture[]::new));
        allOf.join();

        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    public CompletableFuture<Pair<String, Long>> makeHttpRequestAsync() {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(Const.DUMMY_API)).build();

        long startTime = System.currentTimeMillis();

        return httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString()).thenApply(response -> {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            return new Pair<>(response.body(), executionTime);
        }).exceptionally(throwable -> {
            System.err.println("Error occurred: " + throwable.getMessage());
            return new Pair<>("", 0L);
        });
    }

}
