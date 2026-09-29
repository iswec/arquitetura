package br.com.iel.orders;

import org.springframework.stereotype.Component;

@Component
public class OrderValidator {

    public void validate(CreateOrderRequest request) {
        if (request.customerName() == null || request.customerName().isBlank()) {
            throw new IllegalArgumentException("Nome do cliente é obrigatório");
        }
        if (request.customerEmail() == null || !request.customerEmail().contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }
        if (request.items() == null || request.items().isEmpty()) {
            throw new IllegalArgumentException("O pedido precisa ter ao menos um item");
        }
        for (OrderItemRequest item : request.items()) {
            if (item.quantity() <= 0) {
                throw new IllegalArgumentException("A quantidade deve ser maior que 0");
            }
            if (item.unitPrice() == null || item.unitPrice().signum() <= 0) {
                throw new IllegalArgumentException("Preço deve ser maior que 0");
            }
        }
    }
}
