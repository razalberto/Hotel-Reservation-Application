package HRA.controllers;

import HRA.model.Reservation;
import HRA.services.FileSystemService;
import HRA.services.HotelManagerService;
import HRA.services.ReservationsService;
import HRA.services.UserService;
import com.sun.javafx.application.PlatformImpl;
import javafx.application.Platform;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CustomerReservationLogControllerTest {

    private CustomerReservationLogController controller;
//    private TableView<Reservation> newReservationLogTableView = new TableView();

    @BeforeClass
    public static void setupClass() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-HRA";
        FileSystemService.initApplicationHomeDirIfNeeded();
        UserService.loadUsersFromFile();
        HotelManagerService.loadHotelManagersFromFile();
        ReservationsService.loadReservationListFile();
    }

    @Before
    public void setUp() throws Exception {
        PlatformImpl.startup(() -> {});
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath().toFile());
        UserService.loadUsersFromFile();
        HotelManagerService.loadHotelManagersFromFile();
        ReservationsService.loadReservationListFile();

        controller = new CustomerReservationLogController();

        controller.setReservationLogTable(new TableView());
        controller.setHotelName(new TableColumn<>());
        controller.setRoomType(new TableColumn<>());
        controller.setNumberOfRooms(new TableColumn<>());
        controller.setCheckIn(new TableColumn<>());
        controller.setCheckOut(new TableColumn<>());
        controller.setReservationState(new TableColumn<>());
        controller.setMessage(new TableColumn<>());

        ReservationsService.addReservation("roomType1","2","01-02-2020","02-20-2020","testUsername1","testHotelName1","Pending","Yes","testHotelActualName1");
        UserService.addUser("testUsername1", "testPassword1", "Hotel Manager", "testName1", "testAddress1", "testEmail1", "testPhone");
    }

    @Test
    public void testHandleLoginButtonAction() throws Exception {
        controller.makeLogReservationScene("testUsername1");
    }

    @Test
    public void testHandleLoginButtonAction2() throws Exception {
        assertEquals(1,controller.makeReservationLogTableView("testUsername1").getItems().size());
    }

}
