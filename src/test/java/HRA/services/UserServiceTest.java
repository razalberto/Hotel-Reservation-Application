package HRA.services;

import HRA.exceptions.IncorrectPassword;
import HRA.exceptions.UsernameAlreadyExistsException;
import HRA.exceptions.UsernameDoesNotExist;
import HRA.model.User;
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



public class UserServiceTest {

    UserService userService;

    @BeforeClass
    public static void setupClass() {
        FileSystemService.APPLICATION_FOLDER = ".test-HRA";
        FileSystemService.initApplicationHomeDirIfNeeded();
    }

    @Before
    public void setUp() throws IOException {
        userService = new UserService();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomePath().toFile());
    }

    @Test
    public void testCopyDefaultFileIfNotExists() throws Exception {
        UserService.loadUsersFromFile();
        assertTrue(Files.exists(UserService.getUsersPath()));
    }

    @Test
    public void testLoadUsersFromFile() throws Exception {
        UserService.loadUsersFromFile();
        assertNotNull(UserService.getUsersFromUserService());
        assertEquals(0, UserService.getUsersFromUserService().size());
    }

    @Test
    public void testAddOneUser() throws Exception {
        UserService.loadUsersFromFile();
        UserService.addUser("test", "testPass", "Hotel Manager","Ana","Timisoara","ana@yahoo.com","0749452313");
        assertNotNull(UserService.getUsersFromUserService());
        assertEquals(1, UserService.getUsersFromUserService().size());
    }

    @Test
    public void testAddTwoUsers() throws Exception {
        UserService.loadUsersFromFile();
        UserService.addUser("test", "testPass", "Hotel Manager","Ana","Timisoara","ana@yahoo.com","0749452313");
        UserService.addUser("test1", "testPass1", "Customer","Maria","Cluj","maria@yahoo.com","0749452354");
        assertNotNull(UserService.getUsersFromUserService());
        assertEquals(2, UserService.getUsersFromUserService().size());
    }

    @Test(expected = UsernameAlreadyExistsException.class)
    public void testAddUserAlreadyExists() throws Exception {
        UserService.loadUsersFromFile();
        UserService.addUser("test", "testPass", "Hotel Manager","Ana","Timisoara","ana@yahoo.com","0749452313");
        assertNotNull(UserService.getUsersFromUserService());
        UserService.checkUserDoesNotAlreadyExist("test");
    }

    @Test
    public void testAddOneUserIsPersisted() throws Exception {
        UserService.loadUsersFromFile();
        UserService.addUser("test", "testPass", "Hotel Manager","Ana","Timisoara","ana@yahoo.com","0749452313");
        List<User> users = new ObjectMapper().readValue(UserService.getUsersPath().toFile(), new TypeReference<List<User>>() {
        });
        assertNotNull(users);
        assertEquals(1, users.size());
    }

    @Test
    public void testAddTwoUserArePersisted() throws Exception {
        UserService.loadUsersFromFile();
        UserService.addUser("test", "testPass", "Hotel Manager","Ana","Timisoara","ana@yahoo.com","0749452313");
        UserService.addUser("test1", "testPass1", "Customer","Maria","Cluj","maria@yahoo.com","0749452354");
        List<User> users = new ObjectMapper().readValue(UserService.getUsersPath().toFile(), new TypeReference<List<User>>() {
        });
        assertNotNull(users);
        assertEquals(2, users.size());
    }

    @Test
    public void testPasswordEncoding() {
        assertNotEquals("testPass1", UserService.encodePassword("username1", "testPass1"));
    }

    @Test
    public void testUsernameAndPassword1() throws Exception{
        UserService.loadUsersFromFile();
        UserService.addUser("test", "testPass", "Hotel Manager","Ana","Timisoara","ana@yahoo.com","0749452313");
        UserService.checkUserAndPassword("test","testPass");
        assertEquals(true,UserService.getAccession());
    }

    @Test(expected = IncorrectPassword.class)
    public void testUsernameAndPassword2() throws Exception{
        UserService.loadUsersFromFile();
        UserService.addUser("test", "testPass", "Hotel Manager","Ana","Timisoara","ana@yahoo.com","0749452313");
        UserService.checkUserAndPassword("test","testPass321");
        assertEquals(false,UserService.getAccession());
    }

    @Test(expected = UsernameDoesNotExist.class)
    public void testUsernameAndPassword3() throws Exception{
        UserService.loadUsersFromFile();
        UserService.addUser("test", "testPass", "Hotel Manager","Ana","Timisoara","ana@yahoo.com","0749452313");
        UserService.checkUserAndPassword("test5","testPass321");
        assertEquals(false,UserService.getAccession());
    }

}