package structures;

import model.NivelUrgencia;
import model.Paciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import structures.queue.Quack;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe responsável por realizar testes na estrutura Quack (Queue of Stacks)
 * @author Suelle
 * @version 1.0
 * @since 20/03/2026
 */
public class QuackTest {
    private Quack<Paciente> fila;
    private Paciente p1, p2, p3;

    /**
     * Configura o ambiente de testes antes de cada execução
     * @see Quack
     * @since 1.0
     */
    @BeforeEach
    public void setUp() {
        fila = new Quack<>();

        p1 = new Paciente("Suelle", "111.222.333-00", NivelUrgencia.VERMELHO);
        p2 = new Paciente("Gabriela", "111.222.333-01", NivelUrgencia.AZUL);
        p3 = new Paciente("Georgina", "111.222.333-02", NivelUrgencia.VERDE);
    }

    /**
     * Testa o método enqueue verificando a atualização do tamanho (size) e estado (isEmpty)
     * @see Quack#enqueue(Object)
     * @see Quack#isEmpty()
     * @see Quack#size()
     * @since 1.0
     */
    @Test
    void testEnqueueESize(){
        assertTrue(fila.isEmpty());

        fila.enqueue(p1);
        assertFalse(fila.isEmpty());
        assertEquals(1, fila.size());

        fila.enqueue(p2);
        assertEquals(2, fila.size());
    }

    /**
     * Testa o método dequeue verificando a atualização do tamanho (size), estado (isEmpty) e
     * a consulta do próximo elemento (peek).
     * @see Quack#dequeue()
     * @see Quack#isEmpty()
     * @see Quack#size()
     * @see Quack#peek()
     * @since 1.0
     */
    @Test
    void testDequeueEInversao(){
        assertTrue(fila.isEmpty());
        fila.enqueue(p1);
        fila.enqueue(p2);

        assertEquals(p1, fila.peek());
        fila.dequeue();
        assertEquals(p2, fila.peek());
        assertEquals(1, fila.size());

        fila.enqueue(p3);
        assertEquals(2, fila.size());
        assertEquals(p2, fila.peek());

        fila.dequeue();
        assertEquals(p3, fila.peek());
        fila.dequeue();

        assertTrue(fila.isEmpty());
    }

    /**
     * Testa a proteção contra operações inválidas, para garantir que as exceções sejam lançadas corretamente.
     * @see Quack#dequeue()
     * @see Quack#peek()
     * @since 1.0
     */
    @Test
    void testFilaVazia(){
        Exception exceptionDequeue = assertThrows(RuntimeException.class, () -> {fila.dequeue();});
        assertEquals("Fila está vazia", exceptionDequeue.getMessage());

        Exception exceptionPeek = assertThrows(RuntimeException.class, () -> {fila.peek();});
        assertEquals("Fila está vazia", exceptionPeek.getMessage());
    }
}
