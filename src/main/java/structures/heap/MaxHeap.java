package structures.heap;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Uma implementação genérica de Max Heap.
 * 
 * <p>Nessa heap, cada nó pai é maior ou igual aos seus filhos,
 * conforme determinado pelo {@link Comparator} fornecido. A heap é implementada
 * usando um {@link ArrayList} para o armazenamento dos dados.</p>
 * 
 * <p>Utilizada na fila de prioridade, onde elementos com maior prioridade (conforme 
 * definido pelo comparador) precisam ser acessados primeiro.</p>
 * 
 * @param <T> o tipo de elementos armazenados no heap
 * 
 * @author João Victor
 * @version 1.0
 */
public class MaxHeap<T> implements Heap<T> {
    private ArrayList<T> array;
    private int size;
    private Comparator<T> comparator; // Necessário (por ser uma classe genérica) para ditar quais regras estaremos usando ao comparar os pacientes (emergência)

    public MaxHeap(int capacity, Comparator<T> comparator) {
        this.array = new ArrayList<>(capacity);
        this.size = 0;
        this.comparator = comparator;
    }

    /**
     * Retorna o elemento raiz da heap.
     * @return o elemento raiz ou null se a heap estiver vazia
     */
    @Override
    public T getRoot() {
        if (size == 0) return null;
        return array.get(0);
    }

    /**
     * Calcula e retorna a altura da heap. A altura é definida como o número de nós no 
     * caminho mais longo, isto é, da raiz até a folha que está mais distante.
     * @return a altura da heap ou -1 se a heap estiver vazia
     */
    @Override
    public int height() {
        if (size == 0) return -1; // A altura de uma árvore vazia é definida como -1.
        return (int) Math.floor(Math.log(size()) / Math.log(2));
    }

    /**
     * Calcula e retorna o número 'n' de elementos atualmente armazenados na heap.
     * @return o número de elementos na heap
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Calcula e retorna o índice do filho direito de um nó dado pelo índice 'idx'.
     * O índice do filho direito é calculado usando a fórmula: 2 * idx + 2.
     * @param idx o índice do nó pai
     * @return o índice do filho direito
     */
    @Override
    public int right(int idx) {
        return 2 * idx + 2; // Tem que ser + 2 pq o índice começa em 0. Na aula de Janderson a genta tava considerando o índice começando em 1.
    }

    /**
     * Calcula e retorna o índice do filho esquerdo de um nó dado pelo índice 'idx'.
     * O índice do filho esquerdo é calculado usando a fórmula: 2 * idx + 1.
     * @param idx o índice do nó pai
     * @return o índice do filho esquerdo
     */
    @Override
    public int left(int idx) {
        return 2 * idx + 1; // Aqui tem esse +1 pelo mesmo motivo de right ter um +2.
    }

    /**
     * Calcula e retorna o índice do nó pai de um nó dado pelo índice 'idx'.
     * O índice do nó pai é calculado usando a fórmula: (idx - 1) / 2.
     * @param idx o índice do nó filho
     * @return o índice do nó pai
     */
    public int parent(int idx) {
        return (idx - 1) / 2;
    }

    /**
     * Insere um elemento na heap.
     * @param element o elemento a ser inserido
     */
    @Override
    public void insert(T element) {
        array.add(element);
        int currentIdx = size;
        size++;

        while (currentIdx > 0 && comparator.compare(array.get(currentIdx), array.get(parent(currentIdx))) > 0) {
            swap(currentIdx, parent(currentIdx));
            currentIdx = parent(currentIdx);
        }
    }

    /**
     * Remove e retorna o elemento máximo (raiz) da heap. Após a remoção, o último elemento é movido para a raiz 
     * e a propriedade de heap é restaurada usando o método {@link #heapify(int)}.
     * @return o elemento máximo removido ou null se a heap estiver vazia.
     */
    @Override
    public T remove() {
        if (size == 0) return null;

        T removedMax = array.get(0);

        array.set(0, array.get(size - 1));
        array.remove(size - 1);
        size--;

        heapify(0);

        return removedMax;
    }

    /**
     * Método que restaura a propriedade de heap a partir de um nó dado.
     * Isso garante que a estrutura continue sendo uma Max Heap após a remoção do elemento máximo.
     * @param idx o índice do nó a partir do qual restaurar a propriedade de heap
     */
    public void heapify(int idx) {
        int largest = idx;
        int leftNode = left(idx);
        int rightNode = right(idx);

        if (leftNode < size && comparator.compare(array.get(leftNode), array.get(largest)) > 0) {
            largest = leftNode;
        }

        if (rightNode < size && comparator.compare(array.get(rightNode), array.get(largest)) > 0) {
            largest = rightNode;
        }

        if (largest != idx) {
            swap(idx, largest);
            heapify(largest);
        }
    }

    /**
     * Método auxiliar para trocar os elementos em dois índices diferentes na heap.
     * @param i o índice do primeiro elemento
     * @param j o índice do segundo elemento
     */
    private void swap(int i, int j) {
        T temp = array.get(i);
        array.set(i, array.get(j));
        array.set(j, temp);
    }
}
