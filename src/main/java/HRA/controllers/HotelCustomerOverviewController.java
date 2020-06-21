package HRA.controllers;

import HRA.model.HotelManager;
import HRA.model.Room;
import HRA.services.HotelManagerService;
import HRA.services.ReservationsService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
    private String customerUsername;
    private String HMUsername;
    private String reservationState = "Pending";
    private String message = "";

    Text reservationException = new Text("");
    Stage reservePopupWindow = new Stage();
    Stage reservePopupWindowConfirmation = new Stage();
    ChoiceBox roomTypeSelect = new ChoiceBox();
    TextField numberOfRooms = new TextField();
    TextField checkInDate = new TextField();
    TextField checkOutDate = new TextField();

    public void handleHCO(String HMUsername, String customerUsername) throws IOException {

        this.HMUsername = HMUsername;
        this.customerUsername = customerUsername;
        reservePopupWindow.initModality(Modality.APPLICATION_MODAL);
        reservePopupWindowConfirmation.initModality(Modality.APPLICATION_MODAL);
        //TEXT
        Text text1 = new Text("Facilities");
        text1.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        Text text2 = new Text("Rooms");
        text2.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        List <String> facilities = null;
        List <Room> roomList = null;
        Image im1 = null;
        Image im2 = null;

        for(HotelManager hotelManager : HM) {
            if (Objects.equals(hotelManager.getUsername(), HMUsername)) {
                facilities = hotelManager.getFacilities();
                im1 = new Image("file:///C:/Images/" + hotelManager.getImageName1() + ".jpg");
                im2 = new Image("file:///C:/Images/" + hotelManager.getImageName2() + ".jpg");
                roomList = hotelManager.getRoomList();
            }
        }

        TableColumn type = new TableColumn("Type");
        TableColumn capacity = new TableColumn("Capacity");
        TableColumn price = new TableColumn("Price");

        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        type.setStyle("-fx-alignment: CENTER;");
        type.setMinWidth(167);
        capacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        capacity.setStyle("-fx-alignment: CENTER;");
        capacity.setMinWidth(167);
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        price.setStyle("-fx-alignment: CENTER;");
        price.setMinWidth(168);

        roomTable.getColumns().addAll(type, capacity, price);

        for(Room room : roomList) {
            roomTable.getItems().add(room);
            roomTypeSelect.getItems().add(room.getType());
        }

        //IMAGES
        HBox image1 = new HBox();
        HBox image2 = new HBox();
        ImageView Image1 = new ImageView(im1);
        Image1.setFitHeight(450);
        Image1.setFitWidth(450);
        ImageView Image2 = new ImageView(im2);
        Image2.setFitHeight(450);
        Image2.setFitWidth(450);
        image1.getChildren().addAll(Image1);
        image2.getChildren().addAll(Image2);
        image1.setAlignment(Pos.CENTER);
        image2.setAlignment(Pos.CENTER);

        //FACILITIES
        ListView facilitiesLW = new ListView();
        GridPane gpFacilities = new GridPane();
        facilitiesLW.setMinWidth(1280/2-100);
        facilitiesLW.setMaxHeight(300);

        facilitiesLW.getItems().addAll(facilities);
        HBox text1HBox = new HBox(text1);
        text1HBox.setAlignment(Pos.CENTER);
        gpFacilities.getChildren().addAll(text1HBox,facilitiesLW);
        gpFacilities.setConstraints(facilitiesLW, 0, 1);

        //ROOM LIST
        ListView roomsLW = new ListView();
        GridPane gpRooms = new GridPane();
        roomsLW.setMinWidth(1280/2-100);
        roomsLW.setMaxHeight(300);

        roomsLW.getItems().addAll(roomTable);
        HBox text2HBox = new HBox(text2);
        text2HBox.setAlignment(Pos.CENTER);
        gpRooms.getChildren().addAll(text2HBox,roomsLW);
        gpRooms.setConstraints(roomsLW, 0, 1);

        //HOME BUTTON
        Button homeButton = new Button("Home");
        homeButton.setStyle("-fx-background-color: transparent;" +
                "-fx-border-color: white;" +
                "-fx-border-width: 2px 2px 2px 2px;" +
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;" +
                "-fx-font-style: italic;");
        VBox homeButtonVBox = new VBox(homeButton);
        homeButtonVBox.setAlignment(Pos.CENTER);
        homeButtonVBox.setPadding(new Insets(20,20,0,20));
        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LoggedCustomerController LCC = new LoggedCustomerController();
                LCC.getMainScene();
                mainLoginStage.setScene(LCC.getMainScene());
            }
        });

        //RESERVE BUTTON & WINDOW
        Button reserveButton = new Button("Reserve");
        reserveButton.setStyle("-fx-background-color: transparent;" +
                "-fx-border-color: white;" +
                "-fx-border-width: 2px 2px 2px 2px;" +
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;" +
                "-fx-font-style: italic;");
        VBox reserveButtonVBox = new VBox(reserveButton);
        reserveButtonVBox.setPadding(new Insets(20,20,0,20));
        reserveButtonVBox.setAlignment(Pos.CENTER);
        reserveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                reservePopupWindow.setTitle("Reserve");
                    //THINGS IN RESERVE WINDOW
                    Text roomType = new Text("Room type: ");
                    roomType.setStyle("-fx-background-color: transparent;" +
                            "-fx-border-color: white;" +
                            "-fx-border-width: 2px 2px 2px 2px;" +
                            "-fx-font-size: 16px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-font-style: italic;");

                    roomTypeSelect.setMaxWidth(100);

                    Text numberOfRoomsText = new Text("Number of rooms: ");
                    numberOfRoomsText.setStyle("-fx-background-color: transparent;" +
                            "-fx-border-color: white;" +
                            "-fx-border-width: 2px 2px 2px 2px;" +
                            "-fx-font-size: 16px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-font-style: italic;");

                    numberOfRooms.setMaxWidth(100);

                    Text checkInText = new Text("Check In Date(day-month-year): ");
                    checkInText.setStyle("-fx-background-color: transparent;" +
                            "-fx-border-color: white;" +
                            "-fx-border-width: 2px 2px 2px 2px;" +
                            "-fx-font-size: 16px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-font-style: italic;");

                    checkInDate.setMaxWidth(100);
                    Text checkOutText = new Text("Check Out Date(day-month-year): ");
                    checkOutText.setStyle("-fx-background-color: transparent;" +
                            "-fx-border-color: white;" +
                            "-fx-border-width: 2px 2px 2px 2px;" +
                            "-fx-font-size: 16px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-font-style: italic;");

                    checkOutDate.setMaxWidth(100);

                    Button doneButton = new Button("Done");
                    doneButton.setStyle("-fx-background-color: transparent;" +
                            "-fx-border-color: white;" +
                            "-fx-border-width: 2px 2px 2px 2px;" +
                            "-fx-font-size: 16px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-font-style: italic;");

                    doneButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                handleDoneReserveAction();
                            } catch (Exception e) {
                                reservationException.setText(e.toString());
                            }
                        }
                    });

                GridPane gridForReserveElements = new GridPane();
                gridForReserveElements.setConstraints(roomType, 0, 0);
                gridForReserveElements.setConstraints(roomTypeSelect, 1, 0);
                gridForReserveElements.setConstraints(numberOfRoomsText, 0, 1);
                gridForReserveElements.setConstraints(numberOfRooms, 1, 1);
                gridForReserveElements.setConstraints(checkInText, 0, 2);
                gridForReserveElements.setConstraints(checkInDate, 1, 2);
                gridForReserveElements.setConstraints(checkOutText, 0, 3);
                gridForReserveElements.setConstraints(checkOutDate, 1, 3);
                gridForReserveElements.getChildren().addAll(roomType, roomTypeSelect, numberOfRoomsText, numberOfRooms, checkInText, checkInDate, checkOutText, checkOutDate);
                gridForReserveElements.setAlignment(Pos.CENTER);

                reservationException.setStyle("-fx-background-color: transparent;" +
                        "-fx-border-color: white;" +
                        "-fx-border-width: 2px 2px 2px 2px;" +
                        "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-style: italic;");
                VBox reservationExceptionVBox = new VBox(reservationException);
                reservationExceptionVBox.setPadding(new Insets(30,0,0,0));
                reservationExceptionVBox.setAlignment(Pos.CENTER);
                VBox test = new VBox(gridForReserveElements, doneButton, reservationExceptionVBox);
                test.setAlignment(Pos.CENTER);
                test.setStyle(
                        "-fx-background-image: url(" +
                                "Theme.jpg" +
                                "); " +
                                "-fx-background-size: cover;"
                );
                Scene scene = new Scene(test,1280/1.5,720/1.5);
                reservePopupWindow.setScene(scene);
                reservePopupWindow.centerOnScreen();
                reservePopupWindow.show();

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
        mainPage.getChildren().addAll(gpRooms,gpFacilities,image1,image2);
        mainPage.setAlignment(Pos.CENTER);

        //SPACING FOR GRID
        mainPage.setVgap(10);
        mainPage.setHgap(10);

        //VBOX FOR BUTTONS
        HBox buttonsHBox = new HBox(reserveButtonVBox,homeButtonVBox);
        buttonsHBox.setAlignment(Pos.CENTER);

        //MAIN LAYOUT
        VBox layout = new VBox(10);
        layout.getChildren().addAll(buttonsHBox, mainPage);

        //layout.setBackground(bg);
        VBox root2 = new VBox();
        root2.setStyle(
                "-fx-background-image: url(" +
                        "Theme.jpg" +
                        "); " +
                        "-fx-background-size: cover;"
        );
        root2.getChildren().addAll(buttonsHBox, mainPage);
        root2.setPadding(new Insets(20,20,20,20));
        mainHotelCustomerOverviewScene = new Scene(root2, 1300, 850);
    }

    public Scene getMainScene(){
        return mainHotelCustomerOverviewScene;
    }

    public void handleDoneReserveAction() throws Exception {

        ReservationsService.checkInformation((String)roomTypeSelect.getSelectionModel().getSelectedItem(),numberOfRooms.getText(), checkInDate.getText(), checkOutDate.getText());
        String hotelActualName = null;
        for(HotelManager hm : HM){
            if(hm.getUsername().equals(HMUsername)){
                hotelActualName = hm.getHotelName();
                break;
            }
        }
        ReservationsService.addReservation((String)roomTypeSelect.getSelectionModel().getSelectedItem(),numberOfRooms.getText(), checkInDate.getText(), checkOutDate.getText(), customerUsername, HMUsername, reservationState , message, hotelActualName);
            //Confirmation window
            reservePopupWindowConfirmation.setMaxHeight(200);
            reservePopupWindowConfirmation.setMaxWidth(200);
            Button okButton = new Button("Ok");
            okButton.setStyle("-fx-background-color: transparent;" +
                    "-fx-border-color: white;" +
                    "-fx-border-width: 2px 2px 2px 2px;" +
                    "-fx-font-size: 16px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-font-style: italic;");
            okButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    reservePopupWindowConfirmation.close();
                }
            });
            VBox confirmationVBox = new VBox(new Text("Successfully reserved!"), okButton);
            confirmationVBox.setPrefSize(200,100);
            confirmationVBox.setAlignment(Pos.CENTER);
            confirmationVBox.setStyle(
                    "-fx-background-image: url(" +
                            "Theme.jpg" +
                            "); " +
                            "-fx-background-size: cover;" +
                            "-fx-border-color: white;" +
                            "-fx-border-width: 2px 2px 2px 2px;" +
                            "-fx-font-size: 16px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-font-style: italic;"
            );
            reservePopupWindowConfirmation.setScene(new Scene(confirmationVBox));
            reservePopupWindowConfirmation.centerOnScreen();
            reservePopupWindowConfirmation.show();
        reservationException.setText("");
        numberOfRooms.clear();
        checkInDate.clear();
        checkOutDate.clear();
        reservePopupWindow.close();
    }



}

