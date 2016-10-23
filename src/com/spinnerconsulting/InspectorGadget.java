package com.spinnerconsulting;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class InspectorGadget {

	public static void main(String[] args) throws Exception {
		args = new String[] { "-u", "testuser", "-p", "test", "-path", "extras/demo.xlsx", "-base", "http://www.example.com",  "-max", "1000" };
		// args = new String[]{ "-help" };

		Options options = new Options();
		options.addOption("help", "print this message and exit");
		options.addOption("v", "verbose output for debugging");
		options.addOption("u", true, "the username for web requests");
		options.addOption("p", true, "the password for web requests");
		options.addOption("path", true, "the path to the Excel source file");
		options.addOption("max", true, "the max records to read from Excel source file [default: no limit]");
		options.addOption("base", true, "the base URL to the host (ex: http://www.example.com)");

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		WebDriver wd = new WebDriver();
		Excel e = new Excel();

		try {
			CommandLine line = parser.parse(options, args);

			if (line.hasOption("help") || line.getOptions().length == 0) {
				formatter.printHelp("InspectorGadget", options);
				return;
			}

			if (line.hasOption("u")) {
				wd.setUsername(line.getOptionValue("u"));
			}
			if (line.hasOption("p")) {
				wd.setPassword(line.getOptionValue("p"));
			}
			if (line.hasOption("path")) {
				e.setFilePath(line.getOptionValue("path"));
			}
			if (line.hasOption("max")) {
				e.setMaxRecords(Integer.parseInt(line.getOptionValue("max")));
			}
			if (line.hasOption("base")) {
				wd.setBaseUrl(line.getOptionValue("base"));
			}

		} catch (ParseException exp) {
			System.err.println(exp.getMessage());
			formatter.printHelp("InspectorGadget", options);
			return;
		}

		wd.init();
		e.setWebDriver(wd);
		e.runQueries();
		wd.close();

	}

}
