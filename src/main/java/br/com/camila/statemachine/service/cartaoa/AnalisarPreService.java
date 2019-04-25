package br.com.camila.statemachine.service.cartaoa;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.camila.statemachine.anotation.EventTemplate;
import br.com.camila.statemachine.domain.Tipo;
import br.com.camila.statemachine.message.AnalisarPreMotorMessage;
import br.com.camila.statemachine.messaging.Messaging;
import lombok.extern.slf4j.Slf4j;

@Service
@EnableRabbit
@Slf4j
public class AnalisarPreService {

    @Autowired
    @EventTemplate
    private RabbitTemplate eventTemplate;

    public void executar(Long numeroProposta, String cpf) {

        AnalisarPreMotorMessage message = AnalisarPreMotorMessage.builder()
            .cpf(cpf)
            .numeroProposta(numeroProposta)
            .proposta(Tipo.CARTAO_A)
            .build();

        log.info("Envia análise da proposta número {} para o motor.", numeroProposta);
        eventTemplate.convertAndSend(
            Messaging.ANALISAR_PRE_PROPOSTA_MOTOR.getExchange(),
            Messaging.ANALISAR_PRE_PROPOSTA_MOTOR.getRoutingKey(),
            message);
    }
}
