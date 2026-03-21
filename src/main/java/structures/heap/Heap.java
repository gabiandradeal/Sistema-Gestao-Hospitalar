package structures.heap;

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
