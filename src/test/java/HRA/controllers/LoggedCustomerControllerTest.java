package HRA.controllers;

import HRA.services.FileSystemService;
import HRA.services.HotelManagerService;
import HRA.services.ReservationsService;
import HRA.services.UserService;
import com.sun.javafx.application.PlatformImpl;
import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;

public class LoggedCustomerControllerTest extends ApplicationTest {

    private LoggedCustomerController controller;

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
        PlatformImpl.startup(() -> {
        });
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath().toFile());
        UserService.loadUsersFromFile();
        HotelManagerService.loadHotelManagersFromFile();
        ReservationsService.loadReservationListFile();

        controller = new LoggedCustomerController();

        ReservationsService.addReservation("roomType1", "2", "01-02-2020", "02-20-2020", "testUsername1", "testHotelName1", "Pending", "Yes", "testHotelActualName1");
        UserService.addUser("testUsername1", "testPassword1", "Hotel Manager", "testName1", "testAddress1", "testEmail1", "testPhone");
        UserService.addUser("testUsername2", "testPassword2", "Customer", "testName2", "testAddress2", "testEmail2", "testPhone2");

    }

    @Test
    public void testLCC1() throws Exception {
        Platform.runLater(() -> {
            try {
                controller.handleLoggedCustomer("testUsername2");
                controller.searchHotelNameFunction();
                controller.makeDefaultListOfHotels();
                controller.checkIfListIsEmpty();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void testLCC2() throws Exception {
        controller.setListView(new ListView<>());
        Platform.runLater(() -> {
            try {
                controller.checkIfListIsEmpty();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
