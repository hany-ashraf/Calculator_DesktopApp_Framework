package pages;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import io.appium.java_client.MobileBy;


/**
 * Page Object Model for Windows Calculator
 * Implements Fluent Design Pattern for readable test steps
 */
public class P01_CalculatorPage {

    private WindowsDriver driver;
    private WebDriverWait wait;

    // Number buttons (0-9)
    private By num0Button = By.name("Zero");
    private By num1Button = By.name("One");
    private By num2Button = By.name("Two");
    private By num3Button = By.name("Three");
    private By num4Button = By.name("Four");
    private By num5Button = By.name("Five");
    private By num6Button = By.name("Six");
    private By num7Button = By.name("Seven");
    private By num8Button = By.name("Eight");
    private By num9Button = By.name("Nine");

    // Operation buttons
    private By plusButton = By.name("Plus");
    private By minusButton = By.name("Minus");
    private By multiplyButton = By.name("Multiply by");
    private By divideButton = By.name("Divide by");
    private By equalsButton = By.name("Equals");
    private By clearButton = By.name("Clear");
    private By clearEntryButton = By.name("Clear entry");
    private By decimalButton = By.name("Decimal separator");
    private By backspaceButton = By.name("Backspace");
    private By positiveNegativeButton = By.name("Positive negative");
    private By squareRootButton = By.name("Square root");
    private By squareButton = By.name("Square");
    private By reciprocalButton = By.name("Reciprocal");
    private By percentButton = By.name("Percent");

    // Display
    private By displayField = MobileBy.AccessibilityId("CalculatorResults");

    // Menu and mode
    private By menuButton = By.name("Open Navigation");
    private By standardMode = By.name("Standard Calculator");
    private By scientificMode = By.name("Scientific Calculator");
    private By programmerMode = By.name("Programmer Calculator");

