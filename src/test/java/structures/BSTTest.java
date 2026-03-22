package structures;

import model.NivelUrgencia;
import model.Paciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import structures.bst.BST;

import static org.junit.jupiter.api.Assertions.*;

public class BSTTest {

    private BST<Paciente> bst;

    private Paciente pHorlan, pGeoris, pGabi, pSuelle, pJoao;

    @BeforeEach
    public void setUp() {
        bst = new BST<>();

        pHorlan = new Paciente("Horlan", "333.333.333-33", NivelUrgencia.VERDE);
        pGeoris = new Paciente("Georis", "111.111.111-11", NivelUrgencia.AZUL);
        pGabi = new Paciente("Gabi", "222.222.222-22", NivelUrgencia.AMARELO);
        pSuelle = new Paciente("Suelle", "555.555.555-55", NivelUrgencia.VERMELHO);
        pJoao = new Paciente("João", "444.444.444-44", NivelUrgencia.LARANJA);
    }

    @Test
    void testInsertESizeEHeight() {
        assertTrue(bst.isEmpty(), "A árvore deve começar vazia.");
        assertEquals(-1, bst.height(), "Altura de árvore vazia é -1.");

        bst.insert(pHorlan);
        assertFalse(bst.isEmpty());
        assertEquals(1, bst.size());
        assertEquals(0, bst.height(), "Altura deve ser 0 apenas com a raiz.");

        bst.insert(pGeoris);
        bst.insert(pSuelle);
        assertEquals(3, bst.size());
        assertEquals(1, bst.height(), "Altura deve ser 1 após inserir nos dois lados da raiz.");

        bst.insert(pGabi);
        assertEquals(2, bst.height(), "A altura aumentou para 2 com Gabi.");
    }

    @Test
    void testSearch() {
        bst.insert(pHorlan);
        bst.insert(pGeoris);

        assertFalse(bst.search(pHorlan).isEmpty(), "Horlan deve ser encontrado.");
        assertFalse(bst.search(pGeoris).isEmpty(), "Georis deve ser encontrado.");

        Paciente pInexistente = new Paciente("Samuel", "999.999.999-99", NivelUrgencia.AZUL);

        assertTrue(bst.search(pInexistente).isEmpty(), "Samuel não está na árvore, entao deve retornar um nó vazio (NIL)");
    }

    @Test
    void testMinimumEMaximum() {
        bst.insert(pHorlan);
        bst.insert(pGeoris);
        bst.insert(pGabi);
        bst.insert(pSuelle);
        bst.insert(pJoao);

        assertEquals(pGabi, bst.minimum().getData(), "Gabi é a primeira em ordem alfabética.");
        assertEquals(pSuelle, bst.maximum().getData(), "Suelle é a última em ordem alfabética (🙄).");
    }

    @Test
    void testOrdenacaoDoRelatorio() {
        bst.insert(pHorlan);
        bst.insert(pSuelle);
        bst.insert(pGeoris);
        bst.insert(pJoao);
        bst.insert(pGabi);

        //ps: como a árvore é genérica, nao podemos guardar o resultado num array do tipo Paciente[], o java nao deixa
        Comparable[] relatorio = bst.Order();

        assertEquals(pGabi, relatorio[0]);
        assertEquals(pGeoris, relatorio[1]);
        assertEquals(pHorlan, relatorio[2]);
        assertEquals(pJoao, relatorio[3]);
        assertEquals(pSuelle, relatorio[4]);
    }

    @Test
    void testRemove() {
        bst.insert(pHorlan);
        bst.insert(pGeoris);
        bst.insert(pGabi);
        bst.insert(pSuelle);

        bst.remove(pGabi);
        assertEquals(3, bst.size());
        assertTrue(bst.search(pGabi).isEmpty(), "Gabi deve ter sido removida.");

        bst.insert(pJoao);
        bst.remove(pSuelle);
        assertEquals(3, bst.size());
        assertFalse(bst.search(pJoao).isEmpty(), "João deve continuar na árvore (assumiu o lugar de Suelle).");
        assertTrue(bst.search(pSuelle).isEmpty());

        bst.remove(pHorlan);
        assertEquals(2, bst.size());
        assertTrue(bst.search(pHorlan).isEmpty(), "A raiz (Horlan) deve ter sido removida.");
    }
}