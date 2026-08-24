package dk.zealand.adapters;

import dk.zealand.application.OrderRepository;
import dk.zealand.domain.Order;

import java.util.ArrayList;
import java.util.List;

public class InMemoryOrderRepository implements OrderRepository {

    private final List<Order> orders = new ArrayList<>();
    private int nextOrderId = 1;

    @Override
    public int count() {
        return orders.size();
    }

    @Override
    public int nextOrderId() {
        return nextOrderId++;
    }

    @Override
    public void save(Order order) {
        orders.add(order);
    }
}
