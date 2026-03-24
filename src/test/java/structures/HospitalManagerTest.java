package structures;

import model.NivelUrgencia;
import model.Paciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.HospitalManager;
import structures.avl.AVLTree;
import structures.hash.HashTable;
import structures.heap.MaxHeap;
import structures.queue.Quack;
import java.util.Comparator;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe responsável por realizar testes no HospitalManager, garante que todas as estruturas
 * trabalham em conjunto corretamente.
 * @author Suelle
 * @version 1.0
 * @since 24/03/2026
 */
public class HospitalManagerTest {
    private AVLTree<Paciente> prontuarios;
    private MaxHeap<Paciente> filaEmergencia;
    private Quack<Paciente> filaComum;
    private HashTable<Paciente> uti;
    private Paciente pVermelho, pLaranja, pAmarelo, pVerde, pAzul;
    private HospitalManager hospital;

    /**
     * Configura o ambiente de testes antes de cada execução
     * @see service.HospitalManager
     * @since 1.0
     */
    @BeforeEach
    public void setUp(){
        prontuarios = new AVLTree<>();
        filaEmergencia = new MaxHeap<>(20, Comparator.comparing(Paciente::getUrgencia));
        filaComum = new Quack<>();
        uti = new HashTable<>();
        hospital = new HospitalManager(prontuarios,filaEmergencia, filaComum, uti);

        pAzul = new Paciente("Ana Gabriela", "111.222.333-00", NivelUrgencia.AZUL);
        pVerde = new Paciente("Beatriz Maciel", "222.222.222-22", NivelUrgencia.VERDE);
        pAmarelo = new Paciente("Carlos Samuel", "333.333.333-33", NivelUrgencia.AMARELO);
        pLaranja = new Paciente("Danilo Victor", "444.444.444-44", NivelUrgencia.LARANJA);
        pVermelho = new Paciente("Eduardo Lacerda", "555.555.555-55", NivelUrgencia.VERMELHO);
    }

    /**
     * Testa se o sistema encaminha corretamente os pacientes para a fila correta com base na sua gravidade.
     * Obs: todos são salvos no prontuário (AVL)
     * @see HospitalManager#admitirPaciente(Paciente)
     * @since 1.0
     */
    @Test
    void testAdmissaoETriagem() {
        hospital.admitirPaciente(pAzul);
        hospital.admitirPaciente(pVermelho);
        hospital.admitirPaciente(pVerde);
        hospital.admitirPaciente(pLaranja);

        assertEquals(4, prontuarios.size(), "Todos os 4 pacientes ficam salvos na lista de prontuários.");
        assertEquals(2, filaEmergencia.size(), "A fila de emergência só recebe os pacientes mais graves.");
        assertEquals(2, filaComum.size(), "A fila comum recebe os pacientes mais leves");
    }

    /**
     * Testa a lógica de alternância de atendimento. O Sistema prioriza a emergência, mas não pode deixar a fila comum
     * sem atendimento. Deve alternar entre as filas.
     * @see HospitalManager#chamarProximo()
     * @since 1.0
     */
    @Test
    void testAlternanciaDePacientes(){
        hospital.admitirPaciente(pVermelho); //vai p heap
        hospital.admitirPaciente(pLaranja); //vai p heap
        hospital.admitirPaciente(pAmarelo); //vai p quack
        hospital.admitirPaciente(pVerde); //vai p quack

        assertEquals(pVermelho, hospital.chamarProximo(), "O primeiro a ser chamado deve ser da fila de emergência.");
        assertEquals(pAmarelo, hospital.chamarProximo(), "O próximo chamado deve ser um da fila comum.");
        assertEquals(pLaranja, hospital.chamarProximo(), "O próximo deve ser outro da fila de emergência.");
        assertEquals(pVerde, hospital.chamarProximo(), "O próximo chamado deve ser outro da fila comum.");

        assertNull(hospital.chamarProximo(), "Deve retornar null se não tiver ninguém para ser atendido.");
    }

    /**
     * Testa a busca na árvore AVL utilizando um paciente falso e um existente para achar os registros na árvore.
     * @see HospitalManager#consultarHistorico(String, String)
     * @since 1.0
     */
    @Test
    void testConsultarHistorico(){
        hospital.admitirPaciente(pAmarelo);

        Paciente encontrado = hospital.consultarHistorico("Carlos Samuel", "333.333.333-33");
        assertNotNull(encontrado, "O histórico deve ser encontrado para um paciente que existe");
        assertEquals(NivelUrgencia.AMARELO, encontrado.getUrgencia(), "Deve retornar os dados corretos do paciente.");

        Paciente notEncontrado = hospital.consultarHistorico("Suelle", "999.999.999-99");
        assertNull(notEncontrado, "Retorna null para pacientes que não estão no histórico.");
    }

    /**
     * Testa se a listagem de pacientes retorna um array que pode ser percorrido em ordem alfabética.
     * @see HospitalManager#listarPacientesOrdemAlfabetica()
     * @since 1.0
     */
    @Test
    void testListarPacientesEmOrdemAlfabetica(){
        hospital.admitirPaciente(pVerde);
        hospital.admitirPaciente(pAzul);
        hospital.admitirPaciente(pAmarelo);

        Object[] lista = hospital.listarPacientesOrdemAlfabetica();

        assertEquals(3, lista.length);
        assertEquals(pAzul, lista[0], "Ana deve ser a primeira");
        assertEquals(pVerde, lista[1], "Bia deve ser a segunda");
        assertEquals(pAmarelo, lista[2], "Carlos deve ser o terceiro");
    }

    /**
     * Testa o fluxo de internação (HashTable) e alta (remoção da Hash)
     * @see HospitalManager#internarPaciente(Paciente)
     * @see HospitalManager#darAltaUti(String)
     * @since 1.0
     */
    @Test
    void testInternacaoEAlta(){
        hospital.internarPaciente(pVermelho);
        assertEquals(1, uti.size(), "1 paciente na UTI.");
        assertTrue(uti.search(pVermelho));

        hospital.darAltaUti("555.555.555-55");
        assertEquals(0, uti.size(), "O único paciente que tinha na uti recebeu alta.");
        assertFalse(uti.search(pVermelho), "O paciente já saiu.");
    }

}
