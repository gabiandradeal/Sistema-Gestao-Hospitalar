package service;

import model.Paciente;
import model.NivelUrgencia;
import structures.avl.*;
import structures.hash.*;
import structures.heap.*;
import structures.queue.*;
// Não posso usar 'import structures.*;' por não pegar subpacotes


/**
 * Classe responsável pela integração das estruturas de dados e gerenciamento do fluxo hospitalar.
 * Atua como o "Cérebro" do sistema, coordenando a triagem, atendimento e internação.
 * @author Gabriela
 * @version 1.0
 * @since 12/03
 */
public class HospitalManager {

    /** Flag para garantir alternância entre atendimento da fila comum e da prioridade */
    boolean proximoPrioridade = true;

    /** Prontuário Geral do hospital (Baseado em Árvore AVL para buscas estáveis via CPF😷). */
    private AVLTree<Paciente> prontuarios;

    /** Fila de Emergência (Baseada em Max-Heap para priorizar estados graves). */
    private MaxHeap<Paciente> filaEmergencia;

    /** Fila de Triagem Normal (Baseada em 2 Pilhas para casos leves). */
    private Quack<Paciente> filaComum;

    /** Unidade de Internação Crítica (Baseada em Tabela Hash para acesso imediato via CPF). */
    private HashTable<String, Paciente> uti;

    /**
     * Construtor da central de gerenciamento hospitalar.
     * @param prontuarios Instância da Árvore AVL para prontuários.
     * @param filaEmergencia Instância da Max-Heap para emergências.
     * @param filaComum Instância da Fila de 2 Pilhas para triagem comum.
     * @param uti Instância da Tabela Hash para pacientes internados.
     */
    public HospitalManager(AVLTree<Paciente> prontuarios, Heap<Paciente> filaEmergencia,
                           Quack<Paciente> filaComum, HashTable<String, Paciente> uti) {
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
        // Todo paciente é registrado ou atualizado na AVL (Banco de Dados Principal)
        prontuarios.insert(p);

        // Encaminhamento baseado na Triagem: Vermelho/Laranja vai para Emergência
        if (p.getUrgencia() == NivelUrgencia.VERMELHO || p.getUrgencia() == NivelUrgencia.LARANJA) {
            filaEmergencia.insert(p); // Insere na Max-Heap
        } else {
            filaComum.enqueue(p);    // Insere na Fila Comum (Azul/Verde)
        }
    }

    /**
     * Gerencia a chamada do próximo paciente, alternando entre emergência e comum
     * para não travar o fluxo, mas garantindo o atendimento se uma das filas esvaziar.
     * @return O próximo paciente a ser atendido
     * @since 1.0
     */
    public Paciente chamarProximo() {
        // Tenta atender emergência (se for a vez dela E não estiver vazia)
        if (proximoPrioridade && !filaEmergencia.isEmpty()) {
            System.out.println("Chamando próximo paciente da fila de prioridade");
            proximoPrioridade = false; // Próximo será comum
            return filaEmergencia.extractMax();
        }

        // Se chegou aqui, ou era a vez do comum, ou a emergência estava vazia
        if (!filaComum.isEmpty()) {
            System.out.println("Chamando próximo paciente da fila geral");
            proximoPrioridade = true; // Próximo será emergência
            return filaComum.dequeue();
        }

        // Correção do Bug: A fila comum estava vazia, mas ainda tem gente na emergência!
        if (!filaEmergencia.isEmpty()) {
            System.out.println("Chamando próximo paciente da fila de prioridade");
            proximoPrioridade = true;
            return filaEmergencia.extractMax();
        }

        return null; // Hospital totalmente vazio
    }

    /**
     * Realiza a busca do histórico clínico de um paciente no banco de dados principal.
     * @param p Paciente (contendo o CPF) a ser buscado.
     * @return O registro completo do paciente localizado na AVL.
     */

    // No caso, a procura não seria por CPF?
    public Paciente consultarHistorico(Paciente p) {
        return prontuarios.search(p);
    }

    /**
     * Move um paciente para o regime de internação crítica na UTI para monitoramento constante.
     * @param p Paciente a ser internado.
     */
    public void internarPaciente(Paciente p) {
        // O CPF do paciente é a chave para o acesso instantâneo O(1)
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