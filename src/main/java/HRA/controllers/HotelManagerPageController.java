package HRA.controllers;


import HRA.model.Room;

import HRA.services.HotelManagerService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;


import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class HotelManagerPageController  {

    @FXML
    private Text hotelName;
    @FXML
    private Text saveMessage;
    @FXML
    private TextField imageName1Field;
    @FXML
    private Pane paneView1;
    @FXML
    private TextField imageName2Field;
    @FXML
    private Pane paneView2;
    @FXML
    private TextField roomTypeField;
    @FXML
    private TextField capacityField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField hotelFacilityField;
    @FXML
    private ListView<String> hotelFacilitiesList;
    @FXML
    private TableView<Room> roomTableView;
    @FXML
    private TableColumn<Room,String> typeColumn;
    @FXML
    private TableColumn<Room,String> capacityColumn;
    @FXML
    private TableColumn<Room, Double> priceColumn;
    @FXML
    private Button cancelButton;
    @FXML
    private Button reservationListButton;

    private static boolean answer;
    private String name;

    public void transferMessage(String message) {

        hotelName.setText(message);
    }
    public void transferUsername(String username){
        this.name = username;
    }


    //This is functionality for TableView add button
    public void addButtonClicked1(){
        Room room = new Room();
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        room.setType(roomTypeField.getText());
        room.setCapacity(capacityField.getText());
        room.setPrice(Double.parseDouble(priceField.getText()));
        roomTableView.getItems().add(room);
        roomTypeField.clear();
        capacityField.clear();
        priceField.clear();


    }

    //This is functionality for TableView delete button
    public void deleteButtonClicked1(){
        ObservableList<Room> roomSelected, allRooms;
        allRooms = roomTableView.getItems();
        roomSelected = roomTableView.getSelectionModel().getSelectedItems();

        roomSelected.forEach(allRooms::remove);

    }

    //This is functionality for ListView add button
    public void addButtonClicked2(){
        hotelFacilitiesList.getItems().add(hotelFacilityField.getText());
        hotelFacilityField.clear();


    }

    //This is functionality for ListView delete button
    public void deleteButtonClicked2(){
        ObservableList<String> facilitySelected, allFacilities;
        allFacilities = hotelFacilitiesList.getItems();
        facilitySelected = hotelFacilitiesList.getSelectionModel().getSelectedItems();

        facilitySelected.forEach(allFacilities::remove);

    }

    public void displayImage1(){
        paneView1.getChildren().clear();
        String name = imageName1Field.getText();
        Image image = new Image("file:///C:/Images/"+name+".jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(360);
        imageView.setFitHeight(225);
        paneView1.getChildren().add(imageView);


    }

    public void displayImage2(){
        paneView2.getChildren().clear();
        String name = imageName2Field.getText();
        Image image = new Image("file:///C:/Images/"+name+".jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(360);
        imageView.setFitHeight(225);
        paneView2.getChildren().add(imageView);

    }

    public void removeImage1(){
        paneView1.getChildren().clear();
        imageName1Field.clear();
    }

    public void removeImage2(){
        paneView2.getChildren().clear();
        imageName2Field.clear();
    }

    public void saveButtonClicked(){
           ObservableList <Room> allRooms;
           ObservableList <String> facilities;
           allRooms = roomTableView.getItems();
           facilities = hotelFacilitiesList.getItems();
           HotelManagerService.addManager(name,allRooms,imageName1Field.getText(),imageName2Field.getText(),facilities,hotelName.getText());
           saveMessage.setText("File saved successfully!");

    }
    public static boolean createConfirmBox(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        Label label = new Label();
        label.setText(message);

        //Create two buttons
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        //Clicking will set answer and close window
        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });
        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });

        VBox layout = new VBox(10);

        //Add buttons
        layout.getChildren().addAll(label, yesButton, noButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();


        return answer;
    }

    public void cancelButtonClicked() {

        boolean answer = createConfirmBox("Exit","Are you sure you want to exit?");
        if(answer) {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }
    }

    public void reservationListButtonClicked(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reservationList.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) reservationListButton.getScene().getWindow();
            stage.setScene(new Scene(root, 990, 925));
        }catch(IOException e){
            e.printStackTrace();
        }
    }


}
