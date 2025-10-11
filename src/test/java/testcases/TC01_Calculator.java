package testcases;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.P01_CalculatorPage;

import java.net.URL;

public class TC01_Calculator extends testBase{

    //private WindowsDriver driver;
    //private P01_CalculatorPage calculatorPage;
    private static final String WINAPPDRIVER_URL = "http://127.0.0.1:4723";


    @Test(priority = 1, description = "Verify simple addition operation")
    public void testAddition() {
        System.out.println("\n🧪 TEST: Addition Operation");
        System.out.println("-".repeat(40));

        new P01_CalculatorPage(driver).add("25", "17");
        String result = new P01_CalculatorPage(driver).getResult();

        Assert.assertEquals(result, "42", "Addition result should be 42");
        System.out.println("✅ TEST PASSED: Addition works correctly");
    }

    @Test(priority = 2, description = "Verify simple subtraction operation")
    public void testSubtraction() {
        System.out.println("\n🧪 TEST: Subtraction Operation");
        System.out.println("-".repeat(40));

        new P01_CalculatorPage(driver).subtract("100", "42");
        String result = new P01_CalculatorPage(driver).getResult();

        Assert.assertEquals(result, "58", "Subtraction result should be 58");
        System.out.println("✅ TEST PASSED: Subtraction works correctly");
    }

    @Test(priority = 3, description = "Verify simple multiplication operation")
    public void testMultiplication() {
        System.out.println("\n🧪 TEST: Multiplication Operation");
        System.out.println("-".repeat(40));

        new P01_CalculatorPage(driver).multiply("6", "7");
        String result = new P01_CalculatorPage(driver).getResult();

        Assert.assertEquals(result, "42", "Multiplication result should be 42");
        System.out.println("✅ TEST PASSED: Multiplication works correctly");
    }

    @Test(priority = 4, description = "Verify simple division operation")
    public void testDivision() {
        System.out.println("\n🧪 TEST: Division Operation");
        System.out.println("-".repeat(40));

        new P01_CalculatorPage(driver).divide("84", "2");
        String result = new P01_CalculatorPage(driver).getResult();

        Assert.assertEquals(result, "42", "Division result should be 42");
        System.out.println("✅ TEST PASSED: Division works correctly");
    }

    @Test(priority = 5, description = "Verify decimal number operations")
    public void testDecimalOperations() {
        System.out.println("\n🧪 TEST: Decimal Operations");
        System.out.println("-".repeat(40));

        new P01_CalculatorPage(driver).add("10.5", "5.5");
        String result = new P01_CalculatorPage(driver).getResult();

        Assert.assertEquals(result, "16", "Decimal addition result should be 16");
        System.out.println("✅ TEST PASSED: Decimal operations work correctly");
    }

    @Test(priority = 6, description = "Verify clear functionality")
    public void testClearFunction() {
        System.out.println("\n🧪 TEST: Clear Function");
        System.out.println("-".repeat(40));

        new P01_CalculatorPage(driver).enterNumber("12345")
                .clear();
        String result = new P01_CalculatorPage(driver).getResult();

        Assert.assertEquals(result, "0", "Display should show 0 after clear");
        System.out.println("✅ TEST PASSED: Clear function works correctly");
    }

    @Test(priority = 7, description = "Verify backspace functionality")
    public void testBackspace() {
        System.out.println("\n🧪 TEST: Backspace Function");
        System.out.println("-".repeat(40));

        new P01_CalculatorPage(driver).clear()
                .enterNumber("123")
                .clickBackspace();
        String result = new P01_CalculatorPage(driver).getResult();

        Assert.assertEquals(result, "12", "Display should show 12 after backspace");
        System.out.println("✅ TEST PASSED: Backspace works correctly");
    }

