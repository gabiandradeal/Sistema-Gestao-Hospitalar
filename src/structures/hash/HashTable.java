package structures.hash;
import java.util.ArrayList;
import structures.linked.list.LinkedList;
import structures.linked.list.NodeLinked;

/**
 * HashTable é uma estrutura de dados semelhante ao array. Nesse sentido, 
 * 
 * @author João Victor
 * @version 1.0
 * @since 17/03/2026
 */
public class HashTable<T> implements Table<T> {
    private ArrayList<LinkedList<T>> table;
    private int capacity;

    public HashTable(int capacity) {
        this.capacity = capacity;
        this.table = new ArrayList<LinkedList<T>>(capacity);
        for (int i = 0; i < capacity; i++) {
            this.table.add(new LinkedList<T>());
        }
    }

    public HashTable() {
        this.capacity = 10;
        this.table = new ArrayList<LinkedList<T>>(capacity);
        for (int i = 0; i < capacity; i++) {
            this.table.add(new LinkedList<T>());
        }
    }

    /**
     * Calcula o índice de hash para uma chave inteira usando o método da divisão.
     * 
     * <p>Esta função transforma a chave em um número positivo aplicando uma operação
     * AND bit a bit com 0x7FFFFFFF (valor máximo de um inteiro positivo), evitando
     * problemas com números negativos. Em seguida, aplica o operador módulo para
     * obter um índice válido dentro do tamanho da table.</p>
     * 
     * @param k a chave inteira a ser transformada em índice de hash
     * @return o índice de hash calculado, garantidamente entre 0 e {@code capacity - 1}
     */
    private int hashFunction(int k) {
        return (k & 0x7FFFFFFF) % capacity;
    }
    /**
     * Determina se a table de hash está cheia.
     * Essa função considera que a table está cheia quando há pelo menos um elemento em cada "balde" da table.
     * <strong>Esta função não depende do número total de elementos na table, mas sim da distribuição dos elementos entre os baldes.</strong>
     * @return {@code true} se a table estiver cheia, {@code false} caso contrário.
     */
    //
    @Override
    public boolean isFull() {
        for (LinkedList<T> bucket : table) {
            if (!bucket.isEmpty()) return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        for (LinkedList<T> bucket : table) {
            if (!bucket.isEmpty()) return false;
        }
        return true;
    }

    @Override public int capacity() {
        return capacity;
    }

    @Override
    public int size()
    {
        int totalSize = 0;
        for (LinkedList<T> bucket : table) {
            totalSize += bucket.size();
        }
        return totalSize;
    }

    @Override
    public T remove(T element)
    {
        LinkedList<T> bucket = table.get(hashFunction(element.hashCode()));
        int bucketSize = bucket.size();
        NodeLinked<T> currentNode = bucket.getHead();
        T item = currentNode.getData();

        for (int i = 0; i < bucketSize; i++)
        {
            if (item.equals(element)) {
                bucket.remove(item);
                return item;
            }
            currentNode = currentNode.getNext();
            item = currentNode.getData();
        }
        return null;
    }

    @Override
    public void insert(T element)
    {
        LinkedList<T> bucket = table.get(hashFunction(element.hashCode()));
        bucket.add(element);
        return;
    }

    //Utiliza o search da LinkedList.
    @Override
    public boolean search(T element)
    {
        LinkedList<T> bucket = table.get(hashFunction(element.hashCode()));
        return bucket.search(element);
    }

    
    @Override
    public int indexOf(T element)
    {
        LinkedList<T> bucket = table.get(hashFunction(element.hashCode()));
        int bucketSize = bucket.size();
        NodeLinked<T> currentNode = bucket.getHead();
        T item = currentNode.getData();

        for (int i = 0; i <bucketSize; i++)
        {
            if (item.equals(element)) return hashFunction(element.hashCode());
        }
        return -1;
    }

    @Override
    public void rehash() {
        // Cria a nova table que vai ter os antigos "pombos".
        // Multiplica por 2 pq se n vai que tu faz o rehash todinho só pra aumentar o tamanaho de 11 pra 13. Num compensa.
        HashTable<T> newHashTable = new HashTable<T>(nextPrime(capacity * 2));

        for (LinkedList<T> bucket : table)
        {
            int bucketSize = bucket.size();
            NodeLinked<T> currentNode = bucket.getHead();
            T item = currentNode.getData();
        
            for (int i = 0; i < bucketSize; i++)
            {
                newHashTable.insert(item);
                currentNode = currentNode.getNext();
                item = currentNode.getData();
            }
        }

        this.table = newHashTable.table;
        this.capacity = newHashTable.capacity();
        return;
    }

    // Verifica se um número é primo
    // Método auxiliar para o rehashing
    private boolean isPrime(int num) {
        if (num <= 1) return false;
        if (num <= 3) return true;
        if (num % 2 == 0 || num % 3 == 0) return false;

        // Loop otimizado para checar divisores até a raiz quadrada do número
        for (int i = 5; i * i <= num; i += 6) {
            if (num % i == 0 || num % (i + 2) == 0) return false;
        }
        return true;
    }

    // Encontra o próximo número primo a partir de um valor inicial
    // Método auxiliar para o rehashing
    private int nextPrime(int current) {
        // Se for par, já pula pro próximo ímpar para agilizar
        int next = (current % 2 == 0) ? current + 1 : current; 
        
        while (!isPrime(next)) {
            next += 2; // Testa apenas os ímpares
        }
        return next;
    }
}
