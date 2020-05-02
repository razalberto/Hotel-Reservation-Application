package HRA.controllers;

import HRA.exceptions.IncorrectPassword;
import HRA.exceptions.UsernameDoesNotExist;
import HRA.services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginController {

    @FXML
    public Text loginMessage;
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField usernameField;

    @FXML
    public void handleLoginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username == null || username.isEmpty()) {
            loginMessage.setText("Please type in a username!");
            return;
        }

        if (password == null || password.isEmpty()) {
            loginMessage.setText("Password cannot be empty");
            return;
        }

        try{
            UserService.checkUserAndPassword(username,password);
        } catch (UsernameDoesNotExist e) {
            loginMessage.setText(e.toString());
        } catch (IncorrectPassword e) {
            loginMessage.setText(e.toString());
        }

        if(UserService.getAccession()){
            loginMessage.setText("Successfully logged in!");
        }

    }
}
