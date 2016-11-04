package com.spinnerconsulting;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebClient {

	/**
	 * The username to pass to the main login form.
	 */
	private String username;

	/**
	 * The password to send to the main login form.
	 */
	private String password;

	/**
	 * The base URL of the site (ex: http://www.example.com)
	 */
	private String baseUrl;

	/**
	 * The string used to indicate a green condition.
	 */
	private String greenString;

	/**
	 * The string used to compare a red condition.
	 */
	private String redString;

	/**
	 * GeckDriver is required to run FirefoxDriver.
	 * 
	 * Provide the path on the LOCAL file system to the exe.
	 */
	private String geckoDriverPath;

	/**
	 * The Selenium WebDriver used to make requests.
	 */
	private org.openqa.selenium.WebDriver driver;

	/**
	 * 
	 * Call after WebDriver object creation to setup environment.
	 * 
	 * Performs login and navigates to contextPage containing lookup form.
	 * 
	 */
	void init() {

		System.setProperty("webdriver.gecko.driver", geckoDriverPath);
		driver = new FirefoxDriver();
		driver.get(baseUrl + "/pkdata/mainform.aspx");

		// sleep for a second to let the naughty AJAX catch-up
		sleep(20000);

		// username and password entry
		driver.findElement(By.id("TRG_14")).click();
		driver.findElement(By.id("TRG_14")).clear();
		driver.findElement(By.id("TRG_14")).sendKeys(username);
		driver.findElement(By.id("TRG_13")).click();
		driver.findElement(By.id("TRG_13")).clear();
		driver.findElement(By.id("TRG_13")).sendKeys(password);
		// naughty field copying or something going on here, click this
		driver.findElement(By.id("TRG_3")).click();
		driver.findElement(By.id("TRG_3")).sendKeys(username.replaceAll("[^0-9]+", ""));

		// required by form for some reason
		driver.findElement(By.id("TRG_14")).click();
		driver.findElement(By.id("TRG_13")).click();
		driver.findElement(By.id("TRG_3")).click();

		sleep(4000);

		if (System.getProperty("os.name").toLowerCase().contains("mac")) {
			driver.findElement(By.cssSelector("span.cs3")).click();
		} else {
			Actions action = new Actions(driver);
			action.moveToElement(driver.findElement(By.cssSelector("span.cs3")));
			driver.findElement(By.cssSelector("span.cs3")).click();
		}

		// tools link on the left side nav
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.id("TXT_44")));
		driver.findElement(By.id("TXT_44")).click();

		sleep(20000);

	}

	/**
	 * The core method for determining if a value exists in the web datastore.
	 * 
	 * 
	 * @param query
	 *            - The 10 digit number being inspected
	 * @return - a boolean true indicating the value exists or false indicating
	 *         the value does not exist.
	 */
	boolean valueExists(String query) throws Exception {

		String txtFldId = "TRG_345";
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.id(txtFldId)));

		driver.findElement(By.id(txtFldId)).click();
		driver.findElement(By.id(txtFldId)).clear();
		driver.findElement(By.id(txtFldId)).sendKeys(query);
		Actions action = new Actions(driver);
		String imgXpath = "//img[@src='Images.Magnifying.MagnifyingGlass_64.png.aspx']";
		action.moveToElement(driver.findElement(By.xpath(imgXpath)));
		sleep(1000);
		driver.findElement(By.xpath(imgXpath)).click();

		String content = driver.getPageSource();

		if (content.contains(greenString)) {
			return Boolean.TRUE;
		} else if (content.contains(redString)) {
			return Boolean.FALSE;
		} else {
			throw new Exception("Unable to locate green or red condition for number: " + query);
		}

	}

	/**
	 * Closes out the WebClient session.
	 * 
	 * Call this when all queries are complete.
	 * 
	 */
	void close() {
		if (driver != null) {
			driver.close();
		}
	}

	void setUsername(String username) {
		this.username = username;
	}

	void setPassword(String password) {
		this.password = password;
	}

	void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	void setGreenString(String greenString) {
		this.greenString = greenString;
	}

	void setRedString(String redString) {
		this.redString = redString;
	}

	String getGeckoDriverPath() {
		return geckoDriverPath;
	}

	void setGeckoDriverPath(String geckoDriverPath) {
		this.geckoDriverPath = geckoDriverPath;
	}

	private void sleep(long seconds) {
		try {
			Thread.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
