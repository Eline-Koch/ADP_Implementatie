package org.adp_implementatie;

public class DoublyLinkedList<E> {

    // Node class to represent each element in the list
    private static class Node<E> {
        E data;
        Node<E> next;
        Node<E> prev;

        Node(E data) {
            this.data = data;
        }
    }

    private Node<E> head;  // Reference to the first node
    private Node<E> tail;  // Reference to the last node
    private int size;      // Size of the list

    public DoublyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    // Add element at the end of the list
    public void add(E element) {
        Node<E> newNode = new Node<>(element);

        if (tail == null) {  // If the list is empty
            head = newNode;
            tail = newNode;
        } else {  // Append to the end of the list
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }

        size++;
    }

    // Get element at a specific index
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        Node<E> current = getNodeAt(index);
        return current.data;
    }

    // Set (replace) element at a specific index
    public void set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        Node<E> current = getNodeAt(index);
        current.data = element;
    }

    // Remove element at a specific index
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        Node<E> current = getNodeAt(index);
        E removedData = current.data;

        if (current.prev != null) {
            current.prev.next = current.next;
        } else {
            head = current.next;  // Removing the head
        }

        if (current.next != null) {
            current.next.prev = current.prev;
        } else {
            tail = current.prev;  // Removing the tail
        }

        size--;
        return removedData;
    }

    // Remove first occurrence of the element
    public boolean remove(E element) {
        Node<E> current = head;
        while (current != null) {
            if (current.data.equals(element)) {
                if (current.prev != null) {
                    current.prev.next = current.next;
                } else {
                    head = current.next;  // Removing the head
                }

                if (current.next != null) {
                    current.next.prev = current.prev;
                } else {
                    tail = current.prev;  // Removing the tail
                }

                size--;
                return true;
            }
            current = current.next;
        }
        return false;  // Element not found
    }

    // Check if the list contains the specified element
    public boolean contains(E element) {
        Node<E> current = head;
        while (current != null) {
            if (current.data.equals(element)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // Get the index of the first occurrence of the element
    public int indexOf(E element) {
        Node<E> current = head;
        int index = 0;
        while (current != null) {
            if (current.data.equals(element)) {
                return index;
            }
            current = current.next;
            index++;
        }
        return -1;  // Element not found
    }

    // Helper method to get the node at a specific index
    private Node<E> getNodeAt(int index) {
        Node<E> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    // Method to return the size of the list
    public int size() {
        return size;
    }

    // Method to print the list for testing purposes
    public void printList() {
        Node<E> current = head;
        while (current != null) {
            System.out.print(current.data + " <-> ");
            current = current.next;
        }
        System.out.println("null");
    }

    public static void main(String[] args) {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();

        // Adding elements to the list
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);

        list.printList();  // Output: 10 <-> 20 <-> 30 <-> 40 <-> null

        // Get element at index 2
        System.out.println("Element at index 2: " + list.get(2));  // Output: 30

        // Set element at index 1
        list.set(1, 25);
        list.printList();  // Output: 10 <-> 25 <-> 30 <-> 40 <-> null

        // Remove element at index 2
        list.remove(2);
        list.printList();  // Output: 10 <-> 25 <-> 40 <-> null

        // Remove element 25
        list.remove(Integer.valueOf(25));
        list.printList();  // Output: 10 <-> 40 <-> null

        // Check if list contains 40
        System.out.println("Contains 40: " + list.contains(40));  // Output: true

        // Get the index of element 10
        System.out.println("Index of 10: " + list.indexOf(10));  // Output: 0
    }
}

