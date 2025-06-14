package com.curso.domainsState;

import com.curso.domains.Pedido;
import com.curso.services.exceptions.OperacaoNaoPermitidaException;

public class CanceladoState implements State {

    private Pedido pedido;

    public CanceladoState(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public String getEstado() {
        return "CANCELADO";
    }

    @Override
    public void sucessoAoPagar() {
        throw new OperacaoNaoPermitidaException("Não é possível pagar um pedido que foi cancelado.");
    }

    @Override
    public void cancelarPedido() {
        throw new OperacaoNaoPermitidaException("Este pedido já está cancelado.");
    }

    @Override
    public void despacharPedido() {
        throw new OperacaoNaoPermitidaException("Não é possível despachar um pedido que foi cancelado.");
    }
}
