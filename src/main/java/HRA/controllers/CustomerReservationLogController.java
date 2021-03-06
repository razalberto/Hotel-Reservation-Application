package HRA.controllers;

import HRA.model.Reservation;
import HRA.services.ReservationsService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.List;

public class CustomerReservationLogController {

    private static List<Reservation> reservations = ReservationsService.getReservationList();
    private static Scene mainCRLCScene;
    private TableView reservationLogTable = new TableView();

    private Button homeButtonCRLC = new Button("Home");
    private Button deleteButtonCRLC = new Button("Delete");
    private VBox buttonVBox = new VBox(homeButtonCRLC);
    private VBox deleteButtonVBox = new VBox(deleteButtonCRLC);
    private HBox buttonsBox = new HBox(deleteButtonVBox, buttonVBox);
    private VBox mainSceneCRLC = new VBox(reservationLogTable, buttonsBox);

    private VBox VBoxForDeleteButton = new VBox();
    private Scene SceneForDeleteButton;

    private TableColumn hotelName = new TableColumn("Hotel Name");
    private TableColumn roomType = new TableColumn("Room Type");
    private TableColumn numberOfRooms = new TableColumn("Number of rooms");
    private TableColumn checkIn = new TableColumn("Check In");
    private TableColumn checkOut = new TableColumn("Check Out");
    private TableColumn reservationState = new TableColumn("Status");
    private TableColumn message = new TableColumn("Message");

    public void makeLogReservationScene(String customerN) {

        //Home Button
        homeButtonCRLC.setStyle("-fx-background-color: transparent;" +
                "-fx-border-color: white;" +
                "-fx-border-width: 2px 2px 2px 2px;" +
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;" +
                "-fx-font-style: italic;");
        homeButtonCRLC.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LoggedCustomerController.setDefaultCustomerScene();
            }
        });

        //Center Alignment
        hotelName.setStyle("-fx-alignment: CENTER;");
        roomType.setStyle("-fx-alignment: CENTER;");
        numberOfRooms.setStyle("-fx-alignment: CENTER;");
        checkIn.setStyle("-fx-alignment: CENTER;");
        checkOut.setStyle("-fx-alignment: CENTER;");
        reservationState.setStyle("-fx-alignment: CENTER;");
        message.setStyle("-fx-alignment: CENTER;");

        hotelName.setCellValueFactory(new PropertyValueFactory<>("hotelActualName"));
        hotelName.setMinWidth(145);
        roomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        roomType.setMinWidth(145);
        numberOfRooms.setCellValueFactory(new PropertyValueFactory<>("numberOfRooms"));
        numberOfRooms.setMinWidth(145);
        checkIn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        checkIn.setMinWidth(145);
        checkOut.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        checkOut.setMinWidth(145);
        reservationState.setCellValueFactory(new PropertyValueFactory<>("reservationState"));
        reservationState.setMinWidth(145);
        message.setCellValueFactory(new PropertyValueFactory<>("message"));
        message.setMinWidth(400);

        reservationLogTable.getColumns().addAll(hotelName, roomType, numberOfRooms, checkIn, checkOut, reservationState, message);

        //ReservationLog
        reservationLogTable = makeReservationLogTableView(customerN);

        //Delete Button Functionality
        deleteButtonCRLC.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Reservation res = (Reservation) reservationLogTable.getSelectionModel().getSelectedItem();
                ReservationsService.deleteReservation(res);
                try {
                    ReservationsService.loadReservationListFile();
                    reservations = ReservationsService.getReservationList();
                    reservationLogTable = makeReservationLogTableView(customerN);
                    VBoxForDeleteButton = new VBox(reservationLogTable, buttonsBox);
                    VBoxForDeleteButton.setPadding(new Insets(20,5,20,5));
                    VBoxForDeleteButton.setAlignment(Pos.CENTER);
                    VBoxForDeleteButton.setStyle(
                            "-fx-background-image: url(" +
                                    "Theme.jpg" +
                                    "); " +
                                    "-fx-background-size: cover;"
                    );
                    SceneForDeleteButton = new Scene(VBoxForDeleteButton, 1280, 720);
                    LoggedCustomerController.setDefaultReservationCustomerScene(SceneForDeleteButton);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        deleteButtonVBox.setAlignment(Pos.BOTTOM_CENTER);
        deleteButtonVBox.setPadding(new Insets(20,20,20,20));
        deleteButtonCRLC.setStyle("-fx-background-color: transparent;" +
                "-fx-border-color: white;" +
                "-fx-border-width: 2px 2px 2px 2px;" +
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;" +
                "-fx-font-style: italic;");
        buttonVBox.setPadding(new Insets(20,20,20,20));
        buttonVBox.setAlignment(Pos.BOTTOM_CENTER);
        buttonsBox = new HBox(deleteButtonVBox, buttonVBox);
        buttonsBox.setAlignment(Pos.CENTER);
        mainSceneCRLC = new VBox(reservationLogTable, buttonsBox);
        mainSceneCRLC.setStyle(
                "-fx-background-image: url(" +
                        "Theme.jpg" +
                        "); " +
                        "-fx-background-size: cover;"
        );
        mainSceneCRLC.setPadding(new Insets(20,5,20,5));
        mainSceneCRLC.setAlignment(Pos.CENTER);

        mainCRLCScene = new Scene(mainSceneCRLC, 1280, 720);

    }

    public static Scene getMainCRLCScene() {
        return mainCRLCScene;
    }

    public TableView<Reservation> makeReservationLogTableView(String customerName){
        TableView <Reservation> newReservationLogTable = new TableView();
        for(Reservation r : reservations){
            if(r.getCustomerName().equals(customerName)){
                newReservationLogTable.getItems().add(r);
            }
        }
        newReservationLogTable.getColumns().addAll(hotelName, roomType, numberOfRooms, checkIn, checkOut, reservationState, message);
        newReservationLogTable.setMinHeight(720/1.2);
        return newReservationLogTable;
    }

    public void setHotelName(TableColumn hotelName) {
        this.hotelName = hotelName;
    }

    public void setRoomType(TableColumn roomType) {
        this.roomType = roomType;
    }

    public void setNumberOfRooms(TableColumn numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public void setCheckIn(TableColumn checkIn) {
        this.checkIn = checkIn;
    }

    public void setCheckOut(TableColumn checkOut) {
        this.checkOut = checkOut;
    }

    public void setReservationState(TableColumn reservationState) {
        this.reservationState = reservationState;
    }

    public void setMessage(TableColumn message) {
        this.message = message;
    }

    public void setReservationLogTable(TableView reservationLogTable) {
        this.reservationLogTable = reservationLogTable;
    }
}
