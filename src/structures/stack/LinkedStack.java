package structures.stack;

import linked.list.LinkedList;

public class LinkedStack <T> implements Stack<T> {

    // Composition
    private final LinkedList<T> linkedList;

    public LinkedStack() {
        this.linkedList = new LinkedList<>();
    }

    @Override
    public void push(T element){
        linkedList.addFirst(element);
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new RuntimeException("Pilha está vazia");
        }
        // Remove do topo em O(1) e retorna o valor
        return linkedList.removeFirst();
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Pilha está vazia");
        }
        // Em O(1)
        return linkedList.getHead().getData();
    }

    @Override
    public boolean isEmpty() {
        return linkedList.isEmpty();
    }

    @Override
    public int size() {
        return linkedList.size();
    }
}
