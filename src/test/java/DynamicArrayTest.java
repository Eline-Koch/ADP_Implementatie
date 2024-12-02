import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.adp_implementatie.DynamicArray;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;


public class DynamicArrayTest {
    public static void main(String[] args) throws IOException {
        DynamicArrayTest test = new DynamicArrayTest();
        test.performDataSetTest();
        test.containsObjectTest();
        test.performPerformanceTest();
    }

    public void performDataSetTest () throws IOException {
        System.out.println();
        System.out.println("dataset tests");

        String dataString = Files.readString(Path.of("src/test/resources/dataset_sorteren.json"), Charset.defaultCharset());
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, List<Object>> dataMap = objectMapper.readValue(dataString, new TypeReference<>(){});

        for (Map.Entry<String, List<Object>> entry : dataMap.entrySet()) {
            String key = entry.getKey();
            List<Object> value = entry.getValue();

            DynamicArray<Double> dynamicArray = new DynamicArray<>();
            for (Object o : value) {
                if (o != null) {
                    if (o instanceof Double) {
                        dynamicArray.add((Double) o);
                    } else if (o instanceof Integer) {
                        dynamicArray.add(((Integer) o).doubleValue());
                    } else {
                        System.out.println("Cannot add \"o\" to array, because \"o\" is of type" + o.getClass().getName());
                    }
                }
                else {
                    System.out.println("Cannot add \"o\" to array, because \"o\" is null");
                }
            }
            System.out.print(key + ": ");
            dynamicArray.printArray();
        }
    }

    //contains method should compare the value of an Object, not the reference
    public void containsObjectTest() {
        System.out.println();
        System.out.println("containsObject");

        DynamicArray<Pizza> dynamicArray = new DynamicArray<>();
        Pizza pizza1 = new Pizza("mozarella", true);
        Pizza pizza2 = new Pizza("mozarella", true);

        dynamicArray.add(pizza1);

        System.out.println("same reference, same value: " + dynamicArray.contains(pizza1));
        System.out.println("different reference, same value: " + dynamicArray.contains(pizza2));
    }

    public void performPerformanceTest() {
        System.out.println();
        System.out.println("performance tests");


        int[] arraySize = {1000, 10000, 100000};
        int[] numOperations = {1000, 10000, 100000};

        System.out.println("add");
//        add DynamicArray (O)?
//        numOperations: 100000, size: 1000, nanoseconds: 6990400
//        numOperations: 100000, size: 10000, nanoseconds: 1345900
//        numOperations: 100000, size: 100000, nanoseconds: 1655700
//
//        add DoublyLinkedList (O)?
//        numOperations: 100000, size: 1000, nanoseconds: 5118600
//        numOperations: 100000, size: 10000, nanoseconds: 1627900
//        numOperations: 100000, size: 100000, nanoseconds: 2192500
//
//        Tijd per operatie ongeveer constant, verschil tussen DynamicArray en DoublyLinkedList klein,
//        omdat aan het einde van de array toegevoegd wordt,
//        dus hoeven er alleen elementen verplaats worden als de array verdubbeld wordt.
//        Raar de dat middelste (size 10000) bij beide het snelste is.

        for (int size : arraySize) {
            for (int num : numOperations) {
                this.performAddPerformanceTest(size, num);
            }
        }

        System.out.println();
        System.out.println("get");
//        get DynamicArray (O)1
//        numOperations: 100000, size: 1000, nanoseconds: 2588300
//        numOperations: 100000, size: 10000, nanoseconds: 337700
//        numOperations: 100000, size: 100000, nanoseconds: 327800
//
//        get DoublyLinkedList (O)1, iets meer?
//        numOperations: 100000, size: 1000, nanoseconds: 32911600
//        numOperations: 100000, size: 10000, nanoseconds: 58960000
//        numOperations: 100000, size: 100000, nanoseconds: 59328900
//
//        De tijd voor beiden lijkt vrij constant,
//        bij DoublyLinkedList duurt het ongeveer 2x zo lang bij een 100x zo grote list.
//        De operaties duren in het algemeen wel veel langer bij de list, 59328900 / 327800 = 180.

        for (int size : arraySize) {
            for (int num : numOperations) {
                this.performGetPerformanceTest(size, num);
            }
        }

        System.out.println();
        System.out.println("set");
//        set DynamicArray (O)?, minder dan 1
//        numOperations: 100000, size: 1000, nanoseconds: 4869500
//        numOperations: 100000, size: 10000, nanoseconds: 4455300
//        numOperations: 100000, size: 100000, nanoseconds: 899100
//
//        set DoublyLinkedList (O)N
//        numOperations: 100000, size: 1000, nanoseconds: 61680100
//        numOperations: 100000, size: 10000, nanoseconds: 582969600
//        numOperations: 100000, size: 100000, nanoseconds: 5971614500
//
//        Die laatste tijd voor DynamicArray is moeilijk te verklaren. Bij de list stijgt de tijd linear.
//        In alle gevallen duren de operaties langer bij de list,
//        bij size 10000: 582969600 / 4455300 = 130.

        for (int size : arraySize) {
            for (int num : numOperations) {
                this.performSetPerformanceTest(size, num);
            }
        }

        System.out.println();
        System.out.println("remove(index)");

        for (int size : arraySize) {
            for (int num : numOperations) {
                this.performRemoveIndexPerformanceTest(size * 2, num / 100);
            }
        }

        System.out.println();
        System.out.println("remove(element)");

        for (int size : arraySize) {
            for (int num : numOperations) {
                this.performRemoveElementPerformanceTest(size * 2, num / 100);
            }
        }

        System.out.println();
        System.out.println("contains");

        for (int size : arraySize) {
            for (int num : numOperations) {
                this.performContainsPerformanceTest(size, num / 100);
            }
        }

        System.out.println();
        System.out.println("indexOf");

        for (int size : arraySize) {
            for (int num : numOperations) {
                this.performIndexOfPerformanceTest(size, num / 100);
            }
        }
    }

