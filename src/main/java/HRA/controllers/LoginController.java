package HRA.controllers;

import HRA.Main;
import HRA.exceptions.IncorrectPassword;
import HRA.exceptions.UsernameDoesNotExist;
import HRA.model.User;
import HRA.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

public class LoginController{

    private List<User> users = UserService.getUsersFromUserService();

    @FXML
    public Button loginButton;
    @FXML
    public Button registerButton;
    @FXML
    public Text loginMessage;
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField usernameField;

    @FXML
    public void handleLoginButtonAction() throws Exception {
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
            loginButtonAction();
        }

    }


    public void handleRegisterButtonAction() throws Exception {


        Stage regPopupWindow = new Stage();
        regPopupWindow.initModality(Modality.APPLICATION_MODAL);
        regPopupWindow.setTitle("Registration");

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("register.fxml")); //TODO after i get register page and controller
        Scene scene = new Scene(root,1280/1.5, 720/1.5);

        regPopupWindow.setScene(scene);
        regPopupWindow.showAndWait();
    }

    private Stage mainLoginStage = Main.getPrimaryStage();

    public void loginButtonAction() throws Exception {


        mainLoginStage.setTitle("Logged in! HRA");

        for (User user : users) {
            if (Objects.equals(usernameField.getText(), user.getUsername())) {
                if (Objects.equals("Hotel Manager", user.getRole())) {
                    Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("mainpagehm.fxml"));
                    Scene hmScene = new Scene(root,990, 925);
                    mainLoginStage.setScene(hmScene);
                } else {
                    LoggedCustomerController LCC = new LoggedCustomerController();
                    LCC.handleLoggedCustomer();
                    mainLoginStage.setScene(LCC.getMainScene());
                }
            }
        }
    }
}