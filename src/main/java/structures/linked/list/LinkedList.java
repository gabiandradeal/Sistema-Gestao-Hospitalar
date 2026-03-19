package structures.linked.list;

import java.util.NoSuchElementException;

/**
 * Implementação genérica de uma Lista Encadeada baseada em nós com sentinela (NIL).
 * Fornece operações básicas de lista e métodos auxiliares otimizados para implementação de pilhas.
 * @author Georis
 * @version 1.2
 * @since 11/03
 */
public class LinkedList<T> implements List<T> {
    private NodeLinked<T> head;

    /**
     * Constrói um novo objeto representando uma lista encadeada vazia.
     * Inicializa a cabeça (head) da estrutura com um nó sentinela (NIL).
     */
    public LinkedList() {
        this.head = new NodeLinked<>();
    }

    /**
     * Constrói um novo objeto representando uma lista encadeada contendo um elemento inicial.
     * @param firstElement o primeiro elemento a ser inserido na lista
     */
    public LinkedList(T firstElement) {
        this.head = new NodeLinked<>(firstElement);
    }

    public NodeLinked<T> getHead() {
        return head;
    }

    /**
     * Calcula e retorna a quantidade de elementos válidos presentes na lista.
     * @return o tamanho atual da lista
     * @since 1.0
     */
    @Override
    public int size() {
        NodeLinked<T> node = getHead();
        return size(node);
    }

    /**
     * Método auxiliar recursivo para calcular o tamanho da lista a partir de um nó específico.
     * @param node o nó a partir do qual a contagem será realizada
     * @return a quantidade de elementos válidos encontrados
     * @since 1.0
     */
    private int size(NodeLinked<T> node) {
        /*
         *  Obs: preciso da dupla verificação aqui por questão de segurança
         *       Isso ocorre porque ao ser chamado size([NIL]) o '0' será retornado, mas antes de retornar, chama size([NIL].getNext())
         *       se por algum motivo null escapar (bug, lista mal construída), a verificação node == null serve para caso null.isNIL() → EXPLODE 💥
         */
        if (node == null || node.isNIL()) {
            return 0;
        }
        return 1 + size(node.getNext());
    }

    /**
     * Realiza a busca de um elemento específico na lista encadeada.
     * Necessário para a implementação de resolução de colisões e busca na HashTable.
     * @param element o elemento a ser procurado na estrutura
     * @return true se o elemento for encontrado, false caso contrário
     * @since 1.2
     */
    @Override
    public boolean search(T element) {
        NodeLinked<T> current = head;

        while(!current.isNIL()) {
            if (current.getData().equals(element)) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    /**
     * Adiciona um novo elemento ao final da lista encadeada.
     * @param element o elemento a ser inserido na estrutura
     * @since 1.0
     */
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

    /**
     * Remove a primeira ocorrência do elemento especificado da lista encadeada.
     * Utiliza o método .equals() para a comparação de objetos.
     * @param element o elemento a ser removido
     * @throws NullPointerException quando a operação de remoção é chamada em uma lista vazia
     * @throws NoSuchElementException quando o elemento procurado não existe na lista
     * @since 1.0
     */
    @Override
    public void remove(T element) {
        if (isEmpty()) throw new NullPointerException("Não é possível remover nenhuma elemento, a Lista está Vazia");


        /*
         * Obs: Optamos por usar .equals() pela praticidade de estar usando o Generics<T> em que cada Class Wrapper gera um objeto
         */

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

    /**
     * Verifica se a lista encadeada está vazia.
     * @return true se a lista contiver apenas o nó sentinela (NIL), false caso contrário
     * @since 1.0
     */
    @Override
    public boolean isEmpty() {
        return head.isNIL();
    }



    // =========================================================
    // <--------- MÉTODOS PARA IMPLEMENTAÇÃO DE PILHA --------->
    // =========================================================

    /**
     * Adiciona um elemento diretamente no início da lista (cabeça).
     * Essencial para operações de empilhamento (push) com custo O(1).
     * @param element o elemento a ser inserido na primeira posição
     * @since 1.0
     */
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

    /**
     * Remove e retorna o primeiro elemento da lista (cabeça).
     * Essencial para operações de desempilhamento (pop) com custo O(1).
     * @return o elemento que foi removido do início da lista
     * @throws NullPointerException quando a operação é tentada em uma lista vazia
     * @since 1.0
     */
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
