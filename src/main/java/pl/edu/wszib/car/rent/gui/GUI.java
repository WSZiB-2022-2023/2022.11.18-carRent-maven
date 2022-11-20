package pl.edu.wszib.car.rent.gui;

import pl.edu.wszib.car.rent.core.Authenticator;
import pl.edu.wszib.car.rent.model.*;

import java.util.Scanner;

public class GUI {
    private static final Scanner scanner = new Scanner(System.in);

    public static String showMenu() {
        System.out.println("1. List vehicles");
        System.out.println("2. Rent vehicle");
        System.out.println("3. Exit");
        if(Authenticator.loggedUser != null &&
                Authenticator.loggedUser.getRole().equals("ADMIN")) {
            System.out.println("4. Add vehicle");
        }
        return scanner.nextLine();
    }

    public static void listCars(Vehicle[] vehicles) {
        for(Vehicle vehicle : vehicles) {
            System.out.println(vehicle);
        }
    }

    public static String readPlate() {
        System.out.println("Plate:");
        return scanner.nextLine();
    }

    public static void showRentResult(boolean result) {
        if(result) {
            System.out.println("Rent successful");
        } else {
            System.out.println("Plate does not exist or car is already rent");
        }
    }

    public static User readLoginAndPassword() {
        User user = new User();
        System.out.println("Login:");
        user.setLogin(scanner.nextLine());
        System.out.println("Password:");
        user.setPassword(scanner.nextLine());
        return user;
    }

    public static Vehicle readNewVehicleData() {
        System.out.println("1. Car");
        System.out.println("2. Bus");
        System.out.println("3. Motorcycle");
        String choose = scanner.nextLine();
        System.out.println("Brand:");
        String brand = scanner.nextLine();
        System.out.println("Model:");
        String model = scanner.nextLine();
        System.out.println("Year:");
        int year = Integer.parseInt(scanner.nextLine());
        System.out.println("Price:");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.println("Plate:");
        String plate = scanner.nextLine();
        switch(choose) {
            case "1":
                return new Car(brand, model, year, price, plate);
            case "2":
                System.out.println("Seats:");
                return new Bus(brand, model, year, price, plate,
                        Integer.parseInt(scanner.nextLine()));
            case "3":
                System.out.println("Cart: (Y/N)");
                return new Motorcycle(brand, model, year, price, plate,
                        scanner.nextLine().equals("Y"));
        }

        return null;
    }
}
