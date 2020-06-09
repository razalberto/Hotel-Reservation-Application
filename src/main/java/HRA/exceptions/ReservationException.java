package HRA.exceptions;

public class ReservationException extends Exception{
    public ReservationException() {
        super();
    }

    public String toString(){
        return String.format("Reservation is invalid!");
    }

}
