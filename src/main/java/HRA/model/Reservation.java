package HRA.model;

public class Reservation {

    private String roomType;
    private String numberOfRooms;
    private String checkInDate;
    private String checkOutDate;
    private String reservationState = "Pending";

    public Reservation(){}
    public Reservation(String roomType, String numberOfRooms, String checkInDate, String checkOutDate){
        this.roomType = roomType;
        this.numberOfRooms = numberOfRooms;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
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
}
