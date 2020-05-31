package HRA;

import HRA.model.HotelManager;
import HRA.model.User;
import HRA.services.HotelManagerService;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import HRA.services.UserService;

import java.util.List;
import java.util.Objects;

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
        HotelManagerService.loadHotelManagersFromFile();

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
        window.setTitle("Hotel Reservation Aplication");
        window.setScene(new Scene(root, 1280, 720));
        window.show();
    }

    public static Stage getPrimaryStage(){
        return window;
    } //?? why doesn't work // non-static ??
}
