package br.com.iel.orders;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class OrderPriceCalculator {
    public BigDecimal calculateTotal(CreateOrderRequest request) {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItemRequest item : request.items()) {
            total = total.add(item.unitPrice().multiply(BigDecimal.valueOf(item.quantity())));
        }
        if("PROMO10".equals(request.couponCode())) {
            total = total.multiply(new BigDecimal("0.90"));
        } else if (total.compareTo(new BigDecimal("500")) > 0) {
            total = total.multiply(new BigDecimal("0.95"));
        }
        total = total.setScale(2, RoundingMode.HALF_UP);
        return total;
    }
}
