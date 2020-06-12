package HRA.controllers;
import HRA.model.Reservation;
import HRA.model.ReservationHM;
import HRA.services.ReservationsService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Objects;


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
    @FXML
    private Text sendMessage;

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
            status.getItems().addAll("Pending","Approved", "Rejected");
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
            if(!Objects.equals(reservationHM.getStatus().getValue(),"Pending")){
                reservationHM.getMessage().setEditable(false);
                if(Objects.equals(reservationHM.getStatus().getValue(),"Approved")){
                    reservationHM.getStatus().getItems().removeAll("Pending","Rejected");
                }else{
                    reservationHM.getStatus().getItems().removeAll("Pending","Approved");
                }

            }
            reservationTableView.getItems().add(reservationHM);
        }

    }

    public void sendButtonClicked(){
        ObservableList<ReservationHM> allReservations;
        allReservations = reservationTableView.getItems();
        for(ReservationHM reservationHM : allReservations) {
            ReservationsService.addReservation(reservationHM.getRoomType(),reservationHM.getNumberOfRooms(),reservationHM.getCheckInDate(),reservationHM.getCheckOutDate(),reservationHM.getCustomerName(),hotelManagerName,(String) reservationHM.getStatus().getValue(), reservationHM.getMessage().getText());
        }
        sendMessage.setText("Your messages have been send!");

    }

}
