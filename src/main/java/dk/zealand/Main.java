package dk.zealand;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final int MAX_ORDERS = 10;
    private static final String[][] DISHES = {
            {"Festivalburger", "59"},
            {"Sprøde fritter", "35"},
            {"Vegansk bowl", "65"}
    };
    private static final List<Order> ORDERS = new ArrayList<>();
    private static int nextOrderId = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("ByteBites – festivalens foodtruck");

        while (running && scanner.hasNextLine()) {
            showMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> showDishes();
                case "2" -> createOrder(scanner);
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

    private static void showDishes() {
        System.out.println("Retter:");

        for (int i = 0; i < DISHES.length; i++) {
            String[] dish = DISHES[i];
            System.out.printf("%d. %s - %s kr.%n", i + 1, dish[0], dish[1]);
        }
    }

    private static void createOrder(Scanner scanner) {
        if (ORDERS.size() >= MAX_ORDERS) {
            System.out.println("Der kan højst gemmes 10 bestillinger.");
            return;
        }

        System.out.println("Vælg ret:");
        showDishes();
        System.out.print("Ret: ");
        String dishChoice = scanner.nextLine().trim();

        int dishIndex;
        try {
            dishIndex = Integer.parseInt(dishChoice) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Ugyldig ret. Vælg en af de tre retter.");
            return;
        }

        if (dishIndex < 0 || dishIndex >= DISHES.length) {
            System.out.println("Ugyldig ret. Vælg en af de tre retter.");
            return;
        }

        System.out.print("Antal: ");
        String quantityInput = scanner.nextLine().trim();

        int quantity;
        try {
            quantity = Integer.parseInt(quantityInput);
        } catch (NumberFormatException e) {
            System.out.println("Ugyldigt antal. Indtast et positivt tal.");
            return;
        }

        if (quantity <= 0) {
            System.out.println("Ugyldigt antal. Indtast et positivt tal.");
            return;
        }

        String[] dish = DISHES[dishIndex];
        Order order = new Order(nextOrderId++, dish[0], quantity, "MODTAGET");
        ORDERS.add(order);

        System.out.println();
        System.out.println("Bestilling oprettet:");
        System.out.printf("#%d %s x %d - %s%n", order.id, order.dish, order.quantity, order.status);
    }

    private static class Order {
        private final int id;
        private final String dish;
        private final int quantity;
        private final String status;

        private Order(int id, String dish, int quantity, String status) {
            this.id = id;
            this.dish = dish;
            this.quantity = quantity;
            this.status = status;
        }
    }
}
