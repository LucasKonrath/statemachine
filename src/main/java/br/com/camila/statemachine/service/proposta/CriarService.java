package br.com.camila.statemachine.service.proposta;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.camila.statemachine.domain.Eventos;
import br.com.camila.statemachine.domain.Tipo;
import br.com.camila.statemachine.entity.Proposta;
import br.com.camila.statemachine.repository.PropostaRepository;
import br.com.camila.statemachine.statemachine.CustomStateMachineService;

@Service
public class CriarService {

    @Autowired
    private CustomStateMachineService customStateMachineService;

    @Autowired
    private PropostaRepository repository;

    public Proposta executar(String cpf, Tipo tipo) {

        Long numeroProposta = gerarNumeroProposta();

        iniciarMaquinaDeEstados(numeroProposta, cpf, tipo);

        Proposta proposta = Proposta.builder()
            .estado(customStateMachineService.getState(numeroProposta, tipo))
            .tipoProposta(tipo.toString())
            .numero(numeroProposta)
            .cpf(cpf).build();

        return repository.save(proposta);
    }

    private Long gerarNumeroProposta() {
        Proposta ultimaProposta = repository.findTopByOrderByNumeroDesc().orElse(Proposta.builder().numero(0L).build());

        return ultimaProposta.getNumero() + 1L;
    }

    private void iniciarMaquinaDeEstados(Long numeroProposta, String cpf, Tipo proposta) {

        customStateMachineService.start(numeroProposta, proposta);

        Map<String, Object> map = new HashMap<>();
        map.put("cpf", cpf);
        map.put("numeroProposta", numeroProposta);

        customStateMachineService.setVariables(numeroProposta, map, proposta);

        customStateMachineService.sendEvent(numeroProposta, Eventos.INICIAR, proposta);
    }
}
