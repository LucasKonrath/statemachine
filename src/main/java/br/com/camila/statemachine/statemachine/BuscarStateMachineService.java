package br.com.camila.statemachine.statemachine;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;

import br.com.camila.statemachine.domain.Estados;
import br.com.camila.statemachine.domain.Eventos;
import br.com.camila.statemachine.domain.Tipo;

@Service
public class BuscarStateMachineService {

    @Autowired
    @Qualifier("CARTAO_A")
    private StateMachineFactory<Estados, Eventos> cartaoA;

    @Autowired
    @Qualifier("CARTAO_B")
    private StateMachineFactory<Estados, Eventos> cartaoB;

    public StateMachine<Estados, Eventos> executar(final Tipo proposta) {

        if (proposta.name().equals(Tipo.CARTAO_A.name())) {
            return cartaoA.getStateMachine(proposta.name());
        }

        if (proposta.name().equals(Tipo.CARTAO_B.name())) {
            return cartaoB.getStateMachine(proposta.name());
        }

        return null;
    }
}
