package BST;

public interface BST<T extends Comparable <T>> {

    public boolean isEmpty();
    public int height();
    public BSTNode<T> search(T element);

    public void insert(T element);
    public void remove(T element);

    public BSTNode<T> maximum();
    public BSTNode<T> minimum();

    public T[] preOrder();
    public T[] Order();
    public T[] postOrder();

    public int size();
    
}