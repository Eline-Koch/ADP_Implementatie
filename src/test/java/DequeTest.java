import org.adp_implementatie.Deque;
import org.adp_implementatie.PerformanceBenchmark;

public class DequeTest {
    public static void main(String[] args) {
        Pizza pizza = new Pizza("Doner kebab", false);

        int[] dequeSizes = {1000, 10000, 100000, 1000000, 10000000};

        for (int size : dequeSizes) {
            testDequeInsertLeft(size, "String insert");
        }

        for (int size : dequeSizes) {
            testDequeInsertRight(size, pizza);
        }

        for (int size : dequeSizes) {
            testDequeRemoveLeft(size, 1);
        }

        for (int size : dequeSizes) {
            testDequeRemoveRight(size, 1);
        }
    }

    public static void testDequeInsertLeft(int dequeSize, Object insertObject) {
        Deque<Object> deque = new Deque<>();
        PerformanceBenchmark benchmark = new PerformanceBenchmark();

        benchmark.start();
        for (int i = 0; i < dequeSize; i++) {
            deque.insertLeft(insertObject);
        }
        benchmark.stop();

        System.out.println("Deque Insert Left met size: " + dequeSize + " Object to insert: " + insertObject.toString());
        benchmark.printElapsedTime();
        System.out.println();
    }

    public static void testDequeInsertRight(int dequeSize, Object insertObject) {
        Deque<Object> deque = new Deque<>();
        PerformanceBenchmark benchmark = new PerformanceBenchmark();

        benchmark.start();
        for (int i = 0; i < dequeSize; i++) {
            deque.insertRight(insertObject);
        }
        benchmark.stop();

        System.out.println("Deque Insert Right met size: " + dequeSize + " Object to insert: " + insertObject.toString());
        benchmark.printElapsedTime();
        System.out.println();
    }

    public static void testDequeRemoveLeft(int dequeSize, Object insertObject) {
        Deque<Object> deque = new Deque<>();
        PerformanceBenchmark benchmark = new PerformanceBenchmark();

        // Vul de deque eerst met data
        for (int i = 0; i < dequeSize; i++) {
            deque.insertLeft(insertObject);
        }

        benchmark.start();
        for (int i = 0; i < dequeSize; i++) {
            deque.removeLeft();
        }
        benchmark.stop();

        System.out.println("Deque Remove Left met size: " + dequeSize + " Object to remove: " + insertObject.toString());
        benchmark.printElapsedTime();
        System.out.println();
    }

    public static void testDequeRemoveRight(int dequeSize, Object insertObject) {
        Deque<Object> deque = new Deque<>();
        PerformanceBenchmark benchmark = new PerformanceBenchmark();

        // Vul de deque eerst met data
        for (int i = 0; i < dequeSize; i++) {
            deque.insertRight(insertObject);
        }

        benchmark.start();
        for (int i = 0; i < dequeSize; i++) {
            deque.removeRight();
        }
        benchmark.stop();

        System.out.println("Deque Remove Right met size: " + dequeSize + " Object to remove: " + insertObject.toString());
        benchmark.printElapsedTime();
        System.out.println();
    }
}