    @Test(priority = 8, description = "Verify positive/negative toggle")
    public void testPositiveNegativeToggle() {
        System.out.println("\n🧪 TEST: Positive/Negative Toggle");
        System.out.println("-".repeat(40));

        new P01_CalculatorPage(driver).clear()
                .enterNumber("42")
                .clickPositiveNegative();
        String result = new P01_CalculatorPage(driver).getResult();

        Assert.assertEquals(result, "-42", "Display should show -42");
        System.out.println("✅ TEST PASSED: Positive/Negative toggle works correctly");
    }

    @Test(priority = 9, description = "Verify square root operation")
    public void testSquareRoot() {
        System.out.println("\n🧪 TEST: Square Root Operation");
        System.out.println("-".repeat(40));

        new P01_CalculatorPage(driver).clear()
                .enterNumber("16")
                .clickSquareRoot();
        String result = new P01_CalculatorPage(driver).getResult();

        Assert.assertEquals(result, "4", "Square root of 16 should be 4");
        System.out.println("✅ TEST PASSED: Square root works correctly");
    }

    @Test(priority = 10, description = "Verify square operation")
    public void testSquare() {
        System.out.println("\n🧪 TEST: Square Operation");
        System.out.println("-".repeat(40));

        new P01_CalculatorPage(driver).clear()
                .enterNumber("5")
                .clickSquare();
        String result = new P01_CalculatorPage(driver).getResult();

        Assert.assertEquals(result, "25", "Square of 5 should be 25");
        System.out.println("✅ TEST PASSED: Square works correctly");
    }

    @Test(priority = 11, description = "Verify reciprocal operation")
    public void testReciprocal() {
        System.out.println("\n🧪 TEST: Reciprocal Operation");
        System.out.println("-".repeat(40));

        new P01_CalculatorPage(driver).clear()
                .enterNumber("4")
                .clickReciprocal();
        String result = new P01_CalculatorPage(driver).getResult();

        Assert.assertEquals(result, "0.25", "Reciprocal of 4 should be 0.25");
        System.out.println("✅ TEST PASSED: Reciprocal works correctly");
    }

    @Test(priority = 12, description = "Verify chained operations")
    public void testChainedOperations() {
        System.out.println("\n🧪 TEST: Chained Operations");
        System.out.println("-".repeat(40));

        // (10 + 5) * 2 = 30
        new P01_CalculatorPage(driver).clear()
                .enterNumber("10")
                .clickPlus()
                .enterNumber("5")
                .clickEquals()
                .clickMultiply()
                .enterNumber("2")
                .clickEquals();

        String result = new P01_CalculatorPage(driver).getResult();

        Assert.assertEquals(result, "30", "Chained operation result should be 30");
        System.out.println("✅ TEST PASSED: Chained operations work correctly");
    }

    @Test(priority = 13, description = "Verify division by zero handling")
    public void testDivisionByZero() {
        System.out.println("\n🧪 TEST: Division by Zero");
        System.out.println("-".repeat(40));

        new P01_CalculatorPage(driver).divide("10", "0");
        String result = new P01_CalculatorPage(driver).getResult();

        Assert.assertTrue(result.contains("Cannot divide by zero") ||
                        result.contains("∞") ||
                        result.contains("Error"),
                "Division by zero should show error or infinity");
        System.out.println("✅ TEST PASSED: Division by zero handled correctly");
    }

    @Test(priority = 14, description = "Verify large number calculation")
    public void testLargeNumbers() {
        System.out.println("\n🧪 TEST: Large Number Calculation");
        System.out.println("-".repeat(40));

        new P01_CalculatorPage(driver).multiply("999999", "2");
        String result = new P01_CalculatorPage(driver).getResult();

        Assert.assertEquals(result, "1999998", "Large number multiplication should work");
        System.out.println("✅ TEST PASSED: Large numbers work correctly");
    }

