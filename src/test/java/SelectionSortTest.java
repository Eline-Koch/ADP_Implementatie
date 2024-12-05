import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.adp_implementatie.SelectionSort;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SelectionSortTest {
    public static void main(String[] args) throws IOException {
        SelectionSortTest test = new SelectionSortTest();
        test.performDataSetTest();
        test.sortComparableObjectTest();
        test.sortStringTest();
        test.performPerformanceTest();
    }

    public void performDataSetTest() throws IOException {
        System.out.println();
        System.out.println("dataset tests");

        SelectionSort<Double> selectionSort = new SelectionSort<>();

        String dataString = Files.readString(Path.of("src/test/resources/dataset_sorteren.json"), Charset.defaultCharset());
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, List<Object>> dataMap = objectMapper.readValue(dataString, new TypeReference<>(){});

        for (Map.Entry<String, List<Object>> entry : dataMap.entrySet()) {
            String key = entry.getKey();
            List<Object> value = entry.getValue();

            Double[] array = new Double[value.size()];
            int i = 0;
            for (Object o : value) {
                if (o != null) {
                    if (o instanceof Double) {
                        array[i] = (Double) o;
                        i++;
                    } else if (o instanceof Integer) {
                        array[i] = ((Integer) o).doubleValue();
                        i++;
                    } else {
                        System.out.println("Cannot add \"o\" to array, because \"o\" is of type" + o.getClass().getName());
                    }
                }
                else {
                    System.out.println("Cannot add \"o\" to array, because \"o\" is null");
                }
            }
            array = Arrays.stream(array).filter(Objects::nonNull).toArray(Double[]::new);

            System.out.println("before:");
            System.out.print(key + ": ");
            System.out.println(Arrays.toString(array));

            selectionSort.sort(array);

            System.out.println("after:");
            System.out.print(key + ": ");
            System.out.println(Arrays.toString(array));
            System.out.println();
        }
    }

    public void sortComparableObjectTest() {
        System.out.println();
        System.out.println("sort comparable object test");

        SelectionSort<Pizza> selectionSort = new SelectionSort<Pizza>();
        Pizza[] pizzas = {new Pizza ("mozarella", true),
                new Pizza ("salami", false), new Pizza ("anchovis", false)};

        System.out.println("before:");
        for (Pizza pizza : pizzas) {
            pizza.print();
        }

        selectionSort.sort(pizzas);

        System.out.println("after:");
        for (Pizza pizza : pizzas) {
            pizza.print();
        }
    }

    public void sortStringTest() {
        System.out.println();
        System.out.println("sort string test");

        SelectionSort<String> selectionSort = new SelectionSort<>();
        String[] fruits = {"mango", "apple", "orange", "banana"};

        System.out.println("before:");
        System.out.println(Arrays.toString(fruits));

        selectionSort.sort(fruits);

        System.out.println("after:");
        System.out.println(Arrays.toString(fruits));
    }

    public void performPerformanceTest() {

    }
}
