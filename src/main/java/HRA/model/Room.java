package HRA.model;

public class Room {

    private String type;
    private String capacity;
    private Double price;

    public Room(){

    }

    public Room(String type, String capacity, Double price){
        this.type = type;
        this.capacity = capacity;
        this.price = price;

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

