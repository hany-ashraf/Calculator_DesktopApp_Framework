package utils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utility {

    /**
     * Utility method to get current timestamp
     * @return formatted timestamp string
     */
    public String getCurrentTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }


    /**
     * Utility method to pause execution
     * @param milliseconds time to sleep
     */
    protected void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("⚠️ Sleep interrupted: " + e.getMessage());
        }
    }

    /**
     * Utility method to log info messages
     * @param message message to log
     */
    protected void logInfo(String message) {
        System.out.println("ℹ️ " + message);
    }

    /**
     * Utility method to log error messages
     * @param message error message to log
     */
    protected void logError(String message) {
        System.err.println("❌ " + message);
    }

    /**
     * Utility method to log warning messages
     * @param message warning message to log
     */
    protected void logWarning(String message) {
        System.out.println("⚠️ " + message);
    }
}
