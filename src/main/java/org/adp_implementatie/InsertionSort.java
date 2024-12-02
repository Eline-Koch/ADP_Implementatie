package org.adp_implementatie;

public class InsertionSort {

    public static <T extends Comparable<T>> T[] insertionSort(T[] array) {
        for (int i = 1; i < array.length; i++) {
            // Element dat moet worden ingevoegd
            T toBeInserted = array[i];
            int j = i;

            // Verplaats grotere elementen naar rechts
            while (j > 0 && toBeInserted.compareTo(array[j - 1]) < 0) {
//                printArray(array);
                array[j] = array[j - 1];
                j--;
            }
            array[j] = toBeInserted;
        }
        return array;
    }


    public static void main(String[] args) {
        Integer[] numbers = {5, 2, 9, 1, 5, 6};
        printArray(insertionSort(numbers));

        String[] words = {"banana", "apple", "cherry"};
        printArray(insertionSort(words));
    }


    public static <T extends Comparable<T>> void printArray(T[] array){
        System.out.print("[");
        for (T item : array) {
            System.out.print(item + ", ");
        }
        System.out.println("] ");
    }


}
