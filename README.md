# numcheck

# Goal
Numcheck identifies records in an Excel spreadsheet that match a specified criteria in a web database.

# Overview
Our client requested the ability to automate evaluating the presence of numbers in a web database.  We discussed the project requirements, their expectations and goals, and formalized them in a written client proposal.

# Risks
One risk associated with this project was the client was unable to grant us access to the web database.  We discussed this limitation with the client and they accepted the risk and understood it could add time to the final delivery schedule.

# Solution
Spinner Consulting's team implemented a solution utilizing a basic Java jar file that utilized:

- Apache POI for Excel interactions
- Apache CLI for simplifying command line interactions
- Selenium Web Driver for interacting with the web form
- Maven for dependency management

Our team was able to pull together a solution for the core components in one week.  The following week we began working with the client to perform integration testing and begin the process of creating the customized Selenium interactions.  There was heavy use of AJAX in the web forms and this presented several challenges to overcome due to mimicking the event timing, mouse movements, etc. of a typical web browser.

The code consists of:

- InspectorGadget.java: collects user's input and is the main point of entry for the program
- Excel.java: interacts with the Excel file
- WebClient.java: interacts with the web database

The following command:

`java -jar ig.jar`

will produce a help page similar to below:

```console
usage: InspectorGadget
 -base <arg>    the base URL to the host (ex: http://www.example.com)
 -g <arg>       the local path to the geckodriver executable file
 -green <arg>   the string to search for indicating a green response
 -help          print this message and exit
 -max <arg>     the max records to read from Excel source file [default:
                no limit]
 -p <arg>       the password for web requests
 -path <arg>    the local path to the Excel source file
 -red <arg>     the string to search for indicating a red response
 -u <arg>       the username for web requests
```

The `-green` and `-red` parameters were added to align with the client's system syntax.  Ultimately, the strings are used to determine if the result from the form was positive or negative.


# Results
Spinner Consulting successfully delivered the solution to the client and the solution automated a long process of validating 1000's of records in the system. 

