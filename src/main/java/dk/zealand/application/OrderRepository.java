package dk.zealand.application;

import dk.zealand.domain.Order;

public interface OrderRepository {
    int count();

    int nextOrderId();

    void save(Order order);
}
