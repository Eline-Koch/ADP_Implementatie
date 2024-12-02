import org.adp_implementatie.BinarySearch;
import org.adp_implementatie.InsertionSort;
import org.adp_implementatie.PerformanceBenchmark;

public class InsertionSortTest {
    public static void main(String[] args) {
        PerformanceBenchmark benchmark = new PerformanceBenchmark();

        Integer[] largeArray = new Integer[1000];

        int n = largeArray.length; // Stel de lengte van de array in

        // Vul de array achterstevoren
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = n - i;
        }

        System.out.println("Integer array test met insertionsort:");
        benchmark.start();
        Integer[] result = InsertionSort.insertionSort(largeArray);
        benchmark.stop();
        System.out.println(benchmark.getElapsedTimeInSeconds() + " seconden om uit te voeren");
        InsertionSort.printArray(largeArray);
    }
}
