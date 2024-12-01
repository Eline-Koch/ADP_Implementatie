package org.adp_implementatie;

import java.lang.reflect.Array;
import java.util.Arrays;

public class DynamicArray<E extends Comparable<E>> {
    private static final int DEFAULT_CAPACITY = 10;
    private E[] array;
    private int size;

    @SuppressWarnings("unchecked")
    public DynamicArray() {
        this.array = (E[]) new Comparable[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public void add(E element) {
        ensureCapacity(size + 1);
        array[size++] = element;
    }

    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return array[index];
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
        E removedElement = (E) array[index];
        arrayCopy(array, index + 1, array, index, size - index - 1); //aanpassen
        array[--size] = null;
        reduceCapacity(size + 1);
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

    public void arrayCopy(Object src, int srcPos, Object dest, int destPos, int length) {
        if (src == null || dest == null) {
            throw new NullPointerException("Source and destination arrays cannot be null");
        }
        if (!src.getClass().isArray() || !dest.getClass().isArray()) {
            throw new ArrayStoreException("Both source and destination must be arrays");
        }
        if (srcPos < 0 || destPos < 0 || length < 0) {
            throw new ArrayIndexOutOfBoundsException("Indices and length must be non-negative");
        }
        int srcLength = Array.getLength(src);
        int destLength = Array.getLength(dest);
        if (srcPos + length > srcLength || destPos + length > destLength) {
            throw new ArrayIndexOutOfBoundsException("Source or destination indices are out of bounds");
        }
        // Check for overlapping regions when src and dest are the same
        if (src == dest && (srcPos < destPos && srcPos + length > destPos)) {
            // Copy backward to prevent overwriting data
            for (int i = length - 1; i >= 0; i--) {
                Array.set(dest, destPos + i, Array.get(src, srcPos + i));
            }
        } else {
            // Copy forward
            for (int i = 0; i < length; i++) {
                Array.set(dest, destPos + i, Array.get(src, srcPos + i));
            }
        }
    }

    public boolean contains(E element) {return indexOf(element) != -1;}

    public int indexOf(E element) {
        for (int i = 0; i < size; i++) {
            if (array[i].compareTo(element) == 0) {
                return i;
            }
        }
        return -1;
    }

    private void ensureCapacity(int requiredCapacity) {
        if (requiredCapacity > array.length) {
            int newCapacity = array.length * 2;
            array = copyOf(array, newCapacity);
        }
    }

    private void reduceCapacity(int requiredCapacity) {
        if (requiredCapacity < array.length / 2) {
            int newCapacity = array.length / 2;
            array = copyOf(array, newCapacity);
        }
    }

    @SuppressWarnings("unchecked")
//    public static <T> T[] copyOf(T[] original, int newLength) {
//        if (original == null) {
//            throw new NullPointerException("Original array cannot be null");
//        }
//        if (newLength < 0) {
//            throw new NegativeArraySizeException("New length cannot be negative");
//        }
//
//        // Create a new array with the same component type and the desired length
//        T[] newArray = (T[]) Array.newInstance(original.getClass().getComponentType(), newLength);
//
//        // Copy elements manually
//        for (int i = 0; i < Math.min(original.length, newLength); i++) {
//            newArray[i] = original[i];
//        }
//
//        return newArray;
//    }

    public static <T> T[] copyOf(T[] original, int newLength) {
        if (original == null) {
            throw new NullPointerException("Original array cannot be null");
        }
        if (newLength < 0) {
            throw new NegativeArraySizeException("New length cannot be negative");
        }

        // Nieuwe array maken
        T[] newArray = (T[]) new Object[newLength];

        // Bereken de maximale index tot waar gekopieerd moet worden
        int lengthToCopy = (original.length < newLength) ? original.length : newLength;

        // Kopieer elementen handmatig
        for (int i = 0; i < lengthToCopy; i++) {
            newArray[i] = original[i];
        }

        return newArray;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        Arrays.fill(array, 0, size, null);
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

    public static void main(String[] args) {
        DynamicArray<Integer> dynamicArray = new DynamicArray<>();
        dynamicArray.add(1);
        System.out.println(dynamicArray.contains(1));
        dynamicArray.add(2);
        dynamicArray.add(3);
        dynamicArray.remove(1);
        dynamicArray.printArray();

        for (int i = 0; i < 50; i++) {
            dynamicArray.add(i);
        }
        dynamicArray.printArray();
        for (int i = 0; i < 25; i++) {
            dynamicArray.remove(i);
        }
        dynamicArray.printArray();
    }
}
