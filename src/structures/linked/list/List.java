package structures.linked.list;

public interface List<T> {
        void add(T element);
        void remove(T element);
        int size();
        boolean isEmpty();

        // Métodos para implementação eficiente em pilha
        void addFirst(T element);
        T removeFirst();
}
