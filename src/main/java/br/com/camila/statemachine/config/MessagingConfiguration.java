package br.com.camila.statemachine.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.camila.statemachine.anotation.RabbitEnabled;
import br.com.camila.statemachine.messaging.Messaging;

@Configuration
@RabbitEnabled
public class MessagingConfiguration {

    /**
     * Exchanges
     **/

    @Bean
    TopicExchange propostaExchange() {
        return new TopicExchange(Messaging.EXCHANGE);
    }

    /**
     * Queues
     **/

    @Bean
    Queue criarPropostaQueue() {
        return new Queue(Messaging.QUEUE_CRIAR);
    }

    @Bean
    Queue analisarPrePropostaQueue() {
        return new Queue(Messaging.QUEUE_ANALISAR_PRE);
    }

    @Bean
    Queue atualizarInfosPessoaisQueue() {
        return new Queue(Messaging.QUEUE_ATUALIZAR_INFOS_PESSOAIS);
    }

    @Bean
    Queue atualizarEmailValidadoQueue() {
        return new Queue(Messaging.QUEUE_ATUALIZAR_EMAIL_VALIDADO);
    }

    @Bean
    Queue analisarPosPropostaQueue() {
        return new Queue(Messaging.QUEUE_ANALISAR_POS);
    }

    @Bean
    Queue prePropostaAnalisadaQueue() {
        return new Queue(Messaging.QUEUE_PRE_ANALISADA);
    }

    @Bean
    Queue infosPessoaisAtualizadasQueue() {
        return new Queue(Messaging.QUEUE_INFOS_PESSOAIS_ATUALIZADAS);
    }

    @Bean
    Queue emailValidadoAtualizadoQueue() {
        return new Queue(Messaging.QUEUE_EMAIL_VALIDADO_ATUALIZADO);
    }

    @Bean
    Queue posPropostaAnalisadaQueue() {
        return new Queue(Messaging.QUEUE_POS_ANALISADA);
    }

    /**
     * Bindings
     **/

    @Bean
    Binding criarPropostaQueueToPropostaExchangeBinder() {
        return BindingBuilder.bind(criarPropostaQueue())
            .to(propostaExchange())
            .with(Messaging.CRIAR_PROPOSTA.getRoutingKey());
    }

    @Bean
    Binding analisarPrePropostaQueueToPropostaExchangeBinder() {
        return BindingBuilder.bind(analisarPrePropostaQueue())
            .to(propostaExchange())
            .with(Messaging.ANALISAR_PRE_PROPOSTA.getRoutingKey());
    }

    @Bean
    Binding atualizarInfosPessoaisQueueToPropostaExchangeBinder() {
        return BindingBuilder.bind(atualizarInfosPessoaisQueue())
            .to(propostaExchange())
            .with(Messaging.ATUALIZAR_INFOS_PESSOAIS.getRoutingKey());
    }

    @Bean
    Binding atualizarEmailValidadoQueueToPropostaExchangeBinder() {
        return BindingBuilder.bind(atualizarEmailValidadoQueue())
            .to(propostaExchange())
            .with(Messaging.ATUALIZAR_EMAIL_VALIDADO.getRoutingKey());
    }

    @Bean
    Binding analisarPosPropostaQueueToPropostaExchangeBinder() {
        return BindingBuilder.bind(analisarPosPropostaQueue())
            .to(propostaExchange())
            .with(Messaging.ANALISAR_POS_PROPOSTA.getRoutingKey());
    }

    @Bean
    Binding prePropostaAnalisadaQueueToPropostaExchangeBinder() {
        return BindingBuilder.bind(prePropostaAnalisadaQueue())
            .to(propostaExchange())
            .with(Messaging.PRE_PROPOSTA_ANALISADA_MOTOR.getRoutingKey());
    }

    @Bean
    Binding posPropostaAnalisadaQueueToPropostaExchangeBinder() {
        return BindingBuilder.bind(posPropostaAnalisadaQueue())
            .to(propostaExchange())
            .with(Messaging.POS_PROPOSTA_ANALISADA_MOTOR.getRoutingKey());
    }
}
