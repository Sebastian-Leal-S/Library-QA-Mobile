package example;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.windows.WindowsDriver;

public class FirstTest {
	private static AppiumDriver driver;
	
	@BeforeTest
	private void setUp() throws MalformedURLException, URISyntaxException {
		DesiredCapabilities caps = new DesiredCapabilities();
		
		caps.setCapability("app", "Microsoft.WindowsCalculator_8wekyb3d8bbwe!App");
		caps.setCapability("platformName", "windows");
		caps.setCapability("automationName", "windows");
		
		driver = new WindowsDriver(new URI("http://127.0.0.1:4723/").toURL(), caps);
	}
	
	@Test
	public void sumTest() {
		
	}

	public static AppiumDriver getDriver() {
		return driver;
	}
}
