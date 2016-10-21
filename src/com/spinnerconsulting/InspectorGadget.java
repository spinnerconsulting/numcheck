package com.spinnerconsulting;

import java.util.Scanner;

public class InspectorGadget {

	public static void main(String[] args) throws Exception {
		System.out.println("===Start===");
		System.out.println("Welcome to the InspectorGadget");
		System.out.println(" ");
		System.out.println("Please log in");

		// Username input handling
		// TODO - check for various bad inputs
		System.out.print("Username: ");
		Scanner igu = new Scanner(System.in);
		String username = igu.nextLine();

		// Password input handling
		// TODO - check for invalid inputs and do not echo password to console
		System.out.print("Password: ");
		Scanner igp = new Scanner(System.in);
		String password = igp.nextLine();

		System.out.println("Welcome " + username);
		System.out.println(" ");

		// Max records input handling
		// TODO - test this area and try to break
		System.out.print("Maximum number of records to check [no limit]: ");
		Scanner igr = new Scanner(System.in);
		String input = igr.nextLine();
		int recordLimit = (input.equals("") ? Excel.NO_RECORD_LIMIT : Integer.parseInt(input));

		// Base URL input handling
		// TODO - test this area and try to break
		System.out.println("Enter the base URL:");
		Scanner igw = new Scanner(System.in);
		String url = igw.nextLine();

		igu.close();
		igp.close();
		igr.close();
		igw.close();

		WebDriver wd = new WebDriver();
		wd.setUsername(username);
		wd.setPassword(password);
		wd.setBaseUrl(url);
		wd.init();

		Excel e = new Excel();
		e.setWebDriver(wd);
		e.setMaxRecords(recordLimit);

		e.runQueries();
		wd.close();
		System.out.println("===End=====");

	}

}
