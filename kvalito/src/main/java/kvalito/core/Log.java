package kvalito.core;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {

	private static final Logger logger = LogManager.getLogger(Log.class);
	private static boolean desativarLog = false;
	private static boolean carregouPropriedade = false;

	public static void registrarAlerta(String valorParaRegistrar) {
		if (!desativarLog()) {
			logger.warn(valorParaRegistrar);
		}
	}

	public static void registrarDebug(String valorParaRegistrar) {
		if (!desativarLog()) {
			logger.debug(valorParaRegistrar);
		}
	}

	public static void registrarErro(Exception ex) {
		if (!desativarLog()) {
			logger.error(ex.toString());
		}
	}

	public static void registrarErro(String valorParaRegistrar) {
		if (!desativarLog()) {
			logger.error(valorParaRegistrar);
		}
	}

	public static void registrarInformacao(String valorParaRegistrar) {
		if (!desativarLog()) {
			logger.info(valorParaRegistrar);
		}
	}

	private static boolean desativarLog() {
		if (!carregouPropriedade) {
			Properties propriedadesPrincipais = new Properties();
			try {
				File arquivo = new File(Configuracoes.ARQUIVO_CONFIGURACOES_PRINCIPAL);
				propriedadesPrincipais.load(new FileInputStream(arquivo));
				desativarLog = Boolean.valueOf(propriedadesPrincipais.getProperty("desativar-log"));
			} catch (Exception e) {
				desativarLog = false;
			}
			carregouPropriedade = true;
		}
		return desativarLog;
	}
}
