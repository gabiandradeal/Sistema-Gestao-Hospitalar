package structures.stack;

import structures.linked.list.LinkedList;

/**
 * Implementação de uma Pilha (Stack) utilizando uma Lista Encadeada (LinkedList) como estrutura base.
 * Utiliza o princípio de Composição para fornecer operações LIFO (Last-In, First-Out) com eficiência O(1).
 * @author Georis
 * @version 1.0
 * @since 13/03
 */
public class LinkedStack <T> implements Stack<T> {

    // Composition
    private final LinkedList<T> linkedList;

    /**
     * Constrói um novo objeto LinkedStack, inicializando a lista encadeada subjacente.
     */
    public LinkedStack() {
        this.linkedList = new LinkedList<>();
    }

    /**
     * Adiciona um novo elemento ao topo da pilha.
     * A operação é realizada inserindo o elemento no início da lista encadeada associada, com custo O(1).
     * @param element o elemento a ser adicionado na estrutura
     * @since 1.0
     */
    @Override
    public void push(T element){
        linkedList.addFirst(element);
    }

    /**
     * Remove e retorna o elemento que está no topo da pilha.
     * @return o elemento removido do topo da estrutura em tempo O(1)
     * @throws RuntimeException quando a pilha está vazia e não há elementos para remover
     * @since 1.0
     */
    @Override
    public T pop() {
        if (isEmpty()) {
            throw new RuntimeException("Pilha está vazia");
        }
        // Remove do topo em O(1) e retorna o valor
        return linkedList.removeFirst();
    }

    /**
     * Retorna o elemento que está no topo da pilha sem removê-lo.
     * Acessa diretamente a cabeça da lista encadeada subjacente em tempo O(1).
     * @return o elemento atualmente no topo da estrutura
     * @throws RuntimeException quando a pilha está vazia e não há elementos para consultar
     * @since 1.0
     */
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Pilha está vazia");
        }
        // Em O(1)
        return linkedList.getHead().getData();
    }

    /**
     * Verifica se a pilha está vazia.
     * @return true se a estrutura subjacente não contiver elementos, false caso contrário
     * @since 1.0
     */
    @Override
    public boolean isEmpty() {
        return linkedList.isEmpty();
    }

    /**
     * Retorna a quantidade de elementos presentes na pilha.
     * @return o tamanho atual da estrutura baseando-se na lista encadeada
     * @since 1.0
     */
    @Override
    public int size() {
        return linkedList.size();
    }
}
