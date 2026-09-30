package br.com.iel.orders.discount;

import br.com.iel.orders.CreateOrderRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Order(1)
public class Promo10CouponDiscount implements DiscountRule {

    @Override
    public boolean isApplicable(CreateOrderRequest request, BigDecimal subtotal) {
        return "PROMO10".equals(request.couponCode());
    }

    @Override
    public BigDecimal apply(BigDecimal subtotal) {
        return subtotal.multiply(new BigDecimal("0.90"));
    }

}
