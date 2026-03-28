package structures.bst;

/**
* Estrutura genérica para uma Árvore Binária de Busca (BST).
* Garante operações básicas de manipulação, busca e percurso.
* @author Horlan
* @version 1.0
* @since 16/03
*/

public interface Tree<T extends Comparable <T>> {

    public boolean isEmpty();
    public int height();
    public BSTNode<T> search(T element);
    public T searchData(T element); 

    public void insert(T element);
    public void remove(T element);

    public BSTNode<T> maximum();
    public BSTNode<T> minimum();

    public T[] preOrder();
    public T[] Order();
    public T[] postOrder();

    public int size();
    
}