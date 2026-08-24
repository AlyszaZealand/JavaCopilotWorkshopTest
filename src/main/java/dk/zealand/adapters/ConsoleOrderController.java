package dk.zealand.adapters;

import dk.zealand.application.CreateOrderCommand;
import dk.zealand.application.CreateOrderResult;
import dk.zealand.application.CreateOrderUseCase;
import dk.zealand.application.DishCatalog;
import dk.zealand.domain.Dish;
import dk.zealand.domain.Order;

import java.util.List;
import java.util.Scanner;

public class ConsoleOrderController {

    private final DishCatalog dishCatalog;
    private final CreateOrderUseCase createOrderUseCase;

    public ConsoleOrderController(DishCatalog dishCatalog, CreateOrderUseCase createOrderUseCase) {
        this.dishCatalog = dishCatalog;
        this.createOrderUseCase = createOrderUseCase;
    }

    public void showDishes() {
        List<Dish> dishes = dishCatalog.findAll();
        System.out.println("Retter:");

        for (int i = 0; i < dishes.size(); i++) {
            Dish dish = dishes.get(i);
            System.out.printf("%d. %s - %d kr.%n", i + 1, dish.name(), dish.priceInDkk());
        }
    }

    public void createOrder(Scanner scanner) {
        System.out.println("Vælg ret:");
        showDishes();
        System.out.print("Ret: ");
        if (!scanner.hasNextLine()) {
            return;
        }

        Integer dishNumber = parsePositiveInt(scanner.nextLine().trim());
        if (dishNumber == null) {
            System.out.println("Ugyldig ret. Vælg en af de tre retter.");
            return;
        }
        if (dishCatalog.findByMenuNumber(dishNumber).isEmpty()) {
            System.out.println("Ugyldig ret. Vælg en af de tre retter.");
            return;
        }

        System.out.print("Antal: ");
        if (!scanner.hasNextLine()) {
            return;
        }

        Integer quantity = parseInt(scanner.nextLine().trim());
        if (quantity == null) {
            System.out.println("Ugyldigt antal. Indtast et positivt tal.");
            return;
        }

        CreateOrderResult result = createOrderUseCase.execute(new CreateOrderCommand(dishNumber, quantity));
        if (!result.success()) {
            System.out.println(result.message());
            return;
        }

        Order order = result.order().orElseThrow();
        System.out.println();
        System.out.println("Bestilling oprettet:");
        System.out.printf(
                "#%d %s x %d - %s%n",
                order.id(),
                order.dish().name(),
                order.quantity(),
                order.status().name()
        );
    }

    private Integer parsePositiveInt(String value) {
        Integer parsed = parseInt(value);
        if (parsed == null || parsed <= 0) {
            return null;
        }
        return parsed;
    }

    private Integer parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
