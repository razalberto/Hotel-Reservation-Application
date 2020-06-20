package HRA.controllers;



import HRA.model.Reservation;
import HRA.model.Room;

import HRA.services.HotelManagerService;
import HRA.services.ReservationsService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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

    private Stage mainLoginStage = LoginController.getPrimaryStageFromLC();

    private static boolean answer;
    private String name;
    private static Scene mainLoginHotelManagerScene;


    public void transferImageName1(String name){
        imageName1Field.setText(name);
    }

    public void transferImageName2(String name){
        imageName2Field.setText(name);
    }

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
        try {
            room.setType(roomTypeField.getText());
            room.setCapacity(capacityField.getText());
            room.setPrice(Double.parseDouble(priceField.getText()));
            roomTableView.getItems().add(room);
            roomTypeField.clear();
            capacityField.clear();
            priceField.clear();
            saveMessage.setText("");
        }catch(NumberFormatException e){
            saveMessage.setText("Price have to be a number!!");
        }


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
        label.setStyle("-fx-background-color: transparent;" +
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;" +
                "-fx-font-style: italic;");
        label.setText(message);

        //Create two buttons
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");
        yesButton.setStyle("-fx-background-color: transparent;" +
                "-fx-border-color: white;" +
                "-fx-border-width: 2px 2px 2px 2px;" +
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;" +
                "-fx-font-style: italic;");

        noButton.setStyle("-fx-background-color: transparent;" +
                "-fx-border-color: white;" +
                "-fx-border-width: 2px 2px 2px 2px;" +
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;" +
                "-fx-font-style: italic;");

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
        layout.setStyle(
                "-fx-background-image: url(" +
                        "Theme.jpg" +
                        "); " +
                        "-fx-background-size: cover;"
        );
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }

    public void cancelButtonClicked() {

        boolean answer = createConfirmBox("","Are you sure you want to exit?");
        if(answer) {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }
    }

    public void reservationListButtonClicked() {

        ArrayList<Reservation> r = new ArrayList<>();

        for (Reservation reservation : ReservationsService.getReservationList()) {
            if (Objects.equals(this.getName(), reservation.getHotelName())) {
                r.add(reservation);
            }
        }

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("reservationList.fxml"));
                Parent root = loader.load();
                HotelManagerReservationListController x = loader.getController();
                x.loadReservations(r);
                x.transferHotelManagerUsername(getName());
                mainLoginHotelManagerScene = new Scene(root, 1132, 925);
                mainLoginStage.setScene(mainLoginHotelManagerScene);


            } catch (IOException e) {
                e.printStackTrace();
            }



    }



    public void setPaneView1Image(String name) {
        Image image = new Image("file:///C:/Images/"+name+".jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(360);
        imageView.setFitHeight(225);
        paneView1.getChildren().add(imageView);
    }

    public void setPaneView2Image(String name) {
        Image image = new Image("file:///C:/Images/"+name+".jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(360);
        imageView.setFitHeight(225);
        paneView2.getChildren().add(imageView);
    }

    public void setHotelFacilities(List <String> hotelFacilitiesList) {
        for(String facility : hotelFacilitiesList){
            this.hotelFacilitiesList.getItems().add(facility);
        }


    }

    public void setRoomTable(List <Room> rooms) {
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        for(Room room : rooms){
            this.roomTableView.getItems().add(room);

        }

    }

    public Text getHotelName() {
        return hotelName;
    }

    public void setHotelName(Text hotelName) {
        this.hotelName = hotelName;
    }

    public Text getSaveMessage() {
        return saveMessage;
    }

    public void setSaveMessage(Text saveMessage) {
        this.saveMessage = saveMessage;
    }

    public TextField getImageName1Field() {
        return imageName1Field;
    }

    public void setImageName1Field(TextField imageName1Field) {
        this.imageName1Field = imageName1Field;
    }

    public Pane getPaneView1() {
        return paneView1;
    }

    public void setPaneView1(Pane paneView1) {
        this.paneView1 = paneView1;
    }

    public TextField getImageName2Field() {
        return imageName2Field;
    }

    public void setImageName2Field(TextField imageName2Field) {
        this.imageName2Field = imageName2Field;
    }

    public Pane getPaneView2() {
        return paneView2;
    }

    public void setPaneView2(Pane paneView2) {
        this.paneView2 = paneView2;
    }

    public TextField getRoomTypeField() {
        return roomTypeField;
    }

    public void setRoomTypeField(TextField roomTypeField) {
        this.roomTypeField = roomTypeField;
    }

    public TextField getCapacityField() {
        return capacityField;
    }

    public void setCapacityField(TextField capacityField) {
        this.capacityField = capacityField;
    }

    public TextField getPriceField() {
        return priceField;
    }

    public void setPriceField(TextField priceField) {
        this.priceField = priceField;
    }

    public TextField getHotelFacilityField() {
        return hotelFacilityField;
    }

    public void setHotelFacilityField(TextField hotelFacilityField) {
        this.hotelFacilityField = hotelFacilityField;
    }

    public ListView<String> getHotelFacilitiesList() {
        return hotelFacilitiesList;
    }

    public void setHotelFacilitiesList(ListView<String> hotelFacilitiesList) {
        this.hotelFacilitiesList = hotelFacilitiesList;
    }

    public TableView<Room> getRoomTableView() {
        return roomTableView;
    }

    public void setRoomTableView(TableView<Room> roomTableView) {
        this.roomTableView = roomTableView;
    }

    public void setTypeColumn(TableColumn<Room, String> typeColumn) {
        this.typeColumn = typeColumn;
    }


    public void setCapacityColumn(TableColumn<Room, String> capacityColumn) {
        this.capacityColumn = capacityColumn;
    }


    public void setPriceColumn(TableColumn<Room, Double> priceColumn) {
        this.priceColumn = priceColumn;
    }

    public void setCancelButton(Button cancelButton) {
        this.cancelButton = cancelButton;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
