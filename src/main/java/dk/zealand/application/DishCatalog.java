package dk.zealand.application;

import dk.zealand.domain.Dish;

import java.util.List;
import java.util.Optional;

public interface DishCatalog {
    List<Dish> findAll();

    Optional<Dish> findByMenuNumber(int menuNumber);
}
