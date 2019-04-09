package br.com.statemachine.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.statemachine.message.AnalisarPrePropostaMessage;
import br.com.statemachine.response.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString(of = "requisicao")
public class AnalisarPrePropostaEvent implements SucessoErroEvent<AnalisarPrePropostaMessage, String> {

    private static final long serialVersionUID = 1534990062660000468L;

    private final AnalisarPrePropostaMessage requisicao;

    private final String resultado;

    private final ErrorResponse erro;

}