    public P01_CalculatorPage(WindowsDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 25);
    }

    /**
     * Wait for the calculator to load
     * @return this for fluent chaining
     */
    public P01_CalculatorPage waitForCalculatorToLoad() {
        System.out.println("⏳ Waiting for Calculator to load...");
        wait.until(ExpectedConditions.presenceOfElementLocated(num1Button));
        System.out.println("✓ Calculator loaded");
        return this;
    }

    /**
     * Clear the calculator display
     * @return this for fluent chaining
     */
    public P01_CalculatorPage clear() {
        System.out.println("🧹 Clearing calculator");
        WebElement clear = wait.until(
                ExpectedConditions.elementToBeClickable(clearButton)
        );
        clear.click();
        System.out.println("✓ Calculator cleared");
        return this;
    }

    /**
     * Clear the current entry
     * @return this for fluent chaining
     */
    public P01_CalculatorPage clearEntry() {
        System.out.println("🧹 Clearing entry");
        WebElement clearEntry = wait.until(
                ExpectedConditions.elementToBeClickable(clearEntryButton)
        );
        clearEntry.click();
        System.out.println("✓ Entry cleared");
        return this;
    }

    /**
     * Click a number button (0-9)
     * @param number the number to click (0-9)
     * @return this for fluent chaining
     */
    public P01_CalculatorPage clickNumber(int number) {
        System.out.println("🔢 Clicking number: " + number);
        By numberLocator = getNumberLocator(number);
        WebElement numButton = wait.until(
                ExpectedConditions.elementToBeClickable(numberLocator)
        );
        numButton.click();
        System.out.println("✓ Number " + number + " clicked");
        return this;
    }

    /**
     * Enter a multi-digit number
     * @param number the number to enter
     * @return this for fluent chaining
     */
    public P01_CalculatorPage enterNumber(String number) {
        System.out.println("🔢 Entering number: " + number);
        for (char digit : number.toCharArray()) {
            if (digit == '.') {
                clickDecimal();
            } else if (Character.isDigit(digit)) {
                clickNumber(Character.getNumericValue(digit));
            }
        }
        System.out.println("✓ Number entered");
        return this;
    }

    /**
     * Click the plus button
     * @return this for fluent chaining
     */
    public P01_CalculatorPage clickPlus() {
        System.out.println("➕ Clicking Plus");
        clickButton(plusButton, "Plus");
        return this;
    }

    /**
     * Click the minus button
     * @return this for fluent chaining
     */
    public P01_CalculatorPage clickMinus() {
        System.out.println("➖ Clicking Minus");
        clickButton(minusButton, "Minus");
        return this;
    }

    /**
     * Click the multiply button
     * @return this for fluent chaining
     */
    public P01_CalculatorPage clickMultiply() {
        System.out.println("✖️ Clicking Multiply");
        clickButton(multiplyButton, "Multiply");
        return this;
    }

    /**
     * Click the divide button
     * @return this for fluent chaining
     */
    public P01_CalculatorPage clickDivide() {
        System.out.println("➗ Clicking Divide");
        clickButton(divideButton, "Divide");
        return this;
    }

    /**
     * Click the equals button
     * @return this for fluent chaining
     */
    public P01_CalculatorPage clickEquals() {
        System.out.println("🟰 Clicking Equals");
        clickButton(equalsButton, "Equals");
        sleep(500); // Wait for calculation
        return this;
    }

    /**
     * Click the decimal separator button
     * @return this for fluent chaining
     */
    public P01_CalculatorPage clickDecimal() {
        System.out.println("• Clicking Decimal");
        clickButton(decimalButton, "Decimal");
        return this;
    }

    /**
     * Click the backspace button
     * @return this for fluent chaining
     */
    public P01_CalculatorPage clickBackspace() {
        System.out.println("⌫ Clicking Backspace");
        clickButton(backspaceButton, "Backspace");
        return this;
    }

    /**
     * Click the positive/negative toggle button
     * @return this for fluent chaining
     */
    public P01_CalculatorPage clickPositiveNegative() {
        System.out.println("±️ Clicking Positive/Negative");
        clickButton(positiveNegativeButton, "Positive/Negative");
        return this;
    }

    /**
     * Click the square root button
     * @return this for fluent chaining
     */
    public P01_CalculatorPage clickSquareRoot() {
        System.out.println("√ Clicking Square Root");
        clickButton(squareRootButton, "Square Root");
        return this;
    }

    /**
     * Click the square button
     * @return this for fluent chaining
     */
    public P01_CalculatorPage clickSquare() {
        System.out.println("x² Clicking Square");
        clickButton(squareButton, "Square");
        return this;
    }

    /**
     * Click the reciprocal button (1/x)
     * @return this for fluent chaining
     */
    public P01_CalculatorPage clickReciprocal() {
        System.out.println("1/x Clicking Reciprocal");
        clickButton(reciprocalButton, "Reciprocal");
        return this;
    }

    /**
     * Click the percent button
     * @return this for fluent chaining
     */
    public P01_CalculatorPage clickPercent() {
        System.out.println("% Clicking Percent");
        clickButton(percentButton, "Percent");
        return this;
    }

    /**
     * Get the result from the display
     * @return the displayed result as a string
     */
    public String getResult() {
        System.out.println("📊 Getting result from display");
        WebElement display = wait.until(
                ExpectedConditions.presenceOfElementLocated(displayField)
        );
        String result = display.getText();
        // Remove "Display is" prefix if present
        result = result.replace("Display is ", "");
        System.out.println("✓ Result: " + result);
        return result;
    }

    /**
     * Verify the result matches expected value
     * @param expectedResult the expected result
     * @return true if result matches
     */
    public boolean verifyResult(String expectedResult) {
        String actualResult = getResult();
        boolean matches = actualResult.equals(expectedResult);
        if (matches) {
            System.out.println("✅ Result verification passed: " + actualResult);
        } else {
            System.out.println("❌ Result verification failed. Expected: " +
                    expectedResult + ", Actual: " + actualResult);
        }
        return matches;
    }

    /**
     * Perform addition operation
     * @param num1 first number
     * @param num2 second number
     * @return this for fluent chaining
     */
    public P01_CalculatorPage add(String num1, String num2) {
        System.out.println("➕ Performing addition: " + num1 + " + " + num2);
        return clear()
                .enterNumber(num1)
                .clickPlus()
                .enterNumber(num2)
                .clickEquals();
    }

    /**
     * Perform subtraction operation
     * @param num1 first number
     * @param num2 second number
     * @return this for fluent chaining
     */
    public P01_CalculatorPage subtract(String num1, String num2) {
        System.out.println("➖ Performing subtraction: " + num1 + " - " + num2);
        return clear()
                .enterNumber(num1)
                .clickMinus()
                .enterNumber(num2)
                .clickEquals();
    }

    /**
     * Perform multiplication operation
     * @param num1 first number
     * @param num2 second number
     * @return this for fluent chaining
     */
    public P01_CalculatorPage multiply(String num1, String num2) {
        System.out.println("✖️ Performing multiplication: " + num1 + " × " + num2);
        return clear()
                .enterNumber(num1)
                .clickMultiply()
                .enterNumber(num2)
                .clickEquals();
    }

    /**
     * Perform division operation
     * @param num1 first number
     * @param num2 second number
     * @return this for fluent chaining
     */
    public P01_CalculatorPage divide(String num1, String num2) {
        System.out.println("➗ Performing division: " + num1 + " ÷ " + num2);
        return clear()
                .enterNumber(num1)
                .clickDivide()
                .enterNumber(num2)
                .clickEquals();
    }

    /**
     * Switch calculator mode
     * @param mode "Standard", "Scientific", or "Programmer"
     * @return this for fluent chaining
     */
    public P01_CalculatorPage switchMode(String mode) {
        System.out.println("🔄 Switching to " + mode + " mode");
        try {
            WebElement menu = driver.findElement(menuButton);
            menu.click();
            sleep(500);

            By modeLocator;
            switch (mode.toLowerCase()) {
                case "scientific":
                    modeLocator = scientificMode;
                    break;
                case "programmer":
                    modeLocator = programmerMode;
                    break;
                default:
                    modeLocator = standardMode;
            }

            WebElement modeButton = driver.findElement(modeLocator);
            modeButton.click();
            System.out.println("✓ Switched to " + mode + " mode");
        } catch (Exception e) {
            System.out.println("⚠️ Failed to switch mode: " + e.getMessage());
        }
        return this;
    }

    /**
     * Get the number button locator
     * @param number the number (0-9)
     * @return the By locator for the number
     */
    private By getNumberLocator(int number) {
        switch (number) {
            case 0: return num0Button;
            case 1: return num1Button;
            case 2: return num2Button;
            case 3: return num3Button;
            case 4: return num4Button;
            case 5: return num5Button;
            case 6: return num6Button;
            case 7: return num7Button;
            case 8: return num8Button;
            case 9: return num9Button;
            default: throw new IllegalArgumentException("Invalid number: " + number);
        }
    }

    /**
     * Helper method to click a button
     * @param locator the button locator
     * @param buttonName the button name for logging
     */
    private void clickButton(By locator, String buttonName) {
        WebElement button = wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        );
        button.click();
        System.out.println("✓ " + buttonName + " button clicked");
    }

    /**
     * Utility method for waiting
     * @param milliseconds time to sleep
     */
    private void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}