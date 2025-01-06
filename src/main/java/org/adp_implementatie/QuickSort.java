package org.adp_implementatie;
public class QuickSort {
    public static void quickSort(int[] array, int low, int high) {
        if (low < high) {
            // Zoek de index van het pivot-element met de median of three methode
            int pivotIndex = partition(array, low, high);

            // Roep quicksort recursief aan op het linker en rechter deel
            quickSort(array, low, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, high);
        }
    }

    // Partitioneer de array rond een pivot-element
    private static int partition(int[] array, int low, int high) {
        // Kies de pivot met median of three
        int pivot = medianOfThree(array, low, high);

        int i = low - 1; // Index van het kleinere element

        for (int j = low; j < high; j++) {
            // Als het huidige element kleiner is dan of gelijk aan de pivot
            if (array[j] <= pivot) {
                i++;
                // Wissel array[i] en array[j]
                swap(array, i, j);
            }
        }

        // Wissel de pivot naar de juiste positie
        swap(array, i + 1, high);

        return i + 1; // Retourneer de index van de pivot
    }

    private static int medianOfThree(int[] array, int low, int high) {
        int mid = low + (high - low) / 2;

        // Sorteer low, mid en high
        if (array[low] > array[mid]) swap(array, low, mid);
        if (array[low] > array[high]) swap(array, low, high);
        if (array[mid] > array[high]) swap(array, mid, high);

        // Plaats de mediaan op de laatste positie als pivot
        swap(array, mid, high);
        return array[high];
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        int[] array = {10, 7, 8, 9, 1, 5, 3, 2};
        System.out.println("Oorspronkelijke array:");
        printArray(array);

        quickSort(array, 0, array.length - 1);

        System.out.println("Gesorteerde array:");
        printArray(array);
    }

    private static void printArray(int[] array) {
        for (int num : array) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}

// TODO: Algemeen maken met wildcard
// TODO: Testdataset maken
// TODO: Testdataset lezen
// TODO: Performance meten na opwarmrondes
