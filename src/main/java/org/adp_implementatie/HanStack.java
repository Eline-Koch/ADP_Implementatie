//
//package org.adp_implementatie;
//
//public class HanStack<T> {
//    private final DynamicArray<T> stack;
//
//    public HanStack() {
//        stack = new DynamicArray<>();
//    }
//
//    public void push(T item) {
//        this.stack.add(item);
//    }
//
//    public void pop() {
//        if (isEmpty()) {
//            throw new RuntimeException("Stack is leeg");
//        }
//        stack.remove(stack.size() - 1);
//    }
//
//    public T peek() {
//        return stack.get(stack.size() - 1);
//    }
//
//    public int size() {
//        return stack.size();
//    }
//
//    public boolean isEmpty() {
//        return stack.isEmpty();
//    }
//
//    public static void main(String[] args) {
//        HanStack<Integer> hanStack = new HanStack<>();
//
//        System.out.println(hanStack.isEmpty());
//        hanStack.push(1);
//        hanStack.push(2);
//
//        System.out.println(hanStack.peek()); // 2
//
//        hanStack.pop();
//        System.out.println(hanStack.peek()); // 1
//
//        hanStack.pop();
//        System.out.println(hanStack.peek()); // Geeft een error want is leeg. dus correct
//    }
//}
//
//
