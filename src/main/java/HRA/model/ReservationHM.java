package HRA.model;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class ReservationHM {

    private String customerName;
    private String roomType;
    private String numberOfRooms;
    private String checkInDate;
    private String checkOutDate;
    private ChoiceBox<String> status;
    private TextField message;


    public ReservationHM(){}

    public ReservationHM(String roomType, String numberOfRooms, String checkInDate, String checkOutDate,ChoiceBox<String> status,TextField message){
        this.roomType = roomType;
        this.numberOfRooms = numberOfRooms;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = status;
        this.message = message;

    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(String numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public ChoiceBox getStatus() {
        return status;
    }

    public void setStatus(ChoiceBox<String> status) {
        this.status = status;
    }

    public TextField getMessage() {
        return message;
    }

    public void setMessage(TextField message) {
        this.message = message;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
