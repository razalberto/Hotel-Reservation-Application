package HRA.controllers;

import HRA.Main;
import HRA.exceptions.IncorrectPassword;
import HRA.exceptions.UsernameDoesNotExist;
import HRA.model.HotelManager;
import HRA.model.User;
import HRA.services.HotelManagerService;
import HRA.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class LoginController {

    private List<User> users = UserService.getUsersFromUserService();

    @FXML
    public Button loginButton;
    @FXML
    public Button registerButton;
    @FXML
    public Text loginMessage;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;

    private static Stage mainLoginStage = Main.getPrimaryStage();
    private static Scene hmScene ;
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
        regPopupWindow.setTitle("");
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("register.fxml"));
        Scene scene = new Scene(root,1280/1.5,720/1.5);
        regPopupWindow.setScene(scene);
        regPopupWindow.centerOnScreen();
        regPopupWindow.showAndWait();
    }



    public boolean loginButtonAction() throws Exception {

        mainLoginStage.centerOnScreen();

        for (User user : users) {
            if (Objects.equals(usernameField.getText(), user.getUsername())) {
                if (Objects.equals("Hotel Manager", user.getRole())) {
                   try{
                       for(HotelManager manager : HotelManagerService.getHotelManagersFromHotelManagerService()) {
                           if (Objects.equals(manager.getUsername(), user.getUsername())) {
                               FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainpagehm.fxml"));
                               Parent root = loader.load();
                               HotelManagerPageController x = loader.getController();
                               x.transferMessage(user.getName());
                               x.transferUsername(user.getUsername());
                               x.setPaneView1Image(manager.getImageName1());
                               x.setPaneView2Image(manager.getImageName2());
                               x.setHotelFacilities(manager.getFacilities());
                               x.setRoomTable(manager.getRoomList());
                               x.transferImageName1(manager.getImageName1());
                               x.transferImageName2(manager.getImageName2());

                               hmScene = new Scene(root, 990, 947);
                               mainLoginStage.setScene(hmScene);
                               mainLoginStage.centerOnScreen();
                               return true;
                           }
                      }
                       FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainpagehm.fxml"));
                       Parent root = loader.load();
                       HotelManagerPageController y = loader.getController();
                       y.transferMessage(user.getName());
                       y.transferUsername(user.getUsername());

                       hmScene = new Scene(root, 990, 947);
                       mainLoginStage.setScene(hmScene);
                       mainLoginStage.centerOnScreen();
                       return true;
                   }catch (IOException e){
                       e.printStackTrace();
                   }

                } else {
                    LoggedCustomerController LCC = new LoggedCustomerController();
                    LCC.handleLoggedCustomer(user.getUsername());
                    mainLoginStage.setScene(LCC.getMainScene());
                    return true;
                }
            }
        }
        return true;
    }

    public static Stage getPrimaryStageFromLC(){
        return mainLoginStage;
    }
    public static Scene getHmScene(){
        return hmScene;
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

    public void setLoginMessage(Text loginMessage) {
        this.loginMessage = loginMessage;
    }
}