package com.spinnerconsulting;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

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
	 * The string used to compare a positive or negative response.
	 */
	private String searchString;

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
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "/pkdata/mainform.aspx");

		// username and password entry
		driver.findElement(By.id("TRG_14")).clear();
		driver.findElement(By.id("TRG_14")).sendKeys(username);
		driver.findElement(By.id("TRG_13")).clear();
		driver.findElement(By.id("TRG_13")).sendKeys(password);
		// naughty field copying or something going on here, click this
		driver.findElement(By.id("TRG_3")).click();
		driver.findElement(By.cssSelector("span.cs3")).click();

		// tools link on the left side nav
		//driver.findElement(By.id("TXT_44")).click();

	}

	/**
	 * The core method for determining if a value exists in the web datastore.
	 * 
	 * 
	 * @param query
	 *            - The 10 digit number being inspected
	 * @return - a boolean true indicating the value exists or false indicating
	 *         the value does not exist
	 */
	boolean valueExists(String query) {

		driver.findElement(By.id("TRG_313")).click();
		driver.findElement(By.id("TRG_313")).clear();
		driver.findElement(By.id("TRG_313")).sendKeys(query);
		driver.findElement(By.xpath("//div[@id='VWG_304']/div/div/div/div/div/span")).click();
		return driver.getPageSource().contains(searchString);

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

	String getSearchString() {
		return searchString;
	}

	void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	String getGeckoDriverPath() {
		return geckoDriverPath;
	}

	void setGeckoDriverPath(String geckoDriverPath) {
		this.geckoDriverPath = geckoDriverPath;
	}

}
