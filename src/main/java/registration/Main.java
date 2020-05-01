package registration;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {



        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("register.fxml"));
        primaryStage.setTitle("Hotel Registration Application");
        primaryStage.setScene(new Scene(root, 500, 450));
        primaryStage.show();
    }
}