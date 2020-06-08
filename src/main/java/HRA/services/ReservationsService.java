package HRA.services;

import HRA.exceptions.CouldNotWriteHotelManagersException;
import HRA.model.Reservation;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ReservationsService {
    private static List<Reservation> reservationList;
    private static final Path RESERVATIONS_PATH = FileSystemService.getPathToFile("config", "reservationList.json");


    public static void loadReservationListFile() throws IOException {

        if (!Files.exists(RESERVATIONS_PATH)) {
            FileUtils.copyURLToFile(HotelManagerService.class.getClassLoader().getResource("reservationList.json"), RESERVATIONS_PATH.toFile());
        }

        ObjectMapper objectMapper = new ObjectMapper();

        reservationList = objectMapper.readValue(RESERVATIONS_PATH.toFile(), new TypeReference<List<Reservation>>() {
        });
    }
    public static void addReservation(String roomType, String numberOfRooms, String checkInDate, String checkOutDate, String customerName, String currentTime, String hotelName){
        checkOldVersionDoesNotExist(customerName);
        reservationList.add(new Reservation(roomType, numberOfRooms, checkInDate, checkOutDate, customerName, currentTime, hotelName));
        persistReservations();
    }

    private static void persistReservations() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(RESERVATIONS_PATH.toFile(), reservationList);
        } catch (IOException e) {
            throw new CouldNotWriteHotelManagersException();
        }
    }
    public static void checkOldVersionDoesNotExist(String username) {
        for (Iterator<Reservation> iterator = reservationList.iterator(); iterator.hasNext(); ) {
            Reservation value = iterator.next();
            if (username.hashCode() == value.hashCode()) {
                iterator.remove();
            }
        }
    }
    public static List<Reservation> getHotelManagersFromHotelManagerService() {
        return reservationList;
    }
}