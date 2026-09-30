package br.com.iel.orders.discount;

import br.com.iel.orders.CreateOrderRequest;

import java.math.BigDecimal;

public interface DiscountRule {

    boolean isApplicable(CreateOrderRequest request, BigDecimal subtotal);

    BigDecimal apply(BigDecimal subtotal);
}
