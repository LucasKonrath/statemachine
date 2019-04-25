package br.com.camila.statemachine.domain;

public enum Estados {

    P_INEXISTENTE,
    P_CRIADA,
    ANALISE_PRE,
    NEGADO_PRE,
    APROVADO_PRE,
    INFOS_PESSOAIS,
    EMAIL_VALIDADO,
    ANALISE_POS,
    NEGADO_POS,
    APROVADO_POS,
    PENDENTE_PRE
}
