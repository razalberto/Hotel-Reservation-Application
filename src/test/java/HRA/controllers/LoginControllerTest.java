package HRA.controllers;

import HRA.services.FileSystemService;
import HRA.services.HotelManagerService;
import HRA.services.UserService;
import javafx.application.Platform;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.*;

public class LoginControllerTest extends ApplicationTest{

    public static final String TEST_USER = "testUser";
    public static final String TEST_PASSWORD = "testPassword";
    private LoginController controller;

    @BeforeClass
    public static void setupClass() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-HRA";
        FileSystemService.initApplicationHomeDirIfNeeded();
        UserService.loadUsersFromFile();
    }

    @Before
    public void setUp() throws Exception {
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath().toFile());
        UserService.loadUsersFromFile();
        HotelManagerService.loadHotelManagersFromFile();

        controller = new LoginController();
        controller.setUsernameField(new TextField());
        controller.setPasswordField(new PasswordField());
        controller.setLoginMessage(new Text());

        controller.getPasswordField().setText(TEST_PASSWORD);
        controller.getUsernameField().setText(TEST_USER);
        UserService.addUser("testUsername1", "testPassword1", "Hotel Manager", "testName1", "testAddress1", "testEmail1", "testPhone");
    }

    @Test
    public void testHandleLoginButtonAction() throws Exception {
        controller.setUsernameField(new TextField(null));
        controller.handleLoginButtonAction();
        assertEquals("Please type in a username!", controller.loginMessage.getText());
    }

    @Test
    public void testHandleLoginButtonAction2() throws Exception {
        controller.setPasswordField(new PasswordField());
        controller.handleLoginButtonAction();
        assertEquals("Password cannot be empty", controller.loginMessage.getText());
    }

    @Test
    public void testHandleLoginButtonAction3() throws Exception {
        controller.handleLoginButtonAction();
        assertEquals(false, UserService.getAccession());
    }

    @Test
    public void testHandleLoginButtonAction4() throws Exception {
        controller.setUsernameField(new TextField("testUsername1"));
        controller.handleLoginButtonAction();
        assertEquals(false, UserService.getAccession());
    }

    @Test
    public void testHandleLoginButtonAction5() throws Exception {
        controller.setUsernameField(new TextField("testUsername1"));
        controller.getPasswordField().setText("testPassword1");
        UserService.checkUserAndPassword(controller.getUsernameField().getText(), controller.getPasswordField().getText());
        assertEquals(true, UserService.getAccession());
    }

    @Test
    public void testHandleRegisterButtonAction() throws Exception{
        Platform.runLater(
                () -> {
                    try {
                        controller.handleRegisterButtonAction();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
    }
}
