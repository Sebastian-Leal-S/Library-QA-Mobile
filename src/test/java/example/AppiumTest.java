package example;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;

public class AppiumTest {
	private WebDriver driver;
	
	@Parameters({"driverType"})
	@BeforeClass
	private void setup(String driverType) throws MalformedURLException, URISyntaxException {
		driver = Setup.createDriver(driverType);
	}
	
	@Test
	private void simpleTest() {
		if (driver instanceof AppiumDriver) {
            AppiumDriver appiumDriver = (AppiumDriver) driver;
            appiumDriver.findElement(AppiumBy.id("com.example:id/button")).click();
        } else {
            // Realiza acciones con WebDriver
            driver.get("https://www.example.com");
            // Agrega más acciones de prueba aquí
        }
	}

	@AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
