package br.com.camila.statemachine.messaging;

/**
 * Interface para definição de nomes de exchanges, queues e routing keys.
 */
public interface Messaging {

    MessageInbox CRIAR_PROPOSTA = new MessageInbox("proposta.criar-proposta.message");
    MessageInbox ANALISAR_PRE_PROPOSTA = new MessageInbox("proposta.analisar-pre-proposta.message");
    MessageInbox ATUALIZAR_INFOS_PESSOAIS = new MessageInbox("proposta.atualizar-infos-pessoais.message");
    MessageInbox ATUALIZAR_EMAIL_VALIDADO = new MessageInbox("proposta.atualizar-email-validado.message");
    MessageInbox ANALISAR_POS_PROPOSTA = new MessageInbox("proposta.analisar-pos-proposta.message");

    MessageOutbox ANALISAR_PRE_PROPOSTA_MOTOR = new MessageOutbox("motor.analisar-pre-proposta-motor.message");
    MessageOutbox ANALISAR_POS_PROPOSTA_MOTOR = new MessageOutbox("motor.analisar-pos-proposta-motor.message");

    MessageInbox PRE_PROPOSTA_ANALISADA_MOTOR = new MessageInbox("proposta.pre-proposta-analisada.message");
    MessageInbox POS_PROPOSTA_ANALISADA_MOTOR = new MessageInbox("proposta.pos-proposta-analisada.message");

    MessageOutbox ATUALIZAR_INFOS_PESSOAIS_PROCESSADORA = new MessageOutbox("processadora.atualizar-infos-pessoais-processadora.message");
    MessageOutbox ATUALIZAR_EMAIL_VALIDADO_PROCESSADORA = new MessageOutbox("processadora.atualizar-email-validado-processadora.message");

    MessageInbox INFOS_PESSOAIS_ATUALIZADAS_PROCESSADORA = new MessageInbox("proposta.infos-pessoais-atualizadas.message");
    MessageInbox EMAIL_VALIDADO_ATUALIZADO_PROCESSADORA = new MessageInbox("proposta.email-validado-atualizado.message");

    String EXCHANGE = "proposta.exchange";
    String EXCHANGE_EVENTS = "proposta.events.exchange";

    String QUEUE_CRIAR = "proposta.criar-proposta.queue";
    String QUEUE_ANALISAR_PRE = "proposta.analisar-pre-proposta.queue";
    String QUEUE_PRE_ANALISADA = "proposta.analisar-pre-proposta.queue";
    String QUEUE_ATUALIZAR_INFOS_PESSOAIS = "proposta.atualizar-infos-pessoais.queue";
    String QUEUE_INFOS_PESSOAIS_ATUALIZADAS = "proposta.infos-pessoais-atualizadas.queue";
    String QUEUE_ATUALIZAR_EMAIL_VALIDADO = "proposta.atualizar-email-validado.queue";
    String QUEUE_EMAIL_VALIDADO_ATUALIZADO = "proposta.email-validado-atualizado.queue";
    String QUEUE_ANALISAR_POS = "proposta.analisar-pos-proposta.queue";
    String QUEUE_POS_ANALISADA = "proposta.pos-proposta-analisada.queue";
}
