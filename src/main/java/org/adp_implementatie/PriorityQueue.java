package org.adp_implementatie;


public class PriorityQueue <E extends Comparable<E>> {

    private static final int DEFAULT_CAPACITY = 10;
    private E[] heap;
    private int size;
    private int capacity;

    @SuppressWarnings("unchecked")
    public PriorityQueue() {
        this.capacity = DEFAULT_CAPACITY;
        this.size = 0;
        this.heap = (E[]) new Comparable[capacity];
    }

    public E peek() {
        if (size == 0) {
            throw new IllegalStateException("Priority Queue is empty.");
        }
        return heap[0];
    }

    public E poll() {
        if (size == 0) {
            throw new IllegalStateException("Priority Queue is empty.");
        }
        E root = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown(0);
        reduceCapacity();
        return root;
    }

    public void add(E element) {
        ensureExtraCapacity();
        heap[size] = element;
        size++;
        heapifyUp(size - 1);
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

        E[] newArray = (E[]) new Comparable[newLength];

        int lengthToCopy = (original.length < newLength) ? original.length : newLength;

        for (int i = 0; i < lengthToCopy; i++) {
            newArray[i] = original[i];
        }

        return newArray;
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap[index].compareTo(heap[parentIndex]) >= 0) {
                break;
            }
            swap(index, parentIndex);
            index = parentIndex;
        }
    }

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
                break;
            }

            swap(index, smallest);
            index = smallest;
        }
    }

    private void swap(int i, int j) {
        E temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

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
