import org.adp_implementatie.HanStackMetArray;
import org.adp_implementatie.PerformanceBenchmark;

public class StackTest {
    public static void main(String[] args) {
        Pizza pizza = new Pizza("Doner kebab", false);

        int[] stackSize = {1000, 10000, 100000, 1000000, 10000000, 100000000};

        for (int i : stackSize) {
            testStackAddOperation(i, "String insert");
        }

        for (int i : stackSize) {
            testStackAddOperation(i, pizza);
        }

        for (int i : stackSize) {
            testStackAddOperation(i, 1);
        }

        for (int i : stackSize) {
            testStackPopOperation(i, 1);
        }

        for (int i : stackSize) {
            testStackPeekOperation(i, 1);
        }
    }

    public static void testStackAddOperation(int stackSize, Object pushObject) {
        HanStackMetArray stack = new HanStackMetArray(stackSize);
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
        HanStackMetArray stack = new HanStackMetArray(stackSize);
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
        HanStackMetArray stack = new HanStackMetArray(stackSize);
        PerformanceBenchmark benchmark = new PerformanceBenchmark();

        for (int i = 0; i < stackSize; i++) {
            stack.push(pushObject);

            if (i == stackSize / 2){
                benchmark.start();
                stack.peek();
                benchmark.stop();
                System.out.println("Stack Peek met size: " + stackSize + " Object to peek: " + pushObject.toString());
                benchmark.printElapsedTime();
                System.out.println();
            }
        }
    }
}
