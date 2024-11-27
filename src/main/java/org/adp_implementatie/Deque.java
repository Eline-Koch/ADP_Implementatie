package org.adp_implementatie;

public class Deque<T> {
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

    private Node<T> front;
    private Node<T> rear;
    private int size;

    public Deque() {
        front = null;
        rear = null;
        size = 0;
    }

    public void insertLeft(T value) {
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
            front = rear = newNode;
        } else {
            newNode.next = front;
            front.prev = newNode;
            front = newNode;
        }
        size++;
    }

    public void insertRight(T value) {
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
            front = rear = newNode;
        } else {
            newNode.prev = rear;
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void display() {
        if (isEmpty()) {
            System.out.println("Deque is empty.");
            return;
        }
        Node<T> current = front;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }

    public T removeLeft() {
        if (isEmpty()) throw new IllegalStateException("Deque is empty.");
        T value = front.data;
        front = front.next;
        if (front == null) {
            rear = null;
        } else {
            front.prev = null;
        }
        size--;
        return value;
    }

    public T removeRight() {
        if (isEmpty()) throw new IllegalStateException("Deque is empty.");
        T value = rear.data;
        rear = rear.prev;
        if (rear == null) {
            front = null;
        } else {
            rear.next = null;
        }
        size--;
        return value;
    }
}