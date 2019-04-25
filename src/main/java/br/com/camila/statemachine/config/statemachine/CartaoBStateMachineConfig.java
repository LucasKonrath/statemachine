package br.com.camila.statemachine.config.statemachine;

import java.util.EnumSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import br.com.camila.statemachine.domain.Estados;
import br.com.camila.statemachine.domain.Eventos;
import br.com.camila.statemachine.domain.Tipo;
import br.com.camila.statemachine.service.cartaob.AnalisarPreCartaoBService;

@Configuration
@EnableStateMachineFactory(name = "CARTAO_B")
public class CartaoBStateMachineConfig extends EnumStateMachineConfigurerAdapter<Estados, Eventos> {

    @Autowired
    private AnalisarPreCartaoBService analisarPreCartaoBService;

    @Override
    public void configure(StateMachineConfigurationConfigurer<Estados, Eventos> config) throws Exception {
        config.withConfiguration()
            .machineId(Tipo.CARTAO_B.name());
    }

    @Override
    public void configure(StateMachineStateConfigurer<Estados, Eventos> states) throws Exception {

        states
            .withStates()
            .initial(Estados.P_INEXISTENTE)
            .end(Estados.NEGADO_PRE)
            .states(EnumSet.allOf(Estados.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<Estados, Eventos> transitions) throws Exception {
        transitions

            .withExternal()
            .source(Estados.P_INEXISTENTE)
            .target(Estados.P_CRIADA)
            .event(Eventos.INICIAR)

            .and()
            .withExternal()
            .source(Estados.P_CRIADA)
            .target(Estados.ANALISE_PRE)
            .event(Eventos.ANALISAR)
            .action(
                context -> {
                    analisarPreCartaoBService.executar(
                        context.getExtendedState().get("numeroProposta", Long.class),
                        context.getExtendedState().get("cpf", String.class));
                }
            )

            .and()
            .withExternal()
            .source(Estados.ANALISE_PRE)
            .target(Estados.APROVADO_PRE)
            .event(Eventos.APROVAR)

            .and()
            .withExternal()
            .source(Estados.ANALISE_PRE)
            .target(Estados.NEGADO_PRE)
            .event(Eventos.NEGAR)

            .and()
            .withExternal()
            .source(Estados.ANALISE_PRE)
            .target(Estados.PENDENTE_PRE)
            .event(Eventos.AGUARDAR);
    }
}
