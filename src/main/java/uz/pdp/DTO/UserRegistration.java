package uz.pdp.DTO;



import uz.pdp.exceptions.InvalidEmailException;
import uz.pdp.exceptions.InvalidPasswordException;

import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public record UserRegistration(String userName,String email, String password, String confirmPassword) {

    public static final String IS_VALID_EMAIL_ADDRESS = "^[a-zA-Z0-9.]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}$";
    public static final String IS_VALID_PASSWORD = "^(?=.*[A-Z]+)(?=.*[a-z]+)(?=.*\\d+).{5,16}$";

    public UserRegistration {
        if (userName == null || email == null || password == null)
            throw new IllegalArgumentException("Input can't be null");
        if (!email.matches(IS_VALID_EMAIL_ADDRESS)) {
            try {
                throw new InvalidEmailException("Invalid email input");
            } catch (InvalidEmailException e) {
                throw new RuntimeException(e);
            }
        }
        if (!password.equals(confirmPassword) || !(password.matches(IS_VALID_PASSWORD))) {
            try {
                throw new InvalidPasswordException("Invalid password input");
            } catch (InvalidPasswordException e) {
                throw new RuntimeException(e);
            }
        }
    }
}