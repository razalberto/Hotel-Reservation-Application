package HRA.controllers;



import HRA.exceptions.UsernameAlreadyExistsException;
import HRA.exceptions.UsernameIsEmptyException;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import HRA.services.FileSystemService;
import HRA.services.UserService;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;

    public class RegistrationControllerTest extends ApplicationTest {

        public static final String TEST_USER = "testUser";
        public static final String TEST_PASSWORD = "testPassword";
        private RegistrationController controller;

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

            controller = new RegistrationController();
            controller.setUsernameField(new TextField());
            controller.setPasswordField( new PasswordField());
            controller.setAddressField(new TextField());
            controller.setEmailField(new TextField());
            controller.setNameField(new TextField());
            controller.setPhoneNumberField(new TextField());
            controller.setRole(new ChoiceBox());
            controller.setRegistrationMessage(new Text());

            controller.getPasswordField().setText(TEST_PASSWORD);
            controller.getUsernameField().setText(TEST_USER);
        }

        @Test
        public void testInitialize(){
            controller.initialize();
            assertEquals("Customer",controller.getRole().getItems().get(0));
            assertEquals("Hotel Manager",controller.getRole().getItems().get(1));
        }

        @Test
        public void testHandleAddUserActionCode() {
            controller.handleRegisterAction();
            assertEquals(1, UserService.getUsersFromUserService().size());
            assertEquals("Account created successfully!", controller.getRegistrationMessage().getText());
        }

        @Test
        public void testAddSameUserTwice() {
            UsernameAlreadyExistsException exception = new UsernameAlreadyExistsException("testUser");
            controller.handleRegisterAction();
            controller.handleRegisterAction();
            assertEquals("testUser", exception.getUsername());
            assertEquals("An account with the username " + TEST_USER + " already exists!", controller.getRegistrationMessage().getText());
        }

        @Test
        public void testUsernameFieldIsEmpty(){
            controller.getUsernameField().setText("");
            controller.handleRegisterAction();
            assertEquals("Username field cannot be empty!",controller.getRegistrationMessage().getText());
        }

        @Test
        public void testPasswordIsTooShort(){
            controller.getPasswordField().setText("123");
            controller.handleRegisterAction();
            assertEquals("Password must contain at least 4 characters!",controller.getRegistrationMessage().getText());

        }
    }

