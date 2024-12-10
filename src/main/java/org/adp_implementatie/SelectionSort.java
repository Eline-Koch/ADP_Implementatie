package org.adp_implementatie;

public class SelectionSort <E extends Comparable<E>> {
    public void sort(E[] arr) {
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j].compareTo(arr[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            E temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }
    public static void main(String[] args) {
        SelectionSort<Integer> selectionSort = new SelectionSort<>();

        // Example array
        Integer[] arr = {5, 1, 12, -5, 16, 2, 12, 14};
        System.out.println();
        System.out.println("Before Selection sort:");
        for (int num : arr) {
            System.out.print(num + " ");
        }
        // Sort the array using the selection sort
        selectionSort.sort(arr);
        // Print the sorted array
        System.out.println();
        System.out.println("After Selection sort:");
        for (int num : arr) {
            System.out.print(num + " ");
        }
    }
}
