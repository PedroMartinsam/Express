package com.curso.domains;

import com.curso.domains.dtos.PedidoDTO;
import com.curso.domainsState.*;
import com.curso.freteStrategy.Frete;
import com.curso.freteStrategy.FreteAviao;
import com.curso.freteStrategy.FreteCaminhao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idPedido;

    @Column(nullable = false, unique = true, updatable = false)
    private Long nroPedido;

    @NotBlank @NotNull
    private String nomeCliente;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    private BigDecimal valor;

    private BigDecimal valorFrete;

    private BigDecimal valorTotal;

    @JsonIgnore
    @Transient
    private Frete frete;

    @NotNull
    private String tipoFrete;

    @JsonIgnore
    @Transient
    private State estado;

    @NotNull
    private String estadoAtual;

    public void sucessoAoPagar() {
        if (estado == null) this.estado = createStateFromString(this.estadoAtual);
        estado.sucessoAoPagar();
    }

    public void cancelarPedido() {
        if (estado == null) this.estado = createStateFromString(this.estadoAtual);
        estado.cancelarPedido();
    }

    public void despacharPedido() {
        if (estado == null) this.estado = createStateFromString(this.estadoAtual);
        estado.despacharPedido();
    }

    private Frete createFreteFromString(String tipo) {
        return switch (tipo) {
            case "AVIAO" -> new FreteAviao();
            case "CAMINHAO" -> new FreteCaminhao();
            default -> throw new IllegalArgumentException("Tipo de frete inválido: " + tipo);
        };
    }

    private State createStateFromString(String estado) {
        return switch (estado) {
            case "AGUARDANDO_PAGAMENTO" -> new AguardandoPagamentoState(this);
            case "PAGO" -> new PagoState(this);
            case "ENVIADO" -> new EnviadoState(this);
            case "CANCELADO" -> new CanceladoState(this);
            default -> throw new IllegalArgumentException("Estado inválido: " + estado);
        };
    }
    public Pedido(){}

    public Pedido(UUID idPedido, String nomeCliente, BigDecimal valor, Frete frete, State estado) {
        this.idPedido = idPedido;
        this.nomeCliente = nomeCliente;
        this.valor = valor;
        this.frete = frete;
        if (frete != null) {
            this.tipoFrete = frete.getFrete();
        }
        if (estado != null) {
            this.setEstado(estado);
        }
    }


    public Pedido(PedidoDTO dto) {
        this.idPedido = dto.getIdPedido();
        this.nomeCliente = dto.getNomeCliente();
        this.valor = dto.getValor();
        this.valorFrete = dto.getValorFrete();
        this.valorTotal = dto.getValorTotal();
        this.tipoFrete = dto.getTipoFrete();
        this.estadoAtual = dto.getEstadoAtual();

        this.frete = createFreteFromString(tipoFrete);
        this.estado = createStateFromString(estadoAtual);
    }

    public Long getNroPedido() {
        return nroPedido;
    }

    public void setNroPedido(Long nroPedido) {
        this.nroPedido = nroPedido;
    }

    public UUID getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(UUID idPedido) {
        this.idPedido = idPedido;
    }

    public @NotBlank @NotNull String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(@NotBlank @NotNull String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    @NotNull
    @Digits(integer = 6, fraction = 2)
    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(@NotNull @Digits(integer = 10, fraction = 2) BigDecimal valor) {
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

    public Frete getFrete() {
        if (frete == null && tipoFrete != null) {
            if ("AVIAO".equals(tipoFrete)) {
                frete = new FreteAviao();
            } else if ("CAMINHAO".equals(tipoFrete)) {
                frete = new FreteCaminhao();
            }
        }
        return frete;
    }

    public void setFrete(Frete frete) {
        this.frete = frete;
        if (frete != null) {
            this.tipoFrete = frete.getFrete();
        }
    }

    public State getEstado() {
        if ("AGUARDANDO PAGAMENTO".equals(estadoAtual)) {
            return new AguardandoPagamentoState(this);
        } else if ("PAGO".equals(estadoAtual)) {
            return new PagoState(this);
        } else if ("ENVIADO".equals(estadoAtual)) {
            return new EnviadoState(this);
        } else if ("CANCELADO".equals(estadoAtual)) {
            return new CanceladoState(this);
        }
        return null;
    }

    public void setEstado(State estado) {
        this.estadoAtual = estado.getEstado();
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
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(idPedido, pedido.idPedido) && Objects.equals(nroPedido, pedido.nroPedido) && Objects.equals(nomeCliente, pedido.nomeCliente) && Objects.equals(valor, pedido.valor) && Objects.equals(valorFrete, pedido.valorFrete) && Objects.equals(valorTotal, pedido.valorTotal) && Objects.equals(frete, pedido.frete) && Objects.equals(tipoFrete, pedido.tipoFrete) && Objects.equals(estado, pedido.estado) && Objects.equals(estadoAtual, pedido.estadoAtual);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPedido, nroPedido, nomeCliente, valor, valorFrete, valorTotal, frete, tipoFrete, estado, estadoAtual);
    }
}
