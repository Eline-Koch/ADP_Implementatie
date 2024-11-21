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

        this.performAddPerformanceTest(1000);
        this.performAddPerformanceTest(10000);
        this.performAddPerformanceTest(100000);

//        System.out.println();
//        System.out.println("get");
//
//        startingTime= System.nanoTime();
//        for(int i = 0; i < dynamicArray1.size(); i++){
//            dynamicArray1.get(rn.nextInt(dynamicArray1.size()));
//        }
//        System.out.println(System.nanoTime() - startingTime);

        //set

        //remove(int index)

        //remove(E element)

        //contains(E element)

        //indexOf(E element)

    }

    public void performAddPerformanceTest(int numOperations) {
        System.out.println();
        System.out.println("add");

        DynamicArray<Integer> dynamicArray = new DynamicArray<>();
        Random rn = new Random();
        long startingTime= System.nanoTime();
        for(int i = 0; i < numOperations; i++){
            dynamicArray.add(rn.nextInt());
        }

        System.out.println(System.nanoTime() - startingTime);
    }

    public void performGetPerformanceTest(int numOperations) {
        System.out.println();
        System.out.println("get");
    }

    public static void main(String[] args) throws IOException {
        DynamicArrayTest test = new DynamicArrayTest();
        test.performDataSetTest();
        test.performPerformanceTest();

    }
}
