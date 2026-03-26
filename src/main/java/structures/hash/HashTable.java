package structures.hash;
import java.util.ArrayList;
import structures.linked.list.LinkedList;
import structures.linked.list.NodeLinked;

/**
 * HashTable - Uma implementação genérica de tabela hash usando encadeamento separado com listas encadeadas.
 * 
 * <p>Essa classe implementa uma estrutura de dados chamda tabela hash, a qual utiliza um array de listas encadeadas
 * para lidar com colisões através de endereçamento fechado (chaining). Cada posição no array (balde) contém
 * uma lista encadeada que armazena todos os elementos os quais códigos hash "mapeiam" para essa posição.</p>
 * <p> Esse mapeamente é feito através de uma <i>hash function</i> que usa um intero k como chave e retorna um índice na tabela hash.</p>
 * 
 * <p><strong>Características Principais:</strong></p>
 * <ul>
 *   <li>Suporte a tipos genéricos para armazenar qualquer tipo de objeto T</li>
 *   <li>Estratégia de resolução de colisões por endereçamento fechado (chaining)</li>
 *   <li>Rehashing dinâmico para manter a eficiência da tabela à medida que cresce</li>
 * </ul>
 * 
 * <p><strong>Resumo de Complexidade de Tempo:</strong></p>
 * <ul>
 *   <li>Inserção: O(m) pior caso, O(1) amortizado (m = tamanho do balde, n = total de elementos)</li>
 *   <li>Remoção: O(m) pior caso, O(1) amortizado</li>
 *   <li>Busca: O(m) pior caso, O(1) amortizado</li>
 *   <li>Rehash: Theta(n) onde n é o número total de elementos</li>
 * </ul>
 * 
 * 
 * @param <T> o tipo de elementos armazenados na tabela hash
 * 
 * @author João Victor
 * @version 1.0
 * @see Table
 * @see LinkedList
 * @see NodeLinked
 */
public class HashTable<T> implements Table<T> {
    private ArrayList<LinkedList<T>> table;
    private int capacity;

    //#region Construtores
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
    //#endregion

    /**
     * Calcula o índice de hash para uma chave inteira usando o método da divisão.
     * 
     * <p>Esta função transforma a chave em um número positivo aplicando uma operação
     * AND bit a bit com 0x7FFFFFFF (valor máximo de um inteiro positivo), evitando
     * problemas com números negativos. Em seguida, aplica o operador módulo para
     * obter um índice válido dentro do tamanho da table.</p>
     * Custo Theta(1) -> O(1), Omega(1).
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
     * Custo assintótico: Theta(1)
     * @return {@code true} se a table estiver cheia, {@code false} caso contrário.
     */
    //
    @Override
    public boolean isFull() {
        for (LinkedList<T> bucket : table) {
            if (bucket.isEmpty()) return false; // Se tiver UM espaço vazio, ela não está cheia.
        }
        return true;
    }

    /**
     * Determina se a tabela hash está vazia.
     * <strong> Importante: esta função verifica se todos os baldes estão vazios, e não se um balde individual está vazio</strong>
     * Custo assintótico: Theta(1) -> O(1), Omega(1)
     * @return {@code true} se a tabela estiver vazia, {@code false} caso contrário.
     */
    @Override
    public boolean isEmpty() {
        for (LinkedList<T> bucket : table) {
            if (!bucket.isEmpty()) return false;
        }
        return true;
    }

    /**
     * Retorna o tamanho (ou capacidade) da tabela hash.
     * Custo assintótico: Theta(1) -> O(1), Omega(1)
     * @return a capacidade da tabela.
     */
    @Override public int capacity() {
        return capacity;
    }

    /**
     * Retorna o número de elementos na tabela hash.
     * Custo assintótico: Theta(n), onde n é o número de elementos na tabela.
     * Ressaltando: Theta significa que os limites assintóticos O e Omega se coincidem.
     * @return o número de elementos na tabela.
     */
    @Override
    public int size()
    {
        int totalSize = 0;
        for (LinkedList<T> bucket : table) {
            totalSize += bucket.size();
        }
        return totalSize;
    }

    /**
     * Remove um elemento da tabela hash.
     * Custo assintótico: O(m) onde m é o tamanho da linkedlist (chamada de balde) localizada no código hash dado pelo 
     * elemento que será removido. No pior caso, se todos os elementos caírem no mesmo balde, o custo pode chegar a O(n) 
     * onde n é o número total de elementos na tabela. O melhor caso é Omega(1), quando o primeiro elemento no balde corresponde
     * ao elemento a ser removido.
     * Na hash table, frequentemente o custo de remoção é considerado como custo amortizado O(1), já que a ideia da tabela hash
     * é justamente separar o máximo de elementos em vários baldes diferentes.
     * @param element o elemento a ser removido.
     * @return o elemento removido ou null se não encontrado.
     */
    @Override
    public T remove(T element) {
        LinkedList<T> bucket = table.get(hashFunction(element.hashCode()));
        NodeLinked<T> currentNode = bucket.getHead();

        // Percorre a lista nó por nó.
        while (currentNode != null && !currentNode.isNIL()) {
            T item = currentNode.getData();
            if (item != null && item.equals(element)) {
                bucket.remove(item);
                return item;
            }
            currentNode = currentNode.getNext();
        }
        return null; // Retorna null se não encontrar o elemento
    }

