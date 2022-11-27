package pl.edu.wszib.car.rent.gui;

import pl.edu.wszib.car.rent.core.Authenticator;
import pl.edu.wszib.car.rent.database.VehicleDB;
import pl.edu.wszib.car.rent.model.*;

import java.util.Scanner;

public class GUI {
    private final Scanner scanner = new Scanner(System.in);
    final Authenticator authenticator = Authenticator.getInstance();
    final VehicleDB vehicleDB = VehicleDB.getInstance();
    private static final GUI instance = new GUI();

    private GUI() {
    }

    public String showMenu() {
        System.out.println("1. List vehicles");
        System.out.println("2. Rent vehicle");
        System.out.println("3. Exit");
        if(this.authenticator.getLoggedUser() != null &&
                this.authenticator.getLoggedUser().getRole() == User.Role.ADMIN) {
            System.out.println("4. Add vehicle");
        }
        return scanner.nextLine();
    }

    public void listCars() {
        for(Vehicle vehicle : this.vehicleDB.getVehicles()) {
            System.out.println(vehicle);
        }
    }

    public String readPlate() {
        System.out.println("Plate:");
        return this.scanner.nextLine();
    }

    public void showRentResult(boolean result) {
        if(result) {
            System.out.println("Rent successful");
        } else {
            System.out.println("Plate does not exist or car is already rent");
        }
    }

    public User readLoginAndPassword() {
        User user = new User();
        System.out.println("Login:");
        user.setLogin(this.scanner.nextLine());
        System.out.println("Password:");
        user.setPassword(this.scanner.nextLine());
        return user;
    }

    public Vehicle readNewVehicleData() {
        System.out.println("1. Car");
        System.out.println("2. Bus");
        System.out.println("3. Motorcycle");
        String choose = this.scanner.nextLine();
        System.out.println("Brand:");
        String brand = this.scanner.nextLine();
        System.out.println("Model:");
        String model = this.scanner.nextLine();
        System.out.println("Year:");
        int year = Integer.parseInt(this.scanner.nextLine());
        System.out.println("Price:");
        double price = Double.parseDouble(this.scanner.nextLine());
        System.out.println("Plate:");
        String plate = this.scanner.nextLine();
        switch(choose) {
            case "1":
                return new Car(brand, model, year, price, plate);
            case "2":
                System.out.println("Seats:");
                return new Bus(brand, model, year, price, plate,
                        Integer.parseInt(this.scanner.nextLine()));
            case "3":
                System.out.println("Cart: (Y/N)");
                return new Motorcycle(brand, model, year, price, plate,
                        this.scanner.nextLine().equals("Y"));
        }

        return null;
    }

    public static GUI getInstance() {
        return instance;
    }
}
