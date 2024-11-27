package org.adp_implementatie;


public class PriorityQueue {
    private int[] heap; // Array om de heap op te slaan
    private int size;   // Huidige grootte van de heap
    private int capacity; // Maximale capaciteit van de heap

    // Constructor
    public PriorityQueue(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.heap = new int[capacity];
    }

    // Peek: Bekijk het kleinste element zonder te verwijderen
    public int peek() {
        if (size == 0) {
            throw new IllegalStateException("Priority Queue is empty.");
        }
        return heap[0]; // Het wortelelement
    }

    // Poll: Haal het kleinste element uit de queue
    public int poll() {
        if (size == 0) {
            throw new IllegalStateException("Priority Queue is empty.");
        }
        int root = heap[0];
        heap[0] = heap[size - 1]; // Verplaats het laatste element naar de wortel
        size--; // Verminder de grootte
        heapifyDown(0); // Herstel de heap-eigenschap
        return root;
    }

    // Add: Voeg een element toe aan de queue
    public void add(int element) {
        if (size == capacity) {
            throw new IllegalStateException("Priority Queue is full.");
        }
        heap[size] = element; // Voeg het element toe aan het einde
        size++; // Verhoog de grootte
        heapifyUp(size - 1); // Herstel de heap-eigenschap
    }

    // Heapify omhoog: Herstel de heap-eigenschap na toevoegen
    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap[index] >= heap[parentIndex]) {
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

            if (leftChildIndex < size && heap[leftChildIndex] < heap[smallest]) {
                smallest = leftChildIndex;
            }

            if (rightChildIndex < size && heap[rightChildIndex] < heap[smallest]) {
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
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    // Methode om de grootte van de queue te verkrijgen
    public int size() {
        return size;
    }

    // Testprogramma
    public static void main(String[] args) {
        PriorityQueue pq = new PriorityQueue(10);

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
