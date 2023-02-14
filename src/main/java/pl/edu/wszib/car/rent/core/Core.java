package pl.edu.wszib.car.rent.core;

import pl.edu.wszib.car.rent.database.VehicleDAO;
import pl.edu.wszib.car.rent.database.VehicleDB;
import pl.edu.wszib.car.rent.gui.GUI;
import pl.edu.wszib.car.rent.model.User;

public class Core {
    final VehicleDAO vehicleDB = VehicleDAO.getInstance();
    final Authenticator authenticator = Authenticator.getInstance();
    final GUI gui = GUI.getInstance();
    private static final Core instance = new Core();

    private Core() {

    }
    public void start() {
        boolean isRunning = false;
        int counter = 0;

        while(!isRunning && counter < 3) {
            this.authenticator.authenticate(this.gui.readLoginAndPassword());
            isRunning = this.authenticator.getLoggedUser() != null;
            if(!isRunning) {
                System.out.println("Not authorized !!");
            }
            counter++;
        }

        while(isRunning) {
            switch(this.gui.showMenu()) {
                case "1":
                    this.gui.listCars();
                    break;
                case "2":
                    this.gui.showRentResult(this.vehicleDB.rentVehicle(this.gui.readPlate()));
                    break;
                case "3":
                    isRunning = false;
                    break;
                case "4":
                    if(this.authenticator.getLoggedUser() != null &&
                            this.authenticator.getLoggedUser().getRole() == User.Role.ADMIN) {
                        this.vehicleDB.addVehicle(this.gui.readNewVehicleData());
                        break;
                    }
                default:
                    System.out.println("Wrong choose !!");
                    break;
            }
        }
    }

    public static Core getInstance() {
        return instance;
    }
}
