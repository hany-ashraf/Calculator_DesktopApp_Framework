package utils;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Manages WinAppDriver sessions and configuration for Windows applications (e.g., Calculator).
 */
public class DriverManager {

    private static WindowsDriver<WebElement> driver = null;

    // Optional runtime overrides (can be set from testBase)
    private static String winAppDriverUrlOverride;
    private static String appPathOverride;

    // --- Setters ---
    public static void setWinAppDriverUrl(String url) {
        winAppDriverUrlOverride = url;
    }

    public static void setAppPath(String path) {
        appPathOverride = path;
    }

    /**
     * Returns a singleton WindowsDriver instance.
     */
    public static WindowsDriver<WebElement> getDriver() {
        if (driver == null) {

            // Load configuration file (if not already loaded)
            ConfigReader.loadConfig();

            // Determine final URL and App path
            String finalUrl = (winAppDriverUrlOverride != null && !winAppDriverUrlOverride.isEmpty())
                    ? winAppDriverUrlOverride
                    : ConfigReader.getProperty("winAppDriverUrl");

            String finalAppPath = (appPathOverride != null && !appPathOverride.isEmpty())
                    ? appPathOverride
                    : ConfigReader.getProperty("calculatorAppPath");

            System.out.println("⚙️ WinAppDriver URL: " + finalUrl);
            System.out.println("📘 Target App Path: " + finalAppPath);

            // Validate app path (only if it's a file path)
            if (finalAppPath.toLowerCase().endsWith(".exe")) {
                File appFile = new File(finalAppPath);
                if (!appFile.exists() || !appFile.isFile()) {
                    throw new RuntimeException("❌ App not found at path: " + finalAppPath);
                }
            }

            // --- Set Desired Capabilities ---
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("app", "Microsoft.WindowsCalculator_8wekyb3d8bbwe!App");
            //capabilities.setCapability("app", finalAppPath);
            capabilities.setCapability("platformName", "Windows");
            capabilities.setCapability("deviceName", "WindowsPC");

            try {
                driver = new WindowsDriver<>(new URL(finalUrl), capabilities);
                // ✅ Wait for the app to be fully ready
                Thread.sleep(2000);
                System.out.println("🟢 WinAppDriver session started successfully for app: " + finalAppPath);
            } catch (MalformedURLException e) {
                throw new RuntimeException("❌ Invalid WinAppDriver URL: " + finalUrl, e);
            } catch (Exception e) {
                throw new RuntimeException("❌ Failed to start WindowsDriver session. Details: " + e.getMessage(), e);
            }
        }
        return driver;
    }

    /**
     * Quits the current WinAppDriver session.
     */
    public static void quitDriver() {
        if (driver != null) {
            try {
                driver.quit();
                System.out.println("🟡 WinAppDriver session closed.");
            } catch (Exception e) {
                System.err.println("⚠️ Error while closing driver: " + e.getMessage());
            } finally {
                driver = null;
            }
        }
    }
}
