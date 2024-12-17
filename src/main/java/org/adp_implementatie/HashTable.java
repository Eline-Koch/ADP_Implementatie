package org.adp_implementatie;

import java.util.Arrays;

public class HashTable {

    private static final int INITIAL_CAPACITY = 17; // Prime number as initial capacity
    private static final double LOAD_FACTOR_THRESHOLD = 0.4;
    private static final Entry DELETED = new Entry(null, null);

    private int size;
    private Entry[] table;

    public HashTable() {
        this.size = 0;
        this.table = new Entry[INITIAL_CAPACITY];
    }

    private static class Entry {
        String key;
        double[] value;

        Entry(String key, double[] value) {
            this.key = key;
            this.value = value;
        }
    }

    // Hash function
    private int hash(String key) {
        return key.toCharArray()[0];
    }

    // Put method with quadratic probing
    public void add(String key, double[] value) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");

        if ((double) size / (double) table.length >= LOAD_FACTOR_THRESHOLD) {
            System.out.println(true);
            resize();
        }

        int index = hash(key);
        int i = 0;

        // Quadratic probing to find a slot
        while (table[(index + i * i) % table.length] != null && table[(index + i * i) % table.length] != DELETED) {
            int probeIndex = (index + i * i) % table.length;
            if (table[probeIndex].key.equals(key)) {
                table[probeIndex].value = value; // Update the value if key exists
                return;
            }
            i++;
        }

        // Insert the new key-value pair
        table[(index + i * i) % table.length] = new Entry(key, value);
        size++;
    }

    // Get method with quadratic probing
    public double[] get(String key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");

        int index = hash(key);
        int i = 0;

        // Quadratic probing to find the key
        while (table[(index + i * i) % table.length] != null && table[(index + i * i) % table.length] != DELETED) {
            int probeIndex = (index + i * i) % table.length;
            if (table[probeIndex].key.equals(key)) {
                return table[probeIndex].value;
            }
            i++;
        }

        return null; // Key not found
    }

    public double[] set(String key, double[] value) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");

        int index = hash(key);
        int i = 0;

        // Quadratic probing to find the key
        while (table[(index + i * i) % table.length] != null && table[(index + i * i) % table.length] != DELETED) {
            int probeIndex = (index + i * i) % table.length;
            if (table[probeIndex].key.equals(key)) {
                table[probeIndex].value = value;
            }
            i++;
        }

        return null; // Key not found
    }

    // Remove method with quadratic probing
    public void remove(String key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");

        int index = hash(key);
        int i = 0;

        // Quadratic probing to find the key
        while (table[(index + i * i) % table.length] != null && table[(index + i * i) % table.length] != DELETED) {
            int probeIndex = (index + i * i) % table.length;
            if (table[probeIndex].key.equals(key)) {
                table[probeIndex] = DELETED;
                size--;
                return;
            }
            i++;
        }
    }

    public void remove(double[] value) {
        for (int i = 0; i < table.length; ) {
            if (table[i].value == value) {
                table[i] = DELETED;
                size--;
                return;
            }
        }
    }

    public boolean contains(double[] value) {
        for (int i = 0; i < table.length; ) {
            if (table[i].value == value) {
                return true;
            }
        }
        return false;
    }

    public String keyOf(double[] value) {
        for (int i = 0; i < table.length; ) {
            if (table[i].value == value) {
                return table[i].key;
            }
        }
        return null;
    }

    // Resize the table when the load factor exceeds the threshold
    private void resize() {
        Entry[] oldTable = table;
        table = new Entry[nextPrime(oldTable.length * 2)];
        size = 0;

        for (Entry entry : oldTable) {
            if (entry != null) {
                add(entry.key, entry.value);
            }
        }
    }

    // Utility method to find the next prime number greater than the given number
    private int nextPrime(int n) {
        while (true) {
            if (isPrime(n)) {
                return n;
            }
            n++;
        }
    }

    // Utility method to check if a number is prime
    private boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) return false;
        }
        return true;
    }

    // Utility method to print the current state of the hash map
    public void printTable() {
        System.out.println("HashMap contents:");
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                System.out.println("Index " + i + ": Key = " + table[i].key + ", " +
                        "Value = " + Arrays.toString(table[i].value));
            } else {
                System.out.println("Index " + i + ": null");
            }
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        //Quadratic probing example, size = 17
        HashTable hashTable = new HashTable();
        hashTable.add("P", new double[]{1}); //ASCII: 80
        hashTable.add("a", new double[]{2}); //ASCII: 97 (80 + 17) index + 1
        hashTable.add("r", new double[]{3}); //ASCII: 114 (97 + 17) index + 4
        hashTable.remove("r");
        hashTable.add("ƒ", new double[]{4}); //ASCII: 131 (114 + 17) index + 16
        hashTable.printTable();

        hashTable.add("b", new double[]{5});
        hashTable.add("c", new double[]{6});
        hashTable.add("d", new double[]{7});
        hashTable.add("e", new double[]{8});
        hashTable.add("f", new double[]{9});
        hashTable.printTable();
    }
}
