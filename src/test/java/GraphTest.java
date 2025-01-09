import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.adp_implementatie.Graph;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class GraphTest {
    public static void main(String[] args) throws IOException {
        GraphTest test = new GraphTest();
        test.performDataSetTest();
        runTests();
    }

    public void performDataSetTest() throws IOException {
        System.out.println();
        System.out.println("dataset tests");

        String dataString = Files.readString(Path.of("src/test/resources/dataset_grafen.json"), Charset.defaultCharset());
        System.out.println(dataString);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object[]> dataMap = objectMapper.readValue(dataString, new TypeReference<>(){});
        for (Map.Entry<String, Object[]> entry : dataMap.entrySet()) {
            String key = entry.getKey();
            Object[] value = entry.getValue();
            System.out.println("key: " + key);
            for (int i = 0; i < value.length; i++) {
                System.out.println(value[i]);
            }
        }
    }

    public static void runTests() {
        // Testcase 1
        Graph graph1 = new Graph(6);
        graph1.addEdge(0, 1, 1, true);
        graph1.addEdge(0, 2, 1, true);
        graph1.addEdge(1, 3, 1, true);
        graph1.addEdge(2, 3, 1, true);
        graph1.addEdge(2, 4, 1, true);
        graph1.addEdge(4, 5, 1, true);
        graph1.calculateShortestPaths(0);

        // Testcase 2
        Graph graph2 = new Graph(6);
        graph2.addEdge(0, 1, 4, true);
        graph2.addEdge(0, 2, 3, true);
        graph2.addEdge(1, 3, 2, true);
        graph2.addEdge(2, 3, 5, true);
        graph2.addEdge(4, 5, 1, true);
        graph2.addEdge(3, 4, 6, true);
        graph2.calculateShortestPaths(0);


        // Testcase 3
        Graph graph3 = new Graph(4);
        graph3.addEdge(0, 1, 2, true);
        graph3.addEdge(1, 3, 3, true);
        graph3.addEdge(2, 0, 6, true);
        graph3.addEdge(2, 3, 1, true);
        graph3.calculateShortestPaths(0);


        // Testcase 4
        Graph graph4 = new Graph(4);
        graph4.addEdge(0, 1, 1, true);
        graph4.addEdge(2, 3, 3, true);
        graph4.calculateShortestPaths(0);


        // Testcase 5
        Graph graph5 = new Graph(4);
        graph5.addEdge(0, 1, 1, true);
        graph5.addEdge(0, 2, 4, true);
        graph5.addEdge(1, 2, 2, true);
        graph5.addEdge(1, 3, 7, true);
        graph5.addEdge(2, 3, 3, true);
        graph5.calculateShortestPaths(0);

    }

}
