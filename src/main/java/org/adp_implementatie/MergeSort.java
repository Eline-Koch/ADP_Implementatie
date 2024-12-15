package org.adp_implementatie;

import java.util.Arrays;

public class MergeSort<E extends Comparable<E>> {

    @SuppressWarnings("unchecked")
    public void mergeSort(E[] array) {
        E[] tmpArray = (E[]) new Comparable[array.length];
        this.mergeSort(array, tmpArray, 0, array.length - 1);
    }

    public void mergeSort(E[] array, E[] tmpArray, int left, int right) {
        if (left < right) {
            int center = (left + right) / 2;
            this.mergeSort(array, tmpArray, left, center);
            this.mergeSort(array, tmpArray, center + 1, right);
            this.merge(array, tmpArray, left, center + 1, right);
        }
    }

    public void merge(E[] array, E[] tmpArray, int leftStart, int rightStart, int rightEnd) {
        // Initial indexes of the subarrays
        int i = leftStart, j = rightStart, k = 0;

        // Merge the two subarrays into temp
        while (i < rightStart && j <= rightEnd) {
            if (array[i].compareTo(array[j]) < 0) {
                tmpArray[leftStart + k] = array[i];
                i++;
            } else {
                tmpArray[leftStart + k] = array[j];
                j++;
            }
            k++;
        }

        // Copy remaining elements of the left subarray if any
        while (i < rightStart) {
            tmpArray[leftStart + k] = array[i];
            i++;
            k++;
        }

        // Copy remaining elements of the right subarray if any
        while (j <= rightEnd) {
            tmpArray[leftStart + k] = array[j];
            j++;
            k++;
        }

        for (int m = leftStart; m < rightEnd + 1;m++) {
            array[m] = tmpArray[m];
        }
    }
    public static void main(String[] args) {
        MergeSort<Integer> mergeSort = new MergeSort<>();
        Integer[] testArray = {7, 6, 3, 9, 2, 5, 1, 4, 8};
        mergeSort.mergeSort(testArray);
        System.out.println(Arrays.toString(testArray));
    }
}
