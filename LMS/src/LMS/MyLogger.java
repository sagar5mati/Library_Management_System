package LMS;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyLogger {	
	public static MyLogger declareLogger = null;
	public Logger LOGGER;
	public Handler fileHandler = null;
	final String filename = "./filelogs.xml";
	private MyLogger() {
		try {
			LOGGER = Logger.getLogger(MyLogger.class.getName());
		}
		catch(Exception e) {
			LOGGER.log(Level.SEVERE, "LOGGER DECLARATION ERROR", e);
		}
		try {
			fileHandler = new FileHandler(filename, false);
		}
		catch(IOException io) {
			LOGGER.log(Level.SEVERE, "FILE HANDLER ERROR", io);
		}
		LOGGER.addHandler(fileHandler);
		LOGGER.setLevel(Level.ALL);
		fileHandler.setLevel(Level.ALL);
		
	}
	
	public static Logger getInstance() {
		if(declareLogger == null) {
			declareLogger = new MyLogger();		
		}
		return declareLogger.LOGGER;
	}
	
	/*public void attachHandler() {
		LOGGER.addHandler(fileHandler);
		//LOGGER.config("HANDLER ADDED");
		return;
	}
	
	public void config() {
		// Setting logging at all levels
		fileHandler.setLevel(Level.ALL);
		LOGGER.setLevel(Level.ALL);
		//LOGGER.config("CONFIG DONE");
		return;
	}
	
	public void dettachHandler() {
		LOGGER.removeHandler(fileHandler);
		return;
	}
	
	// 3 arguments are level, message, exception
	public void logEvent(Level level ,String message, Exception e) {
		LOGGER.log(level, message, e);
		return;
	}
	
	// 2 arguments are level ,message
	public void logEvent(Level level, String message) {
		LOGGER.log(level, message);
		return;
	}
	
	public void logInfo(String message) {
		LOGGER.info(message);
		return;
	}
	
	public void logWarning(String message) {
		LOGGER.warning(message);
		return;
	}
	
	public void logConfig(String message) {
		LOGGER.config(message);
		return;
	}
	
	public void turnOnAtALL() {
		LOGGER.setLevel(Level.ALL);
		return;
	}*/
}