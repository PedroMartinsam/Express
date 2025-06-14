package com.curso.services;

import com.curso.domains.dtos.PedidoDTO;
import com.curso.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DBService {

    @Autowired
    private PedidoRepository pedidoRepo;

    @Autowired
    private PedidoService pedidoService;

    public void initDB() {
        PedidoDTO dto1 = new PedidoDTO();
        dto1.setNomeCliente("DOUGLAS");
        dto1.setValor(new BigDecimal("15860.00"));
        dto1.setTipoFrete("CAMINHAO");
        dto1.setEstadoAtual("AGUARDANDO_PAGAMENTO");

        PedidoDTO dto2 = new PedidoDTO();
        dto2.setNomeCliente("AMANDA");
        dto2.setValor(new BigDecimal("15220.00"));
        dto2.setTipoFrete("AVIAO");
        dto2.setEstadoAtual("AGUARDANDO_PAGAMENTO");

        pedidoService.create(dto1);
        pedidoService.create(dto2);
    }
}
