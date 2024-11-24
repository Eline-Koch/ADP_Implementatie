import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.adp_implementatie.HanStackMetArray;
import org.adp_implementatie.PerformanceBenchmark;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class StackTest {
    public static void main(String[] args) throws IOException {
        Pizza pizza = new Pizza("Doner kebab", false);

        performDataSetTest();

        int[] stackSize = {1000, 10000, 100000, 1000000, 10000000, 100000000};

        for (int i : stackSize) {
            testStackAddOperation(i, "String insert");
        }

        for (int i : stackSize) {
            testStackAddOperation(i, pizza);
        }

        for (int i : stackSize) {
            testStackAddOperation(i, new int[]{1, 3, 4});
        }

        for (int i : stackSize) {
            testStackPopOperation(i, 1);
        }

        for (int i : stackSize) {
            testStackPeekOperation(i, 1);
        }

        for (int i : stackSize) {
            testStackTopOperation(i, 1);
        }
    }

    public static void performDataSetTest() throws IOException {
        System.out.println();
        System.out.println("dataset test");

        String dataString = Files.readString(Path.of("src/test/resources/dataset_sorteren.json"), Charset.defaultCharset());
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, List<Object>> dataMap = objectMapper.readValue(dataString, new TypeReference<>() {});

        for (Map.Entry<String, List<Object>> entry : dataMap.entrySet()) {
            String key = entry.getKey();
            List<Object> value = entry.getValue();

            HanStackMetArray<Object> stack = new HanStackMetArray<>(10000);
            for (Object o : value) {
                stack.push(o);
            }

            System.out.print(key + ": ");
            stack.printStack();
        }
    }

    public static void testStackAddOperation(int stackSize, Object pushObject) {
        HanStackMetArray<Object> stack = new HanStackMetArray<>(stackSize);
        PerformanceBenchmark benchmark = new PerformanceBenchmark();

        benchmark.start();
        for (int i = 0; i < stackSize; i++) {
            stack.push(pushObject);
        }
        benchmark.stop();
        System.out.println("Stack add met size: " + stackSize + " Object to insert: " + pushObject.toString());
        benchmark.printElapsedTime();
        System.out.println();
    }

    public static void testStackPopOperation(int stackSize, Object pushObject) {
        HanStackMetArray<Object> stack = new HanStackMetArray<>(stackSize);
        PerformanceBenchmark benchmark = new PerformanceBenchmark();

        for (int i = 0; i < stackSize; i++) {
            stack.push(pushObject);
        }

        benchmark.start();
        for (int i = 0; i < stackSize - 1; i++) {
            stack.pop();
        }
        benchmark.stop();
        System.out.println("Stack Pop met size: " + stackSize + " Object to delete: " + pushObject.toString());
        benchmark.printElapsedTime();
        System.out.println();
    }

    public static void testStackPeekOperation(int stackSize, Object pushObject) {
        HanStackMetArray<Object> stack = new HanStackMetArray<>(stackSize);
        PerformanceBenchmark benchmark = new PerformanceBenchmark();

        for (int i = 0; i < stackSize; i++) {
            stack.push(pushObject);
        }

        benchmark.start();
        stack.peek();
        benchmark.stop();
        System.out.println("Stack Peek met size: " + stackSize + " Object to peek: " + pushObject.toString());
        benchmark.printElapsedTime();
        System.out.println();
    }

    public static void testStackTopOperation(int stackSize, Object pushObject) {
        HanStackMetArray<Object> stack = new HanStackMetArray<>(stackSize);
        PerformanceBenchmark benchmark = new PerformanceBenchmark();

        for (int i = 0; i < stackSize; i++) {
            stack.push(pushObject);
        }

        benchmark.start();
        for (int i = 0; i < stackSize - 1; i++) {
            stack.top();
        }
        benchmark.stop();
        System.out.println("Stack Top met size: " + stackSize + " Object to top: " + pushObject.toString());
        benchmark.printElapsedTime();
        System.out.println();
    }
}
