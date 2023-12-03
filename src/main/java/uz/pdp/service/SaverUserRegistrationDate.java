package uz.pdp.service;


import uz.pdp.DTO.UserRegistration;
import uz.pdp.entities.User;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class SaverUserRegistrationDate {
    private File userData;
    private static SaverUserRegistrationDate saver = new SaverUserRegistrationDate();
    private SaverUserRegistrationDate() {
        this.userData = new File("/Users/safixonabdusattorov/Java online tasks/CHAT APP/recourses/user_data.txt");
    }

    public static SaverUserRegistrationDate getInstance() {
        return saver;
    }

    public Optional<User> saveUserDateRegistration(UserRegistration userRegistration) {
        User user = null;
        if (userRegistration == null) {
            return Optional.empty();
        } else {

            try (FileOutputStream FileOut = new FileOutputStream(userData, true);
                 ObjectOutputStream out = new ObjectOutputStream(FileOut)) {
                user = new User(userRegistration.userName(), userRegistration.email(), userRegistration.password());
                out.writeObject(user);
            } catch (IOException e) {
                Optional.empty();
//                throw new RuntimeException(e);
            }
        }
        return Optional.of(user);
    }

    private User getUser(String email) {
        try (FileInputStream file = new FileInputStream(userData);
             ObjectInputStream in = new ObjectInputStream(file)) {
            while (true) {
                User curr = (User) in.readObject();
                if (curr.getEmail().equals(email)) {
                    return curr;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
    public boolean checkUser(String email) {
        return !(getUser(email) == null);
    }
    public Optional<User> getUser(String email, String password) {
        User curr = getUser(email);
        if (curr != null) {
            if (curr.getPassword().equals(password)) {
                return Optional.of(curr);
            }
        }
        return Optional.empty();
    }
}
