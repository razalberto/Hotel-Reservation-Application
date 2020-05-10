package HRA;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import HRA.services.UserService;

public class Main extends Application {

    private static Stage window; // ??
    //private Stage window; ??

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;
        UserService.loadUsersFromFile();

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
        window.setTitle("Hotel Reservation Aplication");
        window.setScene(new Scene(root, 1280, 720));
        window.show();
    }

    public static Stage getPrimaryStage(){
        return window;
    } //?? why doesn't work // non-static ??
}