import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.adp_implementatie.MergeSort;
import org.adp_implementatie.PerformanceBenchmark;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

//performance tests
//average
//size: 1000, time: 0.000864100 seconden
//size: 10000, time: 0.009390900 seconden
//size: 100000, time: 0.080532500 seconden
//
//Complexiteit van merge sort is O(n log n). Het lijkt hier meer 0(n).
//Het sorteren van een 10x zo grote array kost 0.080532500 / 0.009390900 = 8.57558913416x zoveel tijd.
//Sneller dan sorteeralgoritmes met complexiteit O(n^2).
//
//best case (sorted array)
//size: 1000, time: 0.000061900 seconden
//size: 10000, time: 0.000741100 seconden
//size: 100000, time: 0.009625700 seconden
//
//worst case (all indexes contain same value)
//size: 1000, time: 0.000131700 seconden
//size: 10000, time: 0.001316600 seconden
//size: 100000, time: 0.010543300 seconden
//
//Het verschil tussen de best case (gesorteerde array) en de worst case (alle waarden hetzelfde) is heel klein.
//Een array gesorteerd in de omgekeerde volgorde gaf ook weinig verschil in tijd. De performance is dus erg stabiel.

public class MergeSortTest {
    PerformanceBenchmark benchmark = new PerformanceBenchmark();
    Random random = new Random();
    public static void main(String[] args) throws IOException {
        MergeSortTest test = new MergeSortTest();
        test.performDataSetTest();
        test.sortComparableObjectTest();
        test.sortStringTest();
        test.performPerformanceTest();
    }

    public void performDataSetTest() throws IOException {
        System.out.println();
        System.out.println("dataset tests");

        MergeSort<Double> mergeSort = new MergeSort<>();

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

            mergeSort.mergeSort(array);

            System.out.println("after:");
            System.out.print(key + ": ");
            System.out.println(Arrays.toString(array));
            System.out.println();
        }
    }

    public void sortComparableObjectTest() {
        System.out.println();
        System.out.println("sort comparable object test");

        MergeSort<Pizza> mergeSort = new MergeSort<Pizza>();
        Pizza[] pizzas = {new Pizza ("mozarella", true),
                new Pizza ("salami", false), new Pizza ("anchovis", false)};

        System.out.println("before:");
        for (Pizza pizza : pizzas) {
            pizza.print();
        }

        mergeSort.mergeSort(pizzas);

        System.out.println("after:");
        for (Pizza pizza : pizzas) {
            pizza.print();
        }
    }

    public void sortStringTest() {
        System.out.println();
        System.out.println("sort string test");

        MergeSort<String> mergeSort = new MergeSort<>();
        String[] fruits = {"mango", "apple", "orange", "banana"};

        System.out.println("before:");
        System.out.println(Arrays.toString(fruits));

        mergeSort.mergeSort(fruits);

        System.out.println("after:");
        System.out.println(Arrays.toString(fruits));
    }

    public void performPerformanceTest() {
        System.out.println();
        System.out.println("performance tests");

        int[] arraySizes = {1000, 10000, 100000};

        System.out.println("average");

        for (int size : arraySizes) {
            this.performAveragePerformanceTest(size);
        }

        System.out.println();
        System.out.println("best case (sorted array)");

        for (int size : arraySizes) {
            this.performBestCasePerformanceTest(size);
        }

        System.out.println();
        System.out.println("worst case (all indexes contain same value)");

        for (int size : arraySizes) {
            this.performWorstCasePerformanceTest(size);
        }
    }

    public void performAveragePerformanceTest(int arraySize) {
        MergeSort<Integer> mergeSort = new MergeSort<>();
        Integer[] array = new Integer[arraySize];
        for (int i = 0; i < arraySize; i++) {
            array[i] = random.nextInt(arraySize);
        }

        benchmark.start();
        mergeSort.mergeSort(array);
        benchmark.stop();

        System.out.print("size: " + arraySize + ", time: ");
        benchmark.printElapsedTime();
    }

    public void performBestCasePerformanceTest(int arraySize) {
        MergeSort<Integer> mergeSort = new MergeSort<>();
        Integer[] array = new Integer[arraySize];
        for (int i = 0; i < arraySize; i++) {
            array[i] = i;
        }

        benchmark.start();
        mergeSort.mergeSort(array);
        benchmark.stop();

        System.out.print("size: " + arraySize + ", time: ");
        benchmark.printElapsedTime();
    }

    public void performWorstCasePerformanceTest(int arraySize) {
        MergeSort<Integer> mergeSort = new MergeSort<>();
        Integer[] array = new Integer[arraySize];
        for (int i = 0; i < arraySize; i++) {
            array[i] = 1;
        }

        benchmark.start();
        mergeSort.mergeSort(array);
        benchmark.stop();

        System.out.print("size: " + arraySize + ", time: ");
        benchmark.printElapsedTime();
    }
}
