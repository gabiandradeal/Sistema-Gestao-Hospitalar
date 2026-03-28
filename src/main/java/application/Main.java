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
                    admitirPaciente(scanner, manager);
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

                case 6:
                    exibirRelatorio(manager);
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
        System.out.println("4. Listar Pacientes Cadastrados (Ordem Alfabética)");
        System.out.println("5. Gestão da UTI (Acesso Rápido Hash)");
        System.out.println("6. Relatório Geral");
        System.out.println("0. Sair\n");
        System.out.print("Sua escolha: ");
    }

    /**
     * Captura dados para admissão de um novo paciente e realiza a triagem.
     * @param sc Scanner para leitura de dados.
     * @param manager Gerenciador do sistema.
     */
    private static void admitirPaciente(Scanner sc, HospitalManager manager) {
        try {
            // --- BLOCO DO NOME ---
            String nome = "";
            while (nome.isEmpty()) {
                System.out.print("Nome completo: ");
                nome = sc.nextLine().trim();
                if (nome.isEmpty()) {
                    System.out.println("⚠️ Erro: O nome é obrigatório para o prontuário.");
                }
            }

            // --- BLOCO DO CPF ---
            String cpf = "";
            while (cpf.isEmpty() || !cpf.matches("\\d{11}")) {
                System.out.print("CPF (somente números): ");
                cpf = sc.nextLine().trim();
                if (cpf.isEmpty()) {
                    System.out.println("⚠️ Erro: O CPF é obrigatório.");
                } else if (!cpf.matches("\\d{11}")) {
                    System.out.println("⚠️ Erro: CPF Inválido.");
                    cpf = ""; // Reseta para continuar no loop
                }
            }

// --- VERIFICAÇÃO DE RETORNO DE PACIENTE ---
            // Nossa arquitetura assume que readmissões ocorrem para retornos hospitalares, quando o paciente já finalizou o atendimento anterior
            Paciente pacienteExistente = manager.consultarHistorico(nome, cpf);

            if (pacienteExistente != null) {
                System.out.println("\nℹ️ O paciente " + pacienteExistente.getNome() + " já possui prontuário no sistema.");
                System.out.println("O que deseja fazer com este paciente?");
                System.out.println("⚠️ ATENÇÃO: Use esta opção apenas se o paciente NÃO estiver aguardando atendimento ainda"); // Ou, inevitavelmente vamos criar duplicatas
                System.out.println("1 - Nova Triagem (Encaminhar para fila de atendimento)");
                System.out.println("2 - Internação Direta (Encaminhar para UTI)");
                System.out.println("0 - Cancelar operação");
                System.out.print("Sua escolha: ");
                String subOpcao = sc.nextLine().trim();

                if (subOpcao.equals("2")) {
                    manager.internarPaciente(pacienteExistente);
                    System.out.println("🚨 Paciente encaminhado diretamente para a UTI de monitoramento.");
                    return; // Encerra o método aqui, pois já foi para a UTI

                } else if (subOpcao.equals("0")) {
                    System.out.println("ℹ️ Admissão cancelada.");
                    return; // Encerra o método e volta pro menu principal

                } else if (!subOpcao.equals("1")) {
                    System.out.println("⚠️ Opção inválida. Admissão cancelada.");
                    return;
                }

                // Se ele digitou "1", o código apenas segue o fluxo natural para baixo
                System.out.println("\n🔄 Iniciando nova triagem para paciente de retorno...");
            }

            // --- BLOCO DE SINTOMAS ---
            String[] listaSintomas;
            String respSintoma = "";

            // Garante que o usuário só saia daqui digitando S ou N
            while (!respSintoma.equals("S") && !respSintoma.equals("N")) {
                System.out.print("Paciente apresenta sintomas? (S/N): ");
                respSintoma = sc.nextLine().trim().toUpperCase();

                if (!respSintoma.equals("S") && !respSintoma.equals("N")) {
                    System.out.println("⚠️ Opção inválida! Digite 'S' para Sim ou 'N' para Não.");
                }
            }

            if (respSintoma.equals("S")) {
                String entradaSintomas = "";
                // While opcional: obriga a descrever algo se ele disse que TEM sintomas
                while (entradaSintomas.isEmpty()) {
                    System.out.print("Descreva os sintomas (separe por vírgula): ");
                    entradaSintomas = sc.nextLine().trim();
                    if (entradaSintomas.isEmpty()) {
                        System.out.println("⚠️ Se o paciente apresenta sintomas, por favor, descreva-os.");
                    }
                }
                // Converte a string em Array
                listaSintomas = entradaSintomas.split("\\s*,\\s*");
            } else {
                // Se respondeu 'N', cria um array padrão
                listaSintomas = new String[]{"Nenhum sintoma relatado"};
            }

            System.out.println("Cores da Triagem: 1-AZUL, 2-VERDE, 3-AMARELO, 4-LARANJA, 5-VERMELHO");

            int nivel = -1;
            boolean nivelValido = false;

            // Este while garante que o sistema não saia daqui até ter um número de 1 a 5
            while (!nivelValido) {
                try {
                    System.out.print("Selecione a cor baseada na urgência (1-5): ");
                    nivel = Integer.parseInt(sc.nextLine());

                    if (nivel >= 1 && nivel <= 5) {
                        nivelValido = true;
                    } else {
                        System.out.println("⚠️ Nível inválido! Escolha um número entre 1 e 5.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("⚠️ Erro: Por favor, digite apenas NÚMEROS.");
                }
            }
            // Agora temos certeza que o nível é válido
            NivelUrgencia urgencia = NivelUrgencia.values()[nivel - 1];

            // Verificação de retorno para atualizar os dados ou criar um objeto novo
            if (pacienteExistente != null) {
                pacienteExistente.setUrgencia(urgencia);
                pacienteExistente.setSintomas(listaSintomas);
                manager.admitirPaciente(pacienteExistente);
                System.out.println("\n🔄 Registro atualizado! Paciente de retorno encaminhado conforme nível: " + urgencia);
            } else {
                // Se for a primeira vez no hospital, aí sim criamos o objeto novo
                Paciente novo = new Paciente(nome, cpf, urgencia, listaSintomas);
                manager.admitirPaciente(novo);
                System.out.println("\n✅ Novo paciente admitido e encaminhado conforme nível: " + urgencia);
            }

        } catch (Exception e) {
            // Este catch final evita que qualquer erro inesperado feche o sistema
            System.out.println("⚠️ Ocorreu um erro inesperado: " + e.getMessage());
        }
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

        // Lê a resposta, remove espaços em branco e joga para maiúsculo
        String resposta = sc.nextLine().trim().toUpperCase();

        // VALIDAÇÃO: Prende o usuário até ele digitar S ou N
        while (!resposta.equals("S") && !resposta.equals("N")) {
            System.out.print("⚠️ Entrada inválida! Digite 'S' para internar ou 'N' para liberar: ");
            resposta = sc.nextLine().trim().toUpperCase();
        }

        if (resposta.equals("S")) {
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

        // Checa se o cpf só tem números ou se tem exatamente 11 dígitos, se não tiver, pede para digitar novamente
        while (!cpf.matches("\\d{11}")) {
            System.out.print("⚠️ CPF inválido. Tente novamente: ");
            cpf = sc.nextLine();
        }

        System.out.println("📍 Localizando paciente na unidade crítica...");
        Paciente p = manager.consultarPaciente(cpf);

        if (p == null) {
            System.out.println("⚠️ Paciente não encontrado na UTI.");
            return;
        }

        System.out.println("✅ Paciente encontrado na UTI:");
        System.out.println(p.toString());

        System.out.print("Deseja dar alta desta unidade? (S/N): ");
        String resposta = sc.nextLine().trim().toUpperCase();

        // 4Prende o usuário até ele digitar S ou N
        while (!resposta.equals("S") && !resposta.equals("N")) {
            System.out.print("⚠️ Entrada inválida! Digite 'S' para confirmar a alta ou 'N' para manter na UTI: ");
            resposta = sc.nextLine().trim().toUpperCase();
        }

        if (resposta.equals("S")) {
            manager.darAltaUti(cpf);
            System.out.println("✅ Paciente removido da unidade de monitoramento.");
        } else {
            System.out.println("ℹ️ Alta cancelada. O paciente permanece na UTI.");
        }
    }


    /**
     * Exibe o relatório estatístico geral do hospital consultando o tamanho das estruturas.
     * @param manager Gerenciador do sistema.
     */
    private static void exibirRelatorio(HospitalManager manager) {
        // O Manager já devolve a string montada
        System.out.println(manager.gerarRelatorioGeral());
    }

}
