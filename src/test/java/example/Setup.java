package example;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class Setup {
	
	public static WebDriver createDriver(String driverType) throws MalformedURLException, URISyntaxException {
        WebDriver driver;
        switch (driverType.toLowerCase()) {
            case "web":
                // Configuración para WebDriver
                ChromeOptions chromeOptions = new ChromeOptions();
                driver = new ChromeDriver(chromeOptions);
                break;
            case "appium":
                // Configuración para AppiumDriver
                UiAutomator2Options options = new UiAutomator2Options()
                        .setPlatformName("Android")
                        .setPlatformVersion("11.0")
                        .setDeviceName("Android Emulator")
                        .setApp("/path/to/your/app.apk");
                driver = new AndroidDriver(new URI("http://127.0.0.1:4723/wd/hub").toURL(), options);
                break;
            default:
                throw new IllegalArgumentException("Unsupported driver type: " + driverType);
        }
        return driver;
    }
	
}
