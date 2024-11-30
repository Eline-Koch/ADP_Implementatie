package org.adp_implementatie;

public class BinarySearch {

    /*
    This binary search is O(log(N)) because eacht iteration the problem is split in half. This means a logarithmic complexity.

     Om binary search uit te voeren, is een vereiste dat de array gesorteerd is, anders werkt het niet.
     */
    public static <T extends Comparable<T>> int binarySearch(T[] array, T target, int left, int right) {
        // Print het deel van de array dat momenteel wordt doorzocht
        System.out.print("Zoeken in: [");
        for (int i = left; i <= right; i++) {
            System.out.print(array[i] + (i < right ? ", " : ""));
        }
        System.out.print("] ");


        if (left > right) { // Error handling: als het deel leeg is (item niet gevonden)
            return -1;
        }

        // Bereken het midden
        int mid = left + (right - left) / 2;
        System.out.println("zoekpositie in array: array[" + mid + "] = " + array[mid]);


        if (array[mid].compareTo(target) == 0) {
            System.out.println("Gevonden op index: " + mid);
            return mid; // Doel gevonden
        } else if (array[mid].compareTo(target) < 0) { // -1 comparable -> target is groter dan huidige positie
            System.out.println("Ga naar rechterhelft");
            return binarySearch(array, target, mid + 1, right);
        } else {
            System.out.println("Ga naar linkerhelft");
            return binarySearch(array, target, left, mid - 1); // +1 comparable -> target is kleiner dan huidige positie
        }
    }
    // Wrapper-functie
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
