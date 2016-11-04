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
	 * -u testuser -p testpassword -path extras/demo.xlsx -base
	 * http://www.example.com -max 1000 -green "test search string" -red
	 * "test bad search string" -g <path to geckodriver executable file>
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
		options.addOption("path", true, "the local path to the Excel source file");
		options.addOption("max", true, "the max records to read from Excel source file [default: no limit]");
		options.addOption("base", true, "the base URL to the host (ex: http://www.example.com)");
		options.addOption("green", true, "the string to search for indicating a green response");
		options.addOption("red", true, "the string to search for indicating a red response");
		options.addOption("g", true, "the local path to the geckodriver executable file");

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		WebClient client = new WebClient();
		Excel e = new Excel();

		try {
			CommandLine line = parser.parse(options, args);

			if (line.hasOption("help") || line.getOptions().length == 0) {
				formatter.printHelp("InspectorGadget", options);
				return;
			}

			if (line.hasOption("u")) {
				client.setUsername(line.getOptionValue("u"));
			}
			if (line.hasOption("p")) {
				client.setPassword(line.getOptionValue("p"));
			}
			if (line.hasOption("path")) {
				e.setFilePath(line.getOptionValue("path"));
			}
			if (line.hasOption("max")) {
				e.setMaxRows(Integer.parseInt(line.getOptionValue("max")));
			}
			if (line.hasOption("base")) {
				client.setBaseUrl(line.getOptionValue("base"));
			}
			if (line.hasOption("green")) {
				client.setGreenString(line.getOptionValue("green"));
			}
			if (line.hasOption("red")) {
				client.setRedString(line.getOptionValue("red"));
			}
			if (line.hasOption("g")) {
				client.setGeckoDriverPath(line.getOptionValue("g"));
			}

		} catch (ParseException exp) {
			exp.printStackTrace();
			formatter.printHelp("InspectorGadget", options);
			return;
		}

		try {

			client.init();
			e.setWebClient(client);
			e.runQueries();

		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (client != null) {
				client.close();
			}
		}

	}

}