    public void performAddPerformanceTest(int arraySize, int numOperations) {
        DynamicArray<Integer> dynamicArray = new DynamicArray<>();

        for(int i = 0; i < arraySize; i++){
            dynamicArray.add(i);
        }

        long startingTime = System.nanoTime();
        for(int i = 0; i < numOperations; i++){
            dynamicArray.add(i);
        }

        System.out.print("numOperations: " + numOperations + ", size: " + arraySize + ", nanoseconds: ");
        System.out.println(System.nanoTime() - startingTime);
    }

    public void performGetPerformanceTest(int arraySize, int numOperations) {
        DynamicArray<Integer> dynamicArray = new DynamicArray<>();
        for(int i = 0; i < arraySize; i++){
            dynamicArray.add(i);
        }

        long startingTime = System.nanoTime();
        for(int i = 0; i < numOperations; i++){
            dynamicArray.get(i / 100);
        }

        System.out.print("numOperations: " + numOperations + ", size: " + arraySize + ", nanoseconds: ");
        System.out.println(System.nanoTime() - startingTime);
    }

    public void performSetPerformanceTest(int arraySize, int numOperations) {
        DynamicArray<Integer> dynamicArray = new DynamicArray<>();
        for(int i = 0; i < arraySize; i++){
            dynamicArray.add(i);
        }

        long startingTime = System.nanoTime();
        for(int i = 0; i < numOperations; i++){
            dynamicArray.set(dynamicArray.size() / 2, i);
        }

        System.out.print("numOperations: " + numOperations + ", size: " + arraySize + ", nanoseconds: ");
        System.out.println(System.nanoTime() - startingTime);
    }

    public void performRemoveIndexPerformanceTest(int arraySize, int numOperations) {
        DynamicArray<Integer> dynamicArray = new DynamicArray<>();

        for(int i = 0; i < arraySize; i++){
            dynamicArray.add(1);
        }

        long startingTime = System.nanoTime();
        for(int i = 0; i < numOperations; i++){
            dynamicArray.remove(dynamicArray.size() / 2);
        }

        System.out.print("numOperations: " + numOperations + ", size: " + arraySize + ", nanoseconds: ");
        System.out.println(System.nanoTime() - startingTime);
    }

    public void performRemoveElementPerformanceTest(int arraySize, int numOperations) {
        DynamicArray<Integer> dynamicArray = new DynamicArray<>();
        for(int i = 0; i < arraySize; i++){
            dynamicArray.add(i);
        }

        long startingTime = System.nanoTime();
        for(int i = 0; i < numOperations; i++){
            dynamicArray.remove(Integer.valueOf(i + dynamicArray.size() / 2));
        }

        System.out.print("numOperations: " + numOperations + ", size: " + arraySize + ", nanoseconds: ");
        System.out.println(System.nanoTime() - startingTime);
    }

    public void performContainsPerformanceTest(int arraySize, int numOperations) {
        DynamicArray<Integer> dynamicArray = new DynamicArray<>();
        for(int i = 0; i < arraySize; i++){
            dynamicArray.add(i);
        }

        long startingTime = System.nanoTime();
        for(int i = 0; i < numOperations; i++){
            if (i % 2 == 0) {
                dynamicArray.contains(i + dynamicArray.size() / 2);
            }
            else {
                dynamicArray.contains(i + 100000);
            }
        }

        System.out.print("numOperations: " + numOperations + ", size: " + arraySize + ", nanoseconds: ");
        System.out.println(System.nanoTime() - startingTime);
    }

    public void performIndexOfPerformanceTest(int arraySize, int numOperations) {
        DynamicArray<Integer> dynamicArray = new DynamicArray<>();
        for(int i = 0; i < arraySize; i++){
            dynamicArray.add(i);
        }

        long startingTime = System.nanoTime();
        for(int i = 0; i < numOperations; i++){
            dynamicArray.indexOf(i + dynamicArray.size() / 2);
        }

        System.out.print("numOperations: " + numOperations + ", size: " + arraySize + ", nanoseconds: ");
        System.out.println(System.nanoTime() - startingTime);
    }
}
