package br.com.iel.orders.discount;

import br.com.iel.orders.CreateOrderRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Order(1)
public class BlackFridayDiscount implements DiscountRule{

    private static final BigDecimal THRESHOLD = new BigDecimal("1000");


    @Override
    public boolean isApplicable(CreateOrderRequest request, BigDecimal subtotal) {
        return "BLACKFRIDAY".equals(request.couponCode());
    }

    @Override
    public BigDecimal apply(BigDecimal subtotal) {
        return subtotal.multiply(new BigDecimal("0.80"));
    }
}