    /**
     * Insere um elemento na tabela hash.
     * Custo assintótico: O(m) onde m é o tamanho da linkedlist (chamada de balde) localizada no código hash dado pelo 
     * elemento que será inserido. No pior caso, se todos os elementos caírem no mesmo balde, o custo pode chegar a O(n) 
     * onde n é o número total de elementos na tabela. O melhor caso é Omega(1), quando o elemento é inserido no início do balde.
     * Na hash table, frequentemente o custo de inserção é considerado como custo amortizado O(1), já que a ideia da tabela hash
     * é justamente separar o máximo de elementos em vários baldes diferentes.
     * @param element o elemento a ser inserido.
     * @return void;
     */
    @Override
    public void insert(T element)
    {
        LinkedList<T> bucket = table.get(hashFunction(element.hashCode()));
        bucket.add(element);
        return;
    }

    /**
     * Busca um elemento na tabela hash.
     * Custo assintótico: O(m) onde m é o tamanho da linkedlist (chamada de balde) localizada no código hash dado pelo 
     * elemento que será buscado. No pior caso, se todos os elementos caírem no mesmo balde, o custo pode chegar a O(n) 
     * onde n é o número total de elementos na tabela. O melhor caso é Omega(1), quando o primeiro elemento no balde corresponde
     * ao elemento a ser buscado.
     * @param element o elemento a ser buscado.
     * @return true se o elemento for encontrado, false caso contrário.
     */
    @Override
    public boolean search(T element)
    {
        LinkedList<T> bucket = table.get(hashFunction(element.hashCode()));
        return bucket.search(element);
    }

    /**
     * Busca o elemento T correspondendo à chave/hashes do objeto fornecido no bucket correto.
     * Custo assintótico: O(m) onde m é o tamanho da linkedlist (chamada de balde) pelo mesmo motivo do método search(T element).
     * @see #search(Object)
     * @param element elemento cuja chave hash será usada para localizar o bucket.
     * @return o objeto encontrado (mesmo objeto da tabela) ou null se não encontrado.
     */
    public T searchByKey(T element) {
        if (element == null) return null;

        LinkedList<T> bucket = table.get(hashFunction(element.hashCode()));
        NodeLinked<T> currentNode = bucket.getHead();

        while (currentNode != null && !currentNode.isNIL()) {
            T data = currentNode.getData();
            if (data != null && data.equals(element)) {
                return data;
            }
            currentNode = currentNode.getNext();
        }
        return null;
    }

    /**
     * Pega o índice do elemento na tabela hash, ou -1 se não encontrado. Funciona de forma semelhante ao search(T element), 
     * mas retorna o índice do elemento dentro do balde (linked list) em vez de um booleano.
     * Custo assintótico: O(m) onde m é o tamanho da linkedlist (chamada de balde) pelo mesmo motivo do método search(T element).
     * @see #search(Object)
     * @param element o elemento a ser buscado.
     * @return o índice do elemento na tabela hash, -1 se não encontrado.
     */
    @Override
    public int indexOf(T element) {
        LinkedList<T> bucket = table.get(hashFunction(element.hashCode()));
        NodeLinked<T> currentNode = bucket.getHead();

        int idx = 0;
        // Percorre a lista de forma segura (verificação null + sentinel NIL)
        while (currentNode != null && !currentNode.isNIL()) {
            T item = currentNode.getData();
            if (item != null && item.equals(element)) {
                return idx;
            }
            currentNode = currentNode.getNext();
            idx++;
        }
        return -1; // Retorna -1 se não achar
    }

    /** 
     * Faz o rehashing da tabela, recriando 
     * 
    */
    @Override
    public void rehash() {
        // Cria a nova table que vai ter os antigos "pombos".
        // Multiplica por 2 porque se não pode aconter de, por exemplo, fazer o todo rehash, e só pra aumentar o tamanho de 11 pra 13.
        HashTable<T> newHashTable = new HashTable<T>(nextPrime(capacity * 2));

        for (LinkedList<T> bucket : table) {
            NodeLinked<T> currentNode = bucket.getHead();

            // Percorre a lista de forma segura transferindo os elementos
            while (currentNode != null) {
                newHashTable.insert(currentNode.getData());
                currentNode = currentNode.getNext();
            }
        }

        this.table = newHashTable.table;
        this.capacity = newHashTable.capacity();
    }

    /**
     * Verifica se um número é primo. Método auxiliar para o rehashing.
     * Garantindo que a nova capacidade seja um número primo, a distribuição dos elementos ocorre de forma que minimize colisões.
     * @param num o numéro que será checado se é primo ou não.
     * @return true se o número for primo, false caso contrário.
     */
    private boolean isPrime(int num) {
        if (num <= 1) return false;
        if (num <= 3) return true;
        if (num % 2 == 0 || num % 3 == 0) return false;

        // Loop otimizado para checar divisores até a raiz quadrada do número.
        for (int i = 5; i * i <= num; i += 6) {
            if (num % i == 0 || num % (i + 2) == 0) return false;
        }
        return true;
    }

    /**
     * Encontra o menor número primo após o valor "current".
     * <strong> Importante: Retorna Interger.MAX_VALUE se o próximo primo ultrapassar o limite máximo de int, para evitar overflow.</strong>
     * Método auxiliar para o rehashing.
     * @param current o valor inicial.
     * @return o próximo número primo.
     */
    private int nextPrime(int current) {
        if (current < 2) {
            return 2;
        }
        // Esse ternário cehca se é par. Se sim, já pula pro próximo ímpar para agilizar
        int next = (current % 2 == 0) ? current + 1 : current;

        while (!isPrime(next)) {
            next += 2; // Testa apenas os ímpares

            if (current >= Integer.MAX_VALUE - 2) return current; // Evita overflow, retorna o valor atual se ultrapassar o limite máximo de int
        }
        return next;
    }
}
