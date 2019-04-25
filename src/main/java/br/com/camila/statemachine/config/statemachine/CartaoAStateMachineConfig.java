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
import br.com.camila.statemachine.service.cartaoa.AnalisarPosService;
import br.com.camila.statemachine.service.cartaoa.AnalisarPreService;
import br.com.camila.statemachine.service.cartaoa.AtualizarEmailValidadoService;
import br.com.camila.statemachine.service.cartaoa.AtualizarInfosPessoaisService;

@Configuration
@EnableStateMachineFactory(name = "CARTAO_A")
public class CartaoAStateMachineConfig extends EnumStateMachineConfigurerAdapter<Estados, Eventos> {

    @Autowired
    private AnalisarPreService analisarPreService;

    @Autowired
    private AtualizarInfosPessoaisService atualizarInfosPessoaisService;

    @Autowired
    private AtualizarEmailValidadoService atualizarEmailValidadoService;

    @Autowired
    private AnalisarPosService analisarPosService;

    @Override
    public void configure(StateMachineConfigurationConfigurer<Estados, Eventos> config) throws Exception {
        config.withConfiguration()
            .machineId(Tipo.CARTAO_A.name());
    }

    @Override
    public void configure(StateMachineStateConfigurer<Estados, Eventos> states) throws Exception {

        states
            .withStates()
            .initial(Estados.P_INEXISTENTE)
            .end(Estados.NEGADO_PRE)
            .end(Estados.NEGADO_POS)
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
                    analisarPreService.executar(
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
            .source(Estados.APROVADO_PRE)
            .target(Estados.INFOS_PESSOAIS)
            .event(Eventos.ATUALIZAR)
            .action(
                context -> {
                    atualizarInfosPessoaisService.executar(
                        context.getExtendedState().get("numeroProposta", Long.class),
                        context.getExtendedState().get("cpf", String.class));
                }
            )

            .and()
            .withExternal()
            .source(Estados.INFOS_PESSOAIS)
            .target(Estados.EMAIL_VALIDADO)
            .event(Eventos.ATUALIZAR)
            .action(
                context -> {
                    atualizarEmailValidadoService.executar(
                        context.getExtendedState().get("numeroProposta", Long.class),
                        context.getExtendedState().get("cpf", String.class));
                }
            )

            .and()
            .withExternal()
            .source(Estados.EMAIL_VALIDADO)
            .target(Estados.ANALISE_POS)
            .event(Eventos.ANALISAR)
            .action(
                context -> {
                    analisarPosService.executar(
                        context.getExtendedState().get("numeroProposta", Long.class),
                        context.getExtendedState().get("cpf", String.class));
                }
            )

            .and()
            .withExternal()
            .source(Estados.ANALISE_POS)
            .target(Estados.NEGADO_POS)
            .event(Eventos.NEGAR)

            .and()
            .withExternal()
            .source(Estados.ANALISE_POS)
            .target(Estados.APROVADO_POS)
            .event(Eventos.APROVAR);
    }
}
