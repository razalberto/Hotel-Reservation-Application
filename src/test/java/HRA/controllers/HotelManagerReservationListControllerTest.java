package HRA.controllers;



import HRA.model.Reservation;
import HRA.services.FileSystemService;
import HRA.services.HotelManagerService;
import HRA.services.ReservationsService;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import org.apache.commons.io.FileUtils;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class HotelManagerReservationListControllerTest extends ApplicationTest {

    private HotelManagerReservationListController controller;

    @BeforeClass
    public static void setupClass() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-HRA";
        FileSystemService.initApplicationHomeDirIfNeeded();
        HotelManagerService.loadHotelManagersFromFile();
    }

    @Before
    public void setUp() throws Exception {
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath().toFile());
        HotelManagerService.loadHotelManagersFromFile();

        controller = new HotelManagerReservationListController();
        controller.setReservationTableView(new TableView<>());
        controller.setRoomTypeColumn(new TableColumn<>());
        controller.setNumberOfRoomsColumn(new TableColumn<>());
        controller.setCheckInDateColumn(new TableColumn<>());
        controller.setCheckOutDateColumn(new TableColumn<>());
        controller.setStatusColumn(new TableColumn<>());
        controller.setMessageColumn(new TableColumn<>());
        controller.setSendMessage(new Text());


    }

    @Test
    public void testLoadReservationCode() throws Exception{
        ReservationsService.loadReservationListFile();
        ReservationsService.addReservation("Double","2","11-01-2021","12-01-2021","ana","marius","Approved","Message","Majestic");
        ReservationsService.addReservation("Single","2","11-01-2021","12-01-2021","maria","marius","Rejected","Message","Majestic");
        controller.loadReservations(ReservationsService.getReservationList());
        assertEquals (2,controller.getReservationTableView().getItems().size());

    }

    @Test
    public void testSendButtonClicked() throws Exception{
        ReservationsService.loadReservationListFile();
        Reservation reservation = new Reservation("Single","2","11-01-2021","12-01-2021","maria","marius","Pending","Message","Majestic");
        ArrayList<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);
        controller.loadReservations(reservations);
        controller.sendButtonClicked();
        assertEquals(1,ReservationsService.getReservationList().size());
        assertEquals("Your messages have been send!",controller.getSendMessage().getText());

    }

    @Test
    public void testTransferUsername() throws Exception{
        HotelManagerService.loadHotelManagersFromFile();
        HotelManagerService.addManager("andrei",null,"image1","image2",null,"Royal");
        controller.setHM(HotelManagerService.getHotelManagersFromHotelManagerService());
        controller.transferHotelManagerUsername("andrei");
        assertEquals("Royal",controller.getHotelActualName());
    }


}
