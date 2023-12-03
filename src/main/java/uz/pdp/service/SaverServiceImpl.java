package uz.pdp.service;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SaverServiceImpl implements SaverService {
    File chatHistory;

    private static SaverServiceImpl saver = new SaverServiceImpl();

    private SaverServiceImpl() {
        this.chatHistory = new File("/Users/safixonabdusattorov/Java online tasks/CHAT APP/recourses/chat_history.txt");
    }

    public static SaverServiceImpl getInstance() {
        return saver;
    }

    @Override
    public void saveToFile(String username, String text) {
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try (FileOutputStream fileOutputStream = new FileOutputStream(chatHistory, true);) {
            String format1 = LocalDateTime.now().format(dateTimeFormatter);
            String date = LocalDateTime.now().format(dateTimeFormatter);
            fileOutputStream.write((username + "  " + text + "  " + date + '\n').getBytes());
            System.out.println("the information is successfully loaded to Data base ! ");
        } catch (IOException e) {
            throw new RuntimeException("Failed to save");
        }

    }

    @Override
    public String getHistory() {
        try (FileInputStream in = new FileInputStream(chatHistory)) {
            return new String(in.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException("Unreadable Date base from SaverServiceIMPL class");
        }
    }
    @Override
    public void clearHistory() {
        PrintWriter printWriter= null;
        try {
            printWriter = new PrintWriter(chatHistory);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }finally {
            printWriter.close();
        }
    }
}
