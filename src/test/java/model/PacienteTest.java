package model;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PacienteTest {

    @Test
    void testIgualdadePorCpfHash() {
        Paciente p1 = new Paciente("Suelle Maciel", "123.456.789-00");
        Paciente p2 = new Paciente("Suelle M.", "123.456.789-00");

        assertEquals(p1, p2, "Pacientes com o mesmo Cpf devem ser iguais");
        assertEquals(p1.hashCode(), p2.hashCode(), "O hash code deve ser idêntico para cpf's iguais.");

    }

    @Test
    void testDesigualdadePorCpfHash() {
        Paciente p1 = new Paciente("Samuel", "123.456.789-00");
        Paciente p2 = new Paciente("Georis", "111.222.333-00");

        assertNotEquals(p1, p2, "Pacientes com CPF's diferentes devem ser objetos diferentes.");
    }

    @Test
    void testIgualdadePorNomeHash() {
        Paciente p1 = new Paciente("Gabriela Almeida", "111.111.111-11");
        Paciente p2 = new Paciente("Gabriela Almeida", "222.222.222-22");

        assertEquals(p1.getNome(), p2.getNome(), "Srings iguais");

        assertNotEquals(p1, p2, "Pacientes têm o mesmo nome, mas são pessoas diferentes.");
    }

    @Test
    void testComparacaoCpfAVL() {
        //TO-DO
    }
}