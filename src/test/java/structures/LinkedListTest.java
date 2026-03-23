package structures;

import model.NivelUrgencia;
import model.Paciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import structures.linked.list.LinkedList;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe responsável por realizar testes na estrutura LinkedList
 * @author Suelle
 * @version 1.0
 * @since 20/03/2026
 */
public class LinkedListTest {

    private LinkedList<Paciente> lista;

    private Paciente p1;
    private Paciente p2;
    private Paciente p3;


    /**
     * Configura o ambiente de testes antes de cada execução
     * instanciando novas listas para garantir que listas anteriores não interfiram.
     * @see LinkedList
     * @since 1.0
     */
    @BeforeEach
    void setup(){
        lista = new LinkedList<>();

        p1 = new Paciente("Suelle Maciel", "111.111.111-11", NivelUrgencia.AZUL);
        p2 = new Paciente("Horlan Silva", "222.222.222-22", NivelUrgencia.VERDE);
        p3 = new Paciente("Gabi Andrade", "333.333.333-33", NivelUrgencia.AMARELO);
    }

    /**
     * Testa o método de adição normal da lista, verificando se os elementos foram inseridos corretamente
     * e se o tamanho foi atualizado
     * @see LinkedList#add(Object)
     * @since 1.0
     */
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

    /**
     * Testa a inserção e remoção de elementos no começo da lista.
     * @see LinkedList#addFirst(Object)
     * @see LinkedList#removeFirst()
     * @since 1.0
     */
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

    /**
     * Testa a tentativa de remoção de um elemento inexistente na lista
     * @throws NoSuchElementException quando o elemento não foi encontrado
     * @see LinkedList#remove(Object)
     * @since 1.0
     */
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

    /**
     * Testa o método de busca na lista (testando se retorna true ou false)
     * @see LinkedList#search(Object)
     * @since 1.0
     */
    @Test
    void testSearch() {
        lista.add(p1);
        lista.add(p2);

        assertTrue(lista.search(p1), "Deve retornar true para um paciente que está na lista.");
        assertTrue(lista.search(p2), "Deve retornar true para um paciente que está na lista.");

        assertFalse(lista.search(p3), "Deve retornar false para um paciente que não foi adicionado.");
    }

    /**
     * Testa a remoção de um elemento existente.
     * @see LinkedList#remove(Object)
     * @since 1.0
     */
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

    /**
     * Testa a tentativa de remover um elemento específico quando a lista está vazia.
     * @throws NullPointerException quando não há elemento pra remover na lista
     * @see LinkedList#remove(Object)
     * @since 1.0
     */
    @Test
    void testRemoveEmListaVazia() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            lista.remove(p1);
        });

        assertEquals("Não é possível remover nenhuma elemento, a Lista está Vazia", exception.getMessage());
    }

    /**
     * Testa a tentativa de remover o primeiro elemento (head) quando a lista está vazia.
     * @throws NullPointerException quando não há o primeiro elemento pra remover na lista.
     * @see LinkedList#removeFirst()
     * @since 1.0
     */
    @Test
    void testRemoveFirstEmListaVazia() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            lista.removeFirst();
        });

        assertEquals("Não é possível remover o primeiro elemento, a Lista está Vazia", exception.getMessage());
    }
}

