package HRA.exceptions;

public class ReservationAlreadyExist extends ReservationException {
    public ReservationAlreadyExist() {
        super();
    }

    public String toString(){
        return String.format("Reservation already exist,  verify reservation log!");
    }
}
