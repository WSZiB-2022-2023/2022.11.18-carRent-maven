package pl.edu.wszib.car.rent.model;

public class Motorcycle extends Vehicle {
    private boolean cart;

    public Motorcycle(String brand, String model, int year, double price, String plate, boolean cart) {
        super(brand, model, year, price, plate);
        this.cart = cart;
    }

    public Motorcycle(String brand, String model, int year, double price, boolean rent, String plate, boolean cart) {
        super(brand, model, year, price, rent, plate);
        this.cart = cart;
    }

    public Motorcycle() {
    }

    public boolean isCart() {
        return cart;
    }

    public void setCart(boolean cart) {
        this.cart = cart;
    }

    @Override
    public String toString() {
        return new StringBuilder(super.toString())
                .append(" Wozek: ")
                .append(this.isCart() ? "Tak" : "Nie")
                .toString();
    }
}
