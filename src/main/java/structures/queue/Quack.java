package structures.queue;

import structures.stack.LinkedStack;

/**
 * Implementação de uma Fila (Queue) utilizando duas Pilhas (Stacks).
 * Conhecida carinhosamente como Quack, garante operações de enfileiramento e desenfileiramento com custo amortizado otimizado.
 * @author Georis
 * @version 2.0
 * @since 13/03
 */

// Quack = Queue + Stack 🦆
public class Quack<T> implements Queue<T>{
    private final LinkedStack<T> stackIn;
    private final LinkedStack<T> stackOut;

    /**
     * Constrói um novo objeto Quack inicializando as pilhas de entrada (stackIn) e saída (stackOut).
     */
    public Quack() {
        stackIn = new LinkedStack<>();
        stackOut = new LinkedStack<>();
    }

    /**
     * Adiciona um novo elemento ao final da fila.
     * O elemento é inserido na pilha de entrada com custo O(1).
     * @param element o elemento a ser adicionado na fila
     * @since 1.0
     */
    @Override
    public void enqueue(T element) {
        stackIn.push(element);
    }

    /**
     * Remove o elemento que está no início da fila.
     * Caso a pilha de saída esteja vazia, inverte os elementos da pilha de entrada para a de saída.
     * @throws RuntimeException quando a fila está vazia e não há elementos para remover
     * @since 1.0
     */
    // O custo é O(1) Amortizado
    @Override
    public void dequeue() {
        if (stackOut.isEmpty()) {
            while(!stackIn.isEmpty()) {
                stackOut.push(stackIn.pop());
            }
        }
        if(stackOut.isEmpty()) throw new RuntimeException("Fila está vazia");

        stackOut.pop();
    }

    /**
     * Retorna o elemento que está no início da fila sem removê-lo.
     * Realiza a inversão de pilhas caso a pilha de saída esteja vazia.
     * @return o primeiro elemento da fila
     * @throws RuntimeException quando a fila está vazia e não há elementos para consultar
     * @since 1.0
     */
    @Override
    public T peek() {
        if (stackOut.isEmpty()) {
            while(!stackIn.isEmpty()) {
                stackOut.push(stackIn.pop());
            }
        }
        if (stackOut.isEmpty()) throw new RuntimeException("Fila está vazia");

        return stackOut.peek();
    }

    /**
     * Retorna a quantidade de elementos presentes na fila.
     * @return o tamanho atual da fila (soma dos elementos aguardando em ambas as pilhas)
     * @since 1.0
     */
    @Override
    public int size() {
        // O tamanho da fila é a soma do que está aguardando em ambas
        return stackIn.size() + stackOut.size();
    }

    /**
     * Verifica se a fila está completamente vazia.
     * @return true se ambas as pilhas (entrada e saída) estiverem vazias, false caso contrário
     * @since 1.0
     */
    @Override
    public boolean isEmpty() {
        return stackIn.isEmpty() && stackOut.isEmpty();
    }
}
