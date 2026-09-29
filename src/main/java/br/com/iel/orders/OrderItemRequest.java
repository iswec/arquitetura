package br.com.iel.orders;

import java.math.BigDecimal;

public record OrderItemRequest(String productName, int quantity, BigDecimal unitPrice) {

}