package br.com.camila.statemachine.statemachine;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.stereotype.Service;

import br.com.camila.statemachine.config.statemachine.TransitionConfig;
import br.com.camila.statemachine.domain.Estados;
import br.com.camila.statemachine.domain.Eventos;
import br.com.camila.statemachine.domain.Tipo;
import br.com.camila.statemachine.repository.StateMachineRepository;

@Service
public class CustomStateMachineService {

    @Autowired
    private BuscarStateMachineService buscarStateMachineService;

    @Autowired
    private StateMachineRepository repository;

    @Autowired
    private CustomStateMachinePersist persist;

    @Autowired
    private CustomStateMachineInterceptor interceptor;

    private Map<String, StateMachine<Estados, Eventos>> cache = new ConcurrentHashMap<>();

    public void start(final Long numeroProposta, final Tipo proposta) {
        final StateMachine<Estados, Eventos> stateMachine = getStateMachine(numeroProposta.toString(), proposta);
        stateMachine.getExtendedState().getVariables().put("numeroProposta", numeroProposta);
        stateMachine.start();
    }

    public String getState(final Long numeroProposta, final Tipo proposta) {
        final StateMachine<Estados, Eventos> stateMachine = getStateMachine(numeroProposta.toString(), proposta);
        return stateMachine.getState().getId().name();
    }

    public void setVariables(final Long numeroProposta, final Map<String, Object> variables, final Tipo proposta) {
        final StateMachine<Estados, Eventos> stateMachine = getStateMachine(numeroProposta.toString(), proposta);
        stateMachine.getExtendedState().getVariables().putAll(variables);
    }

    public void sendEvent(final Long numeroProposta, final Eventos evento, final Tipo proposta) {
        final String id = numeroProposta.toString();
        final StateMachine<Estados, Eventos> stateMachine = getStateMachine(id, proposta);
        stateMachine.sendEvent(evento);
    }

    public StateMachine getStateMachine(final String id, final Tipo proposta) {

        if (cache.containsKey(id)) {
            return cache.get(id);
        }

        StateMachine<Estados, Eventos> stateMachine = buscarStateMachineService.executar(proposta);

        stateMachine.getStateMachineAccessor().withRegion().addStateMachineInterceptor(interceptor);
        stateMachine.addStateListener(new TransitionConfig());

        cache.put(id, stateMachine);

        if (repository.findByIdMaquina(id).isPresent()) {
            restoreStateMachine(stateMachine, id);
            stateMachine.start();
        }

        return stateMachine;
    }

    private void restoreStateMachine(StateMachine<Estados, Eventos> stateMachine, final String id) {
        try {
            final StateMachineContext<Estados, Eventos> context = persist.read(id);

            stateMachine.getStateMachineAccessor().doWithAllRegions(
                    function -> function.resetStateMachine(context));

        } catch (Exception ex) {
            throw new IllegalArgumentException("StateMachine ausente", ex);
        }
    }
}
