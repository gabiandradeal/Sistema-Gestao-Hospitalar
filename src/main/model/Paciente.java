package main.model;

import java.util.Arrays;
import java.util.Objects;

public class Paciente implements Comparable<Paciente> {
    private String nome;
    private String cpf; // Nosso ID
    private NivelUrgencia urgencia;
    private String[] sintomas;

    // ==========================================
    // <--------- CONSTRUTORES --------->
    // ==========================================

    public Paciente(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

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
    }

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
     * Agora a Árvore será percorrida e balanceada com base no CPF.
     */
    @Override
    public int compareTo(Paciente outroPaciente) {
        return this.cpf.compareTo(outroPaciente.getCpf());
    }

    /*
    /*
     * 🔗 PARA A TABELA HASH (CHAINING)
     * Garante que não haverá duplicatas de CPF na mesma lista encadeada.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paciente paciente = (Paciente) o;
        return Objects.equals(cpf, paciente.cpf);
    }

    /*
     * 🔗 PARA A TABELA HASH
     * Gera o índice numérico (bucket) baseado no CPF.
     */
    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

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

