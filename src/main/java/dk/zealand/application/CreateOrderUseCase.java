package dk.zealand.application;

import dk.zealand.domain.Dish;
import dk.zealand.domain.Order;
import dk.zealand.domain.OrderStatus;

import java.util.Optional;

public class CreateOrderUseCase {

    private final OrderRepository orderRepository;
    private final DishCatalog dishCatalog;
    private final int maxOrders;

    public CreateOrderUseCase(OrderRepository orderRepository, DishCatalog dishCatalog, int maxOrders) {
        this.orderRepository = orderRepository;
        this.dishCatalog = dishCatalog;
        this.maxOrders = maxOrders;
    }

    public CreateOrderResult execute(CreateOrderCommand command) {
        if (orderRepository.count() >= maxOrders) {
            return CreateOrderResult.failure("Der kan højst gemmes 10 bestillinger.");
        }

        Optional<Dish> dish = dishCatalog.findByMenuNumber(command.dishNumber());
        if (dish.isEmpty()) {
            return CreateOrderResult.failure("Ugyldig ret. Vælg en af de tre retter.");
        }

        if (command.quantity() <= 0) {
            return CreateOrderResult.failure("Ugyldigt antal. Indtast et positivt tal.");
        }

        Order order = new Order(
                orderRepository.nextOrderId(),
                dish.get(),
                command.quantity(),
                OrderStatus.MODTAGET
        );
        orderRepository.save(order);
        return CreateOrderResult.success(order);
    }
}
