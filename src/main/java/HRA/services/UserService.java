package HRA.services;
import HRA.exceptions.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import HRA.exceptions.UsernameDoesNotExist;
import HRA.model.User;
import HRA.exceptions.CouldNotWriteUsersException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

public class UserService {

    private static List<User> users;
    private static final Path USERS_PATH = FileSystemService.getPathToFile("config", "users.json");
    private static boolean accession = false;

    public static void loadUsersFromFile() throws IOException {

        if (!Files.exists(USERS_PATH)) {
            FileUtils.copyURLToFile(UserService.class.getClassLoader().getResource("users.json"), USERS_PATH.toFile());
        }

        ObjectMapper objectMapper = new ObjectMapper();

        users = objectMapper.readValue(USERS_PATH.toFile(), new TypeReference<List<User>>() {
        });
    }

    public static void addUser(String username, String password, String role, String name, String address, String email, String phoneNumber) throws UsernameAlreadyExistsException, PasswordTooShortException, UsernameIsEmptyException {
        checkUserDoesNotAlreadyExist(username);
        checkUsernameIsNotEmpty(username);
        checkPasswordIsLongEnough(password);
        users.add(new HRA.model.User(username, encodePassword(username, password), role, name, address, email, phoneNumber));
        persistUsers();
    }

    public static void checkUserDoesNotAlreadyExist(String username) throws UsernameAlreadyExistsException {
        for (HRA.model.User user : users) {
            if (Objects.equals(username, user.getUsername()))
                throw new UsernameAlreadyExistsException(username);
        }
    }
    private static void checkPasswordIsLongEnough(String password) throws PasswordTooShortException{
        if(password.length() < 4)
            throw new PasswordTooShortException();
    }
    private static void checkUsernameIsNotEmpty(String username) throws UsernameIsEmptyException{
        if(username.isEmpty())
            throw new UsernameIsEmptyException();

    }
    private static void persistUsers() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(USERS_PATH.toFile(), users);
        } catch (IOException e) {
            throw new CouldNotWriteUsersException();
        }
    }

    public static void checkUserAndPassword(String username, String password) throws UsernameDoesNotExist, IncorrectPassword {
        for (User user : users) {
            if (Objects.equals(username, user.getUsername()))
                if(Objects.equals(encodePassword(username,password),user.getPassword())) {
                    accession = true;
                    return;
                }
                else {
                    accession = false;
                    throw new IncorrectPassword();
                }
        }
        accession = false;
        throw new UsernameDoesNotExist();
    }

    public static boolean getAccession() {
        return accession;
    }

    public static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        // This is the way a password should be encoded when checking the credentials
        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", ""); //to be able to save in JSON format
    }

    private static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }

    public static List<User> getUsersFromUserService(){
        return users;
    }

    public static Path getUsersPath() {
        return USERS_PATH;
    }
}
