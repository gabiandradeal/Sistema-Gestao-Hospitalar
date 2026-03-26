package structures.bst;

/**
 * Classe genérida para um nó de uma Árvore Binária de Busca (BST).
 * Armazena o dado, referências para o filho à esquerda e à direita.
 * @author Horlan
 * @version 1.0
 * @since 16/03
 */

public class BSTNode<T> {

    private T data;
    private BSTNode<T> left; // Subárvore com valores menores
    private BSTNode<T> right; // Subárvore com valores maiores
    private BSTNode<T> parent; // Referência para o nó superior (pai)

    public BSTNode(){};

    public BSTNode(T data) {
        this.data = data;
    }

    public T getData(){
        return data;
    }

    public void setData(T data){
        this.data = data;
    }

    public BSTNode<T> getLeft(){
        return left;
    }

    public void setLeft(BSTNode<T> left){
        this.left = left;
    }

    public BSTNode<T> getRight(){
        return right;
    }

    public void setRight(BSTNode<T> right){
        this.right = right;
    }

    public BSTNode<T> getParent(){
        return parent;
    }

    public void setParent(BSTNode<T> parent){
        this.parent = parent;
    }

    public boolean isEmpty(){
        return data == null;
    }

    /**
     * @return A string "NILL" se o nó for uma folha sentinela,
     * ou uma representação textual do valor contido no nó
     */
    @Override
    public String toString(){
        return data == null ? "NIL": data.toString();
    }
}
