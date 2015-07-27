package kvalito.core;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {

	private static final Logger logger = LogManager.getLogger(Log.class);

	public static void registrarAlerta(String valorParaRegistrar) {
		logger.warn(valorParaRegistrar);
	}
	
	public static void registrarDebug(String valorParaRegistrar) {
		logger.debug(valorParaRegistrar);
	}

	public static void registrarErro(Exception ex) {
		logger.error(ex.toString());
	}

	public static void registrarErro(String valorParaRegistrar) {
		logger.error(valorParaRegistrar);
	}

	public static void registrarInformacao(String valorParaRegistrar) {
		logger.info(valorParaRegistrar);
	}
}
