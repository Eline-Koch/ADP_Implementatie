import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.adp_implementatie.PerformanceBenchmark;
import org.adp_implementatie.SelectionSort;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

//performance tests
//average
//size: 1000, time: 0.012901400 seconden
//size: 10000, time: 0.182184300 seconden
//size: 100000, time: 18.795509700 seconden
//
//best case
//size: 1000, time: 0.001712400 seconden
//size: 10000, time: 0.163788800 seconden
//size: 100000, time: 16.941172100 seconden
//
//worst case
//size: 1000, time: 0.001930300 seconden
//size: 10000, time: 0.185386000 seconden
//size: 100000, time: 17.019835200 seconden
//
// Het verschil tussen best case en worst case scenario is heel klein, omdat je elke keer de (steeds iets kleiner wordende)
// loop afmaakt, omdat je niet zeker weet wanneer je het minimum gevonden hebt. Dit zou je tussentijds op kunnen slaan,
// maar dan wordt het ook een beetje een andere algoritme.
//
// Waarom de tijd bij average met size 1000 zoveel langer duurt dan de andere met size 1000 is onduidelijk,
// maar over het algemeen zorgt een 10x grotere n voor een 100x langere tijd, dus (O)N^2.
public class SelectionSortTest {
    PerformanceBenchmark benchmark = new PerformanceBenchmark();
    Random random = new Random();
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
        System.out.println();
        System.out.println("performance tests");

        int[] arraySizes = {1000, 10000, 100000};

        System.out.println("average");

        for (int size : arraySizes) {
            this.performAveragePerformanceTest(size);
        }

        System.out.println();
        System.out.println("best case");

        for (int size : arraySizes) {
            this.performBestCasePerformanceTest(size);
        }

        System.out.println();
        System.out.println("worst case");

        for (int size : arraySizes) {
            this.performWorstCasePerformanceTest(size);
        }
    }

    public void performAveragePerformanceTest(int arraySize) {
        SelectionSort<Integer> selectionSort = new SelectionSort<>();
        Integer[] array = new Integer[arraySize];
        for (int i = 0;i < arraySize;i++) {
            array[i] = random.nextInt(arraySize);
        }

        benchmark.start();
        selectionSort.sort(array);
        benchmark.stop();

        System.out.print("size: " + arraySize + ", time: ");
        benchmark.printElapsedTime();
    }

    public void performBestCasePerformanceTest(int arraySize) {
        SelectionSort<Integer> selectionSort = new SelectionSort<>();
        Integer[] array = new Integer[arraySize];
        for (int i = 0;i < arraySize;i++) {
            array[i] = i;
        }

        benchmark.start();
        selectionSort.sort(array);
        benchmark.stop();

        System.out.print("size: " + arraySize + ", time: ");
        benchmark.printElapsedTime();
    }

    public void performWorstCasePerformanceTest(int arraySize) {
        SelectionSort<Integer> selectionSort = new SelectionSort<>();
        Integer[] array = new Integer[arraySize];
        for (int i = 0;i < arraySize;i++) {
            array[i] = arraySize - i;
        }

        benchmark.start();
        selectionSort.sort(array);
        benchmark.stop();

        System.out.print("size: " + arraySize + ", time: ");
        benchmark.printElapsedTime();
    }
}
