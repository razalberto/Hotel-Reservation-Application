package HRA.exceptions;

public class IncorrectPassword extends Exception {

    public IncorrectPassword(){
        super(String.format("Password or username is incorrect!"));
    }

}
