import java.io.IOException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.adp_implementatie.DoublyLinkedList;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class DoublyLinkedListTest {
    public static void main(String[] args) throws IOException {
        DoublyLinkedListTest test = new DoublyLinkedListTest();
        test.performDataSetTest();
        test.containsObjectTest();
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

            DoublyLinkedList<Double> doublyLinkedList = new DoublyLinkedList<>();
            for (Object o : value) {
                if (o != null) {
                    if (o instanceof Double) {
                        doublyLinkedList.add((Double) o);
                    } else if (o instanceof Integer) {
                        doublyLinkedList.add(((Integer) o).doubleValue());
                    } else {
                        System.out.println("Cannot add \"o\" to list, because \"o\" is of type" + o.getClass().getName());
                    }
                }
                else {
                    System.out.println("Cannot add \"o\" to list, because \"o\" is null");
                }
            }
            System.out.print(key + ": ");
            doublyLinkedList.printList();
        }
    }

    //contains method should compare the value of an Object, not the reference
    public void containsObjectTest() {
        System.out.println();
        System.out.println("containsObject");

        DoublyLinkedList<Pizza> doublyLinkedList = new DoublyLinkedList<>();
        Pizza pizza1 = new Pizza("mozarella", true);
        Pizza pizza2 = new Pizza("mozarella", true);

        doublyLinkedList.add(pizza1);

        System.out.println("same reference, same value: " + doublyLinkedList.contains(pizza1));
        System.out.println("different reference, same value: " + doublyLinkedList.contains(pizza2));
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
                this.performRemoveIndexPerformanceTest(size * 2, num / 100);
            }
        }

        System.out.println();
        System.out.println("remove(element)");

        for (int size : arraySize) {
            for (int num : numOperations) {
                this.performRemoveElementPerformanceTest(size * 2, num / 100);
            }
        }

        System.out.println();
        System.out.println("contains");

        for (int size : arraySize) {
            for (int num : numOperations) {
                this.performContainsPerformanceTest(size, num / 100);
            }
        }

        System.out.println();
        System.out.println("indexOf");

        for (int size : arraySize) {
            for (int num : numOperations) {
                this.performIndexOfPerformanceTest(size, num / 100);
            }
        }
    }

    public void performAddPerformanceTest(int arraySize, int numOperations) {
        DoublyLinkedList<Integer> doublyLinkedList = new DoublyLinkedList<>();

        for(int i = 0; i < arraySize; i++){
            doublyLinkedList.add(i);
        }

        long startingTime = System.nanoTime();
        for(int i = 0; i < numOperations; i++){
            doublyLinkedList.add(i);
        }

        System.out.print("numOperations: " + numOperations + ", size: " + arraySize + ", nanoseconds: ");
        System.out.println(System.nanoTime() - startingTime);
    }

    public void performGetPerformanceTest(int arraySize, int numOperations) {
        DoublyLinkedList<Integer> doublyLinkedList = new DoublyLinkedList<>();

        for(int i = 0; i < arraySize; i++){
            doublyLinkedList.add(i);
        }

        long startingTime = System.nanoTime();
        for(int i = 0; i < numOperations; i++){
            doublyLinkedList.get(i / 100);
        }

        System.out.print("numOperations: " + numOperations + ", size: " + arraySize + ", nanoseconds: ");
        System.out.println(System.nanoTime() - startingTime);
    }

    public void performSetPerformanceTest(int arraySize, int numOperations) {
        DoublyLinkedList<Integer> doublyLinkedList = new DoublyLinkedList<>();

        for(int i = 0; i < arraySize; i++){
            doublyLinkedList.add(i);
        }

        long startingTime = System.nanoTime();
        for(int i = 0; i < numOperations; i++){
            doublyLinkedList.set(doublyLinkedList.size() / 2, i);
        }

        System.out.print("numOperations: " + numOperations + ", size: " + arraySize + ", nanoseconds: ");
        System.out.println(System.nanoTime() - startingTime);
    }

    public void performRemoveIndexPerformanceTest(int arraySize, int numOperations) {
        DoublyLinkedList<Integer> doublyLinkedList = new DoublyLinkedList<>();

        for(int i = 0; i < arraySize; i++){
            doublyLinkedList.add(i);
        }

        long startingTime = System.nanoTime();
        for(int i = 0; i < numOperations; i++){
            doublyLinkedList.remove(doublyLinkedList.size() / 2);
        }

        System.out.print("numOperations: " + numOperations + ", size: " + arraySize + ", nanoseconds: ");
        System.out.println(System.nanoTime() - startingTime);
    }

    public void performRemoveElementPerformanceTest(int arraySize, int numOperations) {
        DoublyLinkedList<Integer> doublyLinkedList = new DoublyLinkedList<>();
        for(int i = 0; i < arraySize; i++){
            doublyLinkedList.add(i);
        }

        long startingTime = System.nanoTime();
        for(int i = 0; i < numOperations; i++){
            doublyLinkedList.remove(Integer.valueOf(i + doublyLinkedList.size() / 2));
        }

        System.out.print("numOperations: " + numOperations + ", size: " + arraySize + ", nanoseconds: ");
        System.out.println(System.nanoTime() - startingTime);
    }

    public void performContainsPerformanceTest(int arraySize, int numOperations) {
        DoublyLinkedList<Integer> doublyLinkedList = new DoublyLinkedList<>();
        for(int i = 0; i < arraySize; i++){
            doublyLinkedList.add(i);
        }

        long startingTime = System.nanoTime();
        for(int i = 0; i < numOperations; i++){
            if (i % 2 == 0) {
                doublyLinkedList.contains(i + doublyLinkedList.size() / 2);
            }
            else {
                doublyLinkedList.contains(i + 100000);
            }
        }

        System.out.print("numOperations: " + numOperations + ", size: " + arraySize + ", nanoseconds: ");
        System.out.println(System.nanoTime() - startingTime);
    }

    public void performIndexOfPerformanceTest(int arraySize, int numOperations) {
        DoublyLinkedList<Integer> doublyLinkedList = new DoublyLinkedList<>();
        for(int i = 0; i < arraySize; i++){
            doublyLinkedList.add(i);
        }

        long startingTime = System.nanoTime();
        for(int i = 0; i < numOperations; i++){
            doublyLinkedList.indexOf(i + doublyLinkedList.size() / 2);
        }

        System.out.print("numOperations: " + numOperations + ", size: " + arraySize + ", nanoseconds: ");
        System.out.println(System.nanoTime() - startingTime);
    }
}
