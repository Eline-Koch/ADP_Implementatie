package org.adp_implementatie;

public class HashTable {

    private static final int INITIAL_CAPACITY = 17; // Prime number as initial capacity
    private static final double LOAD_FACTOR_THRESHOLD = 0.4;
    private static final Entry DELETED = new Entry(null, 0.0);

    private int size;
    private Entry[] table;

    public HashTable() {
        this.size = 0;
        this.table = new Entry[INITIAL_CAPACITY];
    }

    private static class Entry {
        String key;
        double value;

        Entry(String key, Double value) {
            this.key = key;
            this.value = value;
        }
    }

    // Hash function
    private int hash(String key) {
        return key.toCharArray()[0];
    }

    // Put method with quadratic probing
    public void add(String key, Double value) {
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
    public Double get(String key) {
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

    public Double[] set(String key, Double value) {
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

    public void remove(Double value) {
        if ((double) size / (double) table.length < LOAD_FACTOR_THRESHOLD * 0.33) {
            downsize();
        }

        for (int i = 0; i < table.length; ) {
            if (table[i].value == value) {
                table[i] = DELETED;
                size--;
                return;
            }
        }
    }

    public boolean contains(Double value) {
        for (int i = 0; i < table.length; ) {
            if (table[i].value == value) {
                return true;
            }
        }
        return false;
    }

    public String keyOf(Double value) {
        for (int i = 0; i < table.length; ) {
            if (table[i].value == value) {
                return table[i].key;
            }
        }
        return null;
    }

    // Resize the table when the load factor exceeds the threshold
    private void upsize() {
        Entry[] oldTable = table;
        table = new Entry[nextPrime(oldTable.length * 2)];
        size = 0;

        for (Entry entry : oldTable) {
            if (entry != null && entry != DELETED) {
                add(entry.key, entry.value);
            }
        }
    }

    private void downsize() {
        Entry[] oldTable = table;
        table = new Entry[nextPrime(oldTable.length / 2)];
        size = 0;

        for (Entry entry : oldTable) {
            if (entry != null && entry != DELETED) {
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

    public int size() {
        return size;
    }

    // Utility method to print the current state of the hash map
    public void printTable() {
        System.out.println();
        System.out.println("HashMap contents:");
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                System.out.print("Index " + i + ": Key = " + table[i].key + ", " +
                        "Value = " + table[i].value + "; ");
            } else {
                System.out.print("Index " + i + ": null; ");
            }
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        //Quadratic probing example, size = 17
        HashTable hashTable = new HashTable();
        hashTable.add("P", 1.0); //ASCII: 80
        hashTable.add("a", 2.0); //ASCII: 97 (80 + 17) index + 1
        hashTable.add("r", 3.0); //ASCII: 114 (97 + 17) index + 4
        hashTable.remove("r");
        hashTable.add("ƒ", 4.0); //ASCII: 131 (114 + 17) index + 16
        hashTable.printTable();
        System.out.println();

        hashTable.add("b", 5.0);
        hashTable.add("c", 6.0);
        hashTable.add("d", 7.0);
        hashTable.add("e", 8.0);
        hashTable.add("f", 9.0);
        hashTable.printTable();
        System.out.println();

        hashTable.remove("b");
        hashTable.remove("c");
        hashTable.remove("d");
        hashTable.remove("e");
        hashTable.remove("f");
        hashTable.printTable();
        System.out.println();
    }
}
