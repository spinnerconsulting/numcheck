/**
 *  Code for Pete
 */


package com.spinnerconsulting;

import java.util.Scanner;                      // Scanner helps to import strings

public class InspectorGadget {

	public static void main(String[] args) throws Exception {
		System.out.println("===Start===");
		System.out.println("Welcome to the InspectorGadget");
		System.out.println("Push Version 19 Oct x:xx");
		System.out.println(" ");
		System.out.println("Please log in");
		System.out.println("Username");        // Prompt for username
		String inspectorgadgetusername;        // Declares variable to store username
		Scanner igu = new Scanner(System.in);
		inspectorgadgetusername = igu.nextLine();
		
		System.out.println("Password");        // Prompt for password
		String inspectorgadgetpassword;        // Declares variable to store password
		Scanner igp = new Scanner(System.in);
		inspectorgadgetpassword = igp.nextLine();

		System.out.println("Welcome "+ inspectorgadgetusername);
		System.out.println(" ");
		System.out.println("Please enter max number of records  (enter 'none' for no maximum)");
		
		String inspectorgadgetrecordinput;   // used to hold user input
		String nomaximumnumber;				 // used as comparison
		nomaximumnumber = "none";			 // matches text for # of records user query
		Boolean norecordlimit;				 // used to store if user wants a limit
		norecordlimit = false;				 // placeholder value
		Integer inspectorgadgetrecordlimit;  // used to store user input as integer
		inspectorgadgetrecordlimit = 1;  	 // sets default value - not needed if println for variable not active
		
		Scanner igr = new Scanner(System.in);
		inspectorgadgetrecordinput = igr.nextLine();
		
		if (inspectorgadgetrecordinput.equals(nomaximumnumber)) {
			norecordlimit = true;
		}	else {
			inspectorgadgetrecordlimit = Integer.parseInt(inspectorgadgetrecordinput);
		}
		
		System.out.println("Please enter the url to pull data from");  	// Prompt for website
		String inspectorgadgeturl;										// Declares variable to store website
		Scanner igw = new Scanner(System.in);
		inspectorgadgeturl = igw.nextLine();

		igu.close();		
		igp.close();		
		igr.close();
		igw.close();
		
		// code to visualize entry    
		//System.out.println("You entered "+ inspectorgadgetusername + "  " + inspectorgadgetpassword);
		//System.out.println(norecordlimit);
		//System.out.println(inspectorgadgetrecordinput);
		//System.out.println(nomaximumnumber);
		//System.out.println(inspectorgadgetrecordlimit);
		//System.out.println(inspectorgadgeturl);
		
		Excel e = new Excel();
		e.doIt();
		System.out.println("===End=====");
		


	}



}
