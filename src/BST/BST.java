package BST;

public interface BST<T> {

    public boolean isEmpty();
    public int height();
    public boolean search(T element);

    public void insert(T element);
    public void remove(T element);

    public T maximum();
    public T minimum();

    

    public T[] preOrder();
    public T[] Order();
    public T[] postOrder();

    public int size();
    
}