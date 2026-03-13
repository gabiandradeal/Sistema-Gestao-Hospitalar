package structures.queue;

import stack.LinkedStack;

// Quack = Queue + Stack
public class Quack<T> implements Queue<T>{
    private final LinkedStack<T> stackIn;
    private final LinkedStack<T> stackOut;

    public Quack() {
        stackIn = new LinkedStack<>();
        stackOut = new LinkedStack<>();
    }

    @Override
    public void enqueue(T element) {
        stackIn.push(element);
    }

    // O custo é O(1) Amortizado
    @Override
    public void dequeue() {
        if (stackOut.isEmpty()) {
            while(!stackIn.isEmpty()) {
                stackOut.push(stackIn.pop());
            }
        }
        if(stackOut.isEmpty()) throw new RuntimeException("Fila está vazia");

        stackOut.pop();
    }

    @Override
    public T peek() {
        if (stackOut.isEmpty()) {
            while(!stackIn.isEmpty()) {
                stackOut.push(stackIn.pop());
            }
        }
        if (stackOut.isEmpty()) throw new RuntimeException("Fila está vazia");

        return stackOut.peek();
    }

    @Override
    public int size() {
        // O tamanho da fila é a soma do que está aguardando em ambas
        return stackIn.size() + stackOut.size();
    }

    @Override
    public boolean isEmpty() {
        return stackIn.isEmpty() && stackOut.isEmpty();
    }
}
