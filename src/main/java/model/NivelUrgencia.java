package model;

/**
 * Enumeração que define os níveis de urgência para a triagem de pacientes.
 * Baseada no sistema internacional de classificação de risco (Protocolo de Manchester).
 * @author Gabriela
 * @version 1.0
 * @since 12/03
 */
public enum NivelUrgencia {

    // Segundo o Protocolo de Manchester

    AZUL,     // Não urgente
    VERDE,    // Pouco urgente
    AMARELO,  // Urgente
    LARANJA,  // Muito urgente
    VERMELHO  // Emergência (Risco de vida)
}
