package br.com.iel.orders.discount;

import br.com.iel.orders.CreateOrderRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Order(2)
public class BulkPurchaseDiscount implements DiscountRule{

    private static final BigDecimal THRESHOLD = new BigDecimal("500");

    @Override
    public boolean isApplicable(CreateOrderRequest request, BigDecimal subtotal) {
        return subtotal.compareTo(THRESHOLD) > 0;
    }

    @Override
    public BigDecimal apply(BigDecimal subtotal) {
        return subtotal.multiply(new BigDecimal("0.95"));
    }
}
