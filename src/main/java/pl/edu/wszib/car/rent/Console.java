package pl.edu.wszib.car.rent;

public enum Console {
    PS4("Sony", "PlayStation 4", 2010),
    PS5("Sony", "PlayStation 5", 2020),
    XBOX("Microsoft", "Xbox", 2002),
    NINTENDO("Nintendo", "Switch", 2014);
    private String brand;
    private String model;
    private int year;

    Console(String brand, String model, int year) {
        this.brand = brand;
        this.model = model;
        this.year = year;
    }

    public String getBrand() {
        return brand;
    }
    public String getModel() {
        return model;
    }
    public int getYear() {
        return year;
    }
}
