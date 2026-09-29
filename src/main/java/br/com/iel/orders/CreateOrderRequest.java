package br.com.iel.orders;

import java.util.List;

public record CreateOrderRequest(
        String customerName,
        String customerEmail,
        String couponCode,
        List<OrderItemRequest> items) {
}
