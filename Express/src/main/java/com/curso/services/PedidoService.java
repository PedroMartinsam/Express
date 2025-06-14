package com.curso.services;

import com.curso.domains.Pedido;
import com.curso.domains.dtos.PedidoDTO;
import com.curso.freteStrategy.Frete;
import com.curso.freteStrategy.FreteAviao;
import com.curso.freteStrategy.FreteCaminhao;
import com.curso.repositories.PedidoRepository;
import com.curso.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepo;

    public List<PedidoDTO> findAll() {
        return pedidoRepo.findAll().stream()
                .map(PedidoDTO::new)
                .collect(Collectors.toList());
    }

    public Pedido findById(UUID idPedido) {
        return pedidoRepo.findById(idPedido)
                .orElseThrow(() -> new ObjectNotFoundException("Pedido não encontrado! Id: " + idPedido));
    }

    public Pedido create(PedidoDTO dto) {
        dto.setIdPedido(null);
        return montarEAtualizarPedido(dto);
    }

    public Pedido update(UUID idPedido, PedidoDTO dto) {
        Pedido pedidoExistente = findById(idPedido);

        pedidoExistente.setNomeCliente(dto.getNomeCliente());
        pedidoExistente.setTipoFrete(dto.getTipoFrete());
        pedidoExistente.setValor(dto.getValor());
        pedidoExistente.setEstadoAtual(dto.getEstadoAtual());

        pedidoExistente.setFrete(getFreteStrategy(dto.getTipoFrete()));
        BigDecimal valorFrete = calculaFrete(pedidoExistente);
        pedidoExistente.setValorFrete(valorFrete);
        pedidoExistente.setValorTotal(dto.getValor().add(valorFrete));

        return pedidoRepo.save(pedidoExistente);
    }

    public void delete(UUID idPedido) {
        Pedido pedido = findById(idPedido);
        pedidoRepo.delete(pedido);
    }

    public Pedido pagar(UUID idPedido) {
        Pedido pedido = findById(idPedido);
        pedido.sucessoAoPagar();
        pedido.setEstadoAtual(pedido.getEstado().getEstado());
        return pedidoRepo.save(pedido);
    }

    public Pedido cancelar(UUID idPedido) {
        Pedido pedido = findById(idPedido);
        pedido.cancelarPedido();
        pedido.setEstadoAtual(pedido.getEstado().getEstado());
        return pedidoRepo.save(pedido);
    }

    public Pedido despachar(UUID idPedido) {
        Pedido pedido = findById(idPedido);
        pedido.despacharPedido();
        pedido.setEstadoAtual(pedido.getEstado().getEstado());
        return pedidoRepo.save(pedido);
    }

    public Frete getFreteStrategy(String tipo) {
        return switch (tipo) {
            case "AVIAO" -> new FreteAviao();
            case "CAMINHAO" -> new FreteCaminhao();
            default -> throw new IllegalArgumentException("Tipo de frete inválido: " + tipo);
        };
    }

    public BigDecimal calculaFrete(Pedido pedido) {
        return pedido.getFrete().calcula(pedido.getValor());
    }

    private Pedido montarEAtualizarPedido(PedidoDTO dto) {
        Pedido pedido = new Pedido();

        pedido.setNomeCliente(dto.getNomeCliente());
        pedido.setTipoFrete(dto.getTipoFrete());
        pedido.setValor(dto.getValor());
        pedido.setEstadoAtual(dto.getEstadoAtual());

        Frete frete = getFreteStrategy(dto.getTipoFrete());
        pedido.setFrete(frete);

        BigDecimal freteCalculado = calculaFrete(pedido);
        pedido.setValorFrete(freteCalculado);
        pedido.setValorTotal(dto.getValor().add(freteCalculado));

        Long ultimoNumero = pedidoRepo.findMaxNroPedido();
        if (ultimoNumero == null) {
            ultimoNumero = 0L;
        }
        pedido.setNroPedido(ultimoNumero + 1);

        return pedidoRepo.save(pedido);

    }
}
