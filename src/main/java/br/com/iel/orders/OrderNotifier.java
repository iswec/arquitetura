package br.com.iel.orders;

import org.springframework.stereotype.Component;

@Component
public class OrderNotifier {
    public void notifyOrderByCreated(Order order) {
        System.out.println("Enviando email para " + order.customerEmail()
                + "pedido #" + order.id() + " confirmado. Total: R$ " + order.total());
    }
}
