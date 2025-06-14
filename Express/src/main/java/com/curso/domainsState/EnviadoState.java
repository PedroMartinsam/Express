package com.curso.domainsState;

import com.curso.domains.Pedido;
import com.curso.services.exceptions.OperacaoNaoPermitidaException;

public class EnviadoState implements State {

    private Pedido pedido;

    public EnviadoState(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public String getEstado() {
        return "ENVIADO";
    }

    @Override
    public void sucessoAoPagar() {
        throw new OperacaoNaoPermitidaException("Não é possível pagar um pedido que já foi enviado.");
    }

    @Override
    public void cancelarPedido() {
        throw new OperacaoNaoPermitidaException("Não é possível cancelar um pedido que já foi enviado.");
    }

    @Override
    public void despacharPedido() {
        throw new OperacaoNaoPermitidaException("O pedido já foi enviado.");
    }
}
