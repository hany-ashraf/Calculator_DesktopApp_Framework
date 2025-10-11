package utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads and validates configuration values from config.properties
 * located in src/test/resources.
 */
public class ConfigReader {

    private static final Properties props = new Properties();

    static {
        loadConfig();
    }

    /**
     * Loads the configuration file once at startup.
     */
    public static void loadConfig() {
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new RuntimeException("❌ config.properties not found in classpath (expected in src/test/resources)");
            }

            props.load(input);
            System.out.println("✅ Configuration loaded successfully");

            validateConfig();

        } catch (IOException e) {
            throw new RuntimeException("❌ Failed to load configuration file: " + e.getMessage(), e);
        }
    }

    /**
     * Validates required properties and basic correctness.
     */
    private static void validateConfig() {
        String appPath = props.getProperty("appPath");
        String winAppDriverUrl = props.getProperty("winAppDriverUrl");

        if (winAppDriverUrl == null || winAppDriverUrl.isEmpty()) {
            throw new RuntimeException("❌ Missing required property: winAppDriverUrl");
        }

        // appPath is optional for UWP apps like Calculator
        if (appPath != null && !appPath.isEmpty()) {
            if (appPath.endsWith(".exe") || appPath.endsWith(".EXE")) {
                File f = new File(appPath);
                if (!f.exists()) {
                    throw new RuntimeException("❌ appPath points to a non-existent file: " + appPath);
                }
            }
        }

        System.out.println("✅ Configuration validation completed.");
    }

    /**
     * Get a property value by key.
     */
    public static String getProperty(String key) {
        return props.getProperty(key);
    }
}
