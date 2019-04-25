package br.com.camila.statemachine.service.cartaoa;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.camila.statemachine.anotation.EventTemplate;
import br.com.camila.statemachine.message.AtualizarEmailValidadoMessage;
import br.com.camila.statemachine.messaging.Messaging;
import lombok.extern.slf4j.Slf4j;

@Service
@EnableRabbit
@Slf4j
public class AtualizarEmailValidadoService {

    @Autowired
    @EventTemplate
    private RabbitTemplate eventTemplate;

    public void executar(Long numeroProposta, String cpf) {

        AtualizarEmailValidadoMessage message = AtualizarEmailValidadoMessage.builder()
            .cpf(cpf)
            .numeroProposta(numeroProposta).build();

        log.info("Envia atualizar email validado da proposta número {} para a processadora.", numeroProposta);
        eventTemplate.convertAndSend(
            Messaging.ATUALIZAR_EMAIL_VALIDADO_PROCESSADORA.getExchange(),
            Messaging.ATUALIZAR_EMAIL_VALIDADO_PROCESSADORA.getRoutingKey(),
            message);
    }
}
