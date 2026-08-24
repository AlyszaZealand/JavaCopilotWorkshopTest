package dk.zealand;

import dk.zealand.adapters.ConsoleOrderController;
import dk.zealand.adapters.InMemoryOrderRepository;
import dk.zealand.adapters.StaticDishCatalog;
import dk.zealand.application.CreateOrderUseCase;
import dk.zealand.application.DishCatalog;
import dk.zealand.application.OrderRepository;
import dk.zealand.domain.Dish;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final int MAX_ORDERS = 10;

    public static void main(String[] args) {
        DishCatalog dishCatalog = new StaticDishCatalog(List.of(
                new Dish("Festivalburger", 59),
                new Dish("Sprøde fritter", 35),
                new Dish("Vegansk bowl", 65)
        ));
        OrderRepository orderRepository = new InMemoryOrderRepository();
        CreateOrderUseCase createOrderUseCase = new CreateOrderUseCase(orderRepository, dishCatalog, MAX_ORDERS);
        ConsoleOrderController controller = new ConsoleOrderController(dishCatalog, createOrderUseCase);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("ByteBites – festivalens foodtruck");

        while (running && scanner.hasNextLine()) {
            showMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> controller.showDishes();
                case "2" -> controller.createOrder(scanner);
                case "0" -> running = false;
                default -> System.out.println(
                        "Ugyldigt valg. Vælg 0, 1 eller 2."
                );
            }
        }

        System.out.println("Programmet er afsluttet.");
    }

    private static void showMenu() {
        System.out.println();
        System.out.println("1. Vis retter");
        System.out.println("2. Opret bestilling");
        System.out.println("0. Afslut");
        System.out.print("Vælg: ");
    }
}
