package org.adp_implementatie;

public class BinarySearch {

    public static <T extends Comparable<T>> int binarySearch(T[] array, T target, int left, int right) {

        if (left > right) {
            return -1;
        }

        int mid = left + (right - left) / 2;

        if (array[mid].compareTo(target) == 0) {
            System.out.println(target + " = Gevonden op index: " + mid);
            return mid;
        } else if (array[mid].compareTo(target) < 0) {
            return binarySearch(array, target, mid + 1, right);
        } else {
            return binarySearch(array, target, left, mid - 1);
        }
    }

    public static <T extends Comparable<T>> int binarySearch(T[] array, T target) {
        return binarySearch(array, target, 0, array.length - 1);
    }

    public static void main(String[] args) {
        Integer[] intArray = {1, 2, 3, 4, 5, 7, 9, 11, 13, 44, 45, 60, 63};
        String[] strArray = {"appel", "banaan", "kaas", "pizza", "sushi", "uien", "vis"};

        System.out.println(binarySearch(intArray, 5)); // Output: 2
        System.out.println(binarySearch(strArray, "banana")); // Output: 1
    }
}
