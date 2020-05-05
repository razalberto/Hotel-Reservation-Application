package HRA.controllers;

import HRA.exceptions.IncorrectPassword;
import HRA.exceptions.UsernameDoesNotExist;
import HRA.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;


public class LoginController{

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

        try {
            UserService.checkUserAndPassword(username, password);
        } catch (UsernameDoesNotExist e) {
            loginMessage.setText(e.getMessage());
        } catch (IncorrectPassword e) {
            loginMessage.setText(e.getMessage());
        }

        if (UserService.getAccession()) {
            loginMessage.setText("Successfully logged in!");
        }

    }

/*
    protected Stage regPopupWindow;
    protected Scene regPopupWindowScene;

    public void handleRegisterButtonAction() throws IOException {

       // Parent mainPage = FXMLLoader.load(getClass().getClassLoader().getResource("register.fxml"));
        regPopupWindow.setTitle("Register Test");

        regPopupWindow.initModality(Modality.WINDOW_MODAL);
        regPopupWindow.setScene(regPopupWindowScene);
        regPopupWindow.showAndWait();
    }
*/
}