package HRA.services;

import HRA.exceptions.CouldNotWriteHotelManagersException;


import HRA.model.HotelManager;
import HRA.model.Room;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class HotelManagerService {
    private static List<HotelManager> hotelManagers;
    private static final Path MANAGERS_PATH = FileSystemService.getPathToFile("config", "hmevidence.json");


    public static void loadHotelManagersFromFile() throws IOException {

        if (!Files.exists(MANAGERS_PATH)) {
            FileUtils.copyURLToFile(HotelManagerService.class.getClassLoader().getResource("hmevidence.json"), MANAGERS_PATH.toFile());
        }

        ObjectMapper objectMapper = new ObjectMapper();

        hotelManagers = objectMapper.readValue(MANAGERS_PATH.toFile(), new TypeReference<List<HotelManager>>() {
        });
    }
    public static void addManager(String username, List<Room> roomList, String imageName1, String imageName2, List<String> facilities, String hotelName){
        checkOldVersionDoesNotExist(username);
        hotelManagers.add(new HotelManager(username,roomList,imageName1,imageName2,facilities,hotelName));
        persistManagers();
    }

    private static void persistManagers() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(MANAGERS_PATH.toFile(), hotelManagers);
        } catch (IOException e) {
            throw new CouldNotWriteHotelManagersException();
        }
    }
    public static void checkOldVersionDoesNotExist(String username) {
        for( HotelManager manager : hotelManagers){
            if(Objects.equals(username,manager.getUsername())){
                hotelManagers.remove(manager);
            }
        }


    }
    public static List<HotelManager> getHotelManagersFromHotelManagerService() {
        return hotelManagers;
    }
}
