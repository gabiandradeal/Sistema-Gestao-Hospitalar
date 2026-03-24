package application;

import java.util.Scanner;
import model.Paciente;
import model.NivelUrgencia;
import service.HospitalManager;
import structures.avl.AVLTree;
import structures.hash.HashTable;
import structures.heap.MaxHeap;
import structures.queue.Quack;

/**
 * Ponto de entrada do sistema de gestão hospitalar.
 * Responsável por gerenciar a interface de linha de comando, capturar entradas do usuário
 * e coordenar o fluxo de atendimento através do HospitalManager.
 * @author Gabriela
 * @version 1.0
 * @since 12/03
 */
public class Main {
    /**
     * Método principal que executa o loop do menu interativo.
     * @param args Argumentos de linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        AVLTree<Paciente> prontuarios = new AVLTree<>();

        // Lembra que a MaxHeap precisa da capacidade máxima e da regra de urgência que fizemos?
        MaxHeap<Paciente> filaEmergencia = new MaxHeap<>(100, (p1, p2) -> p1.compararPorUrgencia(p2));

        Quack<Paciente> filaComum = new Quack<>();

        HashTable<Paciente> uti = new HashTable<>();

        /**
         * Inicialização do gerenciador do sistema.
         * Nota: As instâncias das estruturas devem ser passadas conforme as implementações finalizadas.
         */
        HospitalManager manager = new HospitalManager(prontuarios, filaEmergencia, filaComum, uti);

        int opcao = -1;

        while (opcao != 0) {
            exibirMenu();

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Por favor, digite um número válido.");
                continue;
            }

