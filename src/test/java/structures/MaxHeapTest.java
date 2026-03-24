package structures;

import model.NivelUrgencia;
import model.Paciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import structures.heap.MaxHeap;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe responsável por realizar testes na estrutura MaxHeap
 * @author Suelle
 * @version 1.0
 * @since 21/03/2026
 */
public class MaxHeapTest {

    private MaxHeap<Paciente> filaEmergencia;

    private Paciente pSuelle, pGabi, pHorlan, pJoao;

    /**
     * Configura o ambiente de testes antes de cada execução
     * @see MaxHeap
     * @since 1.0
     */
    @BeforeEach
    public void setUp() {
        // Obs: o Comparator.comparing pega o nível de urgência com a ordem em que está no ENUM de cima para
        // baixo (azul sendo o mais leve e vermelho o mais grave).
        filaEmergencia = new MaxHeap<>(10, Comparator.comparing(Paciente::getUrgencia));

        pSuelle = new Paciente("Suelle", "111.111.111-11", NivelUrgencia.LARANJA);
        pGabi = new Paciente("Gabi", "222.222.222-22", NivelUrgencia.VERMELHO);
        pHorlan = new Paciente("Horlan", "333.333.333-33", NivelUrgencia.LARANJA);
        pJoao = new Paciente("João", "444.444.444-44", NivelUrgencia.VERMELHO);
    }

    /**
     * Testa os métodos básicos de inserção e consulta a altura (height) e tamanho (size).
     * @see MaxHeap#insert(Object)
     * @see MaxHeap#height()
     * @see MaxHeap#size()
     * @since 1.0
     */
    @Test
    void testInsertEHeight() {
        assertEquals(-1, filaEmergencia.height());

        filaEmergencia.insert(pSuelle);
        assertEquals(0, filaEmergencia.height());
        assertEquals(1, filaEmergencia.size());

        filaEmergencia.insert(pGabi);
        filaEmergencia.insert(pHorlan);
        assertEquals(1, filaEmergencia.height());
    }

    /**
     * Testa a principal regra de negócio da nossa fila de emergência: pacientes mais graves (nível de urgência vermelho)
     * devem passar à frente de pacientes com nível de urgência mais leves.
     * @see MaxHeap#remove()
     * @see MaxHeap#insert(Object)
     * @since 1.0
     */
    @Test
    void testFuraFilaEmergencia() {
        filaEmergencia.insert(pSuelle);
        filaEmergencia.insert(pHorlan);

        filaEmergencia.insert(pGabi);
        filaEmergencia.insert(pJoao);

        Paciente primeiroAtendido = filaEmergencia.remove();
        assertEquals(NivelUrgencia.VERMELHO, primeiroAtendido.getUrgencia());

        Paciente segundoAtendido = filaEmergencia.remove();
        assertEquals(NivelUrgencia.VERMELHO, segundoAtendido.getUrgencia());

        Paciente terceiroAtendido = filaEmergencia.remove();
        assertEquals(NivelUrgencia.LARANJA, terceiroAtendido.getUrgencia());
    }

    /**
     * Testa o comportamento 'defensivo' da estrutura, garante que a remoção ou busca numa fila de
     * emergência vazia retorne null.
     * @see MaxHeap#remove()
     * @see MaxHeap#getRoot()
     * @since 1.0
     */
    @Test
    void testRemocaoEmHeapVazia() {
        assertNull(filaEmergencia.remove());
        assertNull(filaEmergencia.getRoot());
    }
}