    @Test(priority = 15, description = "Verify consecutive equals operations")
    public void testConsecutiveEquals() {
        System.out.println("\n🧪 TEST: Consecutive Equals");
        System.out.println("-".repeat(40));

        // 5 + 3 = 8, press = again should give 11 (8+3), press = again should give 14 (11+3)
        new P01_CalculatorPage(driver).clear()
                .enterNumber("5")
                .clickPlus()
                .enterNumber("3")
                .clickEquals();

        String firstResult = new P01_CalculatorPage(driver).getResult();
        Assert.assertEquals(firstResult, "8", "First result should be 8");

        new P01_CalculatorPage(driver).clickEquals();
        String secondResult = new P01_CalculatorPage(driver).getResult();
        Assert.assertEquals(secondResult, "11", "Second result should be 11");

        System.out.println("✅ TEST PASSED: Consecutive equals works correctly");
    }

    @Test(priority = 16, description = "Verify percent operation")
    public void testPercent() {
        System.out.println("\n🧪 TEST: Percent Operation");
        System.out.println("-".repeat(40));

        // 200 * 50% = 100
        new P01_CalculatorPage(driver).clear()
                .enterNumber("200")
                .clickMultiply()
                .enterNumber("50")
                .clickPercent()
                .clickEquals();

        String result = new P01_CalculatorPage(driver).getResult();

        Assert.assertEquals(result, "100", "200 * 50% should be 100");
        System.out.println("✅ TEST PASSED: Percent operation works correctly");
    }

    @Test(priority = 17, description = "Verify clear entry vs clear all")
    public void testClearEntryVsClearAll() {
        System.out.println("\n🧪 TEST: Clear Entry vs Clear All");
        System.out.println("-".repeat(40));

        // Test Clear Entry
        new P01_CalculatorPage(driver).clear()
                .enterNumber("10")
                .clickPlus()
                .enterNumber("999")
                .clearEntry();

        new P01_CalculatorPage(driver).enterNumber("5")
                .clickEquals();

        String result = new P01_CalculatorPage(driver).getResult();
        Assert.assertEquals(result, "15", "After clear entry, operation should continue (10+5=15)");

        System.out.println("✅ TEST PASSED: Clear entry works correctly");
    }

    @Test(priority = 18, description = "Verify mixed operations")
    public void testMixedOperations() {
        System.out.println("\n🧪 TEST: Mixed Operations");
        System.out.println("-".repeat(40));

        // 100 - 50 + 20 = 70
        new P01_CalculatorPage(driver).clear()
                .enterNumber("100")
                .clickMinus()
                .enterNumber("50")
                .clickPlus()
                .enterNumber("20")
                .clickEquals();

        String result = new P01_CalculatorPage(driver).getResult();

        Assert.assertEquals(result, "70", "Mixed operations result should be 70");
        System.out.println("✅ TEST PASSED: Mixed operations work correctly");
    }

    @Test(priority = 19, description = "Verify result verification method")
    public void testResultVerification() {
        System.out.println("\n🧪 TEST: Result Verification Method");
        System.out.println("-".repeat(40));

        new P01_CalculatorPage(driver).add("20", "22");
        boolean isCorrect = new P01_CalculatorPage(driver).verifyResult("42");

        Assert.assertTrue(isCorrect, "Result verification should return true for correct result");

        boolean isIncorrect = new P01_CalculatorPage(driver).verifyResult("100");
        Assert.assertFalse(isIncorrect, "Result verification should return false for incorrect result");

        System.out.println("✅ TEST PASSED: Result verification method works correctly");
    }

    @Test(priority = 20, description = "Verify zero operations")
    public void testZeroOperations() {
        System.out.println("\n🧪 TEST: Zero Operations");
        System.out.println("-".repeat(40));

        // Test 0 + 0
        new P01_CalculatorPage(driver).add("0", "0");
        Assert.assertEquals(new P01_CalculatorPage(driver).getResult(), "0", "0 + 0 should be 0");

        // Test 0 * 100
        new P01_CalculatorPage(driver).multiply("0", "100");
        Assert.assertEquals(new P01_CalculatorPage(driver).getResult(), "0", "0 * 100 should be 0");

        System.out.println("✅ TEST PASSED: Zero operations work correctly");
    }
}
