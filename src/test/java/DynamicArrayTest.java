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

            dynamicArray.printArray();
        }
    }

    public void performPerformanceTest() {
        System.out.println();
        System.out.println("performance tests");
        System.out.println("add");

        int[] testNums = {1000, 10000, 100000};

        for (int num: testNums) {
            this.performAddPerformanceTest(num);
        }

        System.out.println();
        System.out.println("get");

        for (int size : testNums) {
            for (int num: testNums) {
                this.performGetPerformanceTest(size, num);
            }
        }

        System.out.println();
        System.out.println("set");

        for (int size : testNums) {
            for (int num: testNums) {
                this.performSetPerformanceTest(size, num);
            }
        }

        System.out.println();
        System.out.println("remove(index)");

        for (int num: testNums) {
            this.performRemoveIndexPerformanceTest(200000, num);
        }

        System.out.println();
        System.out.println("remove(element)");

        for (int num: testNums) {
            this.performRemoveElementPerformanceTest(200000, num);
        }

        System.out.println();
        System.out.println("contains");



        System.out.println();
        System.out.println("indexOf(element)");




    }

    public void performAddPerformanceTest(int numOperations) {
        DynamicArray<Integer> dynamicArray = new DynamicArray<>();
        Random rn = new Random();

        long startingTime= System.nanoTime();
        for(int i = 0; i < numOperations; i++){
            dynamicArray.add(rn.nextInt());
        }

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
            dynamicArray.remove(i);
        }

        System.out.println(System.nanoTime() - startingTime);
    }
}
