package uz.pdp.service;

import java.io.File;
import java.util.*;

public interface SaverService {

    void saveToFile(String username, String text);
    String getHistory();
    void clearHistory();
}
