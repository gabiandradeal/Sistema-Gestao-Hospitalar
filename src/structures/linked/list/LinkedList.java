package structures.linked.list;

import java.util.NoSuchElementException;

public class LinkedList<T> implements List<T> {
    private NodeLinked<T> head;

    public LinkedList() {
        this.head = new NodeLinked<>();
    }
    public LinkedList(T firstElement) {
        this.head = new NodeLinked<>(firstElement);
    }

    public NodeLinked<T> getHead() {
        return head;
    }

    @Override
    public int size() {
        NodeLinked<T> node  = getHead();
        return size(node);
    }
    // Método auxiliar que garante a lógica de recursividade
    private int size(NodeLinked<T> node){
        /*
        *  Obs: preciso da dupla verificação aqui por questão de segurança
        *       Isso ocorre porque ao ser chamado size([NIL]) o '0' será retornado, mas antes de retornar, chama size([NIL].getNext())
        *       se por algum motivo null escapar (bug, lista mal construída), a verificação node == null serve para caso null.isNIL() → EXPLODE 💥
        */
        if(node == null || node.isNIL()){return 0;}
        return 1 + size(node.getNext());
    }

    @Override
    public void add(T element) {
        NodeLinked<T> newNode = new NodeLinked<>(element);

        if (isEmpty()) {
            head = newNode;
        } else {
            NodeLinked<T> current = head;
            // Percorre até o último nó para adicionar o novo no final
            while (!current.getNext().isNIL()) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }
    }

    /*
     * Obs: Optamos por usar .equals() pela praticidade de estar usando o Generics<T> em que cada Class Wrapper gera um objeto
     */
    @Override
    public void remove(T element) {
        if (isEmpty()) throw new NullPointerException("Não é possível remover nenhuma elemento, a Lista está Vazia");

        // Se o elemento a ser removido for a cabeça (head)
        if (head.getData().equals(element)) { //.equals(): Ele compara o valor/conteúdo do objeto
            head = head.getNext();
            return;
        }

        NodeLinked<T> current = head;
        // Percorre a lista checando o próximo nó
        while (!current.getNext().isNIL()) {
            T nextData = current.getNext().getData();

            // Se encontrou o dado, "pula" o nó para removê-lo
            if (nextData.equals(element)) {
                current.setNext(current.getNext().getNext());
                return; // Sai após remover a primeira ocorrência
            }
            current = current.getNext();
        }
        throw new NoSuchElementException("Elemento não encontrado");
    }

    @Override
    public boolean isEmpty() {
        return head.isNIL();
    }

    // =========================================================
    // <--------- MÉTODOS PARA IMPLEMENTAÇÃO DE PILHA --------->
    // =========================================================
    @Override
    public void addFirst(T element) {
        NodeLinked<T> newNode = new NodeLinked<>(element);
        if (isEmpty()) {
            head = newNode;
        } else {
            // O novo nó aponta para a antiga head
            newNode.setNext(head);
            // A head passa a ser o novo nó
            head = newNode;
        }
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            throw new NullPointerException("Não é possível remover o primeiro elemento, a Lista está Vazia");
        }
        // Guarda o dado para retornar
        T data = head.getData();
        // A cabeça avança para o próximo nó, removendo o primeiro
        head = head.getNext();
        return data;
    }

}
