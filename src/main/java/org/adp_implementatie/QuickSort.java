package org.adp_implementatie;
public class QuickSort<E extends Comparable<E>> {

    public void sort(E[] array) {
        quickSort(array, 0, array.length - 1);
    }

    private void quickSort(E[] array, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(array, low, high);
            quickSort(array, low, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, high);
        }
    }

    private int partition(E[] array, int low, int high) {
        E pivot = medianOfThree(array, low, high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (array[j].compareTo(pivot) <= 0) {
                i++;
                swap(array, i, j);
            }
        }

        swap(array, i + 1, high);
        return i + 1;
    }

    private E medianOfThree(E[] array, int low, int high) {
        int mid = low + (high - low) / 2;

        if (array[low].compareTo(array[mid]) > 0) {
            swap(array, low, mid);
        }
        if (array[low].compareTo(array[high]) > 0) {
            swap(array, low, high);
        }
        if (array[mid].compareTo(array[high]) > 0) {
            swap(array, mid, high);
        }

        swap(array, mid, high);
        return array[high];
    }

    private void swap(E[] array, int i, int j) {
        E temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        QuickSort<Integer> quickSort = new QuickSort<>();
        QuickSort<String> quickSort2 = new QuickSort<>();
        Integer[] array = {10, 7, 8, 9, 1, 5};
        String[] array2 = {"b", "z", "sa", "a", "dd", "pizza"};

        System.out.println("Oorspronkelijke array:");
        printArray(array);
        printArray(array2);

        quickSort.sort(array);
        quickSort2.sort(array2);

        System.out.println("Gesorteerde array:");
        printArray(array);
        printArray(array2);
    }

    public static <E> void printArray(E[] array) {
        for (E element : array) {
            System.out.print(element + " ");
        }
        System.out.println();
    }
}
