package HRA.exceptions;

public class NumberOfRoomsException extends ReservationException {

    public NumberOfRoomsException(){
        super();
    }

    public String toString(){
        return String.format("Number of rooms is invalid!");
    }

}
