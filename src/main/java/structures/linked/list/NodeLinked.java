package structures.linked.list;

/**
 * Classe genérica que representa um nó em uma linked list.
 * Utiliza a abordagem de nó sentinela (NIL) para evitar verificações de null.
 * @author Georis
 * @version 1.0
 * @since 11/03
 */
public class NodeLinked<T> implements Node {
    private final T data;
    private NodeLinked<T> next;

    /**
     * Constrói um novo objeto nó contendo o dado fornecido e inicializa o próximo nó como um sentinela (NIL).
     * @param data o dado a ser armazenado neste nó
     */
    public NodeLinked(T data) {
        this.data = data;
        this.next = new NodeLinked<>(); // ✅ Sentinela NIL, não null;
    }

    /**
     * Constrói um novo objeto representando um nó sentinela (NIL), onde os dados e o próximo nó são nulos.
     */
    public NodeLinked() {
        this.data = null;
        this.next = null;
    }


    public NodeLinked<T> getNext() {
        return next;
    }
    public void setNext(NodeLinked<T> nodeLinked){
        this.next = nodeLinked;
    }

    public T getData() {return data;}

    /**
     * Verifica se este nó atua como um sentinela (NIL), baseando-se na ausência de dados.
     * @return true se o nó for um sentinela (sem dados), false caso possua um valor válido
     * @see Node
     * @since 1.0
     */
    @Override
    public boolean isNIL(){
        return data == null;
    }


}
