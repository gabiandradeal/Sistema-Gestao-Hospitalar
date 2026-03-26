package service;

import model.Paciente;
import model.NivelUrgencia;
import structures.avl.*;
import structures.bst.BSTNode;
import structures.hash.*;
import structures.heap.*;
import structures.queue.*;

import java.util.List;
import java.util.Objects;
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
     * <br>
     * <br>
     * <p><b>📈 Custo Assintótico</b></p>
     * <ul>
         *  <li><b>Prontuário (Árvore AVL):</b> O(log n) — Inserção ou balanceamento do registro no banco principal.</li>
         *  <li><b>Fila de Emergência (Max-Heap):</b> O(log m) sendo m a fração de pacientes graves que estão na Max-Heap de Emergência — Inserção e reordenação (shift up) caso o paciente seja Vermelho ou Laranja.</li>
         *  <li><b>Fila Comum (Quack):</b> Θ(1) — Apenas um {@code push} na Pilha 1 de entrada (constante), caso a urgência seja menor.</li>
         *  <li><b>Custo Total Dominante:</b> O(log n) — A operação mais custosa (AVL) define o tempo assintótico geral.</li>
     * </ul>
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
     * <br>
     * <br>
     * <p><b>📈 Custo Assintótico</b></p>
     * <ul>
         * <li><b>Fila de Emergência (Max-Heap):</b> O(log m) sendo m a quantidade de pacientes graves aguardando — Remoção da raiz (paciente mais grave) e rebaixamento do último nó (shift down) para reordenar a heap.</li>
         * <li><b>Fila Comum (Quack):</b> Amortizado O(1) / Pior caso O(k) sendo k a quantidade de pacientes leves aguardando — A remoção ocorre em tempo constante na maioria das vezes, mas exige O(k) caso a pilha de saída esteja vazia e precise inverter todos os elementos da pilha de entrada.</li>
         *
         * <li><b>Custo Total Dominante:</b> Variável — O(log m) se a chamada for da prioridade, ou Amortizado O(1) se a chamada for da fila comum.</li>
     * </ul>
     * * @return O próximo paciente a ser atendido, ou null caso o hospital esteja vazio.
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

        // Correção do Bug: A fila comum estava vazia, mas ainda tem gente na emergência
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
    O compareTo da classe Paciente organiza as pessoas na árvore pelo NOME primeiro, e só usa o CPF para desempatar nomes iguais.
    Por causa disso, é impossível buscar um paciente na AVL usando APENAS o CPF.
    A árvore vai se perder. Para buscar na AVL, você precisa passar um paciente "falso" que tenha o mesmo Nome e o mesmo CPF da pessoa que você quer achar.
     */
    public Paciente consultarHistorico(String nome, String cpf) {
        // Cria um paciente de mentira só para a árvore conseguir descer nos nós comparando nome/cpf
        Paciente pacienteBusca = new Paciente(nome, cpf, NivelUrgencia.AZUL);
        return prontuarios.searchData(pacienteBusca);
    }

    /**
     * Retorna um array com todos os pacientes registrados no prontuários ordenados alfabeticamente pelo nome.
     * @return Array genérico (Object[]) contendo os pacientes.
     */
    public Object[] listarPacientesOrdemAlfabetica() {

        /*
         * ⚠️ SOLUÇÃO PARA O PROBLEMA DE TYPE ERASURE (ClassCastException)
         * * Por limitação do Java, a BST não consegue fazer `new T[]`. Ela cria um
         * `new Comparable[]` e o disfarça de `T[]`. Se chamarmos `prontuarios.Order()`
         * diretamente, o compilador tentará converter o array inteiro para `Paciente[]`
         * de forma invisível, o que causa um ClassCastException fatal na execução.
         * * O Truque (Raw Type):
         * Ao atribuir a árvore a uma variável "bruta" (sem a tipagem <Paciente>),
         * nós desativamos esse cast invisível e destrutivo do compilador. Assim,
         * recebemos a caixa "crua" e a retornamos como Object[] em segurança.
         * O cast para Paciente será feito elemento por elemento com segurança no Main.
         */
        AVLTree arvoreBruta = prontuarios;

        return (Object[]) arvoreBruta.Order();
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

    /**
     * Gera um relatório contendo o número atual de pacientes
     * distribuídos nas diferentes estruturas de dados do hospital (Quantidade de pacientes cadastrados, quantidade de pacientes na fila comum, fila de emergência e UTI)
     * <br>
     * <br>
     * <p><b>📈 Custo Assintótico</b></p>
     *
     * <ul>
         * <li><b>Fila de Emergência (Max-Heap):</b> O(1) — A estrutura mantém uma variável interna (size) atualizada a cada inserção/remoção, permitindo retorno imediato.</li>
         * <li><b>Fila Comum (Quack):</b> O(k) sendo k os pacientes aguardando — O método percorre recursivamente todos os nós presentes nas listas encadeadas das 2 pilhas.</li>
         * <li><b>UTI (Tabela Hash):</b> O(B + u) sendo B o tamanho do array (buckets) e u os pacientes na UTI — É necessário iterar sobre a tabela inteira e percorrer recursivamente cada Linked List.</li>
         * <li><b>Prontuários (Árvore AVL):</b> O(n) sendo n o total de pacientes já registrados — O cálculo é feito de forma recursiva, visitando cada nó da árvore individualmente para somar 1.</li>
         *
         * <li><b>Custo Total Dominante:</b> O(n) — Como a AVL contém todos os pacientes já registrados (maior volume de dados), a varredura completa dessa estrutura impõe o tempo de execução linear para a geração do relatório.</li>
     * </ul>
     * @return String formatada com os dados consolidados.
     */
    public String gerarRelatorioGeral() {
        return "\n📊 --- RELATÓRIO GERAL DO HOSPITAL ---\n" +
                "📚 Total de Pacientes Registrados (AVL): " + prontuarios.size() + "\n" +
                "🚨 Aguardando na Fila de Emergência (Max-Heap): " + filaEmergencia.size() + "\n" +
                "🛋️ Aguardando na Fila Comum (Quack): " + filaComum.size() + "\n" +
                "🛏️ Pacientes Internados na UTI (Hash): " + uti.size() + "\n" +
                "--------------------------------------";
    }

}