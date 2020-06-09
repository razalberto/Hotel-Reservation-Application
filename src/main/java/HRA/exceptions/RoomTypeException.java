package HRA.exceptions;

public class RoomTypeException extends ReservationException {
    public RoomTypeException() {
        super();
    }

    public String toString(){
        return String.format("Room type not selected!");
    }
}
