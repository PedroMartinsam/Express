package com.curso.domains.dtos;

import com.curso.domains.Pedido;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class PedidoDTO {

    private UUID idPedido;

    private Long nroPedido;

    @NotBlank(message = "O campo nome do cliente não pode estar vazio")
    private String nomeCliente;

    @NotNull(message = "O campo valor não pode ser nulo")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal valor;

    private BigDecimal valorFrete;

    private BigDecimal valorTotal;

    @NotNull(message = "O tipo de frete não pode ser nulo")
    private String tipoFrete;

    @NotNull(message = "O estado atual do pedido não pode ser nulo")
    private String estadoAtual;

    public PedidoDTO() {}

    public PedidoDTO(Pedido pedido) {
        this.idPedido = pedido.getIdPedido();
        this.nroPedido = pedido.getNroPedido();
        this.nomeCliente = pedido.getNomeCliente();
        this.valor = pedido.getValor();
        this.valorFrete = pedido.getValorFrete();
        this.valorTotal = pedido.getValorTotal();
        this.tipoFrete = pedido.getTipoFrete();
        this.estadoAtual = pedido.getEstadoAtual();
    }

    public UUID getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(UUID idPedido) {
        this.idPedido = idPedido;
    }

    public Long getNroPedido() {
        return nroPedido;
    }

    public void setNroPedido(Long nroPedido) {
        this.nroPedido = nroPedido;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(BigDecimal valorFrete) {
        this.valorFrete = valorFrete;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getTipoFrete() {
        return tipoFrete;
    }

    public void setTipoFrete(String tipoFrete) {
        this.tipoFrete = tipoFrete;
    }

    public String getEstadoAtual() {
        return estadoAtual;
    }

    public void setEstadoAtual(String estadoAtual) {
        this.estadoAtual = estadoAtual;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PedidoDTO that)) return false;
        return Objects.equals(idPedido, that.idPedido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPedido);
    }
}
