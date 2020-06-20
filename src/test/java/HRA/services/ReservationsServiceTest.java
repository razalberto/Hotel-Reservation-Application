package HRA.services;

import HRA.exceptions.CheckInOutDateException;
import HRA.exceptions.NumberOfRoomsException;
import HRA.exceptions.ReservationAlreadyExist;
import HRA.exceptions.RoomTypeException;
import HRA.model.Reservation;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import java.io.IOException;
import java.nio.file.Files;
import static org.junit.Assert.*;

public class ReservationsServiceTest extends ApplicationTest {

    @BeforeClass
    public static void setupClass() {
        FileSystemService.APPLICATION_FOLDER = ".test-HRA";
        FileSystemService.initApplicationHomeDirIfNeeded();
    }

    @Before
    public void setUp() throws IOException {
        FileUtils.cleanDirectory(FileSystemService.getPathToFile().toFile());
    }

    @Test
    public void testCopyDefaultFileIfNotExists() throws Exception {
        ReservationsService.loadReservationListFile();
        assertTrue(Files.exists(ReservationsService.getRESERVATIONS_PATH()));
    }

    @Test
    public void testReservationsFromFile() throws Exception {
        ReservationsService.loadReservationListFile();
        assertNotNull(ReservationsService.getReservationList());
        assertEquals(0, ReservationsService.getReservationList().size());
    }

    @Test
    public void testAddOneReservation() throws Exception {
        ReservationsService.loadReservationListFile();
        ReservationsService.addReservation("Double","2","01-12-2020","05-12-2020","testCustomer","testHotelUsername","Pending", "", "testHotelName");
        assertNotNull(ReservationsService.getReservationList());
        assertEquals(1, ReservationsService.getReservationList().size());
    }

    @Test
    public void testAddTwoReservations() throws Exception {
        UserService.loadUsersFromFile();
        ReservationsService.loadReservationListFile();
        ReservationsService.addReservation("Double","2","01-12-2020","05-12-2020","testCustomer","testHotelUsername","Pending", "", "testHotelName");
        ReservationsService.addReservation("Double","2","01-12-2020","05-12-2020","testCustomer2","testHotelUsername","Pending", "", "testHotelName");
        assertNotNull(ReservationsService.getReservationList());
        assertEquals(2, ReservationsService.getReservationList().size());
    }

    @Test(expected = ReservationAlreadyExist.class)
    public void testCheckOldVersion() throws Exception {
        ReservationsService.loadReservationListFile();
        ReservationsService.addReservation("Double","2","01-12-2020","05-12-2020","testCustomer","testHotelUsername","Pending", "", "testHotelName");
        assertNotNull(ReservationsService.getReservationList());
        ReservationsService.checkOldVersionDoesNotExist(new Reservation("Double","2","01-12-2020","05-12-2020","testCustomer","testHotelUsername","Pending", "", "testHotelName"));
    }

    @Test
    public void testCheckOldVersion2() throws Exception {
        ReservationsService.loadReservationListFile();
        ReservationsService.addReservation("Double","2","01-12-2020","05-12-2020","testCustomer2","testHotelUsername2","Pending", "", "testHotelName2");
        ReservationsService.addReservation("Double","2","01-12-2020","05-12-2020","testCustomer","testHotelUsername","Pending", "", "testHotelName");
        ReservationsService.addReservation("Double","2","01-12-2020","05-12-2020","testCustomer3","testHotelUsername3","Pending", "", "testHotelName3");
        ReservationsService.checkOldVersionDoesNotExist2("testCustomer","testHotelUsername","Double","2","01-12-2020","05-12-2020");
        assertEquals(2,ReservationsService.getReservationList().size());
    }

    @Test
    public void testCheckIfIsInt() throws Exception {
        ReservationsService.checkIfIsInt("2");
    }

    @Test(expected = NumberOfRoomsException.class)
    public void testCheckIfIsInt2() throws Exception {
        ReservationsService.checkIfIsInt("a");
    }

    @Test
    public void testIsDate() throws Exception {
        ReservationsService.isDate("1-2-2020");
    }

    @Test(expected = CheckInOutDateException.class)
    public void testIsDate2() throws Exception {
        ReservationsService.isDate("1/2/2020");
    }

    @Test
    public void testDeleteReservation() throws Exception{
        ReservationsService.loadReservationListFile();
        ReservationsService.addReservation("Double","2","01-12-2020","05-12-2020","testCustomer","testHotelUsername","Pending", "", "testHotelName");
        ReservationsService.addReservation("Double","2","01-12-2020","05-12-2020","testCustomer2","testHotelUsername2","Pending", "", "testHotelName2");
        ReservationsService.addReservation("Double","2","01-12-2020","05-12-2020","testCustomer3","testHotelUsername3","Pending", "", "testHotelName3");
        Reservation r2 = ReservationsService.getReservationList().get(1);
        ReservationsService.deleteReservation(r2);
        ReservationsService.loadReservationListFile();
        assertEquals(2,ReservationsService.getReservationList().size());
    }

    @Test
    public void testPersistReservations() throws Exception {
        ReservationsService.loadReservationListFile();
        ReservationsService.addReservation("Double","2","01-12-2020","05-12-2020","testCustomer3","testHotelUsername3","Pending", "", "testHotelName3");
        ReservationsService.persistReservations();
        ReservationsService.loadReservationListFile();
        assertEquals(1,ReservationsService.getReservationList().size());
    }

    @Test
    public void testCheckInformation() throws Exception {
        ReservationsService.checkInformation("room1", "2", "02-12-2020","04-12-2020");
    }

    @Test(expected = RoomTypeException.class)
    public void testCheckInformation2() throws Exception {
        ReservationsService.checkInformation(null, "2", "02-12-2020","04-12-2020");
    }

    @Test(expected = NumberOfRoomsException.class)
    public void testCheckInformation3() throws Exception {
        ReservationsService.checkInformation("room1", "a", "02-12-2020","04-12-2020");
        ReservationsService.checkInformation("room1", "0", "02-12-2020","04-12-2020");
    }

}