package HRA.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Reservation {

    private String customerName;
    private String roomType;
    private String numberOfRooms;
    private String checkInDate;
    private String checkOutDate;
    private String reservationState = "Pending";
    private String currentTime;
    private String hotelName;

    public Reservation(){}

    public Reservation(String roomType, String numberOfRooms, String checkInDate, String checkOutDate, String customerName, String currentTime, String hotelName){
        this.roomType = roomType;
        this.numberOfRooms = numberOfRooms;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.customerName = customerName;
        this.currentTime = currentTime;
        this.hotelName = hotelName;
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

    public String getReservationState() {
        return reservationState;
    }

    public void setReservationState(String reservationState) {
        this.reservationState = reservationState;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "customerName='" + customerName + '\'' +
                ", roomType='" + roomType + '\'' +
                ", numberOfRooms='" + numberOfRooms + '\'' +
                ", checkInDate='" + checkInDate + '\'' +
                ", checkOutDate='" + checkOutDate + '\'' +
                ", reservationState='" + reservationState + '\'' +
                ", reservationDate='" + currentTime + '\'' +
                ", hotelName='" + hotelName + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        int result = customerName.hashCode();
        result += 31 * roomType.hashCode();
        result += 31 * numberOfRooms.hashCode();
        result += 31 * checkInDate.hashCode();
        result += 31 * checkOutDate.hashCode();
        result += 31 * customerName.hashCode();
        result += 31 * hotelName.hashCode();
        return result;
    }

}
