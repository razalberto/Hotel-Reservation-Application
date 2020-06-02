package HRA.controllers;

import HRA.model.HotelManager;
import HRA.model.Room;
import HRA.services.HotelManagerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class HotelCustomerOverviewController {

    private List<HotelManager> HM = HotelManagerService.getHotelManagersFromHotelManagerService();
    private static Scene mainHotelCustomerOverviewScene;
    private Stage mainLoginStage = LoginController.getPrimaryStageFromLC();
    private TableView <Room> roomTable = new TableView();

    public void handleHCO(String HMUsername) throws IOException {

        //TEXT
        Text text1 = new Text("Facilities");
        Text text2 = new Text("Rooms");

        TableView<Room> roomTableView = null;
        List <String> facilities = null;
        List <Room> roomList = null;
        Image im1 = null;
        Image im2 = null;

        for(HotelManager hotelManager : HM) {
            if (Objects.equals(hotelManager.getUsername(), HMUsername)) {
                facilities = hotelManager.getFacilities();
                im1 = new Image("file:///C:/Images/"+hotelManager.getImageName1()+".jpg");
                im2 = new Image("file:///C:/Images/"+hotelManager.getImageName2()+".jpg");
                roomList = hotelManager.getRoomList();
            }
        }

        TableColumn type = new TableColumn("Type");
        TableColumn capacity = new TableColumn("Capacity");
        TableColumn price = new TableColumn("Price");

        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        capacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        roomTable.getColumns().addAll(type, capacity, price);

        for(Room room : roomList) {
            roomTable.getItems().add(room);
        }


        //IMAGES
        HBox image1 = new HBox();
        HBox image2 = new HBox();
        ImageView Image1 = new ImageView(im1);
        Image1.setFitHeight(248);
        Image1.setFitWidth(248);
        ImageView Image2 = new ImageView(im2);
        Image2.setFitHeight(248);
        Image2.setFitWidth(248);
        image1.getChildren().addAll(Image1);
        image2.getChildren().addAll(Image2);

        //FACILITIES
        ListView facilitiesLW = new ListView();
        GridPane gpFacilities = new GridPane();

        facilitiesLW.getItems().addAll(facilities);
        HBox text1HBox = new HBox(text1);
        text1HBox.setAlignment(Pos.CENTER);
        gpFacilities.getChildren().addAll(text1HBox,facilitiesLW);
        gpFacilities.setConstraints(facilitiesLW, 0, 1);

        //ROOM LIST
        ListView roomsLW = new ListView();
        GridPane gpRooms = new GridPane();

        roomsLW.getItems().addAll(roomTable);
        HBox text2HBox = new HBox(text2);
        text2HBox.setAlignment(Pos.CENTER);
        gpRooms.getChildren().addAll(text2HBox,roomsLW);
        gpRooms.setConstraints(roomsLW, 0, 1);

        //HOME BUTTON
        Button homeButton = new Button("Home");
        VBox homeButtonVBox = new VBox(homeButton);
        homeButtonVBox.setAlignment(Pos.CENTER);
        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LoggedCustomerController LCC = new LoggedCustomerController();
                LCC.getMainScene();
                mainLoginStage.setScene(LCC.getMainScene());
            }
        });

        //RESERVE BUTTON
        Button reserveButton = new Button("Reserve");
        VBox reserveButtonVBox = new VBox(reserveButton);
        reserveButtonVBox.setAlignment(Pos.CENTER);
        reserveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Stage reservePopupWindow = new Stage();
                reservePopupWindow.initModality(Modality.APPLICATION_MODAL);
                reservePopupWindow.setTitle("Reserve");
                VBox test = new VBox();
                Scene scene = new Scene(test,1280/1.5,720/1.5);
                reservePopupWindow.setScene(scene);
                reservePopupWindow.showAndWait();

            }
        });


        //MAIN PAGE
        GridPane mainPage = new GridPane();
        mainPage.setConstraints(reserveButtonVBox, 0, 0);
        mainPage.setConstraints(homeButtonVBox, 1, 0);
        mainPage.setConstraints(gpRooms, 0, 1);
        mainPage.setConstraints(gpFacilities, 1, 1);
        mainPage.setConstraints(image1, 0, 2);
        mainPage.setConstraints(image2, 1, 2);
        mainPage.getChildren().addAll(reserveButtonVBox,homeButtonVBox,gpRooms,gpFacilities,image1,image2);
        mainPage.setAlignment(Pos.CENTER);

        //SPACING FOR GRID
        mainPage.setVgap(10);
        mainPage.setHgap(10);

        //MAIN LAYOUT
        VBox layout = new VBox(10);
        layout.getChildren().addAll(mainPage);

        mainHotelCustomerOverviewScene = new Scene(layout, 1400, 900);
    }

    public Scene getMainScene(){
        return mainHotelCustomerOverviewScene;
    }

}

