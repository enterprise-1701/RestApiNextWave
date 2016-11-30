package automationFramework.Utilities;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.SimpleLayout;
import org.apache.log4j.xml.DOMConfigurator;

public class Logging {

	private static Logger Log = Logger.getLogger(Logger.class.getName());

	public static void setLogConsole() {
		// Logging Configuration
		Log.setLevel(Level.INFO);
		DOMConfigurator.configure("log4j.xml");
		ConsoleAppender ConsoleAppender = new ConsoleAppender();
		String pattern = "Time in Milliseconds: %r %n";
		pattern += "Test Case: %C %n";
		pattern += "Time Stamp: %d{ISO8601} %n";
		pattern += "Code Location: %l %n";
		pattern += "Log Message: %m %n %n";
		ConsoleAppender.setLayout(new PatternLayout(pattern));
		Log.addAppender(ConsoleAppender);
		ConsoleAppender.activateOptions();
	}

	public static void setLogFile() {
		Log.setLevel(Level.INFO);
		DOMConfigurator.configure("log4j.xml");
		FileAppender fileAppender = new FileAppender();
		fileAppender.setFile(Global.LOG_FILE);
		String pattern = "Time in Milliseconds: %r %n";
		pattern += "Test Case: %C %n";
		pattern += "Time Stamp: %d{ISO8601} %n";
		pattern += "Code Location: %l %n";
		pattern += "Log Message: %m %n %n";
		fileAppender.setLayout(new PatternLayout(pattern));
		Log.addAppender(fileAppender);
		fileAppender.activateOptions();
	}
}
