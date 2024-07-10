package com.periferiaitgroup;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.languagetool.rules.RuleMatch;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.periferiaitgroup.evidencia.GenerarEvidencia;
import com.periferiaitgroup.utilities.PropertyFileReader;
import com.periferiaitgroup.utilities.RevisorOrtografico;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class Periferia {

	static final Duration TIMEOUT = Duration.ofSeconds(3);
	protected static Logger log = LogManager.getLogger();
	private static AndroidDriver driver;

	private Periferia() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * 
	 * TODO: Document method getDriver
	 *
	 * @return
	 */
	public static AppiumDriver getDriver() {
		return driver;
	}

	/**
	 * 
	 * TODO: Document method setUp
	 *
	 * @return
	 */
	public static AndroidDriver setUp() {
		try {
			Properties properties = PropertyFileReader.readProperty();

			String platformName = properties.getProperty("platformName");
			String deviceName = properties.getProperty("deviceName");
			String platformVersion = properties.getProperty("platformVersion");
			String appPackage = properties.getProperty("appPackage");
			String appActivity = properties.getProperty("appActivity");
			String noReset = properties.getProperty("noReset");
			String autoGrantPermissions = properties.getProperty("autoGrantPermissions");

			DesiredCapabilities capabilities = new DesiredCapabilities();

			capabilities.setCapability("platformName", platformName);
			capabilities.setCapability("deviceName", deviceName);
			capabilities.setCapability("platformVersion", platformVersion);
			capabilities.setCapability("appPackage", appPackage);
			capabilities.setCapability("appActivity", appActivity);
			capabilities.setCapability("noReset", noReset);
			capabilities.setCapability("autoGrantPermissions", autoGrantPermissions);

			URL url = null;
			url = new URI("http://127.0.0.1:4723/wd/hub").toURL();

			driver = new AndroidDriver(url, capabilities);
		} catch (MalformedURLException | URISyntaxException e) {
			e.printStackTrace();
		}

		return driver;
	}

	/**
	 * 
	 * TODO: Document method setUp
	 *
	 * @param urlFileProperty
	 * @return
	 */
	public static AndroidDriver setUp(String urlFileProperty) {
		try {
			Properties properties = PropertyFileReader.readProperty(urlFileProperty);

			String platformName = properties.getProperty("platformName");
			String deviceName = properties.getProperty("deviceName");
			String platformVersion = properties.getProperty("platformVersion");
			String appPackage = properties.getProperty("appPackage");
			String appActivity = properties.getProperty("appActivity");
			String noReset = properties.getProperty("noReset");
			String autoGrantPermissions = properties.getProperty("autoGrantPermissions");

			DesiredCapabilities capabilities = new DesiredCapabilities();

			capabilities.setCapability("platformName", platformName);
			capabilities.setCapability("deviceName", deviceName);
			capabilities.setCapability("platformVersion", platformVersion);
			capabilities.setCapability("appPackage", appPackage);
			capabilities.setCapability("appActivity", appActivity);
			capabilities.setCapability("noReset", noReset);
			capabilities.setCapability("autoGrantPermissions", autoGrantPermissions);

			URL url = null;
			url = new URI("http://127.0.0.1:4723/wd/hub").toURL();

			driver = new AndroidDriver(url, capabilities);
		} catch (MalformedURLException | URISyntaxException e) {
			e.printStackTrace();
		}

		return driver;
	}

	/**
	 * 
	 * TODO: Document method setUp
	 *
	 * @param platformName
	 * @param deviceName
	 * @param platformVersion
	 * @param appPackage
	 * @param appActivity
	 * @param noReset
	 * @param autoGrantPermissions
	 * @return
	 */
	public static AndroidDriver setUp(String platformName, String deviceName, String platformVersion, String appPackage,
			String appActivity, String noReset, String autoGrantPermissions) {
		try {
			DesiredCapabilities capabilities = new DesiredCapabilities();

			capabilities.setCapability("platformName", platformName);
			capabilities.setCapability("deviceName", deviceName);
			capabilities.setCapability("platformVersion", platformVersion);
			capabilities.setCapability("appPackage", appPackage);
			capabilities.setCapability("appActivity", appActivity);
			capabilities.setCapability("noReset", noReset);
			capabilities.setCapability("autoGrantPermissions", autoGrantPermissions);

			URL url = null;
			url = new URI("http://127.0.0.1:4723/wd/hub").toURL();

			driver = new AndroidDriver(url, capabilities);
		} catch (MalformedURLException | URISyntaxException e) {
			e.printStackTrace();
		}

		return driver;
	}

	/**
	 * Cierra el controlador de Selenium si está inicializado.
	 */
	public static void tearDown() {
		try {
			if (driver != null) {
				driver.quit();
				log.info("Driver cerrado correctamente");
			}
		} catch (WebDriverException e) {
			log.fatal("Error al cerrar el controlador: {}", e.getMessage());
			throw new WebDriverException("No se pudo cerrar el driver", e);
		}
	}

	/**
	 * Navega a la URL proporcionada.
	 *
	 * @param url La URL a la que se desea navegar.
	 * @throws WebDriverException Si ocurre un error al navegar a la URL.
	 */
	public static void goUrl(String url) {
		try {
			driver.get(url);
			log.info("Navegando a la url: {}", url);
		} catch (WebDriverException e) {
			log.fatal("Error al navegar a la url: {}", e.getMessage());
			throw new WebDriverException("No se pudo navegar a la url", e);
		}
	}

	/**
	 * Encuentra un elemento web utilizando el localizador proporcionado.
	 *
	 * @param locator El localizador utilizado para encontrar el elemento web.
	 * @return El elemento web encontrado.
	 * @throws NoSuchElementException Si el elemento web no se encuentra en el
	 *                                tiempo de espera de 3 segundos.
	 */
	public static WebElement findElement(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);
		try {
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			log.debug("Elemento encontrado: {}", locator);
			return element;
		} catch (Exception e) {
			log.fatal("No se encontro el elemento: {}, dentro del tiempo de 3 seg. Falla {}", locator, e.getMessage());
			throw new NoSuchElementException(
					"EL elemento no fue encontrado entre del tiempo de espera de 3 seg: " + locator);
		}
	}

	/**
	 * Encuentra un elemento web utilizando el localizador proporcionado.
	 *
	 * @param locator      El localizador utilizado para encontrar el elemento web.
	 * @param tiempoEspera El tiempo de espera en segundos para encontrar el
	 *                     elemento web.
	 * @return El elemento web encontrado.
	 * @throws NoSuchElementException Si el elemento web no se encuentra en el
	 *                                tiempo de espera proporcionado.
	 */
	public static WebElement findElement(By locator, int tiempoEspera) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(tiempoEspera));
		try {
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			log.debug("Elemento encontrado: {}", locator);
			return element;
		} catch (Exception e) {
			log.fatal("No se encontro el elemento: {}, dentro del tiempo de {} seg. Falla {}", locator, tiempoEspera,
					e.getMessage());
			throw new NoSuchElementException("EL elemento " + locator
					+ " no fue encontrado entre del tiempo de espera de " + tiempoEspera + " seg");
		}
	}

	/**
	 * Encuentra todos los elementos web que coinciden con el localizador
	 * proporcionado.
	 *
	 * @param locator El localizador utilizado para encontrar los elementos web.
	 * @return Una lista de elementos web encontrados.
	 * @throws NoSuchElementException Si los elementos web no se encuentran en el
	 *                                tiempo de espera de 3 segundos.
	 */
	public static List<WebElement> findElements(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);
		try {
			List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
			log.debug("Elementos encontrados: {}", locator);
			return elements;
		} catch (Exception e) {
			log.fatal("No se encontraron elementos: {}, dentro del tiempo de 3 seg. Falla {}", locator, e.getMessage());
			throw new NoSuchElementException(
					"Los elementos no fueron encontrados entre del tiempo de espera de 3 seg: " + locator);
		}
	}

	/**
	 * Encuentra todos los elementos web que coinciden con el localizador
	 * proporcionado.
	 *
	 * @param locator      El localizador utilizado para encontrar los elementos
	 *                     web.
	 * @param tiempoEspera El tiempo de espera en segundos para encontrar los
	 *                     elementos web.
	 * @return Una lista de elementos web encontrados.
	 * @throws NoSuchElementException Si los elementos web no se encuentran en el
	 *                                tiempo de espera proporcionado.
	 */
	public static List<WebElement> findElements(By locator, int tiempoEspera) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(tiempoEspera));
		try {
			List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
			log.debug("Elementos encontrados: {}", locator);
			return elements;
		} catch (Exception e) {
			log.fatal("No se encontraron elementos: {}, dentro del tiempo de {} seg. Falla {}", locator, tiempoEspera,
					e.getMessage());
			throw new NoSuchElementException("Los elementos " + locator
					+ " no fueron encontrados entre del tiempo de espera de " + tiempoEspera + " seg");
		}
	}

	/**
	 * Realiza clic en el elemento web que coincide con el localizador
	 * proporcionado.
	 *
	 * @param locator El localizador utilizado para encontrar el elemento web.
	 * @throws ElementNotInteractableException Si el elemento web no es
	 *                                         interactuable en el tiempo de espera
	 *                                         de 3 segundos.
	 */
	public static void click(By locator) {
		try {
			WebElement element = findElement(locator);
			element.click();
			log.debug("Click en el elemento: {}", locator);
		} catch (ElementNotInteractableException e) {
			log.error("No fue posible realizar clic sobre el elemento {}, en el tiempo 3 seg, por el error {}", locator,
					e.getMessage());
			throw e;
		}
	}

	/**
	 * Realiza clic en el elemento web que coincide con el localizador
	 * proporcionado.
	 *
	 * @param locator      El localizador utilizado para encontrar el elemento web.
	 * @param tiempoEspera El tiempo de espera en segundos para encontrar el
	 *                     elemento web.
	 * @throws ElementNotInteractableException Si el elemento web no es
	 *                                         interactuable en el tiempo de espera
	 *                                         proporcionado.
	 */
	public static void click(By locator, int tiempoEspera) {
		try {
			WebElement element = findElement(locator, tiempoEspera);
			element.click();
			log.debug("Click en el elemento: {}, dentro del tiempo {} seg", locator, tiempoEspera);
		} catch (ElementNotInteractableException e) {
			log.fatal("No fue posible realizar clic sobre el elemento {}, en el tiempo {}, por el error {}", locator,
					tiempoEspera, e.getMessage());
			throw e;
		}
	}

	/**
	 * Realiza clic en el elemento web que coincide con el localizador
	 * proporcionado.
	 *
	 * @param locator          El localizador utilizado para encontrar el elemento
	 *                         web.
	 * @param mensajeEvidencia El mensaje que se desea capturar en la evidencia.
	 * @throws ElementNotInteractableException Si el elemento web no es
	 *                                         interactuable en el tiempo de espera
	 *                                         de 3 segundos.
	 */
	public static void click(By locator, String mensajeEvidencia) {
		try {
			WebElement element = findElement(locator);
			element.click();
			GenerarEvidencia.capturarEvidencia(driver, mensajeEvidencia);
			log.debug("Click en el elemento: {} y correcto guardo de evidencia", locator);
		} catch (Exception e) {
			GenerarEvidencia.capturarEvidencia(driver, e.getMessage(), locator);
			log.fatal("No fue posible realizar clic sobre el elemento {}, en el tiempo 3 seg, por el error {}", locator,
					e.getMessage());
			throw e;
		}
	}

	/**
	 * Realiza clic en el elemento web que coincide con el localizador
	 * proporcionado.
	 *
	 * @param locator          El localizador utilizado para encontrar el elemento
	 *                         web.
	 * @param tiempoEspera     El tiempo de espera en segundos para encontrar el
	 *                         elemento web.
	 * @param mensajeEvidencia El mensaje que se desea capturar en la evidencia.
	 * @throws ElementNotInteractableException Si el elemento web no es
	 *                                         interactuable en el tiempo de espera
	 *                                         proporcionado.
	 */
	public static void click(By locator, int tiempoEspera, String mensajeEvidencia) {
		try {
			WebElement element = findElement(locator, tiempoEspera);
			element.click();
			GenerarEvidencia.capturarEvidencia(driver, mensajeEvidencia);
			log.debug("Click en el elemento: {}, dentro del tiempo {} seg y correcto guardo de evidencia", locator,
					tiempoEspera);
		} catch (Exception e) {
			GenerarEvidencia.capturarEvidencia(driver, e.getMessage(), locator);
			log.fatal("No fue posible realizar clic sobre el elemento {}, en el tiempo {}, por el error {}", locator,
					tiempoEspera, e.getMessage());
			throw e;
		}
	}

	/**
	 * Envía texto al elemento web que coincide con el localizador proporcionado.
	 *
	 * @param locator El localizador utilizado para encontrar el elemento web.
	 * @param texto   El texto que se desea enviar al elemento web.
	 * @throws ElementNotInteractableException Si el elemento web no es
	 *                                         interactuable en el tiempo de espera
	 *                                         de 3 segundos.
	 */
	public static void sendKeys(By locator, String texto) {
		try {
			WebElement element = findElement(locator);
			element.sendKeys(texto);
			log.debug("Envio de texto '{}' sobre el elemento: {}", texto, locator);
		} catch (Exception e) {
			log.fatal(
					"No fue posible realizar el envio de texto sobre el elemento {}, en el tiempo 3 seg, por el error {}",
					locator, e.getMessage());
			throw e;
		}
	}

	/**
	 * Envía texto al elemento web que coincide con el localizador proporcionado.
	 *
	 * @param locator      El localizador utilizado para encontrar el elemento web.
	 * @param texto        El texto que se desea enviar al elemento web.
	 * @param tiempoEspera El tiempo de espera en segundos para encontrar el
	 *                     elemento web.
	 * @throws ElementNotInteractableException Si el elemento web no es
	 *                                         interactuable en el tiempo de espera
	 *                                         proporcionado.
	 */
	public static void sendKeys(By locator, String texto, int tiempoEspera) {
		try {
			WebElement element = findElement(locator, tiempoEspera);
			element.sendKeys(texto);
			log.debug("Envio de texto '{}' sobre el elemento: {}, dentro del tiempo {} seg", texto, locator,
					tiempoEspera);
		} catch (Exception e) {
			log.fatal(
					"No fue posible realizar el envio de texto sobre el elemento {}, en el tiempo {}, por el error {}",
					locator, tiempoEspera, e.getMessage());
			throw e;
		}
	}

	/**
	 * Envía texto al elemento web que coincide con el localizador proporcionado.
	 *
	 * @param locator          El localizador utilizado para encontrar el elemento
	 *                         web.
	 * @param texto            El texto que se desea enviar al elemento web.
	 * @param mensajeEvidencia El mensaje que se desea capturar en la evidencia.
	 * @throws ElementNotInteractableException Si el elemento web no es
	 *                                         interactuable en el tiempo de espera
	 *                                         de 3 segundos.
	 */
	public static void sendKeys(By locator, String texto, String mensajeEvidencia) {
		try {
			WebElement element = findElement(locator);
			element.sendKeys(texto);
			GenerarEvidencia.capturarEvidencia(driver, mensajeEvidencia);
			log.debug("Envio de texto '{}' sobre el elemento: {} y correcto guardo de evidencia", texto, locator);
		} catch (Exception e) {
			GenerarEvidencia.capturarEvidencia(driver, e.getMessage(), locator);
			log.fatal(
					"No fue posible realizar el envio de texto sobre el elemento {}, en el tiempo 3 seg, por el error {}",
					locator, e.getMessage());
			throw e;
		}
	}

	/**
	 * Envía texto al elemento web que coincide con el localizador proporcionado.
	 *
	 * @param locator          El localizador utilizado para encontrar el elemento
	 *                         web.
	 * @param texto            El texto que se desea enviar al elemento web.
	 * @param tiempoEspera     El tiempo de espera en segundos para encontrar el
	 *                         elemento web.
	 * @param mensajeEvidencia El mensaje que se desea capturar en la evidencia.
	 * @throws ElementNotInteractableException Si el elemento web no es
	 *                                         interactuable en el tiempo de espera
	 *                                         proporcionado.
	 */
	public static void sendKeys(By locator, String texto, int tiempoEspera, String mensajeEvidencia) {
		try {
			WebElement element = findElement(locator, tiempoEspera);
			element.sendKeys(texto);
			GenerarEvidencia.capturarEvidencia(driver, mensajeEvidencia);
			log.debug(
					"Envio de texto '{}' sobre el elemento: {}, dentro del tiempo {} seg y correcto guardo de evidencia",
					texto, locator, tiempoEspera);
		} catch (Exception e) {
			GenerarEvidencia.capturarEvidencia(driver, e.getMessage(), locator);
			log.fatal(
					"No fue posible realizar el envio de texto sobre el elemento {}, en el tiempo {}, por el error {}",
					locator, tiempoEspera, e.getMessage());
			throw e;
		}
	}

	/**
	 * Obtiene el texto del elemento web que coincide con el localizador
	 * proporcionado.
	 *
	 * @param locator El localizador utilizado para encontrar el elemento web.
	 * @return El texto del elemento web.
	 * @throws NoSuchElementException Si el texto del elemento web no se encuentra
	 *                                en el tiempo de espera de 3 segundos.
	 */
	public static String getText(By locator) {
		try {
			WebElement element = findElement(locator);
			String text = element.getText();
			log.debug("Se obtuvo el texto '{}' sobre el elemento: {}", text, locator);
			return text;
		} catch (Exception e) {
			log.fatal("No fue posible obtener el texto sobre el elemento {}, en el tiempo 3 seg, por el error {}",
					locator, e.getMessage());
			throw e;
		}
	}

	/**
	 * Obtiene el texto del elemento web que coincide con el localizador
	 * proporcionado.
	 *
	 * @param locator      El localizador utilizado para encontrar el elemento web.
	 * @param tiempoEspera El tiempo de espera en segundos para encontrar el
	 *                     elemento web.
	 * @return El texto del elemento web.
	 * @throws NoSuchElementException Si el texto del elemento web no se encuentra
	 *                                en el tiempo de espera proporcionado.
	 */
	public static String getText(By locator, int tiempoEspera) {
		try {
			WebElement element = findElement(locator, tiempoEspera);
			String text = element.getText();
			log.debug("Se obtuvi texto sobre el elemento: {}, dentro del tiempo {} seg", locator, tiempoEspera);
			return text;
		} catch (Exception e) {
			log.fatal("No fue posible obtener el texto sobre el elemento {}, en el tiempo {}, por el error {}", locator,
					tiempoEspera, e.getMessage());
			throw e;
		}
	}

	/**
	 * Obtiene el texto del elemento web que coincide con el localizador
	 * proporcionado.
	 *
	 * @param locator          El localizador utilizado para encontrar el elemento
	 *                         web.
	 * @param mensajeEvidencia El mensaje que se desea capturar en la evidencia.
	 * @return El texto del elemento web.
	 * @throws NoSuchElementException Si el texto del elemento web no se encuentra
	 *                                en el tiempo de espera de 3 segundos.
	 */
	public static String getText(By locator, String mensajeEvidencia) {
		try {
			WebElement element = findElement(locator);
			String text = element.getText();
			GenerarEvidencia.capturarEvidencia(driver, mensajeEvidencia);
			log.debug("Obtener texto sobre el elemento: {} y correcto guardo de evidencia", locator);
			return text;
		} catch (Exception e) {
			GenerarEvidencia.capturarEvidencia(driver, e.getMessage(), locator);
			log.fatal("No fue posible obtener el texto sobre el elemento {}, en el tiempo 3 seg, por el error {}",
					locator, e.getMessage());
			throw e;
		}
	}

	/**
	 * Obtiene el texto del elemento web que coincide con el localizador
	 * proporcionado.
	 *
	 * @param locator          El localizador utilizado para encontrar el elemento
	 *                         web.
	 * @param tiempoEspera     El tiempo de espera en segundos para encontrar el
	 *                         elemento web.
	 * @param mensajeEvidencia El mensaje que se desea capturar en la evidencia.
	 * @return El texto del elemento web.
	 * @throws NoSuchElementException Si el texto del elemento web no se encuentra
	 *                                en el tiempo de espera proporcionado.
	 */
	public static String getText(By locator, int tiempoEspera, String mensajeEvidencia) {
		try {
			WebElement element = findElement(locator, tiempoEspera);
			String text = element.getText();
			GenerarEvidencia.capturarEvidencia(driver, mensajeEvidencia);
			log.debug("Obtener texto sobre el elemento: {}, dentro del tiempo {} seg y correcto guardo de evidencia",
					locator, tiempoEspera);
			return text;
		} catch (Exception e) {
			GenerarEvidencia.capturarEvidencia(driver, e.getMessage(), locator);
			log.fatal("No fue posible obtener el texto sobre el elemento {}, en el tiempo {}, por el error {}", locator,
					tiempoEspera, e.getMessage());
			throw e;
		}
	}

	/**
	 * Limpia el contenido del campo identificado por el locator especificado.
	 *
	 * @param locator El locator del elemento cuyo campo se desea limpiar.
	 * @throws NoSuchElementException Si no es posible limpiar el campo del
	 *                                elemento.
	 */
	public static void clear(By locator) {
		try {
			WebElement element = findElement(locator);
			element.clear();
			log.debug("Limpiar campo sobre el elemento: {}", locator);
		} catch (Exception e) {
			log.fatal("No fue posible limpiar el campo sobre el elemento {}, en el tiempo 3 seg, por el error {}",
					locator, e.getMessage());
			throw e;
		}
	}

	/**
	 * Limpia el contenido del campo identificado por el locator especificado.
	 *
	 * @param locator      El locator del elemento cuyo campo se desea limpiar.
	 * @param tiempoEspera El tiempo de espera en segundos para encontrar el
	 *                     elemento web.
	 * @throws NoSuchElementException Si no es posible limpiar el campo del
	 *                                elemento.
	 */
	public static void clear(By locator, int tiempoEspera) {
		try {
			WebElement element = findElement(locator, tiempoEspera);
			element.clear();
			log.debug("Limpiar campo sobre el elemento: {}, dentro del tiempo {} seg", locator, tiempoEspera);
		} catch (Exception e) {
			log.fatal("No fue posible limpiar el campo sobre el elemento {}, en el tiempo {}, por el error {}", locator,
					tiempoEspera, e.getMessage());
			throw e;
		}
	}

	/**
	 * Limpia el contenido del campo identificado por el locator especificado.
	 *
	 * @param locator          El locator del elemento cuyo campo se desea limpiar.
	 * @param mensajeEvidencia El mensaje que se desea capturar en la evidencia.
	 * @throws NoSuchElementException Si no es posible limpiar el campo del
	 *                                elemento.
	 */
	public static void clear(By locator, String mensajeEvidencia) {
		try {
			WebElement element = findElement(locator);
			element.clear();
			GenerarEvidencia.capturarEvidencia(driver, mensajeEvidencia);
			log.debug("Limpiar campo sobre el elemento: {} y correcto guardo de evidencia", locator);
		} catch (Exception e) {
			GenerarEvidencia.capturarEvidencia(driver, e.getMessage(), locator);
			log.fatal("No fue posible limpiar el campo sobre el elemento {}, en el tiempo 3 seg, por el error {}",
					locator, e.getMessage());
			throw e;
		}
	}

	/**
	 * Limpia el contenido del campo identificado por el locator especificado.
	 *
	 * @param locator          El locator del elemento cuyo campo se desea limpiar.
	 * @param tiempoEspera     El tiempo de espera en segundos para encontrar el
	 *                         elemento web.
	 * @param mensajeEvidencia El mensaje que se desea capturar en la evidencia.
	 */
	public static void clear(By locator, int tiempoEspera, String mensajeEvidencia) {
		try {
			WebElement element = findElement(locator, tiempoEspera);
			element.clear();
			GenerarEvidencia.capturarEvidencia(driver, mensajeEvidencia);
			log.debug("Limpiar campo sobre el elemento: {}, dentro del tiempo {} seg y correcto guardo de evidencia",
					locator, tiempoEspera);
		} catch (Exception e) {
			GenerarEvidencia.capturarEvidencia(driver, e.getMessage(), locator);
			log.fatal("No fue posible limpiar el campo sobre el elemento {}, en el tiempo {}, por el error {}", locator,
					tiempoEspera, e.getMessage());
			throw e;
		}
	}

	/**
	 * 
	 * Preciona la tecla Enter del movil
	 *
	 */
	public static void pressKeyEnter() {
		driver.pressKey(new KeyEvent(AndroidKey.ENTER));
	}

	/**
	 * 
	 * Preciona la tecla Clear del movil
	 *
	 */
	public static void pressKeyClear() {
		driver.pressKey(new KeyEvent(AndroidKey.CLEAR));
	}

	/**
	 * 
	 * Preciona la tecla Back del movil
	 *
	 */
	public static void pressKeyBack() {
		driver.pressKey(new KeyEvent(AndroidKey.BACK));
	}

	/**
	 * 
	 * Preciona la tecla App Switch del movil
	 *
	 */
	public static void pressKeyAppSwitch() {
		driver.pressKey(new KeyEvent(AndroidKey.APP_SWITCH));
	}

	/**
	 * 
	 * Preciona la tecla Home del movil
	 *
	 */
	public static void pressKeyHome() {
		driver.pressKey(new KeyEvent(AndroidKey.HOME));
	}

	/**
	 * 
	 * Preciona la tecla Tab del movil
	 *
	 */
	public static void pressKeyTab() {
		driver.pressKey(new KeyEvent(AndroidKey.TAB));
	}

	/**
	 * 
	 * TODO: Document method tocarPantalla
	 *
	 * @param x
	 * @param y
	 */
	public static void tocarPantalla(int x, int y) {
		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
		Sequence tap = new Sequence(finger, 1)
				.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y))
				.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
				.addAction(new Pause(finger, Duration.ofMillis(200))) // Duración del toque
				.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

		driver.perform(Arrays.asList(tap));
	}

	/**
	 * Método para verificar si un elemento está siendo <b>mostrado</b> en la
	 * interfaz de usuario.
	 *
	 * @param locator el localizador del elemento
	 * @return true si el elemento está siendo mostrado, false de lo contrario
	 * @throws RuntimeException si el elemento no puede ser encontrado o si ocurre
	 *                          un error al intentar determinar su visibilidad
	 */
	public static boolean isDisplayed(By locator) {
		try {
			WebElement element = findElement(locator);
			boolean isDisplayed = element.isDisplayed();
			log.debug("El elemento: {} se encuentra visible", locator);
			return isDisplayed;
		} catch (Exception e) {
			log.fatal("El elemento: {} no se encuentra visible, por el error {}", locator, e.getMessage());
			throw e;
		}
	}

	/**
	 * Método para verificar si un elemento está siendo <b>mostrado</b> en la
	 * interfaz de usuario.
	 *
	 * @param locator      el localizador del elemento
	 * @param tiempoEspera el tiempo de espera en segundos para encontrar el
	 *                     elemento web
	 * @return true si el elemento está siendo mostrado, false de lo contrario
	 * @throws RuntimeException si el elemento no puede ser encontrado o si ocurre
	 *                          un error al intentar determinar su visibilidad
	 */
	public static boolean isDisplayed(By locator, int tiempoEspera) {
		try {
			WebElement element = findElement(locator, tiempoEspera);
			boolean isDisplayed = element.isDisplayed();
			log.debug("El elemento: {} se encuentra visible", locator);
			return isDisplayed;
		} catch (Exception e) {
			log.fatal("El elemento: {} no se encuentra visible, por el error {}", locator, e.getMessage());
			throw e;
		}
	}

	/**
	 * Método para verificar si un elemento está siendo <b>mostrado</b> en la
	 * interfaz de usuario.
	 *
	 * @param locator          el localizador del elemento
	 * @param mensajeEvidencia el mensaje que se desea capturar en la evidencia
	 * @return true si el elemento está siendo mostrado, false de lo contrario
	 * @throws RuntimeException si el elemento no puede ser encontrado o si ocurre
	 *                          un error al intentar determinar su visibilidad
	 */
	public static boolean isDisplayed(By locator, String mensajeEvidencia) {
		try {
			WebElement element = findElement(locator);
			boolean isDisplayed = element.isDisplayed();
			GenerarEvidencia.capturarEvidencia(driver, mensajeEvidencia);
			log.debug("El elemento: {} se encuentra visible y correcto guardo de evidencia", locator);
			return isDisplayed;
		} catch (Exception e) {
			GenerarEvidencia.capturarEvidencia(driver, e.getMessage(), locator);
			log.fatal("El elemento: {} no se encuentra visible, por el error {}", locator, e.getMessage());
			throw e;
		}
	}

	/**
	 * Método para verificar si un elemento está siendo <b>mostrado</b> en la
	 * interfaz de usuario.
	 *
	 * @param locator          el localizador del elemento
	 * @param tiempoEspera     el tiempo de espera en segundos para encontrar el
	 *                         elemento web
	 * @param mensajeEvidencia el mensaje que se desea capturar en la evidencia
	 * @return true si el elemento está siendo mostrado, false de lo contrario
	 * @throws RuntimeException si el elemento no puede ser encontrado o si ocurre
	 *                          un error al intentar determinar su visibilidad
	 */
	public static boolean isDisplayed(By locator, int tiempoEspera, String mensajeEvidencia) {
		try {
			WebElement element = findElement(locator, tiempoEspera);
			boolean isDisplayed = element.isDisplayed();
			GenerarEvidencia.capturarEvidencia(driver, mensajeEvidencia);
			log.debug("El elemento: {} se encuentra visible, dentro del tiempo {} seg y correcto guardo de evidencia",
					locator, tiempoEspera);
			return isDisplayed;
		} catch (Exception e) {
			GenerarEvidencia.capturarEvidencia(driver, e.getMessage(), locator);
			log.fatal("El elemento: {} no se encuentra visible, por el error {}", locator, e.getMessage());
			throw e;
		}
	}

	/**
	 * Método para verificar si un elemento está <b>habilitado</b> en la interfaz de
	 * usuario.
	 *
	 * @param locator el localizador del elemento
	 * @return true si el elemento está habilitado, false de lo contrario
	 * @throws RuntimeException si el elemento no puede ser encontrado o si ocurre
	 *                          un error al intentar determinar su habilitación
	 */
	public static boolean isEnable(By locator) {
		try {
			WebElement element = findElement(locator);
			boolean isEnable = element.isEnabled();
			log.debug("El elemento: {} se encuentra habilitado", locator);
			return isEnable;
		} catch (Exception e) {
			log.fatal("El elemento: {} no se encuentra habilitado, por el error {}", locator, e.getMessage());
			throw e;
		}
	}

	/**
	 * Método para verificar si un elemento está <b>habilitado</b> en la interfaz de
	 * usuario.
	 *
	 * @param locator      el localizador del elemento
	 * @param tiempoEspera el tiempo de espera en segundos para encontrar el
	 *                     elemento web
	 * @return true si el elemento está habilitado, false de lo contrario
	 * @throws RuntimeException si el elemento no puede ser encontrado o si ocurre
	 *                          un error al intentar determinar su habilitación
	 */
	public static boolean isEnable(By locator, int tiempoEspera) {
		try {
			WebElement element = findElement(locator, tiempoEspera);
			boolean isEnable = element.isEnabled();
			log.debug("El elemento: {} se encuentra habilitado", locator);
			return isEnable;
		} catch (Exception e) {
			log.fatal("El elemento: {} no se encuentra habilitado, por el error {}", locator, e.getMessage());
			throw e;
		}
	}

	/**
	 * Método para verificar si un elemento está <b>habilitado</b> en la interfaz de
	 * usuario.
	 *
	 * @param locator          el localizador del elemento
	 * @param mensajeEvidencia el mensaje que se desea capturar en la evidencia
	 * @return true si el elemento está habilitado, false de lo contrario
	 * @throws RuntimeException si el elemento no puede ser encontrado o si ocurre
	 *                          un error al intentar determinar su habilitación
	 */
	public static boolean isEnable(By locator, String mensajeEvidencia) {
		try {
			WebElement element = findElement(locator);
			boolean isEnable = element.isEnabled();
			GenerarEvidencia.capturarEvidencia(driver, mensajeEvidencia);
			log.debug("El elemento: {} se encuentra habilitado y correcto guardo de evidencia", locator);
			return isEnable;
		} catch (Exception e) {
			GenerarEvidencia.capturarEvidencia(driver, e.getMessage(), locator);
			log.fatal("El elemento: {} no se encuentra habilitado, por el error {}", locator, e.getMessage());
			throw e;
		}
	}

	/**
	 * Método para verificar si un elemento está <b>habilitado</b> en la interfaz de
	 * usuario.
	 *
	 * @param locator          el localizador del elemento
	 * @param tiempoEspera     el tiempo de espera en segundos para encontrar el
	 *                         elemento web
	 * @param mensajeEvidencia el mensaje que se desea capturar en la evidencia
	 * @return true si el elemento está habilitado, false de lo contrario
	 * @throws RuntimeException si el elemento no puede ser encontrado o si ocurre
	 *                          un error al intentar determinar su habilitación
	 */
	public static boolean isEnable(By locator, int tiempoEspera, String mensajeEvidencia) {
		try {
			WebElement element = findElement(locator, tiempoEspera);
			boolean isEnable = element.isEnabled();
			GenerarEvidencia.capturarEvidencia(driver, mensajeEvidencia);
			log.debug(
					"El elemento: {} se encuentra habilitado, dentro del tiempo {} seg y correcto guardo de evidencia",
					locator, tiempoEspera);
			return isEnable;
		} catch (Exception e) {
			GenerarEvidencia.capturarEvidencia(driver, e.getMessage(), locator);
			log.fatal("El elemento: {} no se encuentra habilitado, por el error {}", locator, e.getMessage());
			throw e;
		}
	}

	/**
	 * Método para verificar si un elemento está <b>seleccionado</b> en la interfaz
	 * de usuario. <br/>
	 * Este método se utiliza con elementos de entrada como casillas de verificación
	 * (checkbox), botones de radio (radio buttons), elementos de entrada (input) y
	 * elementos de opción (option).
	 *
	 * @param locator el localizador del elemento
	 * @return true si el elemento está seleccionado, false de lo contrario
	 * @throws RuntimeException si el elemento no puede ser encontrado o si ocurre
	 *                          un error al intentar determinar su selección
	 */
	public static boolean isSelected(By locator) {
		try {
			WebElement element = findElement(locator);
			boolean isSelected = element.isSelected();
			log.debug("El elemento: {} se encuentra seleccionado", locator);
			return isSelected;
		} catch (Exception e) {
			log.fatal("El elemento: {} no se encuentra seleccionado, por el error {}", locator, e.getMessage());
			throw e;
		}
	}

	/**
	 * Método para verificar si un elemento está <b>seleccionado</b> en la interfaz
	 * de usuario. <br/>
	 * Este método se utiliza con elementos de entrada como casillas de verificación
	 * (checkbox), botones de radio (radio buttons), elementos de entrada (input) y
	 * elementos de opción (option).
	 *
	 * @param locator      el localizador del elemento
	 * @param tiempoEspera el tiempo de espera en segundos para encontrar el
	 *                     elemento web
	 * @return true si el elemento está seleccionado, false de lo contrario
	 * @throws RuntimeException si el elemento no puede ser encontrado o si ocurre
	 *                          un error al intentar determinar su selección
	 */
	public static boolean isSelected(By locator, int tiempoEspera) {
		try {
			WebElement element = findElement(locator, tiempoEspera);
			boolean isSelected = element.isSelected();
			log.debug("El elemento: {} se encuentra seleccionado", locator);
			return isSelected;
		} catch (Exception e) {
			log.fatal("El elemento: {} no se encuentra seleccionado, por el error {}", locator, e.getMessage());
			throw e;
		}
	}

	/**
	 * Método para verificar si un elemento está <b>seleccionado</b> en la interfaz
	 * de usuario. <br/>
	 * Este método se utiliza con elementos de entrada como casillas de verificación
	 * (checkbox), botones de radio (radio buttons), elementos de entrada (input) y
	 * elementos de opción (option).
	 *
	 * @param locator          el localizador del elemento
	 * @param mensajeEvidencia el mensaje que se desea capturar en la evidencia
	 * @return true si el elemento está seleccionado, false de lo contrario
	 * @throws RuntimeException si el elemento no puede ser encontrado o si ocurre
	 *                          un error al intentar determinar su selección
	 */
	public static boolean isSelected(By locator, String mensajeEvidencia) {
		try {
			WebElement element = findElement(locator);
			boolean isSelected = element.isSelected();
			GenerarEvidencia.capturarEvidencia(driver, mensajeEvidencia);
			log.debug("El elemento: {} se encuentra seleccionado y correcto guardo de evidencia", locator);
			return isSelected;
		} catch (Exception e) {
			GenerarEvidencia.capturarEvidencia(driver, e.getMessage(), locator);
			log.fatal("El elemento: {} no se encuentra seleccionado, por el error {}", locator, e.getMessage());
			throw e;
		}
	}

	/**
	 * Método para verificar si un elemento está <b>seleccionado</b> en la interfaz
	 * de usuario. <br/>
	 * Este método se utiliza con elementos de entrada como casillas de verificación
	 * (checkbox), botones de radio (radio buttons), elementos de entrada (input) y
	 * elementos de opción (option).
	 *
	 * @param locator          el localizador del elemento
	 * @param tiempoEspera     el tiempo de espera en segundos para encontrar el
	 *                         elemento web
	 * @param mensajeEvidencia el mensaje que se desea capturar en la evidencia
	 * @return true si el elemento está seleccionado, false de lo contrario
	 * @throws RuntimeException si el elemento no puede ser encontrado o si ocurre
	 *                          un error al intentar determinar su selección
	 */
	public static boolean isSelected(By locator, int tiempoEspera, String mensajeEvidencia) {
		try {
			WebElement element = findElement(locator, tiempoEspera);
			boolean isSelected = element.isSelected();
			GenerarEvidencia.capturarEvidencia(driver, mensajeEvidencia);
			log.debug(
					"El elemento: {} se encuentra seleccionado, dentro del tiempo {} seg y correcto guardo de evidencia",
					locator, tiempoEspera);
			return isSelected;
		} catch (Exception e) {
			GenerarEvidencia.capturarEvidencia(driver, e.getMessage(), locator);
			log.fatal("El elemento: {} no se encuentra seleccionado, por el error {}", locator, e.getMessage());
			throw e;
		}
	}

	/**
	 * 
	 * Valida si un elemento es visible, si falla retorna false
	 *
	 * @param locator
	 * @param time
	 * @return true si no falla la condicion, false si falla la condicion
	 */
	public static boolean validarElemento(By locator, int time) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			return true;
		} catch (Exception e) {
			log.fatal("El elemento: {} no fue encuentrado", locator);
			return false;
		}
	}

	/**
	 * Obtiene el tag del elemento web que coincide con el localizador
	 * proporcionado.
	 *
	 * @param locator El localizador utilizado para encontrar el elemento web.
	 * @return El tag del elemento web.
	 * @throws NoSuchElementException Si el tag del elemento web no se encuentra en
	 *                                el tiempo de espera de 3 segundos.
	 */
	public static String obtenerTag(By locator) {
		try {
			WebElement element = findElement(locator);
			String tag = element.getTagName();
			log.debug("Se obtuvo el tag '{}' sobre el elemento: {}", tag, locator);
			return tag;
		} catch (Exception e) {
			log.fatal("No fue posible obtener el tag sobre el elemento {}, en el tiempo 3 seg, por el error {}",
					locator, e.getMessage());
			throw e;
		}
	}

	/**
	 * Obtiene el tag del elemento web que coincide con el localizador
	 * proporcionado.
	 *
	 * @param locator      El localizador utilizado para encontrar el elemento web.
	 * @param tiempoEspera El tiempo de espera en segundos para encontrar el
	 *                     elemento web.
	 * @return El tag del elemento web.
	 * @throws NoSuchElementException Si el tag del elemento web no se encuentra en
	 *                                el tiempo de espera proporcionado.
	 */
	public static String obtenerTag(By locator, int tiempoEspera) {
		try {
			WebElement element = findElement(locator, tiempoEspera);
			String tag = element.getTagName();
			log.debug("Se obtuvo el tag sobre el elemento: {}, dentro del tiempo {} seg", locator, tiempoEspera);
			return tag;
		} catch (Exception e) {
			log.fatal("No fue posible obtener el tag sobre el elemento {}, en el tiempo {}, por el error {}", locator,
					tiempoEspera, e.getMessage());
			throw e;
		}
	}

	/**
	 * Obtiene el atributo del elemento web que coincide con el localizador
	 * proporcionado.
	 *
	 * @param locator  El localizador utilizado para encontrar el elemento web.
	 * @param atributo El atributo que se desea obtener del elemento web.
	 * @return El valor del atributo del elemento web.
	 * @throws NoSuchElementException Si el atributo del elemento web no se
	 *                                encuentra en el tiempo de espera de 3
	 *                                segundos.
	 */
	public static String obtenerAtributo(By locator, String atributo) {
		try {
			WebElement element = findElement(locator);
			String attribute = element.getAttribute(atributo);
			log.debug("Se obtuvo el atributo '{}' sobre el elemento: {}", attribute, locator);
			return attribute;
		} catch (Exception e) {
			log.fatal("No fue posible obtener el atributo sobre el elemento {}, en el tiempo 3 seg, por el error {}",
					locator, e.getMessage());
			throw e;
		}
	}

	/**
	 * Obtiene el atributo del elemento web que coincide con el localizador
	 * proporcionado.
	 *
	 * @param locator      El localizador utilizado para encontrar el elemento web.
	 * @param atributo     El atributo que se desea obtener del elemento web.
	 * @param tiempoEspera El tiempo de espera en segundos para encontrar el
	 *                     elemento web.
	 * @return El valor del atributo del elemento web.
	 * @throws NoSuchElementException Si el atributo del elemento web no se
	 *                                encuentra en el tiempo de espera
	 *                                proporcionado.
	 */
	public static String obtenerAtributo(By locator, String atributo, int tiempoEspera) {
		try {
			WebElement element = findElement(locator, tiempoEspera);
			String attribute = element.getAttribute(atributo);
			log.debug("Se obtuvo el atributo sobre el elemento: {}, dentro del tiempo {} seg", locator, tiempoEspera);
			return attribute;
		} catch (Exception e) {
			log.fatal("No fue posible obtener el atributo sobre el elemento {}, en el tiempo {}, por el error {}",
					locator, tiempoEspera, e.getMessage());
			throw e;
		}
	}

	/**
	 * Obtiene el título de la página actual.
	 *
	 * @return El título de la página actual como una cadena de caracteres.
	 * @throws IllegalStateException Si no es posible obtener el título de la
	 *                               página.
	 */
	public static String obtenerTitulo() {
		try {
			return driver.getTitle();
		} catch (Exception e) {
			log.fatal("No fue posible obtener el titulo de la pagina, por el error {}", e.getMessage());
			throw new IllegalStateException("No se pudo obtener el título de la página.", e);
		}
	}

	/**
	 * Obtiene la URL actual de la página.
	 *
	 * @return La URL actual de la página como una cadena de caracteres.
	 * @throws IllegalStateException Si no es posible obtener la URL actual de la
	 *                               página.
	 */
	public static String obtenerUrlActual() {
		try {
			return driver.getCurrentUrl();
		} catch (Exception e) {
			log.fatal("No fue posible obtener la url actual de la pagina, por el error {}", e.getMessage());
			throw new IllegalStateException("No se pudo obtener la URL actual de la página.", e);
		}
	}

	/**
	 * Navega a la página anterior en el historial del navegador.
	 *
	 * @throws IllegalStateException Si no es posible navegar a la página anterior.
	 */
	public static void paginaAtras() {
		try {
			driver.navigate().back();
		} catch (Exception e) {
			log.fatal("No fue posible navegar a la pagina anterior, por el error {}", e.getMessage());
			throw new IllegalStateException("No se pudo navegar a la página anterior.", e);
		}
	}

	/**
	 * Navega a la página siguiente en el historial del navegador.
	 *
	 * @throws IllegalStateException Si no es posible navegar a la página siguiente.
	 */
	public static void paginaAdelante() {
		try {
			driver.navigate().forward();
		} catch (Exception e) {
			log.fatal("No fue posible navegar a la pagina siguiente, por el error {}", e.getMessage());
			throw new IllegalStateException("No se pudo navegar a la página siguiente.", e);
		}
	}

	/**
	 * Actualiza la página actual.
	 *
	 * @throws IllegalStateException Si no es posible actualizar la página.
	 */
	public static void actualizarPagina() {
		try {
			driver.navigate().refresh();
		} catch (Exception e) {
			log.fatal("No fue posible actualizar la pagina, por el error {}", e.getMessage());
			throw new IllegalStateException("No se pudo actualizar la página.", e);
		}
	}

	/**
	 * Cambia el foco del controlador de Selenium al frame especificado.
	 *
	 * @param frameID El ID del marco al que se desea cambiar el foco.
	 * @throws IllegalStateException Si no es posible cambiar el foco al marco
	 *                               especificado.
	 */
	public static void cambiarFrame(String frameID) {
		try {
			driver.switchTo().frame(frameID);
		} catch (Exception e) {
			log.fatal("No fue posible cambiar de frame, por el error {}", e.getMessage());
			throw new IllegalStateException("No se pudo cambiar de frame.", e);
		}
	}

	/**
	 * Cambia el foco del controlador de Selenium al frame especificado.
	 *
	 * @param index El índice del marco al que se desea cambiar el foco.
	 * @throws IllegalStateException Si no es posible cambiar el foco al marco
	 *                               especificado.
	 */
	public static void cambiarFrame(int index) {
		try {
			driver.switchTo().frame(index);
		} catch (Exception e) {
			log.fatal("No fue posible cambiar de frame, por el error {}", e.getMessage());
			throw new IllegalStateException("No se pudo cambiar de frame.", e);
		}
	}

	/**
	 * Cambia el foco del controlador de Selenium al frame especificado.
	 *
	 * @param nombreFrame El nombre del marco al que se desea cambiar el foco.
	 * @throws IllegalStateException Si no es posible cambiar el foco al marco
	 *                               especificado.
	 */
	public static void cambiarFrame(By nombreFrame) {
		try {
			driver.switchTo().frame(findElement(nombreFrame));
		} catch (Exception e) {
			log.fatal("No fue posible cambiar de frame, por el error {}", e.getMessage());
			throw new IllegalStateException("No se pudo cambiar de frame.", e);
		}
	}

	/**
	 * Sale del frame actual y regresa al frame padre.
	 *
	 * @throws IllegalStateException Si no es posible salir del frame.
	 */
	public static void salirFrame() {
		try {
			driver.switchTo().parentFrame();
		} catch (Exception e) {
			log.fatal("No fue posible salir del frame, por el error {}", e.getMessage());
			throw new IllegalStateException("No se pudo salir del frame.", e);
		}
	}

	/**
	 * Captura una evidencia de la pantalla actual.
	 *
	 * @param mensaje El mensaje que se desea capturar en la evidencia.
	 */
	public static void capturarEvidencia(String mensaje) {
		GenerarEvidencia.capturarEvidencia(driver, mensaje);
	}

	public static void tiempoEspera(int seg) {
		try {
			Thread.sleep(seg);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Captura una evidencia de la pantalla actual.
	 *
	 * @param mensaje      El mensaje que se desea capturar en la evidencia.
	 * @param tiempoEspera El tiempo de espera en segundos para capturar la
	 *                     evidencia.
	 * @throws InterruptedException Si ocurre un error al intentar capturar la
	 *                              evidencia.
	 */
	public static void capturarEvidencia(String mensaje, int tiempoEspera) {
		try {
			Thread.sleep(tiempoEspera * 1000L);
			GenerarEvidencia.capturarEvidencia(driver, mensaje);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Revisa la ortografía del texto obtenido mediante el localizador especificado.
	 * Obtiene el texto del elemento identificado por el localizador y luego realiza
	 * una verificación ortográfica. Para cada error ortográfico encontrado,
	 * registra el error y sus sugerencias de corrección en el archivo PDF y
	 * registro de depuración.
	 *
	 * @param locator El localizador que identifica el elemento del cual se obtendrá
	 *                el texto para la revisión ortográfica.
	 */
	public static void revisarOrtografia(By locator) {
		String texto = getText(locator);
		List<RuleMatch> matches = RevisorOrtografico.spellCheck(texto);
		assert matches != null;
		for (RuleMatch match : matches) {
			int inicio = match.getFromPos();
			int fin = match.getToPos();
			String error = texto.substring(inicio, fin);
			log.debug("Palabra potencialmente mal escrita: {}", error);
			log.debug("Sugerencias de correciones: {}", match.getSuggestedReplacements());
			GenerarEvidencia.capturarEvidencia("Palabra potencialmente mal escrita: " + error,
					"Sugerencias de correciones: " + match.getSuggestedReplacements());
		}
	}

	/**
	 * Imprime un mensaje en la consola utilizando el nivel de registro de
	 * información.
	 *
	 * @param message El mensaje a imprimir en la consola.
	 */
	public static void printConsole(Object message) {
		System.out.println(message);
		log.info(message);
	}

	/**
	 * 
	 * TODO: Document method aceptarAlerta
	 *
	 */
	public void aceptarAlerta() {
		driver.switchTo().alert().accept();
	}

	/**
	 * 
	 * TODO: Document method cancelarAlerta
	 *
	 */
	public void cancelarAlerta() {
		driver.switchTo().alert().dismiss();
	}

	/**
	 * 
	 * TODO: Document method escribirAlerta
	 *
	 * @param propiedades
	 * @throws IOException
	 */
	public void escribirAlerta(Properties propiedades) throws IOException {
		driver.switchTo().alert().sendKeys(propiedades.getProperty("Nombre"));
	}

	/**
	 * 
	 * TODO: Document method scroll
	 *
	 * @param yInicial
	 * @param xInicial
	 * @param yFinal
	 * @param rutaCarpeta
	 * @throws IOException
	 */
	public static void scroll(int yInicial, int xInicial, int yFinal, int cantRepet) throws IOException {
		for (int i = 0; i < cantRepet; i++) {

			PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

			Sequence tap = new Sequence(finger, 1);
			tap.addAction(
					finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), xInicial, yFinal));

			driver.perform(Arrays.asList(tap));
		}
	}

	/**
	 * 
	 * TODO: Document method scrollVertical
	 *
	 * @param xInit
	 * @param yInit
	 * @param yFinal
	 * @param iteraciones
	 */
	public static void scrollVertical(int xInit, int yInit, int yFinal, int iteraciones) {
		for (int i = 1; i <= iteraciones; i++) {
			PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

			Sequence swipe = new Sequence(finger, 1)
					.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), xInit, yInit))
					.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
					.addAction(new Pause(finger, Duration.ofSeconds(3))).addAction(finger
							.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), xInit, yFinal))
					.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

			driver.perform(Arrays.asList(swipe));
		}
	}

	/**
	 * Realiza un desplazamiento horizontal en un elemento específico en una
	 * aplicación móvil.
	 *
	 * @param locator     El localizador del elemento WebElement que se va a
	 *                    desplazar.
	 * @param xFinal      La cantidad de píxeles que se desplazará horizontalmente.
	 * @param iteraciones El número de veces que se realizará el desplazamiento.
	 *                    <br>
	 *                    Ejemplo de uso:
	 * 
	 *                    <pre>
	 * {@code
	 * By locator = By.id("elementoId");
	 * scrollHorizontal(locator, 100, 5);
	 * }
	 * </pre>
	 */
	public static void scrollHorizontal(By locator, int xFinal, int iteraciones) {
		WebElement element = driver.findElement(locator);
		int centerX = element.getLocation().getX() + element.getSize().getWidth() / 2;
		int centerY = element.getLocation().getY() + element.getSize().getHeight() / 2;
		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
		Sequence swipe = new Sequence(finger, 1);

		for (int i = 1; i <= iteraciones; i++) {
			swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerX, centerY));
			swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
			swipe.addAction(finger.createPointerMove(Duration.ofMillis(1000), PointerInput.Origin.viewport(),
					centerX - xFinal, centerY));
			swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

			driver.perform(Arrays.asList(swipe));
		}
	}

	/**
	 * Realiza un desplazamiento horizontal desde una posición inicial especificada.
	 *
	 * @param xInit       La coordenada X inicial desde donde comienza el
	 *                    desplazamiento.
	 * @param yInit       La coordenada Y inicial desde donde comienza el
	 *                    desplazamiento.
	 * @param xFinal      La distancia en píxeles a desplazar en la dirección
	 *                    horizontal.
	 * @param iteraciones El número de iteraciones del desplazamiento. * <br>
	 *                    Ejemplo de uso:
	 * 
	 *                    <pre>
	 * {@code
	 * scrollHorizontal(100, 100, 150);
	 * }
	 * </pre>
	 */
	public static void scrollHorizontal(int xInit, int yInit, int xFinal, int iteraciones) {
		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
		for (int i = 1; i <= iteraciones; i++) {
			Sequence swipe = new Sequence(finger, 1);
			swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), xInit, yInit));
			swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
			swipe.addAction(finger.createPointerMove(Duration.ofMillis(1000), PointerInput.Origin.viewport(),
					xInit - xFinal, yInit));
			swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
			driver.perform(Arrays.asList(swipe));
		}
	}

	/**
	 * Reemplaza un marcador de posición en una expresión XPath con un valor específico y devuelve un nuevo localizador.
	 *
	 * <p>Este método toma un localizador XPath que contiene un marcador de posición (por ejemplo, "{0}")
	 * y lo reemplaza con el valor proporcionado. Luego, devuelve un nuevo objeto {@code By} con el XPath actualizado.</p>
	 *
	 * @param xpath El localizador {@code By} que contiene el XPath con el marcador de posición.
	 * @param valor El valor que reemplazará el marcador de posición en el XPath.
	 * @return Un nuevo objeto {@code By} con el XPath actualizado.
	 * @throws IllegalArgumentException si el localizador proporcionado no es un XPath.
	 *
	 * <p>Ejemplo de uso:</p>
	 * <pre>
	 * By locator = By.xpath("//input[@id='{0}']");
	 * String valor = "username";
	 * By resultado = Periferia.localizadorVariable(locator, valor);
	 * // Resultado: By.xpath("//input[@id='username']")
	 * </pre>
	 */
	public static By localizadorVariable(By xpath, String valor) {
	    String localizadorCadena = String.format(xpath.toString(), valor);
	    String xpathCadena = localizadorCadena.replaceFirst("By\\.xpath:", "").trim();	    
	    return By.xpath(xpathCadena);
	}

	/**
	 * Devuelve un objeto {@code By} basado en el tipo de localizador y el valor proporcionados.
	 *
	 * <p>Este método toma un tipo de localizador (por ejemplo, "id", "name", "xpath", etc.) y un valor de localizador,
	 * y devuelve un objeto {@code By} correspondiente para localizar elementos en una página web.</p>
	 *
	 * @param locatorType El tipo de localizador (por ejemplo, "id", "name", "xpath", "cssSelector", "className", "tagName", "linkText", "partialLinkText").
	 * @param locatorValue El valor del localizador que se utilizará para encontrar el elemento.
	 * @return Un objeto {@code By} que se puede usar para localizar elementos en una página web.
	 * @throws IllegalArgumentException si el tipo de localizador proporcionado no es válido.
	 *
	 * <p>Ejemplo de uso:</p>
	 * <pre>
	 * {@code
	 * By locator = Periferia.getLocator("id", "username");
	 * // Resultado: By.id("username")
	 * }
	 * </pre>
	 */
	public static By getLocator(String locatorType, String locatorValue) {
        switch (locatorType.toLowerCase()) {
            case "id":
                return By.id(locatorValue);
            case "name":
                return By.name(locatorValue);
            case "xpath":
                return By.xpath(locatorValue);
            case "cssselector":
                return By.cssSelector(locatorValue);
            case "classname":
                return By.className(locatorValue);
            case "tagname":
                return By.tagName(locatorValue);
            case "linktext":
                return By.linkText(locatorValue);
            case "partiallinktext":
                return By.partialLinkText(locatorValue);
            default:
                throw new IllegalArgumentException("Invalid locator type: " + locatorType);
        }
    }
	
	/**
	 * Presiona la tecla numérica correspondiente en un dispositivo Android.
	 *
	 * <p>Este método toma un número entero entre 0 y 9 y simula la presión de la tecla correspondiente
	 * en un dispositivo Android. Si el número proporcionado está fuera de este rango, se lanzará una excepción.</p>
	 *
	 * @param numero El número entero que corresponde a la tecla numérica que se desea presionar (de 0 a 9).
	 * @throws IllegalArgumentException si el número proporcionado no está entre 0 y 9.
	 *
	 * <p>Ejemplo de uso:</p>
	 * <pre>
	 * {@code
	 * pressKeyNumero(3);
	 * // Esto simulará la presión de la tecla '3' en el dispositivo Android.
	 * }
	 * </pre>
	 */
	public static void pressKeyNumero(int numero) {
	    if (numero < 0 || numero > 9) {
	        throw new IllegalArgumentException("Número debe estar entre 0 y 9.");
	    }
	    
	    AndroidKey[] teclas = {
	        AndroidKey.DIGIT_0,
	        AndroidKey.DIGIT_1,
	        AndroidKey.DIGIT_2,
	        AndroidKey.DIGIT_3,
	        AndroidKey.DIGIT_4,
	        AndroidKey.DIGIT_5,
	        AndroidKey.DIGIT_6,
	        AndroidKey.DIGIT_7,
	        AndroidKey.DIGIT_8,
	        AndroidKey.DIGIT_9
	    };
	    
	    driver.pressKey(new KeyEvent(teclas[numero]));
	}
}
