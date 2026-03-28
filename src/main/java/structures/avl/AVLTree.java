package structures.avl;

import structures.bst.*;

/**
 * Implementação genérica de uma Árvore AVL.
 * Garante que, após inserções e remoções, a
 * árvore permanceça balanceada por meio de rotações.
 * @author Horlan
 * @version 1.0
 * @since 16/03
 */
public class AVLTree<T extends Comparable<T>> extends BST<T> implements AVL<T> {

    /**
     * Calcula o fator de balanceamtno, que é definido pela diferença entre a 
     * altura da subárvore esquerda e a altura da subárvore direita.
     * @param node O nó cujo fator de balanceamento será calculado.
     * @return O fator de balanceamento do nó.
     * @since 1.0
     */
    private int calculateBalanceFactor(BSTNode<T> node){

        return height(node.getLeft()) - height(node.getRight());
    }

    /**
     * Realiza uma rotação simples à esquerda (Left Rotation) em um nó.
     * 
     * Essa rotação é utilizada para corrigir o desbalanceamento do tipo 
     * "Right-Right (RR)", onde a subárvore direita é mais pesada.
     * 
     * @param node O nó onde a rotação será aplicada.
     * @since 1.0
     */
    private void leftRotation(BSTNode<T> node){

        // O pivot é o filho direito do nó desbalanceado.
        BSTNode<T> pivot = node.getRight();

        pivot.setParent(node.getParent());

        // Se o nó for a raiz, o pivot passa a ser a nova raiz.
        if(node.getParent() == null){
            this.root = pivot;
        }

        // Se o nó era o filho esquerdo, atualiza o ponteiro do pai.
        else if(node == node.getParent().getLeft()){
            node.getParent().setLeft(pivot);
        }

        // Caso contrrário, o nó era o filho à direita.
        else{
            node.getParent().setRight(pivot);
        }

        node.setRight(pivot.getLeft());

        // Se essa subárvore não for vazia, atualiza o pai dela
        if(!pivot.getLeft().isEmpty()){
            pivot.getLeft().setParent(node);
        }

        pivot.setLeft(node);
        node.setParent(pivot); // A troca só é feita aqui.
    }

    /**
     * Realiza uma rotação simples à direita (Right Rotation) em um nó.
     * 
     * Essa rotação é utilizada para corrigir o desbalanceamento do tipo
     * "Left-Left (LL), onde a subárvore esquerda é mais pesada."
     * 
     * @param node O nó onde a rotação será aplicada.
     * @since 1.0
     */
    private void rightRotation(BSTNode<T> node){

        // O pivot é o filho esquerdo do nó desbalanceado.
        BSTNode<T> pivot = node.getLeft();

        pivot.setParent(node.getParent());

        // Se o nó for a raiz, o pivô passa a ser a nova raiz
        if(node.getParent() == null){
            this.root = pivot;
        }

        // Se o nó era o filho esquerdo, atualiza o ponteiro do pai.
        else if(node == node.getParent().getLeft()){
            node.getParent().setLeft(pivot);
        }

        // Caso contrário o filho era o da direita.
        else{
            node.getParent().setRight(pivot);
        }

        node.setLeft(pivot.getRight());

        // Se essa subárvore não for vazia, atualiza o pai dela
        if(!pivot.getRight().isEmpty()){
            pivot.getRight().setParent(node);
        }

        pivot.setRight(node);
        node.setParent(pivot);
    }

    /**
     * Realiza o rebalanceamento da árvore a partir de um nó, aplicando
     * as rotações se necessário.
     * @param node O nó a partir do qual o balanceamento será verificado.
     * @since 1.0
     */
    private void rebalance(BSTNode<T> node){

        int balance = calculateBalanceFactor(node);

        // Caso a subárvore esquerda esteja mais pesada
        if(balance > 1){

            // Verifica se é caso Left-Right(LR)
            if(calculateBalanceFactor(node.getLeft()) < 0){

                // Corrige o filho(rotação à esquerda)
                leftRotation(node.getLeft());
            }
            
            // Caso LL ou após corrigir a LR
            rightRotation(node);
        }

        // Caso a subárvore direita esteja mais pesada
        else if(balance < -1){

            // Verifica se é caso de Right-left(RL)
            if(calculateBalanceFactor(node.getRight()) > 0){

                // Corrige o filho (rotação à direita)
                rightRotation(node.getRight());
            }

            // Caso RR ou após corrigir a RL
            leftRotation(node);
        }
    }

    /**
     * Insere um elemento ns AVL
     * 
     * A inserção é realizada como uma BST comum, mas depois percorre-se
     * o caminho do nó inserido até a raiz, aplicando o rebalanceamento 
     * se necessário.
     * 
     * @param element o elemento a ser inserido
     * @since 1.0
     */
    @Override
    public void insert(T element){

        super.insert(element);

        BSTNode<T> node = search(element);

        while (node != null) {
            rebalance(node);
            node = node.getParent(); // subir até o topo de pai em pai.
            
        }
    }

    /**
     * Remove um elemento da AVL.
     * 
     * A remoção é realizada como uma BST comum, mas percorre-se
     * o caminho do nó removido (ou seu ancestral) até a raiz, aplicando
     * o rebalanceamento se necessário.
     * 
     * @param element o elemento a ser removido.
     * @since 1.0
     */
    @Override
    public void remove(T element){

        BSTNode<T> node = search(element);

        super.remove(element);

        while(node != null){
            rebalance(node);
            node = node.getParent();
        }
    }




    
}
