package utils;
import model.Paciente;
import model.NivelUrgencia;
import java.lang.Math;
import java.util.ArrayList;

public class PacienteGenerator {
    
    private static String gerarCpf() {
        StringBuilder cpf = new StringBuilder();
        for (int i = 0; i < 11; i++) {
            cpf.append((int) (Math.random() * 10));
        }
        return cpf.toString();
    }

    private static String gerarNome() {
        String[] nomes = {"Danilo", "Laura", "Pedro", "Ana", "Lucas", "Beatriz", "Gustavo", "Carla", "Rafael"};
        String[] sobrenomes = {"Jacobi", "Silva", "Santos", "Oliveira", "Souza", "Costa", "Pereira", "Almeida", "Gomes", "Gentili"};
        String nome = nomes[(int) (Math.random() * nomes.length)];
        String sobrenome = sobrenomes[(int) (Math.random() * sobrenomes.length)];
        return nome + " " + sobrenome;
    }

    private static String gerarSintomas() {
        String[] sintomas = {"Dor de cabeça", "Febre", "Tosse", "Náusea", "Fadiga", "Tremores", "Fraqueza", "Dor no peito", "Falta de ar", "Dor nas costas"};
        return sintomas[(int) (Math.random() * sintomas.length)];
    }

    private static NivelUrgencia gerarUrgencia() {
        NivelUrgencia[] niveis = NivelUrgencia.values();
        return niveis[(int) (Math.random() * niveis.length)];
    }

    public static Paciente gerarPaciente() {
        String cpf = gerarCpf();
        String nome = gerarNome();
        ArrayList<String> sintomas = new ArrayList<>();
        int numSintomas = (int) (Math.random() * 10);
        for (int i = 0; i < numSintomas; i++) {
            sintomas.add(gerarSintomas());
        }
        String[] arrSintomas = sintomas.toArray(new String[numSintomas]);
        NivelUrgencia urgencia = gerarUrgencia();
        return new Paciente(nome, cpf, urgencia, arrSintomas);
    }
}
