package br.com.camila.statemachine.service.auditoria;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import br.com.camila.statemachine.domain.Estados;
import br.com.camila.statemachine.domain.Eventos;
import br.com.camila.statemachine.domain.Tipo;
import br.com.camila.statemachine.entity.Auditoria;
import br.com.camila.statemachine.repository.AuditoriaRepository;
import br.com.camila.statemachine.statemachine.CustomStateMachineService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SalvarAuditoriaService {

    @Autowired
    private CustomStateMachineService customStateMachineService;

    @Autowired
    private AuditoriaRepository repository;

    public void executar(Long numeroProposta, String proposta) {

        Tipo tipo = Tipo.valueOf(proposta);

        StateMachine<Estados, Eventos> stateMachine = customStateMachineService.getStateMachine(numeroProposta.toString(),
            tipo);

        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);

        Auditoria auditoria = Auditoria.builder()
            .cpf(stateMachine.getExtendedState().get("cpf", String.class))
            .diaHora(timestamp)
            .numeroProposta(stateMachine.getExtendedState().get("numeroProposta", Long.class))
            .estado(stateMachine.getState().getId().name())
            .tipoProposta(stateMachine.getId()).build();

        log.info("Inicia auditoria para: {} com número: {}",
            stateMachine.getState().getId().name(),
            stateMachine.getExtendedState().get("numeroProposta", Long.class));

        repository.save(auditoria);

        log.info("Finaliza auditoria para: {} com número: {}",
            stateMachine.getState().getId().name(),
            stateMachine.getExtendedState().get("numeroProposta", Long.class));
    }
}
