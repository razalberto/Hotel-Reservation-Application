package registration.exceptions;

public class PasswordTooShortException extends Exception {
    private String password;

    public PasswordTooShortException() {
        super(String.format("Password must contain at least 4 characters!"));

    }
    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
