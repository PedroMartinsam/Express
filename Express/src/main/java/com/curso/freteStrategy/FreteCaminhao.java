package com.curso.freteStrategy;

import java.math.BigDecimal;

public class FreteCaminhao implements Frete{

    @Override
    public BigDecimal calcula(BigDecimal valor) {
        return valor.multiply(new BigDecimal("0.05"));
    }

    @Override
    public String getFrete() {
        return "CAMINHAO";
    }
}
