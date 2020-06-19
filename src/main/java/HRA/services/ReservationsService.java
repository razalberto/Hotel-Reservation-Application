package HRA.services;

import HRA.exceptions.*;
import HRA.model.Reservation;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ReservationsService {
    private static List<Reservation> reservationList;
    private static final Path RESERVATIONS_PATH = FileSystemService.getPathToFile("config", "reservationList.json");

    public static void loadReservationListFile() throws IOException {

        if (!Files.exists(RESERVATIONS_PATH)) {
            FileUtils.copyURLToFile(ReservationsService.class.getClassLoader().getResource("reservationList.json"), RESERVATIONS_PATH.toFile());
        }

        ObjectMapper objectMapper = new ObjectMapper();

        reservationList = objectMapper.readValue(RESERVATIONS_PATH.toFile(), new TypeReference<List<Reservation>>() {
        });
    }
    public static void addReservation(String roomType, String numberOfRooms, String checkInDate, String checkOutDate, String customerName, String hotelName, String reservationState, String message, String hotelActualName) throws ReservationAlreadyExist {
        checkOldVersionDoesNotExist(new Reservation(roomType, numberOfRooms, checkInDate, checkOutDate, customerName, hotelName, reservationState, message, hotelActualName));
        checkOldVersionDoesNotExist2(customerName,hotelName,roomType,numberOfRooms,checkInDate,checkOutDate);
        reservationList.add(new Reservation(roomType, numberOfRooms, checkInDate, checkOutDate, customerName, hotelName, reservationState, message, hotelActualName));
        persistReservations();
    }

    private static void persistReservations() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(RESERVATIONS_PATH.toFile(), reservationList);
        } catch (IOException e) {
            throw new CouldNotWriteReservationsException();
        }
    }
    public static void checkOldVersionDoesNotExist(Reservation username) throws ReservationAlreadyExist {
        for (Iterator<Reservation> iterator = reservationList.iterator(); iterator.hasNext(); ) {
            Reservation value = iterator.next();
            if (username.hashCode() == value.hashCode()) {
                throw new ReservationAlreadyExist();
            }
        }
    }
    public static void checkOldVersionDoesNotExist2(String customerName,String hotelManagerName,String roomType, String numberOfRooms, String checkInDate, String checkOutDate) {
        for (Iterator<Reservation> iterator = reservationList.iterator(); iterator.hasNext(); ) {
            Reservation value = iterator.next();
            if (Objects.equals(customerName, value.getCustomerName()) && Objects.equals(hotelManagerName, value.getHotelName()) && Objects.equals(roomType, value.getRoomType()) && Objects.equals(numberOfRooms, value.getNumberOfRooms()) && Objects.equals(checkInDate, value.getCheckInDate()) && Objects.equals(checkOutDate, value.getCheckOutDate())) {
                iterator.remove();
            }
        }
    }

    public static void checkInformation(String roomType, String numberOfRooms, String checkInDate, String checkOutDate) throws ReservationException {
            if (roomType == null) {
                throw new RoomTypeException();
            }
            if (checkIfIsInt(numberOfRooms) && Integer.parseInt(numberOfRooms) < 1) {
                throw new NumberOfRoomsException();
            }
            isDate(checkInDate);
            isDate(checkOutDate);
        }

        public static boolean checkIfIsInt(String number) throws NumberOfRoomsException {
            try {
                Integer.parseInt(number);
            }
            catch (NumberFormatException e)
            {
                throw new NumberOfRoomsException();
            }
        return true;
        }

    public static void isDate(String date) throws CheckInOutDateException {

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
            dateFormat.parse(date);
        } catch (DateTimeParseException | ParseException e) {
            throw new CheckInOutDateException();
        }
    }

    public static List<Reservation> getReservationList() {
        return reservationList;
    }

    public static void deleteReservation(Reservation r){
        reservationList.remove(r);
        persistReservations();
    }
}