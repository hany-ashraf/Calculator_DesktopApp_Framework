package testcases;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.appium.java_client.windows.WindowsDriver;
import listeners.TestExecutionListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.P01_CalculatorPage;
import utils.ConfigReader;
import utils.DriverManager;
import listeners.TestExecutionListener;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

/**
 * Base Test Class for Calculator Automation
 * Handles driver lifecycle, configuration, and common test utilities
 */
@Listeners({ChainTestListener.class, TestExecutionListener.class})
public class testBase {

    static FileInputStream readProperty;
    static Properties prop;
    private static String PROJECT_NAME = null;
    private static String PROJECT_URL = null;
    protected WindowsDriver<WebElement> driver;
    protected P01_CalculatorPage calculatorPage;

    // logger
    private static final Logger logger = LogManager.getLogger(testBase.class);

    // extend report
    protected static ExtentSparkReporter htmlReporter;
    protected static ExtentReports extent;
    protected static ExtentTest test;

    // Load configuration once when class is loaded
    static {
        try {
            ConfigReader.loadConfig();
            System.out.println("✅ Configuration loaded successfully");
        } catch (Exception e) {
            System.err.println("❌ Failed to load configuration: " + e.getMessage());
            throw new RuntimeException("Configuration initialization failed", e);
        }
    }

    @BeforeSuite
    public void beforeSuite() throws Exception {

        // initialize the HtmlReporter
        htmlReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/testReport.html");

        //initialize ExtentReports and attach the HtmlReporter
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        setProjectDetails();

        // initialize test
        test = extent.createTest(PROJECT_NAME + " Test Automation Project");

        //configuration items to change the look and fee add content, manage tests etc
        htmlReporter.config().setDocumentTitle(PROJECT_NAME + " Test Automation Report");
        htmlReporter.config().setReportName(PROJECT_NAME + " Test Report");
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
    }


    /**
     * Suite-level setup - runs once before all tests
     * Configures WinAppDriver URL and Calculator path
     */
    @BeforeSuite(alwaysRun = true)
    private void setProjectDetails() throws IOException {
        // TODO: Step1: define object of properties file
        readProperty = new FileInputStream(
                System.getProperty("user.dir") + "/src/test/resources/properties/environment.properties");
        prop = new Properties();
        prop.load(readProperty);

        // define project name from properties file
        PROJECT_NAME = prop.getProperty("projectName");
        PROJECT_URL = prop.getProperty("url");
    }

    @Parameters({"winAppDriverUrl", "calculatorAppPath"})
    public void setupSuite(
            @Optional("http://127.0.0.1:4723") String winAppDriverUrl,
            @Optional("") String calculatorAppPath
    ) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("🚀 CALCULATOR AUTOMATION TEST SUITE INITIALIZATION");
        System.out.println("=".repeat(70));
        System.out.println("⏰ Start Time: " + getCurrentTimestamp());

        // Use TestNG parameters if provided, otherwise fallback to config.properties
        if (calculatorAppPath == null || calculatorAppPath.isEmpty()) {
            calculatorAppPath = ConfigReader.getProperty("calculatorAppPath");
            System.out.println("📋 Using Calculator path from config: " + calculatorAppPath);
        } else {
            System.out.println("📋 Using Calculator path from TestNG parameter: " + calculatorAppPath);
        }

        // Configure DriverManager with runtime parameters
        DriverManager.setWinAppDriverUrl(winAppDriverUrl);
        DriverManager.setAppPath(calculatorAppPath);

        System.out.println("🔗 WinAppDriver URL: " + winAppDriverUrl);
        System.out.println("✅ Suite setup completed successfully\n");
    }

    /**
     * Test-level setup - runs before each test class
     * Initializes driver and page objects
     */
    @BeforeTest(alwaysRun = true)
    public void setupTest() {
        System.out.println("\n" + "-".repeat(70));
        System.out.println("📱 Launching Calculator Application...");

        try {
            driver = DriverManager.getDriver();
            calculatorPage = new P01_CalculatorPage(driver);
            calculatorPage.waitForCalculatorToLoad();
            System.out.println("✅ Calculator launched and ready for testing");
        } catch (Exception e) {
            System.err.println("❌ Failed to launch Calculator: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Calculator initialization failed", e);
        }
    }

    /**
     * Method-level setup - runs before each test method
     * Can be used for test-specific initialization
     */
    @BeforeMethod(alwaysRun = true)
    public void setupMethod(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();

        System.out.println("\n" + "─".repeat(70));
        System.out.println("🧪 Starting Test: " + testName);
        if (description != null && !description.isEmpty()) {
            System.out.println("📝 Description: " + description);
        }
        System.out.println("⏰ Test Start Time: " + getCurrentTimestamp());
        System.out.println("─".repeat(70));

        // Clear calculator before each test
        if (calculatorPage != null) {
            try {
                calculatorPage.clear();
            } catch (Exception e) {
                System.out.println("⚠️ Could not clear calculator: " + e.getMessage());
            }
        }
    }

    /**
     * Method-level teardown - runs after each test method
     * Logs test results and captures failures
     */
    @AfterMethod(alwaysRun = true)
    public void teardownMethod(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String status = getTestStatus(result);

        System.out.println("─".repeat(70));
        System.out.println("📊 Test Result: " + status);
        System.out.println("⏰ Test End Time: " + getCurrentTimestamp());

        if (result.getStatus() == ITestResult.FAILURE) {
            System.err.println("❌ Test Failed: " + testName);
            System.err.println("💥 Failure Reason: " + result.getThrowable().getMessage());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            System.out.println("✅ Test Passed: " + testName);
        } else if (result.getStatus() == ITestResult.SKIP) {
            System.out.println("⏭️ Test Skipped: " + testName);
        }

        System.out.println("─".repeat(70) + "\n");
    }

    /**
     * Test-level teardown - runs after each test class
     * Closes driver and cleans up resources
     */
    @AfterTest(alwaysRun = true)
    public void teardownTest() {
        System.out.println("\n" + "-".repeat(70));
        System.out.println("🧹 Cleaning up test session...");

        if (driver != null) {
            try {
                DriverManager.quitDriver();
                driver = null;
                calculatorPage = null;
                System.out.println("✅ Calculator closed successfully");
            } catch (Exception e) {
                System.err.println("⚠️ Error during cleanup: " + e.getMessage());
            }
        } else {
            System.out.println("ℹ️ No driver to clean up");
        }

        System.out.println("-".repeat(70));
    }

    /**
     * Suite-level teardown - runs once after all tests
     * Final cleanup and summary
     */
    @AfterSuite(alwaysRun = true)
    public void teardownSuite() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("🏁 CALCULATOR TEST SUITE COMPLETED");
        System.out.println("⏰ End Time: " + getCurrentTimestamp());
        System.out.println("=".repeat(70) + "\n");
    }

    /**
     * Utility method to get current timestamp
     * @return formatted timestamp string
     */
    protected String getCurrentTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

    /**
     * Utility method to get test status as string with emoji
     * @param result TestNG result object
     * @return status string
     */
    private String getTestStatus(ITestResult result) {
        switch (result.getStatus()) {
            case ITestResult.SUCCESS:
                return "✅ PASSED";
            case ITestResult.FAILURE:
                return "❌ FAILED";
            case ITestResult.SKIP:
                return "⏭️ SKIPPED";
            default:
                return "❓ UNKNOWN";
        }
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