package structures.hash;

public interface Table<T> {
    public boolean isEmpty();
    public boolean isFull();
    public int capacity();
    public int size();
    public void insert(T element);
    public T remove(T element);
    public boolean search(T element);
    public int indexOf(T element);
    public void rehash();
}