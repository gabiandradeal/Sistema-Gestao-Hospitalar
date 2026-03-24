package structures;

import model.NivelUrgencia;
import model.Paciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import structures.stack.LinkedStack;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe responsável por realizar testes na estrutura LinkedStack
 * @author Suelle
 * @version 1.0
 * @since 20/03/2026
 */
public class LinkedStackTest {
    private LinkedStack<Paciente> pilha;

    private Paciente p1, p2, p3;

    /**
     * Configura o ambiente de testes antes de cada execução
     * @see LinkedStack
     * @since 1.0
     */
    @BeforeEach
    public void setUp() {
        pilha = new LinkedStack<>();

        p1 = new Paciente("João Victor", "111.222.333-09", NivelUrgencia.AZUL);
        p2 = new Paciente("Georis Samuel", "222.333.444-00", NivelUrgencia.VERDE);
        p3 = new Paciente("Horlan Silva", "333.444.555-00", NivelUrgencia.AMARELO);
    }

    /**
     * Testa o método push verificando a atualização do tamanho (size) e do estado (isEmpty).
     * @see LinkedStack#push(Object)
     * @see LinkedStack#isEmpty()
     * @see LinkedStack#size()
     * @since 1.0
     */
    @Test
    void testPushESize(){
        assertTrue(pilha.isEmpty());

        pilha.push(p1);
        assertFalse(pilha.isEmpty());
        assertEquals(1, pilha.size(), "O tamanho deve ser igual a 1.");

        pilha.push(p2);
        assertEquals(2, pilha.size(), "O tamanho deve ser igual a 2.");
    }

    /**
     * Testa o método pop para garantir a ordem correta de saída respeitando ao
     * princípio LIFO (Last-In-First-Out)
     * @see LinkedStack#pop()
     * @since 1.0
     */
    @Test
    void testPop(){
        pilha.push(p1);
        pilha.push(p2);
        pilha.push(p3);

        assertEquals(p3, pilha.pop(), "O último a entrar é o primeiro a sair.");
        assertEquals(2, pilha.size());

        assertEquals(p2, pilha.pop());
        assertEquals(1, pilha.size());

        assertEquals(p1, pilha.pop());
        assertTrue(pilha.isEmpty());
    }

    /**
     * Testa o método peek para garantir que o retorno seja o topo da pilha
     * @see LinkedStack#peek()
     * @since 1.0
     */
    @Test
    void testPeek(){
        pilha.push(p1);
        pilha.push(p2);

        assertEquals(p2, pilha.peek());
        assertEquals(2, pilha.size(), "O tamanho não deve mudar após um peek.");

    }

    /**
     * Testa a proteção contra operações inválidas garantindo que as exceções sejam lançadas corretamente.
     * @throws RuntimeException quando há tentativa de pop ou peek em uma pilha vazia.
     * @see LinkedStack#pop()
     * @see LinkedStack#peek()
     * @since 1.0
     */
    @Test
    void testEmPilhaVazia(){
        Exception exceptionPop = assertThrows(RuntimeException.class, () -> {pilha.pop();});
        assertEquals("Pilha está vazia", exceptionPop.getMessage());

        Exception exceptionPeek = assertThrows(RuntimeException.class, () -> {pilha.peek();});
        assertEquals("Pilha está vazia", exceptionPeek.getMessage());
    }
}
