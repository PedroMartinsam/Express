package com.curso.services.exceptions;

public class OperacaoNaoPermitidaException extends RuntimeException {
    public OperacaoNaoPermitidaException(String mensagem) {
        super(mensagem);
    }
}
