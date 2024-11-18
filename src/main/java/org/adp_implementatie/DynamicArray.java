package org.adp_implementatie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DynamicArray<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] array;
    private int size;

    public DynamicArray() {
        this.array = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public DynamicArray(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0.");
        }
        this.array = new Object[initialCapacity];
        this.size = 0;
    }

    public void add(E element) {
        ensureCapacity(size + 1); // Ensure there is enough capacity
        array[size++] = element;
    }

    @SuppressWarnings("unchecked")
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return (E) array[index];
    }

    public void set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        array[index] = element;
    }

    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        @SuppressWarnings("unchecked")
        E removedElement = (E) array[index];
        System.arraycopy(array, index + 1, array, index, size - index - 1);
        array[--size] = null;
        return removedElement;
    }

    public boolean remove(E element) {
        int index = indexOf(element);
        if (index != -1) {
            remove(index);
            return true;
        }
        return false;
    }

    public boolean contains(E element) {
        return indexOf(element) != -1;
    }

    public int indexOf(E element) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    private void ensureCapacity(int requiredCapacity) {
        if (requiredCapacity > array.length) {
            int newCapacity = array.length * 2;
            array = Arrays.copyOf(array, newCapacity);
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        Arrays.fill(array, 0, size, null); // Help GC by nulling out references
        size = 0;
    }

    public void printArray() {
        System.out.print("[");
        for (int i = 0; i < size; i++) {
            System.out.print(array[i]);
            if (i < size - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

    public static void main(String[] args) throws IOException {
        String dataString = Files.readString(Path.of("src/main/resources/dataset_sorteren.json"), Charset.defaultCharset());

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, List<Object>> dataMap = objectMapper.readValue(dataString, new TypeReference<Map<String, List<Object>>>(){});

        dataMap.forEach((key, value) -> System.out.println(key + " " + value));
        dataMap.forEach((key, value) -> System.out.println(value));
        dataMap.forEach((key, value) -> System.out.println(value.get(0)));

//        dynamicArray.add(10);
//        dynamicArray.add(20);
//        dynamicArray.add(30);
//        dynamicArray.add(40);
//
//        dynamicArray.printArray(); // [10, 20, 30, 40]
//
//        System.out.println("Element at index 2: " + dynamicArray.get(2)); // 30
//
//        dynamicArray.set(1, 25);
//        dynamicArray.printArray(); // [10, 25, 30, 40]
//
//        dynamicArray.remove(0);
//        dynamicArray.printArray(); // [25, 30, 40]
//
//        dynamicArray.remove(Integer.valueOf(30));
//        dynamicArray.printArray(); // [25, 40]
//
//        System.out.println("Contains 40: " + dynamicArray.contains(40)); // true
//        System.out.println("Contains 30: " + dynamicArray.contains(30)); // false
//
//        System.out.println("Index of 40: " + dynamicArray.indexOf(40)); // 1
//        System.out.println("Index of 30: " + dynamicArray.indexOf(30)); // -1
    }
}
