package br.com.camila.statemachine.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.camila.statemachine.domain.Estados;
import br.com.camila.statemachine.domain.Eventos;
import br.com.camila.statemachine.domain.Tipo;
import br.com.camila.statemachine.message.PreAnalisadaMessage;
import br.com.camila.statemachine.messaging.Messaging;
import br.com.camila.statemachine.statemachine.AbstractStateMachineContextBuilder;
import br.com.camila.statemachine.statemachine.CustomStateMachineService;
import lombok.extern.slf4j.Slf4j;

@Component
@RabbitListener(queues = Messaging.QUEUE_PRE_ANALISADA)
@Slf4j
public class PreAnalisadaListener extends AbstractStateMachineContextBuilder<Estados, Eventos> {

    @Autowired
    private CustomStateMachineService customStateMachineService;

    @RabbitHandler
    void receive(@Payload final PreAnalisadaMessage message) {

        log.info("Mensagem: {}", message);

        if (message.getProposta().equals(Tipo.CARTAO_A)) {
            propostaCaptacaoCCR(message);
        }

        if (message.getProposta().equals(Tipo.CARTAO_B)) {
            propostaContratacaoMC(message);
        }
    }

    private void propostaContratacaoMC(final PreAnalisadaMessage message) {
        if (message.getEstado().equals(Estados.NEGADO_PRE.toString())) {
            log.info("Enviando evento {} para StateMachine.", Eventos.NEGAR);
            customStateMachineService.sendEvent(message.getNumeroProposta(), Eventos.NEGAR, message.getProposta());
        }

        if (message.getEstado().equals(Estados.APROVADO_PRE.toString())) {
            log.info("Enviando evento {} para StateMachine.", Eventos.APROVAR);
            customStateMachineService.sendEvent(message.getNumeroProposta(), Eventos.APROVAR, message.getProposta());
        }

        if (message.getEstado().equals(Estados.PENDENTE_PRE.toString())) {
            log.info("Enviando evento {} para StateMachine.", Eventos.AGUARDAR);
            customStateMachineService.sendEvent(message.getNumeroProposta(), Eventos.AGUARDAR, message.getProposta());
        }
    }

    private void propostaCaptacaoCCR(final PreAnalisadaMessage message) {
        if (message.getEstado().equals(Estados.NEGADO_PRE.toString())) {
            log.info("Enviando evento {} para StateMachine.", Eventos.NEGAR);
            customStateMachineService.sendEvent(message.getNumeroProposta(), Eventos.NEGAR, message.getProposta());
        }

        if (message.getEstado().equals(Estados.APROVADO_PRE.toString())) {
            log.info("Enviando evento {} para StateMachine.", Eventos.APROVAR);
            customStateMachineService.sendEvent(message.getNumeroProposta(), Eventos.APROVAR, message.getProposta());
        }
    }
}
