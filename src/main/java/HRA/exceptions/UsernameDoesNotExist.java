package HRA.exceptions;

public class UsernameDoesNotExist extends Exception {

    public UsernameDoesNotExist(){
        super(String.format("Username is incorrect!"));
    }

    public String toString(){
        return "Username is incorrect!";
    }

}
