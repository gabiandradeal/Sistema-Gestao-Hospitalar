package structures;

import model.NivelUrgencia;
import model.Paciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import structures.hash.HashTable;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe responsável por realizar testes na estrutura HashTable
 * @author Suelle
 * @version 1.0
 * @since 20/03/2026
 */
public class HashTableTest {

    private HashTable<Paciente> uti;
    private Paciente p1, p2, p3;

    /**
     * Configura o ambiente de testes antes de cada execução
     * @see HashTable
     * @since 1.0
     */
    @BeforeEach
    public void setUp() {
        uti = new HashTable<>();

        p1 = new Paciente("Georis", "111.111.111-11", NivelUrgencia.VERMELHO);
        p2 = new Paciente("Gabriela", "222.222.222-22", NivelUrgencia.LARANJA);
        p3 = new Paciente("Horlan", "333.333.333-33", NivelUrgencia.VERMELHO);
    }

    /**
     * Testa os métodos de inserção e busca na tabela hash
     * @see HashTable#insert(Object)
     * @see HashTable#search(Object)
     * @since 1.0
     */
    @Test
    void testInsertESearch() {
        assertTrue(uti.isEmpty(), "A uti deve começar vazia.");

        uti.insert(p1);
        uti.insert(p2);

        assertFalse(uti.isEmpty());
        assertEquals(2, uti.size());

        assertTrue(uti.search(p1), "Paciente 1 deve ser encontrado.");
        assertTrue(uti.search(p2), "Paciente 2 deve ser encontrado.");
        assertFalse(uti.search(p3), "Paciente 3 não foi inserido.");
    }

    /**
     * Testa o método de remoção da Hash Table para garantir que remove e retorna o elemento correto.
     * @see HashTable#remove(Object)
     * @since 1.0
     */
    @Test
    void testRemove() {
        uti.insert(p1);
        uti.insert(p2);

        Paciente removido = uti.remove(p1);

        assertEquals(p1, removido);
        assertEquals(1, uti.size(), "O tamanho diminui para 1.");
        assertFalse(uti.search(p1), "O paciente 1 saiu da uti.");

        assertNull(uti.remove(p3), "Remover um elemento inexistente retorna null.");
    }

    /**
     * Testa o comportamento da HashTable diante de colisões de índices, inserindo de forma intencional
     * mais elementos do que a capacidade original (Princípio da Casa de Pombos).
     * @see HashTable#insert(Object)
     * @see HashTable#search(Object)
     * @since 1.0
     */
    @Test
    void testColisaoEEncadeamento() {
        for (int i = 0; i < 15; i++) {
            uti.insert(new Paciente("Doente " + i, "000.000.000-" + String.format("%02d", i), NivelUrgencia.VERMELHO));
        }

        assertEquals(15, uti.size(), "Todos os 15 doentes devem ter sido contabilizados.");

        Paciente testeBusca = new Paciente("Busca", "000.000.000-07", NivelUrgencia.VERMELHO);

        assertTrue(uti.search(testeBusca), "A busca tem que encontrar o doente mesmo que ele esteja perdido no meio de uma colisão");
    }

    /**
     * Testa o rehash da tabela, verificando se a capacidade foi expandida corretamente para o próximo número primo
     * e se os dados continuaram armazenados corretamente
     * @see HashTable#rehash()
     * @see HashTable#capacity()
     * @since 1.0
     */
    @Test
    void testRehash() {
        uti.insert(p1);
        uti.insert(p2);

        assertEquals(10, uti.capacity(), "A capacidade inicial é 10");

        uti.rehash();

        assertEquals(23, uti.capacity(), "A capacidade foi atualizada para 23");
        assertEquals(2, uti.size(), "O size deve manter-se intacto após o rehash");

        assertTrue(uti.search(p1), "Paciente 1 'sobreviveu' ao rehash e foi encontrado.");
        assertTrue(uti.search(p2), "Paciente 2 'sobreviveu' ao rehash e foi encontrado.");
    }
}