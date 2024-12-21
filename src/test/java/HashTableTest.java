import java.io.IOException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.adp_implementatie.HashTable;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class HashTableTest {
    public static void main(String[] args) throws IOException {
        HashTableTest test = new HashTableTest();
        test.performDataSetTest();
        test.performSpecialCasePerformanceTest();
        test.performMethodPerformanceTest();
    }

   @SuppressWarnings("unchecked")
    public void performDataSetTest() throws IOException {
        System.out.println();
        System.out.println("dataset tests");

        String dataString = Files.readString(Path.of("src/test/resources/dataset_hashing.json"), Charset.defaultCharset());
        ObjectMapper objectMapper = new ObjectMapper();

        // Convert JSON string to Map
        Map<String, Object> dataMap = objectMapper.readValue(dataString, new TypeReference<>(){});
        Map<String, List<Integer>> dataMap1 = (Map<String, List<Integer>>) dataMap.values().toArray()[0];

        HashTable hashTable = new HashTable();

        for (Map.Entry<String, List<Integer>> entry : dataMap1.entrySet()) {
            String key = entry.getKey();
            List<Integer> value = entry.getValue();

            for (Integer num : value) {
                hashTable.insert(key, num);
            }
        }
        hashTable.printTable();
    }

    public void performSpecialCasePerformanceTest() {
        System.out.println();
        System.out.println("special case performance tests");
        System.out.println("worst case");

        HashTable hashTable = new HashTable();
        for (int i = 0;i < 10; i++) {
            hashTable.insert("a" + i, 1);
        }


        hashTable.printTable();
    }

    public void performMethodPerformanceTest() {
        System.out.println();
        System.out.println("perform methods tests");
        System.out.println("insert");

        int[] numEntries = {1000, 10000, 100000};
        int numOperations = 1000;

        for (int size : numEntries) {
                this.performInsertPerformanceTest(size, numOperations);
        }

        System.out.println();
        System.out.println("get");

        for (int size : numEntries) {
                this.performGetPerformanceTest(size, numOperations);
        }

        System.out.println();
        System.out.println("delete");

        for (int size : numEntries) {
                this.performDeletePerformanceTest(size, numOperations);
        }

        System.out.println();
        System.out.println("update");

        for (int size : numEntries) {
                this.performUpdatePerformanceTest(size, numOperations);
        }
    }

    public void performInsertPerformanceTest(int numEntries, int numOperations) {
        HashTable hashTable = new HashTable();

        for(int i = 0; i < numEntries; i++){
            hashTable.insert(String.valueOf(i), i);
        }

        long startingTime = System.nanoTime();
        for(int i = 0; i < numOperations; i++){
            hashTable.insert(String.valueOf(i), i);
        }

        System.out.print("numOperations: " + numOperations + ", size: " + numEntries + ", nanoseconds: ");
        System.out.println(System.nanoTime() - startingTime);
    }

    public void performGetPerformanceTest(int numEntries, int numOperations) {
        HashTable hashTable = new HashTable();

        for(int i = 0; i < numEntries; i++){
            hashTable.insert(String.valueOf(i), i);
        }

        long startingTime = System.nanoTime();
        for(int i = 0; i < numOperations; i++){
            hashTable.get(String.valueOf(i / 100));
        }

        System.out.print("numOperations: " + numOperations + ", size: " + numEntries + ", nanoseconds: ");
        System.out.println(System.nanoTime() - startingTime);
    }

    public void performDeletePerformanceTest(int numEntries, int numOperations) {
        HashTable hashTable = new HashTable();

        for(int i = 0; i < numEntries; i++){
            hashTable.insert(String.valueOf(i), i);
        }

        long startingTime = System.nanoTime();
        for(int i = 0; i < numOperations; i++){
            hashTable.delete(String.valueOf(i));
        }

        System.out.print("numOperations: " + numOperations + ", size: " + numEntries + ", nanoseconds: ");
        System.out.println(System.nanoTime() - startingTime);
    }

    public void performUpdatePerformanceTest(int numEntries, int numOperations) {
        HashTable hashTable = new HashTable();

        for(int i = 0; i < numEntries; i++){
            hashTable.insert(String.valueOf(i), i);
        }

        long startingTime = System.nanoTime();
        for(int i = 0; i < numOperations; i++){
            hashTable.update(String.valueOf(hashTable.size() / 2), i);
        }

        System.out.print("numOperations: " + numOperations + ", size: " + numEntries + ", nanoseconds: ");
        System.out.println(System.nanoTime() - startingTime);
    }
}
