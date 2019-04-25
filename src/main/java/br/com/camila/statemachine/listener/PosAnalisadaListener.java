package br.com.camila.statemachine.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.camila.statemachine.domain.Estados;
import br.com.camila.statemachine.domain.Eventos;
import br.com.camila.statemachine.message.PosAnalisadaMessage;
import br.com.camila.statemachine.messaging.Messaging;
import br.com.camila.statemachine.statemachine.CustomStateMachineService;
import br.com.camila.statemachine.statemachine.AbstractStateMachineContextBuilder;
import lombok.extern.slf4j.Slf4j;

@Component
@RabbitListener(queues = Messaging.QUEUE_POS_ANALISADA)
@Slf4j
public class PosAnalisadaListener extends AbstractStateMachineContextBuilder<Estados, Eventos> {

    @Autowired
    private CustomStateMachineService customStateMachineService;

    @RabbitHandler
    void receive(@Payload final PosAnalisadaMessage message) {

        log.info("Mensagem: {}", message);

        if (message.getEstado().equals(Estados.NEGADO_POS.toString())) {
            log.info("Enviando evento {} para StateMachine.", Eventos.NEGAR);
            customStateMachineService.sendEvent(message.getNumeroProposta(), Eventos.NEGAR, message.getProposta());
        }

        if (message.getEstado().equals(Estados.APROVADO_POS.toString())) {
            log.info("Enviando evento {} para StateMachine.", Eventos.APROVAR);
            customStateMachineService.sendEvent(message.getNumeroProposta(), Eventos.APROVAR, message.getProposta());
        }
    }
}
