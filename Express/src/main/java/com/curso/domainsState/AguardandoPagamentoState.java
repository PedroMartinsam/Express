package com.curso.domainsState;

import com.curso.domains.Pedido;
import com.curso.services.exceptions.OperacaoNaoPermitidaException;

public class AguardandoPagamentoState implements State {

    private Pedido pedido;

    public AguardandoPagamentoState(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public String getEstado() {
        return "AGUARDANDO_PAGAMENTO";
    }

    @Override
    public void sucessoAoPagar() {
        this.pedido.setEstado(new PagoState(pedido));
    }

    @Override
    public void cancelarPedido() {
        this.pedido.setEstado(new CanceladoState(pedido));
    }

    @Override
    public void despacharPedido() {
        throw new OperacaoNaoPermitidaException("Não é possível despachar um pedido que ainda não foi pago.");
    }
}
