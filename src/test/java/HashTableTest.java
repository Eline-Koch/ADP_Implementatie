import java.io.IOException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.adp_implementatie.HashTable;
import org.adp_implementatie.PerformanceBenchmark;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

//perform methods tests
//insert
//numOperations: 100000, size: 10000, nanoseconds: 0.171070600 seconden
//numOperations: 100000, size: 100000, nanoseconds: 0.111479600 seconden
//numOperations: 100000, size: 1000000, nanoseconds: 0.161935700 seconden
//Eerste resultaat is vrij onbetrouwbaar, daarna 10x grotere size = 1.45x de uitvoertijd.
//In theorie kan complexiteit O(1) benaderd worden, maar als er meerdere keys dezelfde hashwaarde/index hebben
//en als de hashtabel moet groeien/krimpen wordt de uitvoertijd langer.
//
//get
//numOperations: 100000, size: 10000, nanoseconds: 0.012882600 seconden
//numOperations: 100000, size: 100000, nanoseconds: 0.008933200 seconden
//numOperations: 100000, size: 1000000, nanoseconds: 0.023740600 seconden
//0.023740600 / 0.008933200 = 2.66
//
//special case performance tests
//worst case
//numOperations: 100000, nanoseconds: 0.025842600 seconden
//best case
//numOperations: 100000, nanoseconds: 0.010177000 seconden
// 100000x wordt er een HashTable gemaakt en worden hier 5 entries in geinsert. Bij worst case hebben de keys dezelfde
// hashwaarde/index (bij basis 17, waar de tabel mee begint), bij best case hebben ze een andere index (steeds +1).
// Het verschil is duidelijk te zien, door quadratic probing duurt de worst case meer dan 2x zo lang.

public class HashTableTest {

    PerformanceBenchmark benchmark = new PerformanceBenchmark();
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

        String dataString = Files.readString(Path.of("src/test/resources/dataset_hashing.json"),
                Charset.defaultCharset());
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

        int numOperations = 100000;


        benchmark.start();
        for (int i = 0; i < numOperations; i++) {
            HashTable worstTable = new HashTable();
            worstTable.insert("a.", i);
            worstTable.insert("a?", i);
            worstTable.insert("aP", i);
            worstTable.insert("aa", i);
            worstTable.insert("ar", i);
        }
        benchmark.stop();

        System.out.print("numOperations: " + numOperations + ", nanoseconds: ");
        benchmark.printElapsedTime();

        System.out.println("best case");

        benchmark.start();
        for (int i = 0; i < numOperations; i++) {
            HashTable bestTable = new HashTable();
            bestTable.insert("a1", i);
            bestTable.insert("a2", i);
            bestTable.insert("a3", i);
            bestTable.insert("a4", i);
            bestTable.insert("a5", i);
        }
        benchmark.stop();

        System.out.print("numOperations: " + numOperations + ", nanoseconds: ");
        benchmark.printElapsedTime();
    }

    public void performMethodPerformanceTest() {
        System.out.println();
        System.out.println("perform methods tests");
        System.out.println("insert");

        int[] numEntries = {10000, 100000, 1000000};
        int numOperations = 100000;

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

        benchmark.start();
        for(int i = 0; i < numOperations; i++){
            hashTable.insert(String.valueOf(i), i);
        }
        benchmark.stop();

        System.out.print("numOperations: " + numOperations + ", size: " + numEntries + ", nanoseconds: ");
        benchmark.printElapsedTime();
    }

    public void performGetPerformanceTest(int numEntries, int numOperations) {
        HashTable hashTable = new HashTable();

        for(int i = 0; i < numEntries; i++){
            hashTable.insert(String.valueOf(i), i);
        }

        benchmark.start();
        for(int i = 0; i < numOperations; i++){
            hashTable.get(String.valueOf(i / 100));
        }
        benchmark.stop();

        System.out.print("numOperations: " + numOperations + ", size: " + numEntries + ", nanoseconds: ");
        benchmark.printElapsedTime();
    }

    public void performDeletePerformanceTest(int numEntries, int numOperations) {
        HashTable hashTable = new HashTable();

        for(int i = 0; i < numEntries; i++){
            hashTable.insert(String.valueOf(i), i);
        }

        benchmark.start();
        for(int i = 0; i < numOperations; i++){
            hashTable.delete(String.valueOf(i));
        }
        benchmark.stop();

        System.out.print("numOperations: " + numOperations + ", size: " + numEntries + ", nanoseconds: ");
        benchmark.printElapsedTime();
    }

    public void performUpdatePerformanceTest(int numEntries, int numOperations) {
        HashTable hashTable = new HashTable();

        for(int i = 0; i < numEntries; i++){
            hashTable.insert(String.valueOf(i), i);
        }

        benchmark.start();
        for(int i = 0; i < numOperations; i++){
            hashTable.update(String.valueOf(hashTable.size() / 2), i);
        }
        benchmark.stop();

        System.out.print("numOperations: " + numOperations + ", size: " + numEntries + ", nanoseconds: ");
        benchmark.printElapsedTime();
    }
}
