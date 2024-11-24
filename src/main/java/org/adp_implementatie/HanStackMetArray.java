package org.adp_implementatie;

public class HanStackMetArray<T> {
    private final T[] stack;
    private int top;

    @SuppressWarnings("unchecked")
    public HanStackMetArray(int size) {
        top = 0;
        stack = (T[]) new Object[size];
    }

    public void push(T item) {
        if (top == stack.length - 1) {
            throw new StackOverflowError("Stack is vol");
        }
        stack[++top] = item;
    }

    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is leeg");
        }
        return stack[top--];
    }

    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is leeg");
        }
        return stack[top]; // Retourneer het bovenste item zonder te verwijderen
    }

    public int top() {
        return top; // Retourneer de huidige index van top
    }

    public boolean isEmpty() {
        return top == -1; // Controleer of de stack leeg is
    }

    public void printStack() {
        if (isEmpty()) {
            System.out.println("Stack is empty");
            return;
        }
        System.out.print("Stack elements (top -> bottom): ");
        for (int i = top; i >= 0; i--) {
            System.out.print(stack[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        HanStackMetArray<Integer> integerStack = new HanStackMetArray<>(5);

        integerStack.push(1);
        integerStack.push(2);

        System.out.println("Peek: " + integerStack.peek()); // 2

        System.out.println(integerStack.pop());
        System.out.println("Peek after pop: " + integerStack.peek()); // 1

        HanStackMetArray<String> stringStack = new HanStackMetArray<>(3);
        stringStack.push("Hello");
        stringStack.push("World");
        stringStack.printStack(); // World Hello
        System.out.println(stringStack.pop());
        System.out.println(stringStack.peek());
    }
}
