import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class GraphTest {
    public static void main(String[] args) throws IOException {
        GraphTest test = new GraphTest();
        test.performDataSetTest();
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
}
