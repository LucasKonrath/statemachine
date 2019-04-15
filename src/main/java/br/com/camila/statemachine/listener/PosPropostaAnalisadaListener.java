package br.com.camila.statemachine.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.camila.statemachine.domain.Estados;
import br.com.camila.statemachine.domain.Eventos;
import br.com.camila.statemachine.message.PrePropostaAnalisadaMessage;
import br.com.camila.statemachine.messaging.Messaging;
import br.com.camila.statemachine.statemachine.AbstractStateMachineContextBuilder;
import br.com.camila.statemachine.service.CustomStateMachineService;
import lombok.extern.slf4j.Slf4j;

@Component
@RabbitListener(queues = Messaging.QUEUE_POS_PROPOSTA_ANALISADA)
@Slf4j
public class PosPropostaAnalisadaListener extends AbstractStateMachineContextBuilder<Estados, Eventos> {

    @Autowired
    private CustomStateMachineService customStateMachineService;

    @RabbitHandler
    void receive(@Payload final PrePropostaAnalisadaMessage message) {

        log.info("Mensagem: {}", message);

        if (message.getEstado().equals(Estados.NEGADO_POS.toString())) {
            log.info("Enviando evento {} para StateMachine.", Eventos.NEGAR);
            customStateMachineService.sendEvent(message.getNumeroProposta(), Eventos.NEGAR);
        }

        if (message.getEstado().equals(Estados.APROVADO_POS.toString())) {
            log.info("Enviando evento {} para StateMachine.", Eventos.APROVAR);
            customStateMachineService.sendEvent(message.getNumeroProposta(), Eventos.APROVAR);
        }
    }
}
