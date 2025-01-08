import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.adp_implementatie.AVLTree;
import org.adp_implementatie.DoublyLinkedList;
import org.adp_implementatie.PerformanceBenchmark;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class AVLTreeTest {

    public static void main(String[] args) throws IOException {
        AVLTreeTest test = new AVLTreeTest();
        test.performDataSetTest();

        System.out.println("Start met AVLTree Insert test (oplopen):");
        testInsertAVLTreeAscending(100000);

        System.out.println("Start met AVLTree Insert test (aflopend):");
        testInsertAVLTreeDescending(100000);

        System.out.println("Start met AVLTree Insert test (oplopen):");
        testInsertAVLTreeAscending(100000);

        System.out.println("Start met AVLTree Insert test (aflopend):");
        testInsertAVLTreeDescending(100000);
        System.out.println("Start met AVLTree Insert test (oplopen):");
        testInsertAVLTreeAscending(100000);

        System.out.println("Start met AVLTree Insert test (aflopend):");
        testInsertAVLTreeDescending(100000);
        System.out.println("Start met AVLTree Insert test (oplopen):");
        testInsertAVLTreeAscending(100000);

        System.out.println("Start met AVLTree Insert test (aflopend):");
        testInsertAVLTreeDescending(100000);
        System.out.println("Start met AVLTree Insert test (oplopen):");
        testInsertAVLTreeAscending(100000);

        System.out.println("Start met AVLTree Insert test (aflopend):");
        testInsertAVLTreeDescending(100000);
//        test.findFunctionalityTest();
//        test.findMinMaxTest();
//        test.performanceTest();
    }

    public void performDataSetTest() throws IOException {
        System.out.println("Dataset Tests");

        String dataString = Files.readString(Path.of("src/test/resources/dataset_sorteren.json"), Charset.defaultCharset());
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, List<Object>> dataMap = objectMapper.readValue(dataString, new TypeReference<>(){});

        for (Map.Entry<String, List<Object>> entry : dataMap.entrySet()) {
            String key = entry.getKey();
            List<Object> value = entry.getValue();

            AVLTree<Double> tree = new AVLTree<>();
            for (Object o : value) {
                if (o != null) {
                    if (o instanceof Double) {
                        tree.insert((Double) o);
                    } else if (o instanceof Integer) {
                        tree.insert(((Integer) o).doubleValue());
                    } else {
                        System.out.println("Cannot add \"o\" to list, because \"o\" is of type" + o.getClass().getName());
                    }
                }
                else {
                    System.out.println("Cannot add \"o\" to list, because \"o\" is null");
                }
            }

            System.out.print(key + ": ");
            tree.printInOrder();

        }
    }

    public static void testInsertAVLTreeAscending(int numberOfElements) {
        PerformanceBenchmark benchmark = new PerformanceBenchmark();
        AVLTree<Integer> avlTree = new AVLTree<>();

        benchmark.start();
        for (int i = 0; i < numberOfElements; i++) {
            avlTree.insert(i + 1); // Voeg elementen toe aan AVLTree in oplopende volgorde
        }
        benchmark.stop();

        System.out.println("AVLTree Insert tijd (oplopen) voor " + numberOfElements + " elementen: "
                + benchmark.formatElapsedTime(benchmark.getElapsedTimeInSeconds()) + " seconden");
        System.out.println();
    }

    public static void testInsertAVLTreeDescending(int numberOfElements) {
        PerformanceBenchmark benchmark = new PerformanceBenchmark();
        AVLTree<Integer> avlTree = new AVLTree<>();

        benchmark.start();
        for (int i = numberOfElements; i > 0; i--) {
            avlTree.insert(i); // Voeg elementen toe aan AVLTree in aflopende volgorde
        }
        benchmark.stop();

        System.out.println("AVLTree Insert tijd (aflopend) voor " + numberOfElements + " elementen: "
                + benchmark.formatElapsedTime(benchmark.getElapsedTimeInSeconds()) + " seconden");
        System.out.println();
    }
}