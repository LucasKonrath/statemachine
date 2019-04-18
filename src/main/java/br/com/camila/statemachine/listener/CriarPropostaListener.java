package br.com.camila.statemachine.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.camila.statemachine.domain.Estados;
import br.com.camila.statemachine.domain.Eventos;
import br.com.camila.statemachine.message.CriarPropostaMessage;
import br.com.camila.statemachine.messaging.Messaging;
import br.com.camila.statemachine.service.proposta.CriarPropostaService;
import br.com.camila.statemachine.statemachine.AbstractStateMachineContextBuilder;
import lombok.extern.slf4j.Slf4j;

@Component
@RabbitListener(queues = Messaging.QUEUE_CRIAR_PROPOSTA)
@Slf4j
public class CriarPropostaListener extends AbstractStateMachineContextBuilder<Estados, Eventos> {

    @Autowired
    private CriarPropostaService criarPropostaService;

    @RabbitHandler
    void receive(@Payload final CriarPropostaMessage message, @Headers final MessageHeaders headers) {

        log.info("Mensagem: {}", message);

        try {

            log.info("Inicia persistência da proposta para o cpf: {}", message.getCpf());
            criarPropostaService.executar(message.getCpf(), message.getProposta());
            log.info("Finaliza persistência da proposta para o cpf: {}", message.getCpf());

        } catch (final Exception e) {
            throw e;
        }
    }
}
