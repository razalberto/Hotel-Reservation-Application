package HRA.controllers;

import HRA.model.User;
import HRA.services.UserService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class LoggedCustomerController {

    private List<User> users = UserService.getUsersFromUserService();
    private static Scene mainLoginCustomerScene;
    private ListView<String> listView;
    private TextField searchField = new TextField();

    private Stage mainLoginStage = LoginController.getPrimaryStageFromLC();

    public void handleLoggedCustomer() {

        //Reservation Log
        Button reservationLogButton = new Button("Past Reservations");
        HBox reservationLogPane = new HBox();
        reservationLogPane.setAlignment(Pos.CENTER);
        reservationLogPane.getChildren().addAll(reservationLogButton);

        //List of hotels control
        listView = new ListView<>();
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        makeDefaultListOfHotels();

        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HotelCustomerOverviewController HCO = new HotelCustomerOverviewController();
                try {
                    HCO.handleHCO(listView.getSelectionModel().getSelectedItem()); // Pass the username of hm
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mainLoginStage.setScene(HCO.getMainScene());
            }
        });

        //Search bar + button
            // Search Button
            Button searchButton = new Button();
            Image searchImg = new Image("searchIcon.png");
            ImageView searchIcon = new ImageView();

            searchButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    searchHotelNameFunction();
                }
            });
            //Image dimensions for button
                searchIcon.setFitHeight(25);
                searchIcon.setFitWidth(25);
            searchIcon.setImage(searchImg);
            searchButton.setGraphic(searchIcon);
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

    private void searchHotelNameFunction(){
        listView.getItems().clear();
        if(searchField.getText() == null){
            makeDefaultListOfHotels();
            checkIfListIsEmpty();
        }
        else{
            for (User user : users) {
                if (Objects.equals("Hotel Manager", user.getRole())) {
                    if (user.getUsername().contains(searchField.getText()))
                        listView.getItems().add(user.getUsername());
                }
            }
            checkIfListIsEmpty();
        }
    }

    private void makeDefaultListOfHotels(){
        for (User user : users) {
            if (Objects.equals("Hotel Manager", user.getRole()))
                listView.getItems().add(user.getUsername());
        }
    }

    private void checkIfListIsEmpty(){
        if(listView.getItems().isEmpty()){
            listView.getSelectionModel().setSelectionMode(null);
                //Popup for empty list
                Button emptyListOKButton = new Button("Ok");
                Text emptyListText = new Text("Nu am gasit nici un hotel!" + "\n");
                Image sign = new Image("!.png");
                ImageView signView = new ImageView(sign);
                    signView.setFitWidth(150);
                    signView.setFitHeight(150);

                Stage listIsEmpty = new Stage();
                listIsEmpty.initModality(Modality.APPLICATION_MODAL);
                listIsEmpty.setTitle("Lista e goala!");

                VBox listEmptyLayout = new VBox();
                listEmptyLayout.setAlignment(Pos.CENTER);
                listEmptyLayout.setPadding(new Insets(5,5,5,5));
                listEmptyLayout.getChildren().addAll(signView, emptyListText, emptyListOKButton);

                Scene scene = new Scene(listEmptyLayout,1280/3, 720/3);

                listIsEmpty.setScene(scene);
                listIsEmpty.show();

                emptyListOKButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        listIsEmpty.close();
                    }
                });

        }else{
            listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        }
    }

}
