package org.adp_implementatie;

import java.nio.charset.Charset;

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
        Integer value;

        Entry(String key, Integer value) {
            this.key = key;
            this.value = value;
        }
    }

    // Hash function
    private int hash(String key) {
        int product = 1;
        for (int i = 0; i < key.length(); i++) {
            if (i != 0) {
                product *= (int) key.charAt(i);
            }
        }
        return product;
    }

    // Put method with quadratic probing
    public void insert(String key, Integer value) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");

        if ((double) size / (double) table.length >= LOAD_FACTOR_THRESHOLD) {
            upsize();
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
    public Integer get(String key) {
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

    // Remove method with quadratic probing
    public void delete(String key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");

        if ((double) size / (double) table.length < LOAD_FACTOR_THRESHOLD * 0.33) {
            downsize();
        }

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

    public Integer update(String key, Integer value) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");

        int index = hash(key);
        int i = 0;

        // Quadratic probing to find the key
        while (table[(index + i * i) % table.length] != null && table[(index + i * i) % table.length] != DELETED) {
            int probeIndex = (index + i * i) % table.length;
            if (table[probeIndex].key.equals(key)) {
                table[probeIndex].value = value;
                return value;
            }
            i++;
        }

        return null; // Key not found
    }

    // Resize the table when the load factor exceeds the threshold
    private void upsize() {
        Entry[] oldTable = table;
        table = new Entry[nextPrime(oldTable.length * 2)];
        size = 0;

        for (Entry entry : oldTable) {
            if (entry != null && entry != DELETED) {
                insert(entry.key, entry.value);
            }
        }
    }

    private void downsize() {
        Entry[] oldTable = table;
        table = new Entry[nextPrime(oldTable.length / 2)];
        size = 0;

        for (Entry entry : oldTable) {
            if (entry != null && entry != DELETED) {
                insert(entry.key, entry.value);
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

    public int size() {
        return size;
    }

    // Utility method to print the current state of the hash map
    public void printTable() {
        System.out.println("HashMap contents:");
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                System.out.println("Index " + i + ": Key = " + table[i].key + ", " +
                        "Value = " + table[i].value + "; ");
            } else {
                System.out.println("Index " + i + ": null; ");
            }
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        //Quadratic probing example, size = 17
        HashTable hashTable = new HashTable();
        hashTable.insert("P", 1); //ASCII: 80
        hashTable.insert("a", 2); //ASCII: 97 (80 + 17) index + 1
        hashTable.insert("r", 3); //ASCII: 114 (97 + 17) index + 4
        hashTable.delete("r");
        hashTable.printTable();
        System.out.println();

        hashTable.insert("b", 4);
        hashTable.insert("c", 5);
        hashTable.insert("d", 6);
        hashTable.insert("e", 7);
        hashTable.insert("f", 8);
        hashTable.insert("g", 9);
        hashTable.printTable();
        System.out.println();

        hashTable.delete("b");
        hashTable.delete("c");
        hashTable.delete("d");
        hashTable.delete("e");
        hashTable.delete("f");
        hashTable.printTable();
        System.out.println();
    }
}
