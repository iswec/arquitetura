package br.com.iel.orders;

import org.apache.juli.logging.Log;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class OrderRepository {

    private final Map<Long, Order> orders = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong();

    public Order save(Order order) {
        Long id = sequence.incrementAndGet();
        Order saved = new Order(id, order.customerName(), order.customerEmail(), order.items(), order.total());
        orders.put(id, order);
        return saved;
    }
}
