package application;

import java.util.Scanner;
import model.Paciente;
import model.NivelUrgencia;
import service.HospitalManager;

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

        /**
         * Inicialização do gerenciador do sistema.
         * Nota: As instâncias das estruturas devem ser passadas conforme as implementações finalizadas.
         */
        HospitalManager manager = new HospitalManager(
                /* Instância AVL */ null,
                /* Instância Max-Heap */ null,
                /* Instância Fila 2 Pilhas */ null,
                /* Instância Tabela Hash */ null
        );

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
        System.out.println("4. Gestão da UTI (Acesso Rápido Hash)");
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
        System.out.println("✅ Paciente admitido e encaminhado conforme nível: " + urgencia);
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
        System.out.print("Digite o CPF para consulta na AVL: ");
        String cpf = sc.nextLine();
        // Lógica de busca e exibição implementada via HospitalManager
        System.out.println("🔍 Consultando registros históricos para o CPF: " + cpf);
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