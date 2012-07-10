package theoefros.blogi.log;

import org.apache.log4j.Logger;


/**
 * Klass logi tekitamiseks.
 * @author Theo Efros
 *
 */
public class MyLogger {

	// Logija tekitamine
	static Logger logger = Logger.getLogger(MyLogger.class);

	
	/**
	 * Logib teate ja meetodi nime, kust teade saadeti
	 * @param methodName - meetod, kust teade logitakse
	 * @param msg - teade
	 */
	public static void Log(String methodName, String msg) {
		
		String logiTeade = "VIGA_RAKENDUSES: " + " meetod=" + methodName
				+ " veateade=" + msg;
		
		logger.info(logiTeade);
		
	}

	/**
	 * Logib teate
	 * @param msg - teade
	 */
	public static void LogMessage(String msg) {
		String logiTeade = "VIGA_RAKENDUSES: " + msg;
		logger.info(logiTeade);

	}
}
