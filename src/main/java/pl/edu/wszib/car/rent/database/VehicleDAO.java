package pl.edu.wszib.car.rent.database;

import pl.edu.wszib.car.rent.model.Bus;
import pl.edu.wszib.car.rent.model.Car;
import pl.edu.wszib.car.rent.model.Motorcycle;
import pl.edu.wszib.car.rent.model.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VehicleDAO {
    private static final VehicleDAO instance = new VehicleDAO();

    private final Connection connection;

    private VehicleDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/carrent",
                    "root",
                    "");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Vehicle> getVehicles() {
        ArrayList<Vehicle> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM tvehicle";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Vehicle.Type type = Vehicle.Type.valueOf(rs.getString("type"));
                String brand = rs.getString("brand");
                String model = rs.getString("model");
                int year = rs.getInt("year");
                double price = rs.getDouble("price");
                boolean rent = rs.getBoolean("rent");
                String plate = rs.getString("plate");

                Map<String, String> additionalAttributes;
                switch (type) {
                    case CAR -> {
                        Car car = new Car(brand, model, year, price, rent, plate);
                        result.add(car);
                    }
                    case MOTORCYCLE -> {
                        additionalAttributes =
                                getAdditionalAttributesByVehicleId(rs.getInt("id"));
                        result.add(new Motorcycle(brand, model, year,
                                price, rent, plate,
                                Boolean.parseBoolean(additionalAttributes.get("cart"))));
                    }
                    case BUS -> {
                        additionalAttributes =
                                getAdditionalAttributesByVehicleId(rs.getInt("id"));
                        result.add(new Bus(brand, model, year,
                                price, rent, plate,
                                Integer.parseInt(additionalAttributes.get("seats"))));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private Map<String, String> getAdditionalAttributesByVehicleId(int vehicleId) throws SQLException {
        Map<String, String> result = new HashMap<>();
        String sqlAdditionalAttr = "SELECT * FROM tvehicleadditionalattributes " +
                "WHERE vehicle_id = ?";
        PreparedStatement ps = this.connection.prepareStatement(sqlAdditionalAttr);
        ps.setInt(1, vehicleId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            result.put(rs.getString("name"), rs.getString("value"));
        }
        return result;
    }

    public boolean rentVehicle(String plate) {
        try {
            String sql = "SELECT * FROM tvehicle WHERE plate = ?";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setString(1, plate);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                boolean rent = rs.getBoolean("rent");
                if(!rent) {
                    String updateSql = "UPDATE tvehicle SET rent = ? WHERE id = ?";
                    int vehicleId = rs.getInt("id");

                    PreparedStatement updatePs = this.connection.prepareStatement(updateSql);
                    updatePs.setBoolean(1, true);
                    updatePs.setInt(2, vehicleId);

                    updatePs.executeUpdate();
                    return true;
                }
            }
        } catch (SQLException e) {}
        return false;
    }

    public void addVehicle(Vehicle vehicle) {
        Vehicle.Type type;
        if(vehicle instanceof Car) {
            type = Vehicle.Type.CAR;
        } else if(vehicle instanceof Bus) {
            type = Vehicle.Type.BUS;
        } else {
            type = Vehicle.Type.MOTORCYCLE;
        }

        try {
            String sql = "INSERT INTO tvehicle " +
                    "(brand, model, year, price, rent, plate, type) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, vehicle.getBrand());
            ps.setString(2, vehicle.getModel());
            ps.setInt(3, vehicle.getYear());
            ps.setDouble(4, vehicle.getPrice());
            ps.setBoolean(5, vehicle.isRent());
            ps.setString(6, vehicle.getPlate());
            ps.setString(7, type.toString());

            ps.executeUpdate();

            switch (type) {
                case BUS -> {
                    Bus bus = (Bus) vehicle;
                    addAdditionalAttribute(ps, "seats", String.valueOf(bus.getSeats()));
                }
                case MOTORCYCLE -> {
                    Motorcycle motorcycle = (Motorcycle) vehicle;
                    addAdditionalAttribute(ps, "cart", String.valueOf(motorcycle.isCart()));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addAdditionalAttribute(PreparedStatement addVehicleStatement,
                                        String name, String value) throws SQLException {
            ResultSet rs = addVehicleStatement.getGeneratedKeys();
            rs.next();
            int vehicleId = rs.getInt(1);

            String sql = "INSERT INTO tvehicleadditionalattributes " +
                    "(name, value, vehicle_id) VALUES (?,?,?)";

            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(3, vehicleId);
            ps.setString(1, name);
            ps.setString(2, value);

            ps.executeUpdate();
    }

    public static VehicleDAO getInstance() {
        return instance;
    }
}
