package pl.edu.wszib.car.rent.core;

import pl.edu.wszib.car.rent.database.UserDB;
import pl.edu.wszib.car.rent.database.VehicleDB;
import pl.edu.wszib.car.rent.gui.GUI;

public class Core {
    public static void start() {
        final VehicleDB vehicleDB = new VehicleDB();
        final UserDB userDB = new UserDB();
        boolean isRunning = false;
        int counter = 0;

        while(!isRunning && counter < 3) {
            Authenticator.authenticate(GUI.readLoginAndPassword(), userDB);
            isRunning = Authenticator.loggedUser != null;
            if(!isRunning) {
                System.out.println("Not authorized !!");
            }
            counter++;
        }

        while(isRunning) {
            switch(GUI.showMenu()) {
                case "1":
                    GUI.listCars(vehicleDB.getVehicles());
                    break;
                case "2":
                    GUI.showRentResult(vehicleDB.rentVehicle(GUI.readPlate()));
                    break;
                case "3":
                    isRunning = false;
                    break;
                case "4":
                    if(Authenticator.loggedUser != null && Authenticator.loggedUser.getRole().equals("ADMIN")) {
                        vehicleDB.addVehicle(GUI.readNewVehicleData());
                        break;
                    }
                default:
                    System.out.println("Wrong choose !!");
                    break;
            }
        }
    }
}
