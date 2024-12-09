import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.adp_implementatie.BinarySearch;
import org.adp_implementatie.InsertionSort;
import org.adp_implementatie.PerformanceBenchmark;
import org.adp_implementatie.SelectionSort;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class InsertionSortTest {
    public static void main(String[] args) throws IOException {
        sortComparableObjectTest();
        performDataSetTest();
        performBestCaseTest();
        performWorstCaseTest();
        performRandomCaseTest();
    }

    public static void sortComparableObjectTest() {
        System.out.println();
        System.out.println("sort comparable object test");

        SelectionSort<Pizza> selectionSort = new SelectionSort<Pizza>();
        Pizza[] pizzas = {new Pizza ("mozarella", true),
                new Pizza ("salami", false), new Pizza ("anchovis", false)};

        System.out.println("before:");
        for (Pizza pizza : pizzas) {
            pizza.print();
        }

        InsertionSort.insertionSort(pizzas);

        System.out.println("after:");
        for (Pizza pizza : pizzas) {
            pizza.print();
        }
    }

    public static void performWorstCaseTest() {
        PerformanceBenchmark benchmark = new PerformanceBenchmark();
        Integer[] largeArray = new Integer[1000];

        // Vul de array achterstevoren
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = largeArray.length - i;
        }

        System.out.println("Integer array test met insertionsort (worst case: reversed array): ");
        benchmark.start();
        InsertionSort.insertionSort(largeArray);
        benchmark.stop();
        System.out.println(benchmark.getElapsedTimeInSeconds() + " seconden om uit te voeren");
    }

    public static void performBestCaseTest() {
        PerformanceBenchmark benchmark = new PerformanceBenchmark();
        Integer[] largeArray = new Integer[1000];

        System.out.println("Integer array test met insertionsort (best case: sorted array): ");

        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = i;
        }
        benchmark.start();
        InsertionSort.insertionSort(largeArray);
        benchmark.stop();
        System.out.println(benchmark.formatElapsedTime(benchmark.getElapsedTimeInSeconds()) + " seconden om uit te voeren");
    }


    public static void performRandomCaseTest() {
        PerformanceBenchmark benchmark = new PerformanceBenchmark();
        Integer[] largeArray = new Integer[1000];

        System.out.println("Integer array test met insertionsort (Random case: random ints array): ");

        Random random = new Random();

        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = random.nextInt(largeArray.length);
            ;
        }
        InsertionSort.printArray(largeArray);
        benchmark.start();
        InsertionSort.insertionSort(largeArray);
        benchmark.stop();
        System.out.println(benchmark.formatElapsedTime(benchmark.getElapsedTimeInSeconds()) + " seconden om uit te voeren");
        InsertionSort.printArray(largeArray);
    }


    public static void performDataSetTest() throws IOException {
        System.out.println();
        System.out.println("dataset tests");

        String dataString = Files.readString(Path.of("src/test/resources/dataset_sorteren.json"), Charset.defaultCharset());
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, List<Object>> dataMap = objectMapper.readValue(dataString, new TypeReference<>() {
        });

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
                } else {
                    System.out.println("Cannot add \"o\" to array, because \"o\" is null");
                }
            }
            array = Arrays.stream(array).filter(Objects::nonNull).toArray(Double[]::new);

            System.out.println("before:");
            System.out.print(key + ": ");
            System.out.println(Arrays.toString(array));

            InsertionSort.insertionSort(array);

            System.out.println("after:");
            System.out.print(key + ": ");
            System.out.println(Arrays.toString(array));
            System.out.println();
        }
    }
}
