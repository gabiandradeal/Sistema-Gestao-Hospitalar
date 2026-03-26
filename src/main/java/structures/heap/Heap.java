package structures.heap;

/**
 * Interface genérica que define as operações básicas para uma heap.
 * @param <T> o tipo de elementos armazenados na heap
 * @author João Victor
 * @version 1.0
 */
public interface Heap<T> {
    public T getRoot();
    public int height();
    public int right(int idx);
    public int left(int idx);
    public int size();
    public void insert(T element);
    public T remove();
    public void heapify(int idx);
}
