package structures.stack;

public interface Stack<T> {
    void push(T element);
    T pop();              // Remove e retorna o topo
    T peek();
    boolean isEmpty();
    int size();
}
