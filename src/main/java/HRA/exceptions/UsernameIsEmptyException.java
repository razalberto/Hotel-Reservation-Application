package HRA.exceptions;

public class UsernameIsEmptyException extends Exception {
    public UsernameIsEmptyException() {
        super(String.format("Username field cannot be empty!"));

    }
}
