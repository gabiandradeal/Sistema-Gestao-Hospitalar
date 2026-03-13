package structures.queue;

public interface Queue<T> {
    void enqueue(T element);
    void dequeue();
    T peek();  // Olha quem é o primeiro da fila
    boolean isEmpty();
    int size();
}
