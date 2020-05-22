package HRA.exceptions;

public class PasswordTooShortException extends Exception {

    public PasswordTooShortException() {
        super(String.format("Password must contain at least 4 characters!"));

    }
}
