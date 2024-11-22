import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.adp_implementatie.DynamicArray;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DynamicArrayTest {
    public static void main(String[] args) throws IOException {
        DynamicArrayTest test = new DynamicArrayTest();
        test.performDataSetTest();
        test.performPerformanceTest();
    }

    public void performDataSetTest () throws IOException {
        System.out.println();
        System.out.println("dataset tests");

        String dataString = Files.readString(Path.of("src/main/resources/dataset_sorteren.json"), Charset.defaultCharset());
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, List<Object>> dataMap = objectMapper.readValue(dataString, new TypeReference<>(){});

        for (Map.Entry<String, List<Object>> entry : dataMap.entrySet()) {
            String key = entry.getKey();
            List<Object> value = entry.getValue();

            DynamicArray<Object> dynamicArray = new DynamicArray<>();
            for (Object o : value) {
                dynamicArray.add(o);
            }

            System.out.print(key + ": ");
            dynamicArray.printArray();
        }
    }

    public void performPerformanceTest() {
        System.out.println();
        System.out.println("performance tests");
        System.out.println("add");

        int[] arraySize = {1000, 10000, 100000};
        int[] numOperations = {1000, 10000, 100000};

        for (int size : arraySize) {
            for (int num : numOperations) {
                this.performAddPerformanceTest(size, num);
            }
        }

        System.out.println();
        System.out.println("get");

        for (int size : arraySize) {
            for (int num : numOperations) {
                this.performGetPerformanceTest(size, num);
            }
        }

        System.out.println();
        System.out.println("set");

        for (int size : arraySize) {
            for (int num : numOperations) {
                this.performSetPerformanceTest(size, num);
            }
        }

        System.out.println();
        System.out.println("remove(index)");

        for (int size : arraySize) {
            for (int num : numOperations) {
                this.performRemoveIndexPerformanceTest(size * 20, num / 10);
            }
        }

        System.out.println();
        System.out.println("remove(element)");

        for (int size : arraySize) {
            for (int num : numOperations) {
                this.performRemoveElementPerformanceTest(size * 20, num / 10);
            }
        }

        System.out.println();
        System.out.println("contains");

        for (int size : arraySize) {
            for (int num : numOperations) {
                this.performContainsPerformanceTest(size * 10, num / 10);
            }
        }

        System.out.println();
        System.out.println("indexOf");

        for (int size : arraySize) {
            for (int num : numOperations) {
                this.performIndexOfPerformanceTest(size * 10, num / 10);
            }
        }
    }

    public void performAddPerformanceTest(int arraySize, int numOperations) {
        DynamicArray<Integer> dynamicArray = new DynamicArray<>();
        Random rn = new Random();
        for(int i = 0; i < arraySize; i++){
            dynamicArray.add(rn.nextInt());
        }

        long startingTime= System.nanoTime();
        for(int i = 0; i < numOperations; i++){
            dynamicArray.add(rn.nextInt());
        }

        System.out.print("numOperations: " + numOperations + ", arraySize: " + arraySize + ", nanoseconds: ");
        System.out.println(System.nanoTime() - startingTime);
    }

    public void performGetPerformanceTest(int arraySize, int numOperations) {
        DynamicArray<Integer> dynamicArray = new DynamicArray<>();
        Random rn = new Random();
        for(int i = 0; i < arraySize; i++){
            dynamicArray.add(rn.nextInt());
        }

        long startingTime= System.nanoTime();
        for(int i = 0; i < numOperations; i++){
            dynamicArray.get(rn.nextInt(dynamicArray.size()));
        }

        System.out.print("numOperations: " + numOperations + ", arraySize: " + arraySize + ", nanoseconds: ");
        System.out.println(System.nanoTime() - startingTime);
    }

    public void performSetPerformanceTest(int arraySize, int numOperations) {
        DynamicArray<Integer> dynamicArray = new DynamicArray<>();
        Random rn = new Random();
        for(int i = 0; i < arraySize; i++){
            dynamicArray.add(rn.nextInt());
        }

        long startingTime= System.nanoTime();
        for(int i = 0; i < numOperations; i++){
            dynamicArray.set(rn.nextInt(dynamicArray.size()), rn.nextInt());
        }

        System.out.print("numOperations: " + numOperations + ", arraySize: " + arraySize + ", nanoseconds: ");
        System.out.println(System.nanoTime() - startingTime);
    }

    public void performRemoveIndexPerformanceTest(int arraySize, int numOperations) {
        DynamicArray<Integer> dynamicArray = new DynamicArray<>();
        Random rn = new Random();
        for(int i = 0; i < arraySize; i++){
            dynamicArray.add(rn.nextInt());
        }

        long startingTime= System.nanoTime();
        for(int i = 0; i < numOperations; i++){
            dynamicArray.remove(rn.nextInt(dynamicArray.size()));
        }

        System.out.print("numOperations: " + numOperations + ", arraySize: " + arraySize + ", nanoseconds: ");
        System.out.println(System.nanoTime() - startingTime);
    }

    public void performRemoveElementPerformanceTest(int arraySize, int numOperations) {
        DynamicArray<Integer> dynamicArray = new DynamicArray<>();
        Random rn = new Random();
        for(int i = 0; i < arraySize; i++){
            dynamicArray.add(i);
        }

        long startingTime= System.nanoTime();
        for(int i = 0; i < numOperations; i++){
            dynamicArray.removeElement(i);
        }

        System.out.print("numOperations: " + numOperations + ", arraySize: " + arraySize + ", nanoseconds: ");
        System.out.println(System.nanoTime() - startingTime);
    }

    public void performContainsPerformanceTest(int arraySize, int numOperations) {
        DynamicArray<Integer> dynamicArray = new DynamicArray<>();
        Random rn = new Random();
        for(int i = 0; i < arraySize; i++){
            dynamicArray.add(i);
        }

        long startingTime= System.nanoTime();
        for(int i = 0; i < numOperations; i++){
            if (i % 2 == 0) {
                dynamicArray.contains(i);
            }
            else {
                dynamicArray.contains(i + 100000);
            }
        }

        System.out.print("numOperations: " + numOperations + ", arraySize: " + arraySize + ", nanoseconds: ");
        System.out.println(System.nanoTime() - startingTime);
    }

    public void performIndexOfPerformanceTest(int arraySize, int numOperations) {
        DynamicArray<Integer> dynamicArray = new DynamicArray<>();
        Random rn = new Random();
        for(int i = 0; i < arraySize; i++){
            dynamicArray.add(i);
        }

        long startingTime= System.nanoTime();
        for(int i = 0; i < numOperations; i++){
            dynamicArray.contains(i);
        }

        System.out.print("numOperations: " + numOperations + ", arraySize: " + arraySize + ", nanoseconds: ");
        System.out.println(System.nanoTime() - startingTime);
    }
}
