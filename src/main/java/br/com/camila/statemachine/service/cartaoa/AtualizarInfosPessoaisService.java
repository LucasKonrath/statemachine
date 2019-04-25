package br.com.camila.statemachine.service.cartaoa;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.camila.statemachine.anotation.EventTemplate;
import br.com.camila.statemachine.message.AtualizarInfosPessoaisMessage;
import br.com.camila.statemachine.messaging.Messaging;
import lombok.extern.slf4j.Slf4j;

@Service
@EnableRabbit
@Slf4j
public class AtualizarInfosPessoaisService {

    @Autowired
    @EventTemplate
    private RabbitTemplate eventTemplate;

    public void executar(Long numeroProposta, String cpf) {

        AtualizarInfosPessoaisMessage message = AtualizarInfosPessoaisMessage.builder()
            .cpf(cpf)
            .numeroProposta(numeroProposta).build();

        log.info("Envia atualizar infos pessoais da proposta número {} para a processadora.", numeroProposta);
        eventTemplate.convertAndSend(
            Messaging.ATUALIZAR_INFOS_PESSOAIS_PROCESSADORA.getExchange(),
            Messaging.ATUALIZAR_INFOS_PESSOAIS_PROCESSADORA.getRoutingKey(),
            message);
    }
}
