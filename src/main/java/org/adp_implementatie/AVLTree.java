package org.adp_implementatie;

public class AVLTree<T extends Comparable<T>> {

    // Knooppuntklasse
    public class Node {
        public T value;
        Node left, right;
        int height;

        Node(T value) {
            this.value = value;
            this.height = 1; // Nieuwe knopen hebben een hoogte van 1
        }
    }

    private Node root;

    // Hulpmethode om de hoogte van een knoop te krijgen
    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    // Hulpmethode om de balansfactor van een knoop te berekenen
    private int getBalance(Node node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    // Rechtsrotatie
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Rotatie uitvoeren
        x.right = y;
        y.left = T2;

        // Hoogtes bijwerken
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        // Nieuwe wortel retourneren
        return x;
    }

    // Linksrotatie
    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Rotatie uitvoeren
        y.left = x;
        x.right = T2;

        // Hoogtes bijwerken
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        // Nieuwe wortel retourneren
        return y;
    }

    // Insert-functie
    public void insert(T value) {
        root = insert(root, value);
    }

    private Node insert(Node node, T value) {
        // Standaard BST-invoeging
        if (node == null) {
            return new Node(value);
        }
        if (value.compareTo(node.value) < 0) {
            node.left = insert(node.left, value);
        } else if (value.compareTo(node.value) > 0) {
            node.right = insert(node.right, value);
        } else {
            return node; // Geen duplicaten toegestaan
        }

        // Hoogte bijwerken
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // Balansfactor berekenen
        int balance = getBalance(node);

        // Balans herstellen met rotaties
        if (balance > 1 && value.compareTo(node.left.value) < 0) {
            return rightRotate(node); // Links-links case
        }
        if (balance < -1 && value.compareTo(node.right.value) > 0) {
            return leftRotate(node); // Rechts-rechts case
        }
        if (balance > 1 && value.compareTo(node.left.value) > 0) {
            node.left = leftRotate(node.left); // Links-rechts case
            return rightRotate(node);
        }
        if (balance < -1 && value.compareTo(node.right.value) < 0) {
            node.right = rightRotate(node.right); // Rechts-links case
            return leftRotate(node);
        }

        return node;
    }

    // Find-functie
    public Node find(T value) {
        return find(root, value);
    }

    private Node find(Node node, T value) {
        if (node == null || node.value.equals(value)) {
            return node;
        }
        if (value.compareTo(node.value) < 0) {
            return find(node.left, value);
        }
        return find(node.right, value);
    }

    // FindMin-functie
    public T findMin() {
        if (root == null) throw new IllegalStateException("Boom is leeg");
        return findMin(root).value;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // FindMax-functie
    public T findMax() {
        if (root == null) throw new IllegalStateException("Boom is leeg");
        return findMax(root).value;
    }

    private Node findMax(Node node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    // Remove-functie
    public void remove(T value) {
        root = remove(root, value);
    }

    private Node remove(Node node, T value) {
        if (node == null) {
            return null;
        }

        // Standaard BST-verwijdering
        if (value.compareTo(node.value) < 0) {
            node.left = remove(node.left, value);
        } else if (value.compareTo(node.value) > 0) {
            node.right = remove(node.right, value);
        } else {
            // Knooppunt gevonden
            if ((node.left == null) || (node.right == null)) {
                node = (node.left != null) ? node.left : node.right;
            } else {
                Node temp = findMin(node.right);
                node.value = temp.value;
                node.right = remove(node.right, temp.value);
            }
        }

        if (node == null) {
            return null;
        }

        // Hoogte bijwerken
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // Balansfactor berekenen
        int balance = getBalance(node);

        // Balans herstellen met rotaties
        if (balance > 1 && getBalance(node.left) >= 0) {
            return rightRotate(node); // Links-links case
        }
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = leftRotate(node.left); // Links-rechts case
            return rightRotate(node);
        }
        if (balance < -1 && getBalance(node.right) <= 0) {
            return leftRotate(node); // Rechts-rechts case
        }
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rightRotate(node.right); // Rechts-links case
            return leftRotate(node);
        }

        return node;
    }

    // Hulpmethode om de boom in-order af te drukken
    public void printInOrder() {
        printInOrder(root);
        System.out.println();
    }

    private void printInOrder(Node node) {
        if (node != null) {
            printInOrder(node.left);
            System.out.print(node.value + " ");
            printInOrder(node.right);
        }
    }

    // Main-methode voor testen
    public static void main(String[] args) {
        AVLTree<Integer> tree = new AVLTree<>();
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(40);
        tree.insert(50);
        tree.insert(25);

        System.out.println("In-order weergave van de AVL-boom:");
        tree.printInOrder();

        System.out.println("Minimale waarde: " + tree.findMin());
        System.out.println("Maximale waarde: " + tree.findMax());

        System.out.println("Verwijderen van 40:");
        tree.remove(40);
        tree.printInOrder();
    }
}
