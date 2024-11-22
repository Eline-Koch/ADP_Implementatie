package org.adp_implementatie;

public class DoublyLinkedList<E> {

    private static class Node<E> {
        E data;
        Node<E> next;
        Node<E> prev;

        Node(E data) {
            this.data = data;
        }
    }

    private Node<E> head;
    private Node<E> tail;
    private int size;

    public DoublyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public void add(E element) {
        Node<E> newNode = new Node<>(element);

        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }

        size++;
    }

    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        Node<E> current = getNodeAt(index);
        return current.data;
    }

    public void set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        Node<E> current = getNodeAt(index);
        current.data = element;
    }

    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        Node<E> current = getNodeAt(index);
        E removedData = current.data;

        if (current.prev != null) {
            current.prev.next = current.next;
        } else {
            head = current.next;
        }

        if (current.next != null) {
            current.next.prev = current.prev;
        } else {
            tail = current.prev;
        }

        size--;
        return removedData;
    }

    public boolean remove(E element) {
        Node<E> current = head;
        while (current != null) {
            if (current.data.equals(element)) {
                if (current.prev != null) {
                    current.prev.next = current.next;
                } else {
                    head = current.next;
                }

                if (current.next != null) {
                    current.next.prev = current.prev;
                } else {
                    tail = current.prev;
                }

                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

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

    public int size() {
        return size;
    }

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

