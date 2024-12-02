import org.adp_implementatie.BinarySearch;
import org.adp_implementatie.PerformanceBenchmark;

public class BinarySearchTest {

    public static void main(String[] args) {
        PerformanceBenchmark benchmark = new PerformanceBenchmark();

        Integer[] largeArray = new Integer[100000000];

        int target = 99999999;

        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = i + 1;
        }

        System.out.println("Integer array test:");
        benchmark.start();
        int result = BinarySearch.binarySearch(largeArray, target);
        benchmark.stop();
        System.out.println(benchmark.getElapsedTimeInSeconds() + " seconden om 'target' te vinden in een array met " + largeArray.length + " items");

        System.out.println("Resultaat: " + result);
        System.out.println();

        System.out.println("Integer array test:");
        benchmark.start();
        int resultInefficient = inefficientSearchWithForLoop(largeArray, target);
        benchmark.stop();
        System.out.println(benchmark.getElapsedTimeInSeconds() + " seconden om 'target' te vinden in een array met " + largeArray.length + " items");

        System.out.println("Resultaat: " + resultInefficient);
        System.out.println();




    }

    public static <T extends Comparable<T>> int inefficientSearchWithForLoop(T[] array, T target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].compareTo(target) == 0) {
                return i;
            }
        }
        return -1; // item niet gevonden
    }
}
