package HRA.controllers;

import HRA.Main;
import HRA.model.User;
import HRA.services.UserService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sun.awt.image.GifImageDecoder;

import java.awt.*;
import java.util.List;
import java.util.Objects;

public class LoggedCustomerController {

    private List<User> users = UserService.getUsersFromUserService();
    private static Scene mainLoginCustomerScene;
    private ListView<String> listView;


    public void handleLoggedCustomer() {

        //Reservation Log
        Button reservationLogButton = new Button("Past Reservations");
        HBox reservationLogPane = new HBox();
        reservationLogPane.setAlignment(Pos.CENTER);
        reservationLogPane.getChildren().addAll(reservationLogButton);

        //List of hotels control
        listView = new ListView<>();
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        for (User user : users) {
            if (Objects.equals("Hotel Manager", user.getRole()))
                listView.getItems().add(user.getUsername());
        }

        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("clicked on " + listView.getSelectionModel().getSelectedItem());
            }
        });

        //Search bar + button
            // Search Button
            Button searchButton = new Button();
            Image searchImg = new Image("searchIcon.png");
            ImageView searchIcon = new ImageView();
            //Image dimensions for button
                searchIcon.setFitHeight(25);
                searchIcon.setFitWidth(25);
            searchIcon.setImage(searchImg);
            searchButton.setGraphic(searchIcon);
            //Search Bar
            TextField searchField = new TextField();
            //Both in one
            GridPane searchButtonAndBar = new GridPane();
            searchButtonAndBar.setConstraints(searchField,0, 0); // set col 1
            searchButtonAndBar.getColumnConstraints().add(new ColumnConstraints(1195)); // set dimension Search Field
            searchButtonAndBar.setConstraints(searchButton, 1, 0); // set col 2
            searchButtonAndBar.getChildren().addAll(searchField, searchButton); // Add content
            //HBox to align them at center
            HBox searchHBox = new HBox();
            searchHBox.setAlignment(Pos.CENTER);
            searchHBox.getChildren().addAll(searchButtonAndBar);

        //Layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(searchHBox,listView, reservationLogPane);

        //Main page spacing
        layout.setPadding(new Insets(20,20,20,20));

        mainLoginCustomerScene = new Scene(layout, 1280, 720);

    }

    public Scene getMainScene(){
        return mainLoginCustomerScene;
    }

}
