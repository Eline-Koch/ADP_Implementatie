import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.adp_implementatie.Deque;
import org.adp_implementatie.PerformanceBenchmark;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class DequeTest {
    public static void main(String[] args) throws IOException {
        Pizza pizza = new Pizza("Doner kebab", false);

        int[] dequeSizes = {1000, 10000, 100000, 1000000, 10000000};

        performDequeDataSetTest();

        System.out.println("Each time the data set is 10x bigger");
        System.out.println("Insert left");
        for (int size : dequeSizes) {
            testDequeInsertLeft(size, "String insert");
        }
        System.out.println("Insert right");
        for (int size : dequeSizes) {
            testDequeInsertRight(size, pizza);
        }

        System.out.println("Remove left");

        for (int size : dequeSizes) {
            testDequeRemoveLeft(size, 1);
        }
        System.out.println("Remove right");

        for (int size : dequeSizes) {
            testDequeRemoveRight(size, 1);
        }
    }

    public static void performDequeDataSetTest() throws IOException {
        System.out.println();
        System.out.println("Dataset test voor Deque");

        String dataString = Files.readString(Path.of("src/test/resources/dataset_sorteren.json"), Charset.defaultCharset());
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, List<Object>> dataMap = objectMapper.readValue(dataString, new TypeReference<>(){});

        for (Map.Entry<String, List<Object>> entry : dataMap.entrySet()) {
            String key = entry.getKey();
            List<Object> value = entry.getValue();

            Deque<Object> dequeLeft = new Deque<>();
            for (Object o : value) {
                dequeLeft.insertLeft(o);
            }

            System.out.print(key + " (insertLeft): ");
            dequeLeft.display();

            Deque<Object> dequeRight = new Deque<>();
            for (Object o : value) {
                dequeRight.insertRight(o);
            }

            System.out.print(key + " (insertRight): ");
            dequeRight.display();
        }
    }


    public static void testDequeInsertLeft(int dequeSize, Object insertObject) {
        Deque<Object> deque = new Deque<>();
        PerformanceBenchmark benchmark = new PerformanceBenchmark();

        benchmark.start();
        for (int i = 0; i < dequeSize; i++) {
            deque.insertLeft(insertObject);
        }
        benchmark.stop();

//        System.out.println("Deque Insert Left met size: " + dequeSize + " Object to insert: " + insertObject.toString());
        benchmark.printElapsedTime();
//        System.out.println();
    }

    public static void testDequeInsertRight(int dequeSize, Object insertObject) {
        Deque<Object> deque = new Deque<>();
        PerformanceBenchmark benchmark = new PerformanceBenchmark();

        benchmark.start();
        for (int i = 0; i < dequeSize; i++) {
            deque.insertRight(insertObject);
        }
        benchmark.stop();

//        System.out.println("Deque Insert Right met size: " + dequeSize + " Object to insert: " + insertObject.toString());
        benchmark.printElapsedTime();
//        System.out.println();
    }

    public static void testDequeRemoveLeft(int dequeSize, Object insertObject) {
        Deque<Object> deque = new Deque<>();
        PerformanceBenchmark benchmark = new PerformanceBenchmark();

        // Vul de deque eerst met data
        for (int i = 0; i < dequeSize; i++) {
            deque.insertLeft(insertObject);
        }

        benchmark.start();
        for (int i = 0; i < dequeSize; i++) {
            deque.removeLeft();
        }
        benchmark.stop();

//        System.out.println("Deque Remove Left met size: " + dequeSize + " Object to remove: " + insertObject.toString());
        benchmark.printElapsedTime();
//        System.out.println();
    }

    public static void testDequeRemoveRight(int dequeSize, Object insertObject) {
        Deque<Object> deque = new Deque<>();
        PerformanceBenchmark benchmark = new PerformanceBenchmark();

        for (int i = 0; i < dequeSize; i++) {
            deque.insertRight(insertObject);
        }

        benchmark.start();
        for (int i = 0; i < dequeSize; i++) {
            deque.removeRight();
        }
        benchmark.stop();

//        System.out.println("Deque Remove Right met size: " + dequeSize + " Object to remove: " + insertObject.toString());
        benchmark.printElapsedTime();
//        System.out.println();
    }
}
