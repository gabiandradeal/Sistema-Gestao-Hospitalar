package structures;

import model.NivelUrgencia;
import model.Paciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import structures.avl.AVLTree;

import static org.junit.jupiter.api.Assertions.*;

public class AVLTest {

    private AVLTree<Paciente> avl;

    private Paciente pGabriela, pGeoris, pSamuel; //já na ordem alfabetica

    @BeforeEach
    public void setUp() {
        avl = new AVLTree<>();

        pGabriela = new Paciente("Gabriela", "111.111.111-11", NivelUrgencia.AZUL);
        pGeoris = new Paciente("Georis", "222.222.222-22", NivelUrgencia.VERDE);
        pSamuel = new Paciente("Samuel", "333.333.333-33", NivelUrgencia.AMARELO);
    }

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

    @Test
    void testRotacaoDuplaDireitaEsquerdaRL() {
        avl.insert(pGabriela);
        avl.insert(pSamuel);
        avl.insert(pGeoris);

        assertEquals(1, avl.height());

        Object[] preOrder = ((AVLTree) avl).preOrder();
        assertEquals(pGeoris, preOrder[0]);
    }

    @Test
    void testRotacaoDuplaEsquerdaDireitaLR() {
        avl.insert(pSamuel);
        avl.insert(pGabriela);
        avl.insert(pGeoris);

        assertEquals(1, avl.height());

        Object[] preOrder = ((AVLTree) avl).preOrder();
        assertEquals(pGeoris, preOrder[0]);
    }

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