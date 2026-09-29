package br.com.iel.orders;

import java.math.BigDecimal;
import java.util.List;

public record Order(
        Long id,
        String customerName,
        String customerEmail,
        List<OrderItemRequest> items,
        BigDecimal total) {
}
