package HRA.controllers;

import HRA.exceptions.ReservationAlreadyExist;
import HRA.model.HotelManager;
import HRA.model.Reservation;
import HRA.model.ReservationHM;
import HRA.services.HotelManagerService;
import HRA.services.ReservationsService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.util.List;
import java.util.Objects;


public class HotelManagerReservationListController {
    @FXML
    private TableView<ReservationHM> reservationTableView;
    @FXML
    private TableColumn<ReservationHM, String> roomTypeColumn;
    @FXML
    private TableColumn<ReservationHM, String> numberOfRoomsColumn;
    @FXML
    private TableColumn<ReservationHM, String> checkInDateColumn;
    @FXML
    private TableColumn<ReservationHM, String> checkOutDateColumn;
    @FXML
    private TableColumn<ReservationHM, ChoiceBox> statusColumn;
    @FXML
    private TableColumn<ReservationHM, TextField> messageColumn;
    @FXML
    private Text sendMessage;


    private String hotelActualName;
    private List<HotelManager> HM = HotelManagerService.getHotelManagersFromHotelManagerService();

    private String hotelManagerName;

    private Stage mainLoginStage = LoginController.getPrimaryStageFromLC();

    public void transferHotelManagerUsername(String x) {
        this.hotelManagerName = x;
        for(HotelManager hm : HM){
            if(hm.getUsername().equals(x)){
                hotelActualName = hm.getHotelName();
            }
        }
    }


    public void loadReservations(List<Reservation> reservations) {


        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        numberOfRoomsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfRooms"));
        checkInDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        checkOutDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));

        for (Reservation reservation : reservations) {
            ReservationHM reservationHM = new ReservationHM();
            ChoiceBox<String> status = new ChoiceBox<>();
            TextField message = new TextField();
            status.getItems().addAll("Pending", "Approved", "Rejected");
            status.setValue(reservation.getReservationState());
            message.setText(reservation.getMessage());
            reservationHM.setCustomerName(reservation.getCustomerName());
            reservationHM.setRoomType(reservation.getRoomType());
            reservationHM.setNumberOfRooms(reservation.getNumberOfRooms());
            reservationHM.setCheckInDate(reservation.getCheckInDate());
            reservationHM.setCheckOutDate(reservation.getCheckOutDate());
            reservationHM.setStatus(status);
            reservationHM.setMessage(message);
            reservationHM.getMessage().setPromptText("Please enter a message");
            if (!Objects.equals(reservationHM.getStatus().getValue(), "Pending")) {
                reservationHM.getMessage().setEditable(false);
                if (Objects.equals(reservationHM.getStatus().getValue(), "Approved")) {
                    reservationHM.getStatus().getItems().removeAll("Pending", "Rejected");
                } else {
                    reservationHM.getStatus().getItems().removeAll("Pending", "Approved");
                }

            }
            reservationTableView.getItems().add(reservationHM);
        }

    }

    public void sendButtonClicked() throws ReservationAlreadyExist {
        ObservableList<ReservationHM> allReservations;
        allReservations = reservationTableView.getItems();
        for (ReservationHM reservationHM : allReservations) {
            if(reservationHM.getStatus().getItems().contains("Pending"))
                ReservationsService.addReservation(reservationHM.getRoomType(), reservationHM.getNumberOfRooms(), reservationHM.getCheckInDate(), reservationHM.getCheckOutDate(), reservationHM.getCustomerName(), hotelManagerName, (String) reservationHM.getStatus().getValue(), reservationHM.getMessage().getText(), hotelActualName);
        }
        sendMessage.setText("Your messages have been send!");

    }

    public void overviewButtonClicked() {

        for (HotelManager manager : HotelManagerService.getHotelManagersFromHotelManagerService()) {
            if (Objects.equals(manager.getUsername(), hotelManagerName)) {
                    mainLoginStage.setScene(LoginController.getHmScene());

            }

        }

    }

    public TableView<ReservationHM> getReservationTableView() {
        return reservationTableView;
    }

    public void setReservationTableView(TableView<ReservationHM> reservationTableView) {
        this.reservationTableView = reservationTableView;
    }

    public void setRoomTypeColumn(TableColumn<ReservationHM, String> roomTypeColumn) {
        this.roomTypeColumn = roomTypeColumn;
    }


    public void setNumberOfRoomsColumn(TableColumn<ReservationHM, String> numberOfRoomsColumn) {
        this.numberOfRoomsColumn = numberOfRoomsColumn;
    }


    public void setCheckInDateColumn(TableColumn<ReservationHM, String> checkInDateColumn) {
        this.checkInDateColumn = checkInDateColumn;
    }


    public void setCheckOutDateColumn(TableColumn<ReservationHM, String> checkOutDateColumn) {
        this.checkOutDateColumn = checkOutDateColumn;
    }


    public void setStatusColumn(TableColumn<ReservationHM, ChoiceBox> statusColumn) {
        this.statusColumn = statusColumn;
    }


    public void setMessageColumn(TableColumn<ReservationHM, TextField> messageColumn) {
        this.messageColumn = messageColumn;
    }

    public Text getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(Text sendMessage) {
        this.sendMessage = sendMessage;
    }

    public String getHotelActualName() {
        return hotelActualName;
    }

    public void setHM(List<HotelManager> HM) {
        this.HM = HM;
    }
}