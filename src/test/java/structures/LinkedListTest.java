package structures;

import model.NivelUrgencia;
import model.Paciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import structures.linked.list.LinkedList;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedListTest {

    private LinkedList<Paciente> lista;

    private Paciente p1;
    private Paciente p2;
    private Paciente p3;


    @BeforeEach
    void setup(){
        lista = new LinkedList<>();

        p1 = new Paciente("Suelle Maciel", "111.111.111-11", NivelUrgencia.AZUL);
        p2 = new Paciente("Horlan Silva", "222.222.222-22", NivelUrgencia.VERDE);
        p3 = new Paciente("Gabi Andrade", "333.333.333-33", NivelUrgencia.AMARELO);
    }

    @Test
    void testAdd(){
        assertTrue(lista.isEmpty(), "Uma lista recém-criada deve ser vazia.");
        assertEquals(0, lista.size(), "O tamanho inicial é igual a 0");

        lista.add(p1);
        assertFalse(lista.isEmpty(), "A lista não deve ser vazia com um item.");
        assertEquals(1, lista.size(), "O tamanho da lista deve ser igual a 1");

        lista.add(p2);
        assertEquals(2, lista.size(), "O tamanho da lista deve ser igual a 2");
    }

    @Test
    void testAddFirstRemoveFirst(){
        lista.addFirst(p1);
        lista.addFirst(p2);
        lista.addFirst(p3);

        assertEquals(3, lista.size());

        assertEquals(p3, lista.removeFirst(), "O primeiro a sair deve ser o p3");
        assertEquals(2, lista.size());

        assertEquals(p2, lista.removeFirst(), "O primeiro a sair deve ser o p2");
        assertEquals(1, lista.size());

        assertEquals(p1, lista.removeFirst(), "O primeiro a sair deve ser o p1");
        assertEquals(0, lista.size());

        assertTrue(lista.isEmpty());
    }

    @Test
    void testRemoveElementoInexistente(){
        lista.add(p1);
        lista.add(p2);

        Paciente pInexistente = new Paciente("Samuel", "999.999.999-99", NivelUrgencia.VERMELHO);

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            lista.remove(pInexistente);
        });

        assertEquals("Elemento não encontrado", exception.getMessage());
        assertEquals(2, lista.size(), "O tamanho da lista não deve mudar após uma tentativa de remoção falha.");
    }

    @Test
    void testSearch() {
        lista.add(p1);
        lista.add(p2);

        assertTrue(lista.search(p1), "Deve retornar true para um paciente que está na lista.");
        assertTrue(lista.search(p2), "Deve retornar true para um paciente que está na lista.");

        assertFalse(lista.search(p3), "Deve retornar false para um paciente que não foi adicionado.");
    }

    @Test
    void testRemoveElementoExistente() {
        lista.add(p1);
        lista.add(p2);
        lista.add(p3);
        assertEquals(3, lista.size());

        lista.remove(p2);
        assertEquals(2, lista.size(), "O tamanho deve diminuir para 2.");
        assertFalse(lista.search(p2), "O paciente p2 não deve mais ser encontrado na busca.");

        lista.remove(p1);
        assertEquals(1, lista.size(), "O tamanho deve diminuir para 1.");
        assertFalse(lista.search(p1), "O paciente p1 não deve mais ser encontrado.");

        assertTrue(lista.search(p3));
    }

    @Test
    void testRemoveEmListaVazia() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            lista.remove(p1);
        });

        assertEquals("Não é possível remover nenhuma elemento, a Lista está Vazia", exception.getMessage());
    }

    @Test
    void testRemoveFirstEmListaVazia() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            lista.removeFirst();
        });

        assertEquals("Não é possível remover o primeiro elemento, a Lista está Vazia", exception.getMessage());
    }
}

