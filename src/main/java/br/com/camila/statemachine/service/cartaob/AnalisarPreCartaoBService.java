package br.com.camila.statemachine.service.cartaob;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.camila.statemachine.anotation.EventTemplate;
import br.com.camila.statemachine.domain.Tipo;
import br.com.camila.statemachine.message.AnalisarPreMotorMessage;
import br.com.camila.statemachine.messaging.Messaging;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AnalisarPreCartaoBService {

    @Autowired
    @EventTemplate
    private RabbitTemplate eventTemplate;

    public void executar(final Long numeroProposta, final String cpf) {

        AnalisarPreMotorMessage message = AnalisarPreMotorMessage.builder()
            .cpf(cpf)
            .numeroProposta(numeroProposta)
            .proposta(Tipo.CARTAO_B).build();

        log.info("Analisa pré proposta número {}, contratacao_mc.", numeroProposta);
        eventTemplate.convertAndSend(
            Messaging.ANALISAR_PRE_PROPOSTA_MOTOR.getExchange(),
            Messaging.ANALISAR_PRE_PROPOSTA_MOTOR.getRoutingKey(),
            message);
    }
}
