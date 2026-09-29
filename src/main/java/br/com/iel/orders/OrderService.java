package br.com.iel.orders;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class OrderService {

    private final Map<Long, Order> orders = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong();

    public Order createOrder(CreateOrderRequest request) {

        // VALIDAÇÃO
        if(request.customerName() == null || request.customerName().isBlank()) {
            throw new IllegalArgumentException("Nome do cliente é obrigatório");
        }
        if(request.customerEmail() == null || !request.customerEmail().contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }
        if(request.items() == null || request.items().isEmpty()) {
            throw new IllegalArgumentException("O pedido precisa ter ao menos um item");
        }
        for(OrderItemRequest item : request.items()){
            if(item.quantity() <= 0) {
                throw new IllegalArgumentException("A quantidade deve ser maior que 0");
            }
            if(item.unitPrice() == null || item.unitPrice().signum() <= 0) {
                throw new IllegalArgumentException("Preço deve ser maior que 0");
            }
        }

        // CALCULA O TOTAL DE DESCONTOS
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

        // PERSISTÊNCIA
        Long id = sequence.incrementAndGet();
        Order order = new Order(id, request.customerName(), request.customerEmail(), request.items(), total);
        orders.put(id, order);

        // NOTIFICAÇÃO
        System.out.println("Enviando email para " + request.customerEmail()
                + "pedido #" + id + " confirmado. Total: R$ " + total);

        return order;
    }
}
