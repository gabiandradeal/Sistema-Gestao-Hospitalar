package model;

/**
 * Enumeração que define os níveis de urgência para a triagem de pacientes.
 * Baseada no sistema internacional de classificação de risco (Protocolo de Manchester).
 */
public enum NivelUrgencia {

    // Segundo o Protocolo de Manchester

    AZUL,     // Não urgente
    VERDE,    // Pouco urgente
    AMARELO,  // Urgente
    LARANJA,  // Muito urgente
    VERMELHO  // Emergência (Risco de vida)
}