            switch (opcao) {
                case 1:
                    admitirNovoPaciente(scanner, manager);
                    break;

                case 2:
                    processarAtendimento(scanner, manager);
                    break;

                case 3:
                    consultarProntuario(scanner, manager);
                    break;

                case 4:
                    listarPacientes(manager);
                    break;

                case 5:
                    gerenciarUTI(scanner, manager);
                    break;

                case 0:
                    System.out.println("Encerrando o sistema hospitalar... Até logo!");
                    break;

                default:
                    System.out.println("⚠️ Opção inválida! Tente novamente.");
            }
        }
        scanner.close();
    }

    /**
     * Exibe as opções do menu principal no console.
     */
    private static void exibirMenu() {
        System.out.println("\n========================================");
        System.out.println("    🏥 SISTEMA DE GESTÃO HOSPITALAR");
        System.out.println("========================================");
        System.out.println("1. Admitir Paciente (Cadastro e Triagem)");
        System.out.println("2. Chamar Próximo (Fluxo de Atendimento)");
        System.out.println("3. Consultar Prontuário (Busca AVL)");
        System.out.println("4. Listar Pacientes (Ordem Alfabética)");
        System.out.println("5. Gestão da UTI (Acesso Rápido Hash)");
        System.out.println("0. Sair");
        System.out.print("Sua escolha: ");
    }

    /**
     * Captura dados para admissão de um novo paciente e realiza a triagem.
     * @param sc Scanner para leitura de dados.
     * @param manager Gerenciador do sistema.
     */
    private static void admitirNovoPaciente(Scanner sc, HospitalManager manager) {
        System.out.print("Nome completo: ");
        String nome = sc.nextLine();
        System.out.print("CPF (somente números): ");
        String cpf = sc.nextLine();

        System.out.println("Cores da Triagem: 1-AZUL, 2-VERDE, 3-AMARELO, 4-LARANJA, 5-VERMELHO");
        System.out.print("Selecione a cor baseada na urgência (1-5): ");
        int nivel = Integer.parseInt(sc.nextLine());

        // Adicionando verificação para impedir erro de Index
        while (nivel < 1 || nivel > 5) {
            System.out.println("⚠️ Nível inválido! Definindo como AZUL por padrão.");
            nivel = Integer.parseInt(sc.nextLine());
        }
        NivelUrgencia urgencia = NivelUrgencia.values()[nivel - 1];

        Paciente novo = new Paciente(nome, cpf, urgencia);

        manager.admitirPaciente(novo);
        System.out.println("\n✅ Paciente admitido e encaminhado conforme nível: " + urgencia);
    }

    /**
     * Realiza a chamada do próximo paciente e define o desfecho clínico.
     * @param sc Scanner para leitura de dados.
     * @param manager Gerenciador do sistema.
     */
    private static void processarAtendimento(Scanner sc, HospitalManager manager) {
        Paciente p = manager.chamarProximo();
        if (p == null) {
            System.out.println("ℹ️ Não há pacientes aguardando atendimento.");
            return;
        }

        System.out.println("📢 ATENDIMENTO: " + p.getNome() + " [Urgência: " + p.getUrgencia() + "]");
        System.out.print("O paciente requer internação na UTI? (S/N): ");
        if (sc.nextLine().equalsIgnoreCase("S")) {
            manager.internarPaciente(p);
            System.out.println("🚨 Paciente internado na UTI para monitoramento.");
        } else {
            System.out.println("🏠 Paciente liberado com orientações médicas.");
        }
    }

    /**
     * Busca informações históricas do paciente no prontuário eletrônico.
     * @param sc Scanner para leitura de dados.
     * @param manager Gerenciador do sistema.
     */
    private static void consultarProntuario(Scanner sc, HospitalManager manager) {
        System.out.print("Digite o Nome do paciente: ");
        String nome = sc.nextLine();
        System.out.print("Digite o CPF (somente números) para consulta na AVL: ");
        String cpf = sc.nextLine();

        System.out.println("🔍 Consultando registros históricos para: " + nome);

        Paciente resultado = manager.consultarHistorico(nome, cpf);

        if (resultado == null) {
            System.out.println("⚠️ Nenhum registro encontrado! Este paciente não possui histórico no hospital.");
        } else {
            System.out.println("✅ Histórico Encontrado: ");
            System.out.println(resultado.toString());
        }
    }

    /**
     * Lista todos os pacientes cadastrados no histórico do sistema em ordem alfabética. Mesmo com o casting individual, o custo assintótico se mantém
     * @param manager Gerenciador do sistema.
     */
    private static void listarPacientes(HospitalManager manager) {
        System.out.println("\n📋 --- Lista Geral de Pacientes (A-Z) ---");

        /*
         * ⚠️ SOLUÇÃO PARA A LIMITAÇÃO DE ARRAYS GENÉRICOS NO JAVA
         * Por que Object[] e não Paciente[]?
         * Devido ao "Type Erasure" (Apagamento de Tipos), a Árvore AVL é forçada a
         * criar um array de Comparable[] internamente, pois o Java proíbe `new T[]`.
         * Se o Main tentasse receber isso como Paciente[], a Máquina Virtual do Java (JVM)
         * explodiria um ClassCastException por tentar converter a estrutura do array inteiro.
         * Receber como Object[] (que é a classe "Mãe" de todas) evita a quebra do sistema.
         */
        Object[] lista = manager.listarPacientesOrdemAlfabetica();

        // Verifica se a árvore está vazia (array nulo ou tamanho 0)
        if (lista == null || lista.length == 0) {
            System.out.println("ℹ️ Nenhum paciente registrado no prontuário do hospital até o momento.");
        } else {
            // Percorre o array e imprime o nome e CPF de cada paciente
            for (Object obj : lista) {
                    /*
                     * O PULO DO 😺: O Cast Individual
                     * O Java proíbe o cast da "caixa" (o Array inteiro), mas permite o cast dos itens de forma individual
                     * Como sabemos que o Manager só insere Pacientes na árvore, nós garantimos o cast seguro (Paciente) obj para acessar os métodos.
                     */
                Paciente p = (Paciente) obj; // Transforma o Object em Paciente
                System.out.println(" - " + p.getNome() + " (CPF: " + p.getCpf() + ")");
            }
        }
        System.out.println("-----------------------------------------");
    }

    /**
     * Gerencia a busca e monitoramento de pacientes internados na UTI.
     * @param sc Scanner para leitura de dados.
     * @param manager Gerenciador do sistema.
     */
    private static void gerenciarUTI(Scanner sc, HospitalManager manager) {
        System.out.print("CPF do paciente internado (Busca O(1)): ");
        String cpf = sc.nextLine();
        System.out.println("📍 Localizando paciente na unidade crítica...");

        System.out.print("Deseja dar alta desta unidade? (S/N): ");
        if (sc.nextLine().equalsIgnoreCase("S")) {
            manager.darAltaUti(cpf);
            System.out.println("✅ Paciente removido da unidade de monitoramento.");
        }
    }
}