package HRA.controllers;

import HRA.exceptions.UsernameIsEmptyException;
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
            usernameField.clear();
            passwordField.clear();
            registrationMessage.setText(e.getMessage());
        }catch (PasswordTooShortException e){
            passwordField.clear();
            registrationMessage.setText(e.getMessage());
        }catch (UsernameIsEmptyException e){
            usernameField.clear();
            passwordField.clear();
            registrationMessage.setText(e.getMessage());
        }

    }

    public Text getRegistrationMessage() {
        return registrationMessage;
    }

    public void setRegistrationMessage(Text registrationMessage) {
        this.registrationMessage = registrationMessage;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(PasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public void setUsernameField(TextField usernameField) {
        this.usernameField = usernameField;
    }

    public ChoiceBox getRole() {
        return role;
    }

    public void setRole(ChoiceBox role) {
        this.role = role;
    }

    public void setNameField(TextField nameField) {
        this.nameField = nameField;
    }

    public void setAddressField(TextField addressField) {
        this.addressField = addressField;
    }

    public void setEmailField(TextField emailField) {
        this.emailField = emailField;
    }

    public void setPhoneNumberField(TextField phoneNumberField) {
        this.phoneNumberField = phoneNumberField;
    }
}
