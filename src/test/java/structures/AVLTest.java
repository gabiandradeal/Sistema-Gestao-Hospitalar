package structures;

import model.NivelUrgencia;
import model.Paciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import structures.avl.AVLTree;
import structures.bst.BSTNode;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe responsável por realizar testes na estrutura AVL
 * @author Suelle
 * @version 1.0
 * @since 21/03/2026
 */
public class AVLTest {

    private AVLTree<Paciente> avl;

    private Paciente pGabriela, pGeoris, pSamuel;

    /**
     * Configura o ambiente de testes antes de cada execução. Os nomes dos pacientes foram escolhidos estrategicamente
     * para forçar diferentes cenários de rotação (Gabriela < Georis < Samuel)
     * @see AVLTree
     * @since 1.0
     */
    @BeforeEach
    public void setUp() {
        avl = new AVLTree<>();

        pGabriela = new Paciente("Gabriela", "111.111.111-11", NivelUrgencia.AZUL);
        pGeoris = new Paciente("Georis", "222.222.222-22", NivelUrgencia.VERDE);
        pSamuel = new Paciente("Samuel", "333.333.333-33", NivelUrgencia.AMARELO);
    }

    /**
     * Testa o desbalanceamento à direita-direita gerada pela inserção de elementos
     * em ordem alfabética.
     * @see AVLTree#insert(Comparable)
     * @since 1.0
     */
    @Test
    void testRotacaoSimplesEsquerdaRR() {
        avl.insert(pGabriela);
        avl.insert(pGeoris);
        avl.insert(pSamuel);

        assertEquals(1, avl.height());

        Object[] preOrder = ((AVLTree) avl).preOrder();
        assertEquals(pGeoris, preOrder[0]);
        assertEquals(pGabriela, preOrder[1]);
        assertEquals(pSamuel, preOrder[2]);
    }

    /**
     * Testa o desbalanceamento à esquerda-esquerda gerado pela inserção de elementos
     * em ordem alfabética.
     * @see AVLTree#insert(Comparable) 
     * @since 1.0
     */
    @Test
    void testRotacaoSimplesDireitaLL() {
        avl.insert(pSamuel);
        avl.insert(pGeoris);
        avl.insert(pGabriela);

        assertEquals(1, avl.height());

        Object[] preOrder = ((AVLTree) avl).preOrder();
        assertEquals(pGeoris, preOrder[0]);
        assertEquals(pGabriela, preOrder[1]);
        assertEquals(pSamuel, preOrder[2]);
    }

    /**
     * Testa o desbalanceamento à direita-esquerda gerado pela inserção de elementos
     * em ordem alfabética, criando o formato 'zigue-zague' ou 'joelho'.
     * @since 1.0
     */
    @Test
    void testRotacaoDuplaDireitaEsquerdaRL() {
        avl.insert(pGabriela);
        avl.insert(pSamuel);
        avl.insert(pGeoris);

        assertEquals(1, avl.height());

        Object[] preOrder = ((AVLTree) avl).preOrder();
        assertEquals(pGeoris, preOrder[0]);
    }

    /**
     * Testa o desbalanceamento à esquerda-direita. Verifica se a árvore realiza corretamente
     * a rotação dupla 
     * @since 1.0
     */
    @Test
    void testRotacaoDuplaEsquerdaDireitaLR() {
        avl.insert(pSamuel);
        avl.insert(pGabriela);
        avl.insert(pGeoris);

        assertEquals(1, avl.height());

        Object[] preOrder = ((AVLTree) avl).preOrder();
        assertEquals(pGeoris, preOrder[0]);
    }

    /**
     * Testa o algoritmo de rebalanceamento após a remoção de múltiplos nós e garante que após
     * apagar um elemento, a árvore sempre recalcule os fatores de balanceamento para manter as 
     * propriedades de uma AVL
     * @see AVLTree#remove(Comparable)
     * @since 1.0
     */
    @Test
    void testRemocaoERebalanceamento() {
        avl.insert(pGabriela);
        avl.insert(pGeoris);
        avl.insert(pSamuel);

        avl.remove(pGabriela);
        avl.remove(pSamuel);

        assertEquals(1, avl.size());
        assertEquals(0, avl.height());

        Object[] preOrder = ((AVLTree) avl).preOrder();
        assertEquals(pGeoris, preOrder[0]);
    }
}