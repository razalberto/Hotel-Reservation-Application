package HRA;

import HRA.services.HotelManagerService;
import HRA.services.ReservationsService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import HRA.services.UserService;

public class Main extends Application {

    private static Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;
        UserService.loadUsersFromFile();
        HotelManagerService.loadHotelManagersFromFile();
        ReservationsService.loadReservationListFile();

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
        window.setTitle("Hotel Reservation Application");
        window.setScene(new Scene(root, 1280, 720));
        window.centerOnScreen();
        window.show();
    }

    public static Stage getPrimaryStage(){
        return window;
    }
}
