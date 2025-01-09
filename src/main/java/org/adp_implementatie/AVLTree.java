package org.adp_implementatie;

public class AVLTree<T extends Comparable<T>> {

    public class Node {
        public T value;
        Node left, right;
        int height;

        Node(T value) {
            this.value = value;
            this.height = 1;
        }
    }

    private Node root;

    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    private int getBalance(Node node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    public void insert(T value) {
        root = insert(root, value);
    }

    private Node insert(Node node, T value) {
        if (node == null) {
            return new Node(value);
        }
        if (value.compareTo(node.value) < 0) {
            node.left = insert(node.left, value);
        } else if (value.compareTo(node.value) > 0) {
            node.right = insert(node.right, value);
        } else {
            return node;
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);

        if (balance > 1 && value.compareTo(node.left.value) < 0) {
            return rightRotate(node);
        }
        if (balance < -1 && value.compareTo(node.right.value) > 0) {
            return leftRotate(node);
        }
        if (balance > 1 && value.compareTo(node.left.value) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && value.compareTo(node.right.value) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }


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

    public void remove(T value) {
        root = remove(root, value);
    }

    private Node remove(Node node, T value) {
        if (node == null) {
            return null;
        }

        if (value.compareTo(node.value) < 0) {
            node.left = remove(node.left, value);
        } else if (value.compareTo(node.value) > 0) {
            node.right = remove(node.right, value);
        } else {
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

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);

        if (balance > 1 && getBalance(node.left) >= 0) {
            return rightRotate(node);
        }
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && getBalance(node.right) <= 0) {
            return leftRotate(node);
        }
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

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
