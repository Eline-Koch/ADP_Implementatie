import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.adp_implementatie.PerformanceBenchmark;
import org.adp_implementatie.PriorityQueue;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

//performance tests
//add
//numOperations: 10000000, size: 100000, seconds: 0.216892600 seconden
//numOperations: 10000000, size: 1000000, seconds: 0.268272200 seconden
//numOperations: 10000000, size: 10000000, seconds: 0.246516400 seconden
//
//peek
//numOperations: 10000000, size: 100000, seconds: 0.006253500 seconden
//numOperations: 10000000, size: 1000000, seconds: 0.000004200 seconden
//numOperations: 10000000, size: 10000000, seconds: 0.000001700 seconden
//
//poll
//numOperations: 100000, size: 200000, seconds: 0.002906700 seconden
//numOperations: 100000, size: 2000000, seconds: 0.000570200 seconden
//numOperations: 100000, size: 20000000, seconds: 0.000737200 seconden
// Bij een grotere heap (opgeslagen in een array) worden add en peek sneller?

public class PriorityQueueTest {

    PerformanceBenchmark benchmark = new PerformanceBenchmark();
    public static void main(String[] args) throws IOException {
        PriorityQueueTest test = new PriorityQueueTest();
        test.performDataSetTest();
        test.orderComparableObjectTest();
        test.orderStringTest();
        test.performPerformanceTest();
    }

    public void performDataSetTest () throws IOException {
        System.out.println();
        System.out.println("dataset tests");

        String dataString = Files.readString(Path.of("src/test/resources/dataset_sorteren.json"), Charset.defaultCharset());
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, List<Object>> dataMap = objectMapper.readValue(dataString, new TypeReference<>(){});

        for (Map.Entry<String, List<Object>> entry : dataMap.entrySet()) {
            String key = entry.getKey();
            List<Object> value = entry.getValue();

            PriorityQueue<Double> dynamicArray = new PriorityQueue<>();
            for (Object o : value) {
                if (o != null) {
                    if (o instanceof Double) {
                        dynamicArray.add((Double) o);
                    } else if (o instanceof Integer) {
                        dynamicArray.add(((Integer) o).doubleValue());
                    } else {
                        System.out.println("Cannot add \"o\" to heap, because \"o\" is of type" + o.getClass().getName());
                    }
                }
                else {
                    System.out.println("Cannot add \"o\" to heap, because \"o\" is null");
                }
            }
            System.out.print(key + ": ");
            dynamicArray.printHeap();
        }
    }

    public void orderComparableObjectTest() {
        System.out.println();
        System.out.println("order comparable object test");

        PriorityQueue<Pizza> priorityQueue = new PriorityQueue<>();
        Pizza[] pizzas = {new Pizza ("mozarella", true),
                new Pizza ("salami", false), new Pizza ("anchovis", false)};
        for (Pizza pizza : pizzas) {
            priorityQueue.add(pizza);
        }
        for (int i = 0;i < pizzas.length; i++){
            priorityQueue.poll().print();
        }
    }

    public void orderStringTest() {
        System.out.println();
        System.out.println("order string test");

        PriorityQueue<String> priorityQueue = new PriorityQueue<>();
        String[] fruits = {"mango", "apple", "orange", "banana"};
        for (String fruit : fruits) {
            priorityQueue.add(fruit);
        }
        for (int i = 0;i < fruits.length; i++){
            System.out.println(priorityQueue.poll());
        }
    }

    public void performPerformanceTest() {
        System.out.println();
        System.out.println("performance tests");

        int numOperations = 10000000;
        int[] arraySize = {100000, 1000000, 10000000};

        System.out.println("add");

        for (int size : arraySize) {
                this.performAddPerformanceTest(size, numOperations);
        }

        System.out.println();
        System.out.println("peek");

        for (int size : arraySize) {
            this.performPeekPerformanceTest(size, numOperations);
        }

        System.out.println();
        System.out.println("poll");

        for (int size : arraySize) {
            this.performPollPerformanceTest(size * 2, numOperations / 100);
        }
    }

    public void performAddPerformanceTest(int arraySize, int numOperations) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for(int i = 0; i < arraySize; i++){
            pq.add(i);
        }

        benchmark.start();
        for(int i = 0; i < numOperations; i++){
            pq.add(i);
        }
        benchmark.stop();

        System.out.print("numOperations: " + numOperations + ", size: " + arraySize + ", seconds: ");
       benchmark.printElapsedTime();
    }

    public void performPeekPerformanceTest(int arraySize, int numOperations) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for(int i = 0; i < arraySize; i++){
            pq.add(i);
        }

        benchmark.start();
        for(int i = 0; i < numOperations; i++){
            pq.peek();
        }
        benchmark.stop();

        System.out.print("numOperations: " + numOperations + ", size: " + arraySize + ", seconds: ");
        benchmark.printElapsedTime();

    }

    public void performPollPerformanceTest(int arraySize, int numOperations) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for(int i = 0; i < arraySize; i++){
            pq.add(i);
        }

        benchmark.start();
        for(int i = 0; i < numOperations; i++){
            pq.poll();
        }
        benchmark.stop();

        System.out.print("numOperations: " + numOperations + ", size: " + arraySize + ", seconds: ");
        benchmark.printElapsedTime();

    }
}
