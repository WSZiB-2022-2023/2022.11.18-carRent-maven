package pl.edu.wszib.car.rent.database;

import pl.edu.wszib.car.rent.model.Bus;
import pl.edu.wszib.car.rent.model.Car;
import pl.edu.wszib.car.rent.model.Motorcycle;
import pl.edu.wszib.car.rent.model.Vehicle;

public class VehicleDB {
    private Vehicle[] vehicles = new Vehicle[8];

    public VehicleDB() {
        this.vehicles[0] = new Car("Audi", "A5",
                2015, 400.00, "KR11");
        this.vehicles[1] = new Car("BMW", "5",
                2016, 450.00, "KR22");
        this.vehicles[2] = new Car("Toyota", "Corolla",
                2018, 350.00, "KR33");
        this.vehicles[3] = new Car("Kia", "Ceed",
                2014, 200.00, "KR44");
        this.vehicles[4] = new Car("Mercedes", "E",
                2017, 480.00, "KR55");

        this.vehicles[5] = new Bus("Solaris", "2000",
                2015, 600.00, "KR66", 52);
        this.vehicles[6] = new Bus("Mercedes", "B100",
                2016, 650.00, "KR77", 65);

        this.vehicles[7] = new Motorcycle("Honda", "S1000",
                2019, 399.00, "KR88", true);
    }

    public Vehicle[] getVehicles() {
        return vehicles;
    }

    public boolean rentVehicle(String plate) {
        for(Vehicle vehicle : this.vehicles) {
            if(vehicle.getPlate().equals(plate) && !vehicle.isRent()) {
                vehicle.setRent(true);
                return true;
            }
        }
        return false;
    }

    public void addVehicle(Vehicle vehicle) {
        Vehicle[] newVehicles = new Vehicle[this.vehicles.length + 1];
        for(int i = 0; i < this.vehicles.length; i++) {
            newVehicles[i] = this.vehicles[i];
        }
        newVehicles[newVehicles.length - 1] = vehicle;
        this.vehicles = newVehicles;
    }
}
