package model;

import java.util.Arrays;
import java.util.Objects;

/**
 * Representa a entidade principal no sistema hospitalar, o <i>Paciente<i/>.
 * Fornece atributos e métodos fundamentais para o gerenciamento de dados e ordenação em estruturas complexas na Árvore AVL e Tabela Hash.
 * @author Georis
 * @version 3.0
 * @since 15/03
 */
public class Paciente implements Comparable<Paciente> {
    private String nome;
    private String cpf; // Nosso ID
    private NivelUrgencia urgencia;
    private String[] sintomas;

    // ==========================================
    // <--------- CONSTRUTORES --------->
    // ==========================================

    /**
     * Constrói um novo objeto Paciente contendo as informações básicas de identificação.
     * @param nome o nome completo do paciente
     * @param cpf o número de identificação único (CPF) do paciente
     */
    public Paciente(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    /**
     * Constrói um novo objeto Paciente com o quadro clínico completo.
     * @param nome o nome completo do paciente
     * @param cpf o número de identificação único (CPF) do paciente
     * @param urgencia o nível de urgência associado ao quadro clínico
     * @param sintomas um array contendo os sintomas relatados pelo paciente
     */
    public Paciente(String nome, String cpf, NivelUrgencia urgencia,  String[] sintomas) {
        this.nome = nome;
        this.cpf = cpf;
        this.urgencia = urgencia;
        this.sintomas = sintomas;
    }

    // ==========================================
    // <--------- GETTERS E SETTERS --------->
    // ==========================================
    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public NivelUrgencia getUrgencia() {
        return urgencia;
    }
    public void setUrgencia(NivelUrgencia urgencia) {
        this.urgencia = urgencia;
    } // Caso seja necessário adicionar/modificar depois

    public String[] getSintomas() {
        return sintomas;
    }
    public void setSintomas(String[] sintomas) {
        this.sintomas = sintomas;
    }


    // ========================================================
    // <---------MÉTODOS PARA AS ESTRUTURAS DE DADOS --------->
    // ========================================================

    /**
     * 🌳 PARA A ÁRVORE AVL
     * Ordena os pacientes de modo alfabético com base no Nome.
     * Caso existam pacientes com o mesmo nome, utiliza o CPF como critério de desempate
     * para garantir que ambos sejam inseridos corretamente na árvore sem sobrescrita.
     * @param outroPaciente o outro paciente a ser comparado com este
     * @return um número negativo, zero ou positivo de acordo com a ordem alfabética
     * @author Georis
     * @since 3.0
     */
    @Override
    public int compareTo(Paciente outroPaciente) {

        // Compara os nomes ignorando letras maiúsculas/minúsculas
        int comparacaoNome = this.nome.compareToIgnoreCase(outroPaciente.getNome());

        // Se os nomes forem idênticos, desempata pelo CPF
        if (comparacaoNome == 0) {
            return this.cpf.compareTo(outroPaciente.getCpf());
        }
        return comparacaoNome;

    }
    /**
     * 🔗 PARA A TABELA HASH (CHAINING)
     * Verifica a igualdade entre pacientes exclusivamente pelo CPF.
     * Garante que não haverá duplicatas de pacientes com o mesmo CPF na lista encadeada.
     * @param o o objeto a ser comparado com este paciente
     * @return true se o objeto for um Paciente com o mesmo CPF, false caso contrário
     * @author Georis
     * @version 2.0
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paciente paciente = (Paciente) o;
        return Objects.equals(cpf, paciente.cpf);
    }

    /**
     * 🔗 PARA A TABELA HASH
     * Gera o índice numérico (bucket) na Tabela Hash baseado exclusivamente no CPF do paciente, será usado e aprimorado no objeto do tipo Hash Table.
     * @return o código hash calculado a partir do CPF
     * @author Georis
     * @version 2.0
     */
    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    /**
     * Retorna a representação em String dos dados do Paciente para fácil visualização.
     * @return os dados formatados do paciente
     * @since 2.0
     */
    @Override
    public String toString() {
        // Arrays.toString() converte o array de forma limpa para impressão: [Febre, Tosse, Dor de Cabeça]
        return "Paciente [" +
                "CPF='" + cpf + '\'' +
                ", Nome='" + nome + '\'' +
                ", Urgência=" + urgencia +
                ", Sintomas=" + Arrays.toString(sintomas) +
                ']';
    }
}

