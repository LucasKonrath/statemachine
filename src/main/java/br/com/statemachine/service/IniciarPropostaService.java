package br.com.statemachine.service;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import br.com.statemachine.annotation.EventTemplate;
import br.com.statemachine.domain.Estados;
import br.com.statemachine.domain.Eventos;
import br.com.statemachine.message.CriarPropostaMessage;
import br.com.statemachine.messaging.Messaging;
import br.com.statemachine.persistency.CustomStateMachineInterceptor;

@Service
@EnableRabbit
public class IniciarPropostaService {

    @Autowired
    private StateMachine<Estados, Eventos> stateMachine;

    @Autowired
    @EventTemplate
    private RabbitTemplate eventTemplate;

    @Autowired
    private CustomStateMachineInterceptor interceptor;

    public void executar(String cpf) {

        stateMachine.start();
        stateMachine.getExtendedState().getVariables().put("cpf", cpf);

        stateMachine.getStateMachineAccessor()
            .doWithRegion(access -> access.addStateMachineInterceptor(interceptor));

        CriarPropostaMessage novaProposta = CriarPropostaMessage.builder().cpf(cpf).build();

        eventTemplate.convertAndSend(
            Messaging.CRIAR_PROPOSTA.getExchange(),
            Messaging.CRIAR_PROPOSTA.getRoutingKey(),
            novaProposta);
    }
}
