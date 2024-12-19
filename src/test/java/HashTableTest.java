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
        test.performPerformanceTest();
    }

    public void performDataSetTest() throws IOException {
        System.out.println();
        System.out.println("dataset tests");

        String dataString = Files.readString(Path.of("src/test/resources/dataset_sorteren.json"), Charset.defaultCharset());
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, List<Object>> dataMap = objectMapper.readValue(dataString, new TypeReference<>() {
        });
        for (Map.Entry<String, List<Object>> entry : dataMap.entrySet()) {
            String key = entry.getKey();
            List<Object> value = entry.getValue();
            HashTable hashTable = new HashTable();

            int i = 0;
            for (Object o : value) {
                if (o != null) {
                    if (o instanceof Double) {
                        hashTable.add(key + "[" + i + "]", (double) o);
                    } else if (o instanceof Integer) {
                        hashTable.add(key + "[" + i + "]", ((Integer) o).doubleValue());
                    } else {
                        System.out.println("Cannot add \"o\" to list, because \"o\" is of type" + o.getClass().getName());
                    }
                } else {
                    System.out.println("Cannot add \"o\" to list, because \"o\" is null");
                }
                i++;
            }
            hashTable.printTable();
        }
    }

    public void performPerformanceTest() {

    }
}
