package HRA.exceptions;

public class IncorrectPassword extends Exception {

    public IncorrectPassword(){
        super(String.format("Password is incorrect!"));
    }

    public String toString(){
        return "Password is incorrect!";
    }
}
