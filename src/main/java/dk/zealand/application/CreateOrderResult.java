package dk.zealand.application;

import dk.zealand.domain.Order;

import java.util.Optional;

public record CreateOrderResult(boolean success, String message, Optional<Order> order) {

    public static CreateOrderResult success(Order order) {
        return new CreateOrderResult(true, "", Optional.of(order));
    }

    public static CreateOrderResult failure(String message) {
        return new CreateOrderResult(false, message, Optional.empty());
    }
}
