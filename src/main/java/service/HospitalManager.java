package service;

import model.Paciente;
import model.NivelUrgencia;
import structures.avl.*;
import structures.bst.BSTNode;
import structures.hash.*;
import structures.heap.*;
import structures.linked.list.NodeLinked;
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
    private HashTable<Paciente> uti;

    /**
     * Construtor da central de gerenciamento hospitalar.
     * @param prontuarios Instância da Árvore AVL para prontuários.
     * @param filaEmergencia Instância da Max-Heap para emergências.
     * @param filaComum Instância da Fila de 2 Pilhas para triagem comum.
     * @param uti Instância da Tabela Hash para pacientes internados.
     */
    public HospitalManager(AVLTree<Paciente> prontuarios, MaxHeap<Paciente> filaEmergencia,
                           Quack<Paciente> filaComum, HashTable<Paciente> uti) {
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
        if (proximoPrioridade && filaEmergencia.size() > 0) {
            System.out.println("Chamando próximo paciente da fila de prioridade");
            proximoPrioridade = false; // Próximo será comum
            return filaEmergencia.remove();
        }

        // Se chegou aqui, ou era a vez do comum, ou a emergência estava vazia
        if (!filaComum.isEmpty()) {
            System.out.println("Chamando próximo paciente da fila geral");
            proximoPrioridade = true; // Próximo será emergência
            return filaComum.dequeue();
        }

        // Correção do Bug: A fila comum estava vazia, mas ainda tem gente na emergência!
        if (filaEmergencia.size() > 0) {
            System.out.println("Chamando próximo paciente da fila de prioridade");
            proximoPrioridade = true;
            filaEmergencia.remove();
        }

        return null; // Hospital totalmente vazio
    }

    /**
     * Realiza a busca do histórico clínico de um paciente no banco de dados principal.
     * @param nome Nome do paciente.
     * @param cpf CPF do paciente.
     * @return O registro completo do paciente localizado na AVL.
     */

    /*
    o compareTo da classe Paciente organiza as pessoas na árvore pelo NOME primeiro, e só usa o CPF para desempatar nomes iguais.
    Por causa disso, é impossível buscar um paciente na AVL usando APENAS o CPF.
    A árvore vai se perder. Para buscar na AVL, você precisa passar um paciente "falso" que tenha o mesmo Nome e o mesmo CPF da pessoa que você quer achar.
     */
    public Paciente consultarHistorico(String nome, String cpf) {
        // Cria um paciente de mentira só para a árvore conseguir descer nos nós comparando nome/cpf
        Paciente pacienteBusca = new Paciente(nome, cpf, NivelUrgencia.AZUL);
        return prontuarios.searchData(pacienteBusca);
    }


    /**
     * Move um paciente para o regime de internação crítica na UTI para monitoramento constante.
     * @param p Paciente a ser internado.
     */
    public void internarPaciente(Paciente p) {
        // Insere o paciente diretamente. A HashTable usará o p.hashCode() que já calcula via CPF!
        uti.insert(p);
    }

    /**
     * Finaliza o monitoramento na UTI, removendo o paciente da tabela de acesso rápido
     * após alta ou óbito.
     * @param cpf CPF do paciente a ser removido da UTI.
     */
    public void darAltaUti(String cpf) {
        // Como o método remove agora exige um objeto do tipo T (Paciente),
        // criamos um paciente falso contendo o CPF para a Hash conseguir calcular o índice e comparar.
        Paciente pacienteDummy = new Paciente("", cpf, NivelUrgencia.AZUL);
        uti.remove(pacienteDummy);
    }

}