package automationFramework.Utilities;

import static com.jayway.restassured.RestAssured.given;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Random;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.eclipse.jetty.util.log.Log;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.jayway.restassured.specification.RequestSpecification;

import automationFramework.Utilities.Global;

import java.io.IOException;
import org.apache.commons.codec.binary.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public final class Utils {

	public static WebDriver driver = null;

	//API helper method
	public static RequestSpecification getRequestSpecifications() {

		return given().contentType("application/json").header("Authorization", "d2ViOnBhc3N3b3jk")
				.header("x-cub-uid", "11111111111").header("x-cub-device", "crm");
	}

	public static void waitForElement(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public static void waitTime(long milliseconds) throws InterruptedException {
		Thread.sleep(milliseconds);
	}

	public static void isPageLoaded(WebDriver driver, String title) {
		Assert.assertTrue(driver.getTitle().equals(title));
	}

	public static WebDriver openBrowser(String browser) {

		try {
			if (browser.equals("firefox.exe")) {

				FirefoxProfile prof = new FirefoxProfile();
				prof.setPreference("browser.startup.homepage_override.mstone", "ignore");
				prof.setPreference("startup.homepage_welcome_url.additional", "about:blank");
				prof.setPreference("browser.cache.disk.enable", false);
				prof.setPreference("browser.cache.memory.enable", false);
				prof.setPreference("browser.cache.offline.enable", false);
				prof.setPreference("network.http.use-cache", false);
				prof.setPreference("browser.private.browsing.autostart", true);
				driver = new FirefoxDriver(prof);
				driver.manage().deleteAllCookies();
				driver.manage().window().maximize();
				Reporter.log("firefox driver instantiated");

			} else if (browser.equals("chrome.exe")) {
				System.setProperty("webdriver.chrome.driver", "C:/ChromeDriver/chromedriver.exe");

				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				capabilities.setCapability("chrome.switches", Arrays.asList("--ignore-certificate-errors"));
				ChromeOptions options = new ChromeOptions();
				options.addArguments("test-type");
				options.addArguments("incognito");
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				driver = new ChromeDriver();
				driver.manage().deleteAllCookies();
				driver.manage().window().maximize();
				Log.info("chrome driver instantiated");

			} else if (browser.equals("ie10.exe")) {

				System.setProperty("webdriver.ie.driver",
						"C:/Users/200123/Downloads/IEDriverServer_x64_2.53.1/IEDriverServer.exe");

				DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
				capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, false);
				capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
				capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						true);

				InternetExplorerDriver driver = new InternetExplorerDriver(capabilities);
				driver.manage().window().maximize();
				Log.info("IE10 driver instantiated");
				return driver;
			}

		} catch (Exception e) {
			Reporter.log("Utils.openBrowser failed");
		}

		return driver;
	}

	// Creating random email and password
	public static String randomEmailString() {
		String randomEmail = generateRandomString(12) + Global.GMAIL;
		return randomEmail;
	}

	public static String randomPasswdString() {
		String randomPasswd = generateRandomString(12) + "1X!";
		return randomPasswd;
	}

	// Generate random user name and phone number
	public static String randomUsernameString() {
		String randomUsername = "USER_" + generateRandomString(7);
		return randomUsername;
	}

	// Generating random string
	private static String generateRandomString(int length) {
		StringBuffer randStr = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = getRandomNumber();
			char ch = Global.CHAR_LIST.charAt(number);
			randStr.append(ch);
		}
		return randStr.toString();
	}

	// Generate random number
	private static int getRandomNumber() {
		int randomInt = 0;
		Random randomGenerator = new Random();
		randomInt = randomGenerator.nextInt(Global.CHAR_LIST.length());
		if (randomInt - 1 == -1) {
			return randomInt;
		} else {
			return randomInt - 1;
		}
	}

	public static String randomPhoneNumber() {
		return generatePhoneNumber();
	}

	// Generate random phone number
	private static String generatePhoneNumber() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		// first not 0 or 1
		sb.append(random.nextInt(7) + 2);
		// rest of 9 digits
		for (int i = 0; i < 9; i++) {
			sb.append(random.nextInt(8));
		}
		return sb.toString();
	}

	// capturing screenshots
	public static void getScreenShot() throws Exception {
		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile,
					new File("C:/Automation/ScreenShots/" + System.currentTimeMillis() + "_screenshot.png"));
			Reporter.log("Screenshot Captured");
		} catch (IOException e) {
			Reporter.log("Failed to capture screenshot");
		}
	}

	// handling multiple windows and selecting the right one
	public static void handleMultipleWindows(WebDriver driver, String windowTitle) {
		Set<String> windows = driver.getWindowHandles();

		for (String window : windows) {
			driver.switchTo().window(window);
			if (driver.getTitle().contains(windowTitle)) {
				return;
			}
		}
	}

	// MAC generating code
	public String calcHmac(String src) throws Exception {
		String base64Key = "GaAodwiA6BREnloZYjOkONxCC//EKClXhzAuYoX91oU=";

		Mac mac = Mac.getInstance("HmacMD5");
		byte[] decodedBytes = Base64.decodeBase64(base64Key.getBytes("UTF-16LE"));
		SecretKeySpec sk = new SecretKeySpec(decodedBytes, mac.getAlgorithm());
		mac.init(sk);

		String srcStr = src;
		byte[] result = mac.doFinal(srcStr.getBytes("UTF-16LE"));

		String s = new String(result, "UTF-16LE");

		byte[] resultBase64 = Base64.encodeBase64(mac.doFinal(srcStr.getBytes("ASCII")));

		String sB64 = new String(resultBase64, "UTF-8");

		return sB64;
	}

}