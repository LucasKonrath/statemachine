package br.com.rabbit.exemplo.demo.event;

import java.io.Serializable;

import br.com.rabbit.exemplo.demo.ErrorResponse;

/**
 * Contrato para definição de eventos que representam tanto sucesso quanto erro de uma operação.
 *
 * @param <T> Representa o tipo do objeto de entrada (requisição) da operação.
 * @param <R> Representa o tipo do objeto de saída (sucesso) da operação.
 */
public interface SucessoErroEvent<T extends Serializable, R extends Serializable> extends Serializable {

    T getRequisicao();

    ErrorResponse getErro();

    R getResultado();

}
