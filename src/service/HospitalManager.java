package service;

import model.Paciente;
import model.NivelUrgencia;
import structures.interfaces.*;

/**
 * Classe responsável pela integração das estruturas de dados e gerenciamento do fluxo hospitalar.
 * Atua como o "Cérebro" do sistema, coordenando a triagem, atendimento e internação.
 */
public class HospitalManager {

    /** Prontuário Geral do hospital (Baseado em Árvore AVL para buscas estáveis). */
    private ITree<Paciente> prontuarios;

    /** Fila de Emergência (Baseada em Max-Heap para priorizar estados graves). */
    private IHeap<Paciente> filaEmergencia;

    /** Fila de Triagem Normal (Baseada em 2 Pilhas para casos leves). */
    private IQueue<Paciente> filaComum;

    /** Unidade de Internação Crítica (Baseada em Tabela Hash para acesso imediato via CPF). */
    private IHashTable<String, Paciente> uti;

    /**
     * Construtor da central de gerenciamento hospitalar.
     * @param prontuarios Instância da Árvore AVL para prontuários.
     * @param filaEmergencia Instância da Max-Heap para emergências.
     * @param filaComum Instância da Fila de 2 Pilhas para triagem comum.
     * @param uti Instância da Tabela Hash para pacientes internados.
     */
    public HospitalManager(ITree<Paciente> prontuarios, IHeap<Paciente> filaEmergencia,
                           IQueue<Paciente> filaComum, IHashTable<String, Paciente> uti) {
        this.prontuarios = prontuarios;
        this.filaEmergencia = filaEmergencia;
        this.filaComum = filaComum;
        this.uti = uti;
    }

    /**
     * Admite um paciente no sistema, registrando-o no prontuário geral e encaminhando-o
     * para a fila de espera correta baseada na triagem.
     * @param p Objeto Paciente a ser admitido.
     */
    public void admitirPaciente(Paciente p) {
        // Todo paciente é registrado ou atualizado na AVL (Banco de Dados Principal) [cite: 33, 80]
        prontuarios.insert(p);

        // Encaminhamento baseado na Triagem: Vermelho/Laranja vai para Emergência
        if (p.getUrgencia() == NivelUrgencia.VERMELHO || p.getUrgencia() == NivelUrgencia.LARANJA) {
            filaEmergencia.insert(p); // Insere na Max-Heap [cite: 42, 97]
        } else {
            filaComum.enqueue(p);    // Insere na Fila Comum (Azul/Verde)
        }
    }

    /**
     * Gerencia a chamada do próximo paciente pelo médico, respeitando a prioridade absoluta
     * da fila de emergência sobre a comum.
     * @return O próximo paciente a ser atendido.
     */
    public Paciente chamarProximo() {
        // Prioridade absoluta para quem está na Emergência (Max-Heap)
        if (!filaEmergencia.isEmpty()) {
            return filaEmergencia.extractMax();
        }
        // Se a emergência estiver vazia, retira da fila comum
        return filaComum.dequeue();
    }

    /**
     * Realiza a busca do histórico clínico de um paciente no banco de dados principal.
     * @param p Paciente (contendo o CPF) a ser buscado.
     * @return O registro completo do paciente localizado na AVL.
     */
    public Paciente consultarHistorico(Paciente p) {
        return prontuarios.search(p);
    }

    /**
     * Move um paciente para o regime de internação crítica na UTI para monitoramento constante.
     * @param p Paciente a ser internado.
     */
    public void internarPaciente(Paciente p) {
        // O CPF do paciente é a chave para o acesso instantâneo O(1) [cite: 113]
        uti.put(p.getCpf(), p);
    }

    /**
     * Finaliza o monitoramento na UTI, removendo o paciente da tabela de acesso rápido
     * após alta ou óbito.
     * @param cpf CPF do paciente a ser removido da UTI.
     */
    public void darAltaUti(String cpf) {
        uti.remove(cpf);
    }
}