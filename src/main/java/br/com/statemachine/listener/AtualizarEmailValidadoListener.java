package br.com.statemachine.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import br.com.statemachine.annotation.RabbitEnabled;
import br.com.statemachine.messaging.Messaging;
import lombok.extern.slf4j.Slf4j;

@Component
@RabbitEnabled
@RabbitListener(queues = Messaging.QUEUE_ATUALIZAR_EMAIL_VALIDADO)
@Slf4j
public class AtualizarEmailValidadoListener {

}