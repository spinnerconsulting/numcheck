package com.spinnerconsulting;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class InspectorGadget {

	/**
	 * The main entry point for this application.
	 * 
	 * To pass variables in Eclipse, go to: Run
	 * Configurations.../Arguments/Program Arguments
	 * 
	 * Enter program arguments in the textarea. Example arguments:
	 * 
	 * -u testuser -p testpassword -path extras/demo.xlsx -base http://www.example.com
	 * -max 1000 -s "test search string"
	 * 
	 * Entering no arguments will produce the -help console output.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		Options options = new Options();
		options.addOption("help", "print this message and exit");
		options.addOption("u", true, "the username for web requests");
		options.addOption("p", true, "the password for web requests");
		options.addOption("path", true, "the path to the Excel source file");
		options.addOption("max", true, "the max records to read from Excel source file [default: no limit]");
		options.addOption("base", true, "the base URL to the host (ex: http://www.example.com)");
		options.addOption("s", true, "the string to search for in the web response");

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
			if (line.hasOption("s")) {
				wd.setSearchString(line.getOptionValue("s"));
			}

		} catch (ParseException exp) {
			exp.printStackTrace();
			formatter.printHelp("InspectorGadget", options);
			return;
		}

		wd.init();
		e.setWebDriver(wd);
		e.runQueries();
		wd.close();

	}

}
