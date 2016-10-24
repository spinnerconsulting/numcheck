package com.spinnerconsulting;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class WebDriver {

	private String username;
	private String password;
	private String baseUrl;
	private String searchString;
	private HtmlPage contextPage;
	private WebClient webClient;

	/**
	 * 
	 * Call after WebDriver object creation to setup environment.
	 * 
	 * Performs login and navigates to contextPage containing lookup form.
	 * 
	 */
	void init() {
		try {
			webClient = new WebClient();
			HtmlPage page = webClient.getPage(baseUrl + "/pkdata/mainform.aspx");

			// username and password entry
			((HtmlTextInput) page.getHtmlElementById("loginBox_UserName")).setValueAttribute(username);
			((HtmlTextInput) page.getHtmlElementById("loginBox_password")).setValueAttribute(password);

			// click the login button
			HtmlPage page2 = ((HtmlSpan) page.getByXPath("//span[@id='cs3']")).click();

			// access web form
			contextPage = ((HtmlSpan) page2.getByXPath("//*[@id='TXT_44']")).click();

		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

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

		boolean retVal = false;
		try {

			// set the query field
			((HtmlTextInput) contextPage.getHtmlElementById("TRG_313")).setValueAttribute(query);

			// click the span button to check
			HtmlPage page4 = ((HtmlSpan) contextPage.getByXPath("//div[@id='VWG_304']/div/div/div/div/div/span"))
					.click();

			// check for the expected text output
			retVal = page4.asText().contains(searchString);

		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return retVal;
	}

	/**
	 * Closes out the WebClient session.
	 * 
	 * Call this when all queries are complete.
	 * 
	 */
	void close() {
		if (webClient != null) {
			webClient.close();
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

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

}
