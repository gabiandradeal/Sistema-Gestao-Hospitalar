package structures.heap;

public interface Heap<T> {
    public T getRoot();
    public int height();
    public int right(int idx);
    public int left(int idx);
    public int size();
    public void insert();
    public T remove();
    public void heapify();
    public Heap<T> buildHeap(T[] array);
    public Heap<T> heapsort(Function<> map);
}
