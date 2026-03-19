package structures.stack;

/**
 * Interface genérica para a estrutura de dados Pilha (Stack).
 * @author Georis
 * @version 1.0
 * @since 13/03
 */
public interface Stack<T> {
    void push(T element);
    T pop();              // Remove e retorna o topo
    T peek();
    boolean isEmpty();
    int size();
}
