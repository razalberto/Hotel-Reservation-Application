package HRA.exceptions;

public class CheckInOutDateException extends ReservationException {

    public CheckInOutDateException(){
        super();
    }

    public String toString(){
        return String.format("Check in or check out date is invalid! (dd-mm-yyyy)");
    }

}
