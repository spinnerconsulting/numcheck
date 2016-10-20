/**
 * Code for Sim
 * 
 */

package com.spinnerconsulting;

public class Selenium {

	private String username;
	private String password;
	private String baseUrl;

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
		// implement Selenium calls here
		return true;
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

}
