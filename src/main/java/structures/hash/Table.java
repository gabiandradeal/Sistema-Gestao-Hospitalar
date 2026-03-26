package structures.hash;

/**
 * Interface genérica que define as operações básicas para uma tabela hash.
 * @param <T> o tipo de elementos armazenados na tabela hash
 * @author João Victor
 * @version 1.0
 */
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