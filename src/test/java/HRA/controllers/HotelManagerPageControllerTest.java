package HRA.controllers;


import HRA.model.Room;
import HRA.services.FileSystemService;
import HRA.services.HotelManagerService;


import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;


import java.util.ArrayList;


import static org.junit.Assert.assertEquals;

public class HotelManagerPageControllerTest extends ApplicationTest {

    private HotelManagerPageController controller;
    private ArrayList<String> facilities = new ArrayList();
    private ArrayList<Room> rooms = new ArrayList();


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

        controller = new HotelManagerPageController();
        controller.setHotelName(new Text());
        controller.setSaveMessage(new Text());
        controller.setImageName1Field(new TextField());
        controller.setPaneView1(new Pane());
        controller.setImageName2Field(new TextField());
        controller.setPaneView2(new Pane());
        controller.setRoomTypeField(new TextField());
        controller.setCapacityField(new TextField());
        controller.setPriceField(new TextField());
        controller.setHotelFacilityField(new TextField());
        controller.setHotelFacilitiesList(new ListView<>());
        controller.setRoomTableView(new TableView<>());
        controller.setTypeColumn(new TableColumn<>());
        controller.setCapacityColumn(new TableColumn<>());
        controller.setPriceColumn(new TableColumn<>());
        controller.setCancelButton(new Button());

    }

    @Test
    public void testSaveButtonCode() {
        controller.getHotelName().setText("Royal");
        controller.setName("Alex");
        controller.saveButtonClicked();
        assertEquals(1, HotelManagerService.getHotelManagersFromHotelManagerService().size());
        assertEquals("File saved successfully!", controller.getSaveMessage().getText());
        assertEquals("Alex", controller.getName());
        assertEquals("Royal", controller.getHotelName().getText());

    }

    @Test
    public void testAddButton1() {
        controller.getRoomTypeField().setText("Double");
        controller.getCapacityField().setText("2 Adults");
        controller.getPriceField().setText("123");
        controller.addButtonClicked1();
        assertEquals(1, controller.getRoomTableView().getItems().size());
    }

    @Test
    public void testAddButton1InvalidPrice() {
        controller.getRoomTypeField().setText("Double");
        controller.getCapacityField().setText("2 Adults");
        controller.getPriceField().setText("abc");
        controller.addButtonClicked1();
        assertEquals(0, controller.getRoomTableView().getItems().size());
        assertEquals("Price have to be a number!!", controller.getSaveMessage().getText());

    }

    @Test
    public void testDeleteButton1() {
        controller.getRoomTypeField().setText("Double");
        controller.getCapacityField().setText("2 Adults");
        controller.getPriceField().setText("123");
        controller.addButtonClicked1();
        controller.getRoomTableView().getSelectionModel().selectFirst();
        controller.deleteButtonClicked1();
        assertEquals(0, controller.getRoomTableView().getItems().size());

    }

    @Test
    public void testAddButton2() {
        controller.getHotelFacilityField().setText("Pool");
        controller.addButtonClicked2();
        assertEquals(1, controller.getHotelFacilitiesList().getItems().size());
        assertEquals("", controller.getHotelFacilityField().getText());

    }

    @Test
    public void testDeleteButton2() {
        controller.getHotelFacilityField().setText("Pool");
        controller.addButtonClicked2();
        controller.getHotelFacilitiesList().getSelectionModel().selectFirst();
        controller.deleteButtonClicked2();
        assertEquals(0, controller.getHotelFacilitiesList().getItems().size());

    }

    @Test
    public void testDisplayImage1() {
        controller.getImageName1Field().setText("Image1");
        controller.displayImage1();
        assertEquals(1, controller.getPaneView1().getChildren().size());


    }

    @Test
    public void testRemoveImage1() {
        controller.transferImageName1("Image1");
        controller.displayImage1();
        controller.removeImage1();
        assertEquals(0, controller.getPaneView1().getChildren().size());


    }

    @Test
    public void testDisplayImage2() {
        controller.getImageName2Field().setText("Image2");
        controller.displayImage2();
        assertEquals(1, controller.getPaneView2().getChildren().size());


    }

    @Test
    public void testRemoveImage2() {
        controller.transferImageName2("Image2");
        controller.displayImage2();
        controller.removeImage2();
        assertEquals(0, controller.getPaneView2().getChildren().size());


    }

    @Test
    public void testSomeUncommonSetters() {
        Room room1 = new Room("Double", "2 Adults", 23.0);
        Room room2 = new Room("Single", "1 Adult", 15.0);
        facilities.add("Pool");
        facilities.add("Bar");
        rooms.add(room1);
        rooms.add(room2);
        controller.setPaneView1Image("Image1");
        controller.setPaneView2Image("Image2");
        controller.setHotelFacilities(facilities);
        controller.setRoomTable(rooms);
        assertEquals(2, controller.getHotelFacilitiesList().getItems().size());
        assertEquals(2, controller.getRoomTableView().getItems().size());


    }




}
