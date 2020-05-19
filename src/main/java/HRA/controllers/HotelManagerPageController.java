package HRA.controllers;

import HRA.model.Room;
import HRA.model.User;
import HRA.services.UserService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Objects;


public class HotelManagerPageController {

    @FXML
    private Text hotelName;
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
        imageName1Field.clear();

    }

    public void displayImage2(){
        paneView2.getChildren().clear();
        String name = imageName2Field.getText();
        Image image = new Image("file:///C:/Images/"+name+".jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(360);
        imageView.setFitHeight(225);
        paneView2.getChildren().add(imageView);
        imageName2Field.clear();
    }

    public void removeImage1(){
        paneView1.getChildren().clear();
    }

    public void removeImage2(){
        paneView2.getChildren().clear();
    }

    public void saveButtonClicked(){

    }

    public void cancelButtonClicked(){

    }

}
