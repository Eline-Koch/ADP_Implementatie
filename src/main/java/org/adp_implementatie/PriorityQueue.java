package org.adp_implementatie;


public class PriorityQueue <E extends Comparable<E>> {

    private static final int DEFAULT_CAPACITY = 10;
    private E[] heap; // Array om de heap op te slaan
    private int size;   // Huidige grootte van de heap
    private int capacity; // Maximale capaciteit van de heap

    // Constructor
    @SuppressWarnings("unchecked")
    public PriorityQueue() {
        this.capacity = DEFAULT_CAPACITY;
        this.size = 0;
        this.heap = (E[]) new Comparable[capacity];
    }

    // Peek: Bekijk het kleinste element zonder te verwijderen
    public E peek() {
        if (size == 0) {
            throw new IllegalStateException("Priority Queue is empty.");
        }
        return heap[0]; // Het wortelelement
    }

    // Poll: Haal het kleinste element uit de queue
    public E poll() {
        if (size == 0) {
            throw new IllegalStateException("Priority Queue is empty.");
        }
        E root = heap[0];
        heap[0] = heap[size - 1]; // Verplaats het laatste element naar de wortel
        size--; // Verminder de grootte
        heapifyDown(0); // Herstel de heap-eigenschap
        reduceCapacity();
        return root;
    }

    // Add: Voeg een element toe aan de queue
    public void add(E element) {
        ensureExtraCapacity();
        heap[size] = element; // Voeg het element toe aan het einde
        size++; // Verhoog de grootte
        heapifyUp(size - 1); // Herstel de heap-eigenschap
    }

    private void ensureExtraCapacity() {
        if (size == capacity) {
            heap = copyArray(heap, capacity * 2);
            capacity *= 2;
        }
    }

    private void reduceCapacity() {
        if (size < capacity / 2) {
            heap = copyArray(heap, capacity / 2);
            capacity /= 2;
        }
    }

    @SuppressWarnings("unchecked")
    public E[] copyArray(E[] original, int newLength) {
        if (original == null) {
            throw new NullPointerException("Original array cannot be null");
        }
        if (newLength < 0) {
            throw new NegativeArraySizeException("New length cannot be negative");
        }

        // Nieuwe array maken
        E[] newArray = (E[]) new Comparable[newLength];

        // Bereken de maximale index tot waar gekopieerd moet worden
        int lengthToCopy = (original.length < newLength) ? original.length : newLength;

        // Kopieer elementen handmatig
        for (int i = 0; i < lengthToCopy; i++) {
            newArray[i] = original[i];
        }

        return newArray;
    }

    // Heapify omhoog: Herstel de heap-eigenschap na toevoegen
    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap[index].compareTo(heap[parentIndex]) >= 0) {
                break; // Heap-eigenschap is voldaan
            }
            swap(index, parentIndex);
            index = parentIndex;
        }
    }

    // Heapify omlaag: Herstel de heap-eigenschap na verwijderen
    private void heapifyDown(int index) {
        while (index < size) {
            int leftChildIndex = 2 * index + 1;
            int rightChildIndex = 2 * index + 2;
            int smallest = index;

            if (leftChildIndex < size && heap[leftChildIndex].compareTo(heap[smallest]) < 0) {
                smallest = leftChildIndex;
            }

            if (rightChildIndex < size && heap[leftChildIndex].compareTo(heap[smallest]) < 0) {
                smallest = rightChildIndex;
            }

            if (smallest == index) {
                break; // Heap-eigenschap is voldaan
            }

            swap(index, smallest);
            index = smallest;
        }
    }

    // Wissel twee elementen in de array
    private void swap(int i, int j) {
        E temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    // Methode om de grootte van de queue te verkrijgen
    public int size() {
        return size;
    }

    public void printHeap() {
        System.out.print("[");
        for (int i = 0; i < size; i++) {
            System.out.print(heap[i]);
            if (i < size - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

    public static void main(String[] args) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        pq.add(10);
        pq.add(5);
        pq.add(20);
        pq.add(2);

        System.out.println("Peek: " + pq.peek()); // Moet 2 zijn
        System.out.println("Poll: " + pq.poll()); // Verwijdert en retourneert 2
        System.out.println("Peek: " + pq.peek()); // Moet 5 zijn
        System.out.println("Size: " + pq.size()); // Moet 3 zijn
    }
}
