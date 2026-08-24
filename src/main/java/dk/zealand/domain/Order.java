package dk.zealand.domain;

public record Order(int id, Dish dish, int quantity, OrderStatus status) {
}
