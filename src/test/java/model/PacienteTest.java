package model;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe responsável por realizar testes na entidade Paciente
 * @author Suelle
 * @version 1.0
 * @since 20/03/2026
 */
public class PacienteTest {

    /**
     * Testa a igualdade entre dois objetos Paciente distintos que possuem o mesmo CPF
     * @see Paciente#equals(Object)
     * @see Paciente#hashCode()
     * @since 1.0
     */
    @Test
    void testIgualdadePorCpfHash() {
        Paciente p1 = new Paciente("Suelle Maciel", "123.456.789-00", NivelUrgencia.AZUL);
        Paciente p2 = new Paciente("Suelle M.", "123.456.789-00",  NivelUrgencia.AZUL);

        assertEquals(p1, p2, "Pacientes com o mesmo Cpf devem ser iguais");
        assertEquals(p1.hashCode(), p2.hashCode(), "O hash code deve ser idêntico para cpf's iguais.");

    }

    /**
     * Testa a desigualdade entre dois objetos Paciente que possuem CPF diferentes.
     * O sistema trata eles como objetos distintos.
     * @see Paciente#equals(Object)
     * @since 1.0
     */
    @Test
    void testDesigualdadePorCpfHash() {
        Paciente p1 = new Paciente("Samuel", "123.456.789-00",  NivelUrgencia.AZUL);
        Paciente p2 = new Paciente("Georis", "111.222.333-00",   NivelUrgencia.AZUL);

        assertNotEquals(p1, p2, "Pacientes com CPF's diferentes devem ser objetos diferentes.");
    }

    /**
     * Testa a igualdade entre dois objetos distintos que possuem o mesmo CPF.
     * O hashCode gerado será idêntico para os dois.
     * @see Paciente#equals(Object)
     * @see Paciente#hashCode()
     * @since 1.0
     */
    @Test
    void testIgualdadePorNomeHash() {
        Paciente p1 = new Paciente("Gabriela Almeida", "111.111.111-11",  NivelUrgencia.AZUL);
        Paciente p2 = new Paciente("Gabriela Almeida", "222.222.222-22",   NivelUrgencia.AZUL);

        assertEquals(p1.getNome(), p2.getNome(), "Srings iguais");

        assertNotEquals(p1, p2, "Pacientes têm o mesmo nome, mas são pessoas diferentes.");
    }

    /**
     * Testa a lógica de ordenação (CompareTo) que foi utilizada na AVL e BST.
     * Verifica a ordenação por ordem alfabética e mostra que o critério de 'desempate' (cpf) funciona para pacientes
     * com o mesmo nome.
     * @see Paciente#compareTo(Paciente)
     * @since 1.0
     */
    @Test
    void testComparacaoCpfAVL() {
        Paciente pAna = new Paciente("Ana", "555.555.555-55", NivelUrgencia.VERDE);
        Paciente pZelia = new Paciente("Zulema", "111.111.111-11", NivelUrgencia.VERDE);

        assertTrue(pAna.compareTo(pZelia) < 0, "Ana deve ser matematicamente 'menor' que Zelia.");
        assertTrue(pZelia.compareTo(pAna) > 0, "Zulema deve ser matematicamente 'maior' que Ana.");

        Paciente pGeoris1 = new Paciente("Georis", "111.111.111-11", NivelUrgencia.AZUL);
        Paciente pGeoris2 = new Paciente("Georis", "222.222.222-22", NivelUrgencia.LARANJA);

        assertTrue(pGeoris1.compareTo(pGeoris2) < 0, "Georis 111 deve vir antes de Georis 222 na árvore.");
        assertTrue(pGeoris2.compareTo(pGeoris1) > 0, "Georis 222 deve vir depois de Georis 111 na árvore.");

        Paciente pGeorisClone = new Paciente("Georis", "111.111.111-11", NivelUrgencia.VERMELHO);
        assertEquals(0, pGeoris1.compareTo(pGeorisClone), "Pacientes com mesmo nome e CPF devem retornar 0");

    }
}