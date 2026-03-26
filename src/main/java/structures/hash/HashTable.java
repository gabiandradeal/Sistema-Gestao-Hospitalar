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
     * <br>
     * <br>
     * <p><b>📈 Custo Assintótico</b></p>
     * <ul>
     * <li><b>Operação Matemática:</b> Θ(1) — Operações elementares bit a bit e módulo executadas instantaneamente</li>
     * <li><b>Custo Total Dominante:</b> Θ(1) — O tempo de execução é constante e independe do volume de dados</li>
     * </ul>
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
     <br>
     * <br>
     * <p><b>📈 Custo Assintótico</b></p>
     * <ul>
     * <li><b>Percorrer Baldes:</b> O(C) no pior caso, sendo C a capacidade (capacity) — O laço verifica balde por balde até achar um vazio</li>
     * <li><b>Melhor Caso:</b> Ω(1) — Encontra um balde vazio logo na primeira iteração (índice 0).</li>
     * <li><b>Custo Total Dominante:</b> O(C) — Escala linearmente com o tamanho do array base.</li>
     * </ul>
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
     * <br>
     * <br>
     * <p><b>📈 Custo Assintótico</b></p>
     * <ul>
     * <li><b>Percorrer Baldes:</b> O(C) no pior caso (tabela vazia), onde C é a capacidade — O laço precisa verificar todas as posições</li>
     * <li><b>Melhor Caso:</b> Ω(1) — Encontra um elemento logo na primeira iteração (índice 0) e já retorna false.</li>
     * <li><b>Custo Total Dominante:</b> O(C) — Limitado ao tamanho total do array de listas.</li>
     * </ul>
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
     <br>
     * <br>
     * <p><b>📈 Custo Assintótico</b></p>
     * <ul>
     * <li><b>Retorno de Variável:</b> Θ(1) — Acesso direto e constante</li>
     * </ul>
     * @return a capacidade da tabela.
     */
    @Override public int capacity() {
        return capacity;
    }

    /**
     * Retorna o número de elementos na tabela hash.
     * <br>
     * <br>
     * <p><b>📈 Custo Assintótico</b></p>
     * <ul>
     * <li><b>Iteração nos Baldes:</b> Θ(C) onde C é a capacidade do array.</li>
     * <li><b>Soma dos Tamanhos:</b> O(1) por balde.</li>
     * <li><b>Custo Total Dominante:</b> Θ(C) — Devido à necessidade de verificar todos os baldes da estrutura.</li>
     * </ul>
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
     * <br>
     * <br>
     * <p><b>📈 Custo Assintótico</b></p>
     * <ul>
     * <li><b>Cálculo do Hash:</b> Θ(1) — Localização instantânea do balde correspondente.</li>
     * <li><b>Busca/Remoção no Balde:</b> O(m) no pior caso, sendo m o tamanho da LinkedList interna resultante de colisões.</li>
     * <li><b>Pior Caso Absoluto:</b> O(n) — Se um desequilíbrio severo fizesse todos os n elementos caírem no mesmo balde.</li>
     * <li><b>Melhor Caso:</b> Ω(1) — Elemento a ser removido é o primeiro da fila no balde.</li>
     * <li><b>Custo Total Dominante (Amortizado):</b> O(1) — Em uma HashTable com bom fator de carga e distribuição uniforme.</li>
     * </ul>
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
     * <br>
     * <br>
     * <p><b>📈 Custo Assintótico</b></p>
     * <ul>
     * <li><b>Cálculo do Hash:</b> Θ(1) — Acesso direto ao balde do array.</li>
     * <li><b>Inserção na Lista:</b> O(1) ou O(m) dependendo da estrutura interna da LinkedList.</li>
     * <li><b>Custo Total Dominante (Amortizado):</b> O(1) — A inserção ocorre quase instantaneamente na maioria dos casos.</li>
     * </ul>
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
     * <br>
     * <br>
     * <p><b>📈 Custo Assintótico</b></p>
     * <ul>
     * <li><b>Cálculo do Hash e Acesso:</b> Θ(1).</li>
     * <li><b>Busca Linear no Balde:</b> O(m) no pior caso daquela lista específica.</li>
     * <li><b>Custo Total Dominante (Amortizado):</b> O(1) — A separação de elementos corta drasticamente o custo de busca.</li>
     * </ul>
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
     * <br>
     * <br>
     * <p><b>📈 Custo Assintótico</b></p>
     * <ul>
     * <li><b>Idêntico ao search comum:</b> O(m) no pior caso regional, ou amortizado O(1).</li>
     * </ul>
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
     * <br>
     * <br>
     * <p><b>📈 Custo Assintótico</b></p>
     * <ul>
     * <li><b>Acesso ao Balde e Busca:</b> O(m) no pior caso, onde m é o tamanho da LinkedList interna percorrida. Amortizado O(1).</li>
     * </ul>
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
     * Faz o rehashing da tabela, criando um array com capacidade expandida para o próximo numero
     * primo do dobro atual.
     * <br>
     * <br>
     * <p><b>📈 Custo Assintótico</b></p>
     * <ul>
     * <li><b>Alocação Nova e Cálculos Primos:</b> O(C_novo) + Custo de Primalidade.</li>
     * <li><b>Redistribuição:</b> Θ(n) — Percorre e reinsere todos os n elementos armazenados na tabela.</li>
     * <li><b>Custo Total Dominante:</b> Θ(n) — Apesar de ser uma operação pesada (linear), ela ocorre esporadicamente (custo amortizado diluído).</li>
     * </ul>
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
            while (currentNode != null && !currentNode.isNIL()) {
                if (currentNode.getData() != null) {
                    newHashTable.insert(currentNode.getData());
                }
                currentNode = currentNode.getNext();
            }
        }

        this.table = newHashTable.table;
        this.capacity = newHashTable.capacity();
    }

    /**
     * Verifica se um número é primo. Método auxiliar para o rehashing.
     * Garantindo que a nova capacidade seja um número primo, a distribuição dos elementos ocorre de forma que minimize colisões.
     * <br>
     * <br>
     * <p><b>📈 Custo Assintótico</b></p>
     * <ul>
     * <li><b>Busca Otimizada:</b> O(√num) — Corta a necessidade de divisão pela metade ao iterar apenas até a raiz quadrada matemática do número.</li>
     * </ul>
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
     * <br>
     * <br>
     * <p><b>📈 Custo Assintótico</b></p>
     * <ul>
     * <li><b>Busca de Gap:</b> O(g * √num) no pior caso — Onde g é a distância (gap) até o próximo primo, checando a primalidade em cada tentativa</li>
     * </ul>
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
