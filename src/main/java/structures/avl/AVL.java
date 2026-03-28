package structures.avl;
import structures.bst.Tree;

/**
 * Estrutura genérica para uma AVL.
 * É um Árvore Binária de Busca (BST) auto-balanceada que garante
 * operações eficientes de busca, inserção e remoção com complexidade
 * O(log n).
 * @author Horlan
 * @version 1.0
 * @since 16/03
 */
public interface AVL<T extends Comparable <T>> extends Tree<T>{
}
