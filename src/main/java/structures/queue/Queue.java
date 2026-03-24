package structures.queue;

/**
 * Interface genérica para a estrutura de dados Fila (Queue).
 * Define as operações fundamentais para a manipulação de elementos no padrão FIFO (First-In, First-Out).
 * @author Georis
 * @version 1.0
 * @since 12/03
 */
public interface Queue<T> {
    void enqueue(T element);
    T dequeue();
    T peek();  // Olha quem é o primeiro da fila
    boolean isEmpty();
    int size();
}
