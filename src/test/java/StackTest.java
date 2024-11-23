import org.adp_implementatie.HanStackMetArray;
import org.adp_implementatie.PerformanceBenchmark;

public class StackTest {
    public static void main(String[] args) {
        PerformanceBenchmark benchmark = new PerformanceBenchmark();

        int[] stackSize = {1000, 100000, 1000000, 1000000};

        HanStackMetArray stack = new HanStackMetArray(stackSize[0]);
        HanStackMetArray stack2 = new HanStackMetArray(stackSize[1]);
        HanStackMetArray stack3 = new HanStackMetArray(stackSize[2]);

        benchmark.start();
        for (int i = 0; i < stackSize[0]; i++) {
            stack.push(i);
        }
        benchmark.stop();
        System.out.println("Stack add met size: " + stackSize[0]);
        benchmark.printElapsedTime();
        System.out.println();

        benchmark.start();
        for (int i = 0; i < stackSize[1]; i++) {
            stack2.push(i);
        }
        benchmark.stop();
        System.out.println("Stack add met size: " + stackSize[1]);
        benchmark.printElapsedTime();
        System.out.println();

        benchmark.start();
        for (int i = 0; i < stackSize[2]; i++) {
            stack3.push(i);
        }
        benchmark.stop();
        System.out.println("Stack add met size: " + stackSize[2]);
        benchmark.printElapsedTime();
        System.out.println();
    }
}
