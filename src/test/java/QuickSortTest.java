import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.adp_implementatie.PerformanceBenchmark;
import org.adp_implementatie.QuickSort;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


//
//Integer array test with QuickSort (Best case: Sorted array):
//        0,0005953 seconden seconds to execute

//        Integer array test with QuickSort (Worst case: Reversed array):
//        0.0011604 seconds to execute

//        Integer array test with QuickSort (Random case: Random ints array):
//        0,001313 seconden seconds to execute
public class QuickSortTest {
    public static void main(String[] args) throws IOException {
        sortComparableObjectTest();

        performDataSetTest();

        System.out.println("--------------------------------------------------------------");
        System.out.println("starting warm up");
        System.out.println("--------------------------------------------------------------");

        for (int i = 0; i < 1000; i++) {
            performBestCaseTest();   // Best case: Already sorted
            performWorstCaseTest();  // Worst case: Reverse sorted
            performRandomCaseTest(); // Random case
        }

        System.out.println("--------------------------------------------------------------");
        System.out.println("warming up complete");
        System.out.println("--------------------------------------------------------------");
        // Testcases with size of 1000
        performBestCaseTest();   // Best case: Already sorted
        performWorstCaseTest();  // Worst case: Reverse sorted
        performRandomCaseTest(); // Random case
    }

    public static void sortComparableObjectTest() {
        System.out.println();
        System.out.println("Sort Comparable Object Test");

        Pizza[] pizzas = {new Pizza("mozarella", true),
                new Pizza("salami", false),
                new Pizza("anchovis", false)};

        System.out.println("Before:");
        for (Pizza pizza : pizzas) {
            pizza.print();
        }

        QuickSort<Pizza> quickSort = new QuickSort<>();
        quickSort.sort(pizzas);

        System.out.println("After:");
        for (Pizza pizza : pizzas) {
            pizza.print();
        }
    }

    public static void performWorstCaseTest() {
        PerformanceBenchmark benchmark = new PerformanceBenchmark();
        Integer[] largeArray = new Integer[10000];

        // Fill the array in reverse order
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = largeArray.length - i;
        }

        System.out.println("Integer array test with QuickSort (Worst case: Reversed array):");
        benchmark.start();
        QuickSort<Integer> quickSort = new QuickSort<>();
        quickSort.sort(largeArray);
        benchmark.stop();
        System.out.println(benchmark.getElapsedTimeInSeconds() + " seconds to execute");
    }

    public static void performBestCaseTest() {
        PerformanceBenchmark benchmark = new PerformanceBenchmark();
        Integer[] largeArray = new Integer[10000];

        // Fill the array in sorted order
        System.out.println("Integer array test with QuickSort (Best case: Sorted array):");
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = i;
        }
        benchmark.start();
        QuickSort<Integer> quickSort = new QuickSort<>();
        quickSort.sort(largeArray);
        benchmark.stop();
        System.out.println(benchmark.formatElapsedTime(benchmark.getElapsedTimeInSeconds()) + " seconds to execute");
    }

    public static void performRandomCaseTest() {
        PerformanceBenchmark benchmark = new PerformanceBenchmark();
        Integer[] largeArray = new Integer[10000];

        System.out.println("Integer array test with QuickSort (Random case: Random ints array):");

        Random random = new Random();
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = random.nextInt(largeArray.length);
        }
        QuickSort.printArray(largeArray);
        benchmark.start();
        QuickSort<Integer> quickSort = new QuickSort<>();
        quickSort.sort(largeArray);
        benchmark.stop();
        System.out.println(benchmark.formatElapsedTime(benchmark.getElapsedTimeInSeconds()) + " seconds to execute");
        QuickSort.printArray(largeArray);
    }

    public static void performDataSetTest() throws IOException {
        System.out.println();
        System.out.println("Dataset Tests");

        String dataString = Files.readString(Path.of("src/test/resources/dataset_sorteren.json"), Charset.defaultCharset());
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, List<Object>> dataMap = objectMapper.readValue(dataString, new TypeReference<>() {});

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

            System.out.println("Before:");
            System.out.print(key + ": ");
            System.out.println(Arrays.toString(array));

            QuickSort<Double> quickSort = new QuickSort<>();
            quickSort.sort(array);

            System.out.println("After:");
            System.out.print(key + ": ");
            System.out.println(Arrays.toString(array));
            System.out.println();
        }
    }
}
