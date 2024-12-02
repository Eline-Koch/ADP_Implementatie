package org.adp_implementatie;


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
        array = copyArray(array, array.length, true, index);
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

    public boolean contains(E element) {return indexOf(element) != -1;}

    public int indexOf(E element) {
        for (int i = 0; i < size; i++) {
            if (array[i].compareTo(element) == 0) {
                return i;
            }
        }
        return -1;
    }

    @SuppressWarnings("unchecked")
    public E[] copyArray(E[] original, int newLength, boolean elementRemoved, int removedIndex) {
        if (original == null) {
            throw new NullPointerException("Original array cannot be null");
        }
        if (newLength < 0) {
            throw new NegativeArraySizeException("New length cannot be negative");
        }

        // Nieuwe array maken
        E[] newArray = (E[]) new Comparable[newLength];

        // Bereken de maximale index tot waar gekopieerd moet worden
        int lengthToCopy = (original.length < newLength) ? original.length : newLength;

        // Kopieer elementen handmatig
        for (int i = 0; i < lengthToCopy; i++) {
            if (elementRemoved && i > removedIndex){
                newArray[i - 1] = original[i];
            }
            newArray[i] = original[i];
        }

        return newArray;
    }

    private void ensureCapacity(int requiredCapacity) {
        if (requiredCapacity > array.length) {
            int newCapacity = array.length * 2;
            array = copyArray(array, newCapacity, false, 0);
        }
    }

    private void reduceCapacity(int requiredCapacity) {
        if (requiredCapacity < array.length / 2) {
            int newCapacity = array.length / 2;
            array = copyArray(array, newCapacity, false, 0);
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
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
        dynamicArray.remove(5);
        dynamicArray.printArray();
    }
}
