package com.curso.freteStrategy;

import java.math.BigDecimal;

public interface Frete {

    public BigDecimal calcula(BigDecimal valor);
    String getFrete();
}
