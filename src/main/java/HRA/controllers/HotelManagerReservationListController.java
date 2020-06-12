package HRA.controllers;
import HRA.model.Reservation;
import HRA.model.ReservationHM;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;


public class HotelManagerReservationListController {
    @FXML
    private TableView<ReservationHM> reservationTableView;
    @FXML
    private TableColumn<ReservationHM,String> roomTypeColumn;
    @FXML
    private TableColumn<ReservationHM,String> numberOfRoomsColumn;
    @FXML
    private TableColumn<ReservationHM, String> checkInDateColumn;
    @FXML
    private TableColumn<ReservationHM, String> checkOutDateColumn;
    @FXML
    private TableColumn<ReservationHM, ChoiceBox> statusColumn;
    @FXML
    private TableColumn<ReservationHM, TextField> messageColumn;

    private String hotelManagerName;

    public void transferHotelManagerUsername(String x){
       this.hotelManagerName = x;
    }



    public void loadReservations(List <Reservation> reservations) {


        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        numberOfRoomsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfRooms"));
        checkInDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        checkOutDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));


        for(Reservation reservation : reservations) {
            ReservationHM reservationHM = new ReservationHM();
            ChoiceBox<String> status = new ChoiceBox<>() ;
            TextField message = new TextField();
            status.getItems().addAll("Pending","Accepted", "Rejected");
            status.setValue(reservation.getReservationState());
            message.setText(reservation.getMessage());
            reservationHM.setCustomerName(reservation.getCustomerName());
            reservationHM.setRoomType(reservation.getRoomType());
            reservationHM.setNumberOfRooms(reservation.getNumberOfRooms());
            reservationHM.setCheckInDate(reservation.getCheckInDate());
            reservationHM.setCheckOutDate(reservation.getCheckOutDate());
            reservationHM.setStatus(status);
            reservationHM.setMessage(message);
            reservationTableView.getItems().add(reservationHM);
        }

    }
}
