package com.spinnerconsulting;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class WebDriver {

	private String username;
	private String password;
	private String baseUrl;
	private HtmlPage contextPage;
	private WebClient webClient;

	/**
	 * Performs login and navigates to contextPage containing lookup form.
	 */
	void init() {
		try {
			webClient = new WebClient();
			HtmlPage page = webClient.getPage(baseUrl + "/default.aspx?page=home");
			HtmlPage page2 = ((HtmlAnchor) page.getByXPath(
					"//table[@id='iconsTable']/tbody/tr[2]/td[2]/table/tbody/tr/td[2]/table/tbody/tr[2]/td/a")).click();
			((HtmlTextInput) page2.getHtmlElementById("loginBox_UserName")).setValueAttribute(username);
			((HtmlTextInput) page2.getHtmlElementById("loginBox_password")).setValueAttribute(password);
			contextPage = ((HtmlSubmitInput) page2.getByXPath("//img[@alt='SANITIZED']")).click();

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
			((HtmlTextInput) contextPage.getHtmlElementById("TRG_313")).setValueAttribute(query);
			HtmlPage page4 = ((HtmlSpan) contextPage.getByXPath("//div[@id='VWG_304']/div/div/div/div/div/span"))
					.click();
			retVal = page4.asText().contains("TRUE CONDITION");

		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return retVal;
	}

	void close() {
		if (webClient != null) {
			webClient.close();
		}
	}

	public static void main(String[] args) {
		WebDriver s = new WebDriver();
		s.setUsername("test");
		s.setPassword("test");
		s.setBaseUrl("http://www.spinnerconsulting.com");
		s.init();
		s.valueExists("TestValue");
		s.valueExists("TestValue");
		s.close();
	}

	void setUsername(String username) {
		this.username = username;
	}

	void setPassword(String password) {
		this.password = password;
	}

	void setBaseUrl(String baseUrl) {
		System.out.println("here-" + baseUrl);
		this.baseUrl = baseUrl;
	}

}
