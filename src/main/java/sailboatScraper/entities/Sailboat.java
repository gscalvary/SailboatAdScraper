package sailboatScraper.entities;

import java.time.LocalDateTime;

public class Sailboat {

    private final long id;
    private final float length;
    private final int year;
    private final String make;
    private final String model;
    private final float price;
    private final String location;
    private final LocalDateTime listingDate;

    public Sailboat(long id, float length, int year, String make, String model, float price, String location, LocalDateTime listingDate) {
        this.id = id;
        this.length = length;
        this.year = year;
        this.make = make;
        this.model = model;
        this.price = price;
        this.location = location;
        this.listingDate = listingDate;
    }

    public long getId() {
        return id;
    }

    public float getLength() {
        return length;
    }

    public int getYear() {
        return year;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public float getPrice() {
        return price;
    }

    public String getLocation() {
        return location;
    }

    public LocalDateTime getListingDate() {
        return listingDate;
    }
}
