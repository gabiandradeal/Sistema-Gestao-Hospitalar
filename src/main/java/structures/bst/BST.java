package structures.bst;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementação genérica de uma Árvore Binária de Busca (BST).
 * Servirá de base para a implementação de uma AVL.
 * @author Horlan
 * @version 1.0
 * @since 16/03
 */
public class BST<T extends Comparable <T>> implements Tree<T>{

    protected BSTNode<T> root;

    /**
     * Construtor padrão que inicializa a árvore com um nó sentinela vazio.
     */
    public BST(){
        root = new BSTNode<T>();
    }

    /**
     * Verifica se a árvore está vazia a partir da raiz.
     * @return true se a raiz for um nó vazio e false caso contrário.
     */
    @Override
    public boolean isEmpty(){
        return root.isEmpty();
    }

    /**
     * Calcula a altura total da árvore a partir da raiz.
     * @return A altura total da árvore, chamando o método auxiliar recursivo.
     * @since 1.0
     */
    @Override
    public int height(){
        return height(root);
    }

    /**
     * Método auxiliar que calcula a altura de um nó de forma recursiva.
     * Adota a convenção de altura -1 para nós nulos ou vazios e 0 para folhas.
     * @param node O nó a partir do qual a altura será calculada.
     * @return a altura do nó informado a partir da subárvore esquerda e direita.
     * @since 1.0
     */
    protected int height(BSTNode<T> node){
        if(node == null || node.isEmpty()) return -1;

        return 1 + Math.max(height(node.getLeft()), height(node.getRight()));
    }

    /**
     * Busca um elemento na árvore a partir da raiz.
     * @param element O valor a ser procurado.
     * @return O nó contendo o elemento ou o um nó vazio (sentinela) caso não seja encontrado.
     * @since 1.0
     */
    @Override
    public BSTNode<T> search(T element){
        return search(root, element);
    }

    /**
     * Método auxiliar recursivo para busca de um elemento.
     * @param node O nó atual da recursão.
     * @param element O valor buscado
     * @return O nó encontrado ou o nó sentinela onde a busca se encerrou.
     * @since 1.0
     */
    protected BSTNode<T> search(BSTNode<T> node, T element){

        // Se o nó for vazio (sentinela), o elemento não está na árvore.
        if(node.isEmpty()) return node;

        int compare = element.compareTo(node.getData());

        // Se o elemento for igual ao dado do nó atual, nó encontrado.
        if(compare == 0) return node;

        // Se o elemento for menor que o dado do nó atual, a busca se dará na subárvore esquerda
        if(compare < 0) return search(node.getLeft(), element);
        
        // Caso contrário (se o elemento for maior), a busca se dará na subárvore direita.
        return search(node.getRight(), element);
    }

    // NOVO MÉTODO: BUSCA RETORNANDO O DADO LIMPO
    // ==========================================
    /**
     * Busca um elemento na árvore e retorna apenas o dado (T).
     * Ideal para ser usado pelo HospitalManager, encapsulando a complexidade do BSTNode.
     */
    public T searchData(T element) {
        // Chama a busca normal que você já programou acima
        BSTNode<T> node = this.search(element);

        // Verifica se a busca retornou um nó nulo ou um nó sentinela (NILL)
        if (node == null || node.isEmpty()) {
            return null; // Não encontrou
        }

        return node.getData();
    }

    /**
     * Insere um novo elemento na árvore.
     * @param element O valor a ser inserido.
     * @since 1.0
     */
    @Override
    public void insert(T element){
        if(element == null) return;

        insert(root, element);
    }

    /**
     * Método auxiliar recursivo para a inserção de um elemento.
     * @param node O nó atual da recursão.
     * @param element O valor a ser inserido.
     * @since 1.0
     */
    protected void insert(BSTNode<T> node, T element){

        // Se o nó atual for vazio, encontra-se a posição de inserção.
        if(node.isEmpty()){
            node.setData(element);

            // Inicializa os filhos como novo nós sentinelas (vazios).
            node.setLeft(new BSTNode<T>());
            node.setRight(new BSTNode<T>());

            // Define o nó atual como pai dos novos nós sentinelas. 
            node.getLeft().setParent(node);
            node.getRight().setParent(node);
        } 
        
        else {
            int compare = element.compareTo(node.getData());

            if(compare < 0) insert(node.getLeft(), element);
            else if(compare > 0) insert(node.getRight(), element);
        }
    }

    /**
     * Remove um elemento da árvore.
     * @param element O valor a ser removido
     * @since 1.0
     */
    @Override
    public void remove(T element){
        BSTNode<T> node = search(element);
        
        if(!node.isEmpty()){
            remove(node);
        }
    }

