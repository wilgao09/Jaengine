package jaengine.modules.messages;

import java.time.LocalDateTime;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

public class Debug { //everything gonna be static
    private static String startTime; //this is here because i cant get final to work the way i want it to
    private static boolean initialized = false;
    private static BufferedWriter spit;
    public static boolean init() {
        if (!Debug.initialized) {
            LocalDateTime now = LocalDateTime.now();
            Debug.startTime = "" + now.getDayOfMonth() + "-" + now.getMonthValue() + "-" +now.getYear() + "---" + now.getHour() + "-" + now.getMinute() + "-" + now.getSecond();
            try {
                FileWriter n = new FileWriter("logs/"+Debug.startTime + ".txt");
                Debug.spit = new BufferedWriter(n);
                Debug.initialized = true;
            } catch (IOException e) {
                System.out.println("ERRRR");
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public static void log(String s) {
        LocalDateTime now = LocalDateTime.now();
        String timeNow = "[" + now.getHour() + ":" + now.getMinute() + ":" + now.getSecond() + "]";
        try {
            
            spit.write(timeNow + "\t\t" +s  + "\n");
        } catch (IOException e) {
            System.out.println("Failed write");
        }
    }

    public static void close() {
        try {
            spit.close();
        } catch (IOException e) {
            System.out.println("FAILED TO CLOSE DEBUG LOGGER");
        }
    }
}