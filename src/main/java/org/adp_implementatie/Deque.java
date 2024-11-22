package org.adp_implementatie;

public class Deque<T> {
    // Inner class for nodes in the deque
    private static class Node<T> {
        T data;
        Node<T> next;
        Node<T> prev;

        Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

    private Node<T> front; // Points to the front of the deque
    private Node<T> rear;  // Points to the rear of the deque
    private int size;      // Tracks the size of the deque

    // Constructor
    public Deque() {
        front = null;
        rear = null;
        size = 0;
    }

    // Inserts an element at the left (front) of the deque
    public void insertLeft(T value) {
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
            front = rear = newNode; // First element in the deque
        } else {
            newNode.next = front;
            front.prev = newNode;
            front = newNode;
        }
        size++;
    }

    // Inserts an element at the right (rear) of the deque
    public void insertRight(T value) {
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
            front = rear = newNode; // First element in the deque
        } else {
            newNode.prev = rear;
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    // Checks if the deque is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Returns the size of the deque
    public int size() {
        return size;
    }

    // Displays the elements of the deque from front to rear
    public void display() {
        if (isEmpty()) {
            System.out.println("Deque is empty.");
            return;
        }
        Node<T> current = front;
        while (current != null) { // With Iterator instead of index
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }

    // Removes and returns the element at the left (front) of the deque
    public T removeLeft() {
        if (isEmpty()) throw new IllegalStateException("Deque is empty.");
        T value = front.data;
        front = front.next;
        if (front == null) { // If the deque becomes empty
            rear = null;
        } else {
            front.prev = null;
        }
        size--;
        return value;
    }

    // Removes and returns the element at the right (rear) of the deque
    public T removeRight() {
        if (isEmpty()) throw new IllegalStateException("Deque is empty.");
        T value = rear.data;
        rear = rear.prev;
        if (rear == null) { // If the deque becomes empty
            front = null;
        } else {
            rear.next = null;
        }
        size--;
        return value;
    }
}

