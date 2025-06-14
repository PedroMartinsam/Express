package com.curso.domainsState;

import com.curso.domains.Pedido;
import com.curso.services.exceptions.OperacaoNaoPermitidaException;

public class PagoState implements State {

    private Pedido pedido;

    public PagoState(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public String getEstado() {
        return "PAGO";
    }

    @Override
    public void sucessoAoPagar() {
        throw new OperacaoNaoPermitidaException("Não é possível pagar um pedido que já está pago.");
    }

    @Override
    public void cancelarPedido() {
        this.pedido.setEstado(new CanceladoState(pedido));
    }

    @Override
    public void despacharPedido() {
        this.pedido.setEstado(new EnviadoState(pedido));
    }
}
