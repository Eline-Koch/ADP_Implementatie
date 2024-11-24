package org.adp_implementatie;

public class HanStackMetArray {
    private final Object[] stack;
    private int top;

    public HanStackMetArray(int size) {
        top = -1; // or else the first element of the array is selected
//        int maxSize = 5;
        stack = new Object[size];
    }

    public void push(Object item) {
        top++;
        this.stack[top] = item;
    }

    public void pop() {
        top--;
        this.stack[top] = null;
    }

    public Object peek() {
        return stack[top];
    }

    public int size() {
        return top + 1;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public static void main(String[] args) {
        HanStackMetArray HanStackMetArray = new HanStackMetArray(5);

        System.out.println(HanStackMetArray.isEmpty());
        HanStackMetArray.push(1);
        HanStackMetArray.push(2);

        System.out.println(HanStackMetArray.peek()); // 2

        HanStackMetArray.pop();
        System.out.println(HanStackMetArray.peek()); // 1

        HanStackMetArray.pop();
        System.out.println(HanStackMetArray.peek()); // Geeft een error want is leeg. dus correct
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
}


