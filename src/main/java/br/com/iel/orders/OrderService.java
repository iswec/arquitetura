package br.com.iel.orders;

import br.com.iel.orders.discount.OrderPriceCalculator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderService {

    private final OrderValidator validator;
    private final OrderPriceCalculator priceCalculator;
    private final OrderRepository repository;
    private final OrderNotifier notifier;

    public OrderService(OrderValidator validator,
                        OrderPriceCalculator priceCalculator,
                        OrderRepository repository,
                        OrderNotifier notifier) {
        this.validator = validator;
        this.priceCalculator = priceCalculator;
        this.repository = repository;
        this.notifier = notifier;
    }

    public Order createOrder(CreateOrderRequest request) {
        validator.validate(request);
        BigDecimal total = priceCalculator.calculateTotal(request);
        Order order = repository.save(
                new Order(null, request.customerName(), request.customerEmail(), request.items(), total));
        notifier.notifyOrderByCreated(order);
        return order;
    }
}
