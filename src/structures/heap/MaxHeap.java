package structures.heap;

public class MaxHeap implements Heap<T> {
    private T[] array;
    private int size;

    public MaxHeap(int capacity) {
        this.array = (T[]) new Comparable[capacity];
        this.size = 0;
    }

    @Override
    public T getRoot() {
        return array[0];
    }

    @Override
    public int height() {
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

    @Override
    public void insert(T element) {

        if (size == array.length) {
            this.array = java.util.Arrays.copyOf(array, size * 2);
        }

        array[size] = element;
        int currentIdx = size;
        size++;

        while (currentIdx > 0 && array[currentIdx].compareTo(array[parent(currentIdx)]) > 0) {
            swap(currentIdx, parent(currentIdx));
            currentIdx = parent(currentIdx);
        }
    }

    @Override
    public T remove() {
        if (size == 0) return null;

        T removedMax = array[0];

        array[0] = array[size - 1];
        array[size - 1] = null;
        size--;

        heapify(0);

        return removedMax;
    }

    public void heapify(int idx) {
        int largest = idx;
        int leftNode = left(idx);
        int rightNode = right(idx);

        if (leftNode < size && array[leftNode].compareTo(array[largest]) > 0) {
            largest = leftNode;
        }

        if (rightNode < size && array[rightNode].compareTo(array[largest]) > 0) {
            largest = rightNode;
        }

        if (largest != idx) {
            swap(idx, largest);
            heapify(largest);
        }
    }

    @Override
    public Heap<T> buildHeap(T[] arr)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Heap<T> heapsort(Function<> map)
    {
        // TODO Auto-generated method stub
        return null;
    }
}
