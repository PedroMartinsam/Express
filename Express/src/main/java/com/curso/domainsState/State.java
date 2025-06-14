package com.curso.domainsState;

public interface State {

    void sucessoAoPagar();
    void cancelarPedido();
    void despacharPedido();
    String getEstado();

}
