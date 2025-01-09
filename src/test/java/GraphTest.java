import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.adp_implementatie.Graph;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class GraphTest {
    public static void main(String[] args) throws IOException {
        GraphTest test = new GraphTest();
        test.performDataSetTest();
    }

    public void performDataSetTest() throws IOException {
        System.out.println();
        System.out.println("dataset tests");

        String dataString = Files.readString(Path.of("src/test/resources/dataset_grafen.json"), Charset.defaultCharset());
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object[][]> dataMap = objectMapper.readValue(dataString, new TypeReference<>(){});

        for (Map.Entry<String, Object[][]> entry : dataMap.entrySet()) {
            String key = entry.getKey();
            Object[][] value = entry.getValue();
            Graph graph = new Graph(10);

            System.out.println("Key: " + key);

            if (key.contains("lijnlijst")) {
                if (!key.contains("gewogen")) {
                    for (int i = 0; i < value.length; i++) {
                        graph.addEdge((Integer) value[i][0], (Integer) value[i][1], true);
                    }
                }
                else if (key.contains("gewogen")) {
                    for (int i = 0; i < value.length; i++) {
                        graph.addEdge((Integer) value[i][0], (Integer) value[i][1], (Integer) value[i][2], true);
                    }
                }
            }
            else if (key.contains("verbindingslijst")) {
                if (!key.contains("gewogen")) {
                    for (int i = 0; i < value.length; i++) {
                        for (int j = 0;j < value[i].length; j++) {
                            graph.addEdge(i, (Integer) value[i][j], true);
                        }
                    }
                }
            }
            else if (key.contains("verbindingsmatrix")) {
                for (int i = 0; i < value.length; i++) {
                    for (int j = 0; j < value[i].length; j++) {
                        if ((Integer) value[i][j] != 0) {
                            graph.addEdge(i, j, (Integer) value[i][j], true);
                        }
                    }
                }
            }

            graph.printGraph();
            System.out.println();
        }
    }
}
