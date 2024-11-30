package org.adp_implementatie;

public class InsertionSort {

    public static int[] insertionSort(int[] a) {
        printArray(a);
        System.out.println("start");
        for (int i = 1; i < a.length; i++) {

            int toBeInserted = a[i];
            int j = i;
            while (j > 0 && toBeInserted < a[j - 1]) {
                printArray(a);

                a[j] = a[j - 1];
                j--;
            }
            a[j] = toBeInserted;
        }
        return a;
    }

    public static void main(String[] args) {
        int[] ints = {4, 11, 1, 7, 5};

        printArray(insertionSort(ints));
    }

    public static void printArray(int[] array){
        System.out.print("[");
        for (int integer : array) {
            System.out.print(integer + ", ");
        }
        System.out.println("] ");
    }


}
