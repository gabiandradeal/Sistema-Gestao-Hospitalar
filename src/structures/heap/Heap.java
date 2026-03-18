package structures.heap;

public interface Heap<T> {
    public T getRoot();
    public int height();
    public int size();
    public void insert();
    public T remove();
    public void heapify();
    public Heap<T> buildHeap();
    public Heap<T> heapsort(Function<> map);
}
