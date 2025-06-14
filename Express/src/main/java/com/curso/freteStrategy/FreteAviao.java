package com.curso.freteStrategy;

import java.math.BigDecimal;

public class FreteAviao implements Frete{

    @Override
    public BigDecimal calcula(BigDecimal valor) {
        return valor.multiply(new BigDecimal("0.1"));
    }

    @Override
    public String getFrete() {
        return "AVIAO";
    }
}
