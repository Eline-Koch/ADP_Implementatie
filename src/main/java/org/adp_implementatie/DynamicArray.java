package org.adp_implementatie;

import java.util.Arrays;

public class DynamicArray<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] array;
    private int size;

    // Constructor to initialize the dynamic array with default capacity
    public DynamicArray() {
        this.array = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    // Constructor to initialize the dynamic array with a specific initial capacity
    public DynamicArray(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0.");
        }
        this.array = new Object[initialCapacity];
        this.size = 0;
    }

    // Main method for testing
    public static void main(String[] args) {
        DynamicArray<Integer> dynamicArray = new DynamicArray<>();

        // Adding elements
        dynamicArray.add(10);
        dynamicArray.add(20);
        dynamicArray.add(30);
        dynamicArray.add(40);

        // Print array
        dynamicArray.printArray(); // [10, 20, 30, 40]

        // Get element at index
        System.out.println("Element at index 2: " + dynamicArray.get(2)); // 30

        // Set element at index 1
        dynamicArray.set(1, 25);
        dynamicArray.printArray(); // [10, 25, 30, 40]

        // Remove element at index 0
        dynamicArray.remove(0);
        dynamicArray.printArray(); // [25, 30, 40]

        // Remove element by value
        dynamicArray.remove(Integer.valueOf(30));
        dynamicArray.printArray(); // [25, 40]

        // Check if an element exists
        System.out.println("Contains 40: " + dynamicArray.contains(40)); // true
        System.out.println("Contains 30: " + dynamicArray.contains(30)); // false

        // Get index of element
        System.out.println("Index of 40: " + dynamicArray.indexOf(40)); // 1
        System.out.println("Index of 30: " + dynamicArray.indexOf(30)); // -1
    }

    // Add an element to the dynamic array
    public void add(E element) {
        ensureCapacity(size + 1); // Ensure there is enough capacity
        array[size++] = element;
    }

    // Get the element at the specified index
    @SuppressWarnings("unchecked")
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return (E) array[index];
    }

    // Set the element at the specified index
    public void set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        array[index] = element;
    }

    // Remove an element at a specified index
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        @SuppressWarnings("unchecked")
        E removedElement = (E) array[index];
        // Shift elements left to fill the gap
        System.arraycopy(array, index + 1, array, index, size - index - 1);
        array[--size] = null; // Nullify the last element to avoid memory leak
        return removedElement;
    }

    // Remove the first occurrence of the element
    public boolean remove(E element) {
        int index = indexOf(element);
        if (index != -1) {
            remove(index);
            return true;
        }
        return false;
    }

    // Check if the array contains the element
    public boolean contains(E element) {
        return indexOf(element) != -1;
    }

    // Find the index of the element
    public int indexOf(E element) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(element)) {
                return i;
            }
        }
        return -1; // Element not found
    }

    // Ensure the array has enough capacity to accommodate the specified size
    private void ensureCapacity(int requiredCapacity) {
        if (requiredCapacity > array.length) {
            int newCapacity = array.length * 2; // Double the capacity
            array = Arrays.copyOf(array, newCapacity);
        }
    }

    // Get the current size of the array
    public int size() {
        return size;
    }

    // Check if the array is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Clear all elements from the array
    public void clear() {
        Arrays.fill(array, 0, size, null); // Help GC by nulling out references
        size = 0;
    }

    // Print the dynamic array
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
}
