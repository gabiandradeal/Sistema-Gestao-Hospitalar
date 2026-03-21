package structures;

import model.NivelUrgencia;
import model.Paciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import structures.stack.LinkedStack;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedStackTest {
    private LinkedStack<Paciente> pilha;

    private Paciente p1, p2, p3;

    @BeforeEach
    public void setUp() {
        pilha = new LinkedStack<>();

        p1 = new Paciente("João Victor", "111.222.333-09", NivelUrgencia.AZUL);
        p2 = new Paciente("Georis Samuel", "222.333.444-00", NivelUrgencia.VERDE);
        p3 = new Paciente("Horlan Silva", "333.444.555-00", NivelUrgencia.AMARELO);
    }

    @Test
    void testPushESize(){
        assertTrue(pilha.isEmpty());

        pilha.push(p1);
        assertFalse(pilha.isEmpty());
        assertEquals(1, pilha.size(), "O tamanho deve ser igual a 1.");

        pilha.push(p2);
        assertEquals(2, pilha.size(), "O tamanho deve ser igual a 2.");
    }

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
    @Test
    void testPeek(){
        pilha.push(p1);
        pilha.push(p2);

        assertEquals(p2, pilha.peek());
        assertEquals(2, pilha.size(), "O tamanho não deve mudar após um peek.");

    }

    @Test
    void testEmPilhaVazia(){
        Exception exceptionPop = assertThrows(RuntimeException.class, () -> {pilha.pop();});
        assertEquals("Pilha está vazia", exceptionPop.getMessage());

        Exception exceptionPeek = assertThrows(RuntimeException.class, () -> {pilha.peek();});
        assertEquals("Pilha está vazia", exceptionPeek.getMessage());
    }
}
