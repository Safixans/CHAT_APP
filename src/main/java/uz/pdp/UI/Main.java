package uz.pdp.UI;

import uz.pdp.DTO.UserRegistration;
import uz.pdp.entities.User;
import uz.pdp.service.SaverService;
import uz.pdp.service.SaverServiceImpl;
import uz.pdp.service.SaverUserRegistrationDate;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
    /**
     * Chat App yozing. Barcha ma'lumotlarni fileda saqlang.
     * Authorization qilib keyin chatni ishlata olsin! email orqali registeratsiya qila olsin faqat.
     * Login ham email orqali bo'ladi regexpdan foydalanib userni barcha ma'lumotlarni tekshiruvdan o'tkazing.
     * Barcha mufaqiyatli bo'lsa log tashlang.
     * User qachon registeratsiya bo'lgani Time classlaridan biridan foydalanib filega yozing filedan o'qib usha classga parse qiling.
     * Barcha chatlashuvlarni ozi bilan qachon yozilganigacha vaqtni saqlab keting.
     * Dastur yozib bo'lganingizdan keyin githubga qo'ying.
     */
    static Logger logger = Logger.getLogger(Main.class.getName());
    static Scanner in = new Scanner(System.in);
    static SaverService chatHistory = SaverServiceImpl.getInstance();
    static SaverUserRegistrationDate userService = SaverUserRegistrationDate.getInstance();

    public static void main(String[] args) throws IOException {


        boolean loop = true;
        LogManager.getLogManager().readConfiguration(new FileInputStream("resources/logging.properties"));
        Optional<User> curr;
        while (loop) {
            System.out.print("""
                    1 - Registration
                    2 - Log in
                    3 - Exit
                    Enter operation: """);
            String operation = in.next();
            curr = switch (operation) {
                case "1" -> registration();
                case "2" -> logIn();
                case "3" -> {
                    loop = false;
                    yield Optional.empty();
                }
                default -> {
                    System.out.println("You entered wrong option");
                    yield Optional.empty();
                }
            };
            curr.ifPresent(Main::userPanel);
        }
    }

    private static void userPanel(User user) {
        boolean loop = true;
        while (loop) {
            System.out.println("""
                    choose options given below.
                    
                    1.Send messages.
                    2.read all messages.
                    3.Clear all messages.
                    4.User registration Date
                    5.Log out""");
            String option = in.next();
            switch (option) {
                case "1" -> sendMessage(user.getUserName());
                case "2" -> readAllMessages();
                case "3" -> clearAllMessages();
                case "4" -> System.out.println(user.getUserName() + "\t" + user.getRegistrationDate());
                case "5" -> loop = false;
                default -> System.out.println("wrong input");
            }
        }
    }

    private static void clearAllMessages() {
        chatHistory.clearHistory();
    }

    private static void readAllMessages() {
        System.out.println("Chat: ");
        System.out.println(chatHistory.getHistory());
    }

    private static void sendMessage(String username) {
        in.nextLine();
        System.out.print(username + ": ");
        String message = in.nextLine();
        chatHistory.saveToFile(username, message);
    }

    private static Optional<User> registration() {
//        String userName,String email, String password, String confirmPassword
        System.out.print("Enter Email: ");
        String email = in.next();
        if (userService.checkUser(email)) {
            System.out.println("This email have already been registered");
            return Optional.empty();
        }
        System.out.print("Enter username: ");
        String username = in.next();
        System.out.print("Enter password: ");
        String password = in.next();
        System.out.print("Confirm password: ");
        String confirmPassword = in.next();

        UserRegistration userRegistration = new UserRegistration(username, email, password, confirmPassword);
        return userService.saveUserDateRegistration(userRegistration);
    }

    private static Optional<User> logIn() {
        System.out.print("Enter email: ");
        String email = in.next();
        System.out.print("Enter password: ");
        String password = in.next();
        Optional<User> user = userService.getUser(email, password);
        if (user.isEmpty()) System.out.println("User is not found");
        return user;

    }


}
