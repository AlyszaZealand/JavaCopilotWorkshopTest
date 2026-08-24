package dk.zealand.adapters;

import dk.zealand.application.DishCatalog;
import dk.zealand.domain.Dish;

import java.util.List;
import java.util.Optional;

public class StaticDishCatalog implements DishCatalog {

    private final List<Dish> dishes;

    public StaticDishCatalog(List<Dish> dishes) {
        this.dishes = List.copyOf(dishes);
    }

    @Override
    public List<Dish> findAll() {
        return dishes;
    }

    @Override
    public Optional<Dish> findByMenuNumber(int menuNumber) {
        int index = menuNumber - 1;
        if (index < 0 || index >= dishes.size()) {
            return Optional.empty();
        }
        return Optional.of(dishes.get(index));
    }
}
