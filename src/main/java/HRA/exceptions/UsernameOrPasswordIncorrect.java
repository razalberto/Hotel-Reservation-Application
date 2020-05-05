package HRA.exceptions;

public class UsernameOrPasswordIncorrect extends Exception{
    public UsernameOrPasswordIncorrect() {
        super(String.format("Username or password is incorrect!"));
    }
}
