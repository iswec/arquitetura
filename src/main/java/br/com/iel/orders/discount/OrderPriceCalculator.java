package br.com.iel.orders.discount;

import br.com.iel.orders.CreateOrderRequest;
import br.com.iel.orders.OrderItemRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class OrderPriceCalculator {

    private final List<DiscountRule> discountRules;

    public OrderPriceCalculator(List<DiscountRule> discountRules) {
        this.discountRules = discountRules;
    }

    public BigDecimal calculateTotal(CreateOrderRequest request) {
        BigDecimal subtotal = calculateSubtotal(request);

        BigDecimal total = discountRules.stream()
                .filter(rule -> rule.isApplicable(request, subtotal))
                .findFirst()
                .map(rule -> rule.apply(subtotal))
                .orElse(subtotal);

        return total.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateSubtotal(CreateOrderRequest request) {
        BigDecimal subtotal = BigDecimal.ZERO;
        for (OrderItemRequest item : request.items()) {
            subtotal = subtotal.add(item.unitPrice().multiply(BigDecimal.valueOf(item.quantity())));
        }
        return subtotal;
    }
}