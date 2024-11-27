package org.adp_implementatie;

import java.util.Arrays;
import java.util.Comparator;

public class PriorityQueue {
    private int[] heap;
    private int size;
    private Comparator<Integer> comparator;

    public PriorityQueue(int capacity, Comparator<Integer> comparator) {
        heap = new int[capacity];
        size = 0;
        this.comparator = comparator;
    }

    public void add(int element) {
        if (size == heap.length) {
            heap = Arrays.copyOf(heap, size * 2);
        }
        heap[size] = element;
        size++;
        heapifyUp();
    }

    public int poll() {
        if (size == 0) {
            throw new IllegalStateException("Queue is empty");
        }
        int root = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown();
        return root;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void heapifyUp() {
        int index = size - 1;
        while (index > 0 && comparator.compare(heap[index], heap[parent(index)]) < 0) {
            swap(index, parent(index));
            index = parent(index);
        }
    }

    private void heapifyDown() {
        int index = 0;
        while (leftChild(index) < size) {
            int smallerChild = leftChild(index);
            if (rightChild(index) < size && comparator.compare(heap[rightChild(index)], heap[smallerChild]) < 0) {
                smallerChild = rightChild(index);
            }

            if (comparator.compare(heap[index], heap[smallerChild]) <= 0) {
                break;
            }

            swap(index, smallerChild);
            index = smallerChild;
        }
    }

    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private int parent(int index) {
        return (index - 1) / 2;
    }

    private int leftChild(int index) {
        return 2 * index + 1;
    }

    private int rightChild(int index) {
        return 2 * index + 2;
    }

    public static void main(String[] args) {
        // Create a comparator for ascending order
        Comparator<Integer> ascending = Integer::compareTo;

        // Create a custom priority queue with a capacity of 10
        PriorityQueue pq = new PriorityQueue(10, ascending);

        // Adding elements
        pq.add(10);
        pq.add(5);
        pq.add(20);
        pq.add(15);

        // Printing and removing elements
        while (!pq.isEmpty()) {
            System.out.println("Removed: " + pq.poll()); // Will print in ascending order
        }
    }
}