    /**
     * Método auxiliar recursivo para a remoção física de um elemento, tratando
     * os casos de folha, com um filho ou com dois filhos (usando o predecessor).
     * @param node O nó a ser removido.
     * @since 1.0
     */
    protected void remove(BSTNode<T> node){
        
        // Verifica se o nó é folha
        if(node.getLeft().isEmpty() && node.getRight().isEmpty()){
            node.setData(null);
            node.setLeft(null);
            node.setRight(null);
        }

        // Verifica se o nó tem apenas um filho
        else if(node.getLeft().isEmpty() || node.getRight().isEmpty()){

            BSTNode<T> child;

            // Identifica qual dos lados possui o filho
            if(!node.getLeft().isEmpty()){
                child = node.getLeft();
            }
            else{
                child = node.getRight();
            }

            // Puxa os dados do filho para o nó atual (pai)
            node.setData(child.getData());
            node.setRight(child.getRight());
            node.setLeft(child.getLeft());
         }

         // Verifica se o nó tem dois filhos
         else{

            // Busca o predecessor para assumir a posição do nó a ser removido
            BSTNode<T> predecessor = maximum(node.getLeft());
            node.setData(predecessor.getData());

            // Remove recursivamente o nó que foi movido para o lugar do atual
            remove(predecessor);

         }
    }

    /**
     * Retorna o nó que contém o maior valor da árvore (extremo direito).
     * @return O nó com o valor máxino ou um NIL, caso a árvore esteja vazia.
     * @since 1.0
     */
    @Override
    public BSTNode<T> maximum(){
        return maximum(root);
    }

    /**
     * Método auxiliar que busca o nó de maior valor a partir de uma determinada subárvore.
     * @param node O nó raiz da subárvore onde a busca será iniciada.
     * @return O nó mais à direita da subárvore informada.
     * @since 1.0
     */
    protected BSTNode<T> maximum(BSTNode<T> node){
        if(node.isEmpty()) return node;

        while(!node.getRight().isEmpty()){
            node = node.getRight();
        }

        return node;
    }

    /**
     * Retorna o nó que contém o menor valor da árvore (extremo esquerdo).
     * @return O nó com o valor mínimo ou um NIL, casoa árvore esteja vazia.
     * @since 1.0
     */
    @Override
    public BSTNode<T> minimum(){
        return minimum(root);
    }

    /**
     * Método auxiliar que busca o nó de menor valor a partir de uma determinada subárvore.
     * @param node O nó raiz da subárvore onde a busca será iniciada.
     * @return o nó mais à esquerda da subárvore informada.
     * @since 1.0
     */
    protected BSTNode<T> minimum(BSTNode<T> node){
        if(node.isEmpty()) return node;

        while(!node.getLeft().isEmpty()){
            node = node.getLeft();
        }

        return node;
    }

    /**
     * Realiza o percurso em pré-ordem (pre-order) da BST.
     * @return Um array contendo os elementos da árvore em pré-ordem.
     * @since 1.0
     */
    @Override
    public T[] preOrder(){
        List<T> list = new ArrayList<>();
        preOrder(root, list);
        return (T[]) list.toArray(new Comparable[list.size()]);
    }

    /**
     * Método auxiliar recursivo que realiza o percurso em pré-ordem.
     * @param node O nó atual da árvore.
     * @param list A lista onde os elementos visitados serão armazenados.
     * @since 1.0
     */
    protected void preOrder(BSTNode<T> node, List<T> list) {

        if (!node.isEmpty()) {

            list.add(node.getData());
            preOrder(node.getLeft(), list);
            preOrder(node.getRight(), list);

        }
    }

    /**
     * Realiza o percurso em ordem (in-order) da BST.
     * @return Um array contendo os elementos da árvore em ordem crescente.
     * @since 1.0
     */
    @Override
    public T[] Order(){
        List<T> list = new ArrayList<>();
        Order(root, list);
        return (T[]) list.toArray(new Comparable[list.size()]);
    }

    /**
     * Método auxiliar recursivo que realiza o percuso em ordem.
     * @param node O nó atual da árvore.
     * @param list A lista onde os elementos visitados serão armazenados.
     * @since 1.0
     */
    protected void Order(BSTNode<T> node, List<T> list) {

        if (!node.isEmpty()) {

            Order(node.getLeft(), list);
            list.add(node.getData());
            Order(node.getRight(), list);

        }
    }

    /**
     * Realiza o percurso em pós-ordem (post-order) da BST.
     * @return Um array contendo os elemento da árvore em pós-ordem.
     * @since 1.0
     */
    @Override
    public T[] postOrder(){
        List<T> list = new ArrayList<>();
        postOrder(root, list);
        return (T[]) list.toArray(new Comparable[list.size()]);
    }

    /**
     * Mérodo auxiliar recursivo para realizar o percurso em pós-ordem.
     * @param node O nó atual da árvore.
     * @param list A lista onde os elementos visitados serão armazenados.
     * @since 1.0
     */
    protected void postOrder(BSTNode<T> node, List<T> list) {

        if (!node.isEmpty()) {

            postOrder(node.getLeft(), list);
            postOrder(node.getRight(), list);
            list.add(node.getData());

        }
    }

    /**
     * Retorna a quantidade de elementos presentes na árvore.
     * @return O número total de nós não vazios da árvore.
     * @since 1.0
     */
    @Override
    public int size(){
        return size(root);
    }

    /**
     * Método auxiliar recursivo para calcular o tamanho da árvore.
     * @param node O nó atual da árvore.
     * @return A quantidade de nós não vazios na subárvore enraizada.
     */
    protected int size(BSTNode<T> node){
        if(node.isEmpty()) return 0;

        return 1 + size(node.getLeft()) + size(node.getRight());
    }

}
