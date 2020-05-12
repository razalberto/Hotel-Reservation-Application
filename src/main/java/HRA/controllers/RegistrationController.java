package HRA.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import HRA.exceptions.PasswordTooShortException;
import HRA.exceptions.UsernameAlreadyExistsException;
import HRA.services.UserService;


public class RegistrationController {

    @FXML
    private Text registrationMessage;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private ChoiceBox role;

    @FXML
    public void initialize() {
        role.getItems().addAll("Customer", "Hotel Manager");
    }

    @FXML
    public void handleRegisterAction() {
        try {
            UserService.addUser(usernameField.getText(), passwordField.getText(), (String) role.getValue(), nameField.getText(), addressField.getText(), emailField.getText(), phoneNumberField.getText());
            registrationMessage.setText("Account created successfully!");

        } catch (UsernameAlreadyExistsException e) {
            registrationMessage.setText(e.getMessage());
        }catch (PasswordTooShortException e){

            registrationMessage.setText(e.getMessage());
        }

    }

}
