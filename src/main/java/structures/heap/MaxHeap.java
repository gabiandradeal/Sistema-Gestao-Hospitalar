package structures.heap;
import java.util.ArrayList;
import java.util.Comparator;

public class MaxHeap<T> implements Heap<T> {
    private ArrayList<T> array;
    private int size;
    private Comparator<T> comparator; // Necessário (por ser uma classe genérica) para ditar quais regras estaremos usando ao comparar os pacientes (emergência)

    public MaxHeap(int capacity, Comparator<T> comparator) {
        this.array = new ArrayList<>(capacity);
        this.size = 0;
        this.comparator = comparator;
    }

    @Override
    public T getRoot() {
        if (size == 0) return null;
        return array.get(0);
    }

    @Override
    public int height() {
        if (size == 0) return -1; // A altura de uma árvore vazia é definida como -1.
        return (int) Math.floor(Math.log(size()) / Math.log(2));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int right(int idx) {
        return 2 * idx + 2; // Tem que ser + 2 pq o índice começa em 0. Na aula de Janderson a genta tava considerando o índice começando em 1.
    }

    @Override
    public int left(int idx) {
        return 2 * idx + 1; // Aqui tem esse +1 pelo mesmo motivo de right ter um +2.
    }

    public int parent(int idx) {
        return (idx - 1) / 2;
    }

    @Override
    public void insert(T element) {
        array.add(element);
        int currentIdx = size;
        size++;

        while (currentIdx > 0 && comparator.compare(array.get(currentIdx), array.get(parent(currentIdx))) > 0)) {
            swap(currentIdx, parent(currentIdx));
            currentIdx = parent(currentIdx);
        }
    }

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

    private void swap(int i, int j) {
        T temp = array.get(i);
        array.set(i, array.get(j));
        array.set(j, temp);
    }
}
