package HRA.services;

import HRA.model.HotelManager;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.Assert.*;

public class HotelManagerServiceTest {

    HotelManagerService hotelManagerService;

    @BeforeClass
    public static void setupClass() {
        FileSystemService.APPLICATION_FOLDER = ".test-HRA";
        FileSystemService.initApplicationHomeDirIfNeeded();
    }

    @Before
    public void setUp() throws IOException {
        hotelManagerService = new HotelManagerService();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath().toFile());
    }

    @Test
    public void testCopyDefaultFileIfNotExists() throws Exception {
        HotelManagerService.loadHotelManagersFromFile();
        assertTrue(Files.exists(HotelManagerService.getManagersPath()));
    }

    @Test
    public void testLoadManagersFromFile() throws Exception {
        HotelManagerService.loadHotelManagersFromFile();
        assertNotNull(HotelManagerService.getHotelManagersFromHotelManagerService());
        assertEquals(0, HotelManagerService.getHotelManagersFromHotelManagerService().size());
    }

    @Test
    public void testAddOneUser() throws Exception {
        HotelManagerService.loadHotelManagersFromFile();
        HotelManagerService.addManager("marius",null,"image1","image2",null,"Majestic");
        assertNotNull(HotelManagerService.getHotelManagersFromHotelManagerService());
        assertEquals(1, HotelManagerService.getHotelManagersFromHotelManagerService().size());
    }

    @Test
    public void testAddOneUserIsPersisted() throws Exception {
        HotelManagerService.loadHotelManagersFromFile();
        HotelManagerService.addManager("marius",null,"image1","image2",null,"Majestic");
        List<HotelManager> hotelManagers = new ObjectMapper().readValue(HotelManagerService.getManagersPath().toFile(), new TypeReference<List<HotelManager>>() {
        });
        assertNotNull(hotelManagers);
        assertEquals(1, hotelManagers.size());
    }

    @Test
    public void testAddTwoUserArePersisted() throws Exception {
        HotelManagerService.loadHotelManagersFromFile();
        HotelManagerService.addManager("marius",null,"image1","image2",null,"Majestic");
        HotelManagerService.addManager("alex",null,"image12","image22",null,"Royal");
        List<HotelManager> hotelManagers = new ObjectMapper().readValue(HotelManagerService.getManagersPath().toFile(), new TypeReference<List<HotelManager>>() {
        });
        assertNotNull(hotelManagers);
        assertEquals(2, hotelManagers.size());
    }

    @Test
    public void testOldVersionExists() throws Exception{
        HotelManagerService.loadHotelManagersFromFile();
        HotelManagerService.addManager("marius",null,"image1","image2",null,"Majestic");
        HotelManagerService.addManager("marius",null,"image12","image22",null,"Majestic");
        assertEquals(1, HotelManagerService.getHotelManagersFromHotelManagerService().size());


    }


}
