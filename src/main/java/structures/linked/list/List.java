package structures.linked.list;

/**
 * Interface genérica para a estrutura de dados Lista.
 * Define as operações fundamentais para a manipulação de elementos.
 * @author Georis
 * @version 1.0
 * @since 11/03
 */
public interface List<T> {
        boolean search(T element);
        void add(T element);
        void remove(T element);
        int size();
        boolean isEmpty();

        // Métodos para implementação eficiente em pilha
        void addFirst(T element);
        T removeFirst();
}
