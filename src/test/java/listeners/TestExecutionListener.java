package listeners;

import io.appium.java_client.windows.WindowsDriver;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * TestExecutionListener for Desktop (Windows) Applications using WinAppDriver.
 * Handles logging, screenshots, and report generation for all test events.
 */
public class TestExecutionListener implements ITestListener, ISuiteListener, IInvokedMethodListener {

    private static final Logger logger = LogManager.getLogger(TestExecutionListener.class);
    private static final Logger failureLogger = LogManager.getLogger("TestFailureLogger");
    private static final String SCREENSHOT_DIR = "screenshots";
    private static final String REPORTS_DIR = "test-reports";

    // ------------------- SUITE LEVEL -------------------
    @Override
    public void onStart(ISuite suite) {
        logger.info("=== SUITE STARTED: {} ===", suite.getName());
        createDirectories();
        logger.info("Suite parameters: {}", suite.getXmlSuite().getParameters());
    }

    @Override
    public void onFinish(ISuite suite) {
        logger.info("=== SUITE FINISHED: {} ===", suite.getName());
    }

    // ------------------- TEST LEVEL -------------------
    @Override
    public void onTestStart(ITestResult result) {
        logger.info("▶️ TEST STARTED: {}.{}",
                result.getTestClass().getName(), result.getMethod().getMethodName());

        if (result.getParameters().length > 0) {
            logger.info("Test parameters: {}", Arrays.toString(result.getParameters()));
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("✅ TEST PASSED: {}.{} (Duration: {} ms)",
                result.getTestClass().getName(),
                result.getMethod().getMethodName(),
                result.getEndMillis() - result.getStartMillis());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getTestClass().getName() + "." + result.getMethod().getMethodName();
        logger.error("❌ TEST FAILED: {} (Reason: {})", testName, result.getThrowable().getMessage());

        // Capture screenshot
        takeDesktopScreenshot(result);

        // Create HTML failure report
        generateFailureReport(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("⚠️ TEST SKIPPED: {}.{}", result.getTestClass().getName(), result.getMethod().getMethodName());
        if (result.getThrowable() != null)
            logger.warn("Skip reason: {}", result.getThrowable().getMessage());
    }

    // ------------------- INVOKED METHOD LEVEL -------------------
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod())
            logger.debug("About to invoke test method: {}", method.getTestMethod().getMethodName());
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod())
            logger.debug("Finished test method: {}", method.getTestMethod().getMethodName());
    }

    // ------------------- HELPER METHODS -------------------
    private void createDirectories() {
        new File(SCREENSHOT_DIR).mkdirs();
        new File(REPORTS_DIR).mkdirs();
    }

    private void takeDesktopScreenshot(ITestResult result) {
        try {
            WindowsDriver<?> driver = getWindowsDriverFromTest(result);

            if (driver != null) {
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
                String fileName = String.format("%s_%s_FAILED.png",
                        result.getMethod().getMethodName(), timestamp);
                File screenshot = driver.getScreenshotAs(OutputType.FILE);
                File destination = new File(SCREENSHOT_DIR, fileName);
                FileUtils.copyFile(screenshot, destination);
                logger.info("📸 Screenshot saved: {}", destination.getAbsolutePath());
            } else {
                logger.warn("⚠️ No WindowsDriver found — screenshot skipped");
            }

        } catch (Exception e) {
            logger.error("Failed to capture desktop screenshot", e);
        }
    }

    private WindowsDriver<?> getWindowsDriverFromTest(ITestResult result) {
        try {
            Object testInstance = result.getInstance();
            java.lang.reflect.Field driverField = null;

            for (String name : new String[]{"driver", "windowsDriver", "appDriver"}) {
                try {
                    driverField = testInstance.getClass().getDeclaredField(name);
                    driverField.setAccessible(true);
                    Object driver = driverField.get(testInstance);
                    if (driver instanceof WindowsDriver<?>) {
                        return (WindowsDriver<?>) driver;
                    }
                } catch (NoSuchFieldException ignored) {
                }
            }
        } catch (Exception e) {
            logger.warn("Failed to retrieve WindowsDriver", e);
        }
        return null;
    }

    private void generateFailureReport(ITestResult result) {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String fileName = String.format("%s_%s_failure_report.html",
                    result.getMethod().getMethodName(), timestamp);

            StringBuilder html = new StringBuilder();
            html.append("<html><head><title>Desktop Test Failure Report</title></head><body>");
            html.append("<h2>❌ Test Failed:</h2>")
                    .append("<p><b>Test Class:</b> ").append(result.getTestClass().getName()).append("</p>")
                    .append("<p><b>Method:</b> ").append(result.getMethod().getMethodName()).append("</p>")
                    .append("<p><b>Reason:</b> ").append(result.getThrowable().getMessage()).append("</p>")
                    .append("<h3>Stack Trace:</h3><pre>")
                    .append(getStackTrace(result.getThrowable()))
                    .append("</pre></body></html>");

            File reportFile = new File(REPORTS_DIR, fileName);
            FileUtils.writeStringToFile(reportFile, html.toString(), "UTF-8");
            logger.info("📄 Failure report created: {}", reportFile.getAbsolutePath());
        } catch (IOException e) {
            logger.error("Failed to generate failure report", e);
        }
    }

    private String getStackTrace(Throwable t) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement el : t.getStackTrace()) {
            sb.append(el.toString()).append("\n");
        }
        return sb.toString();
    }
}