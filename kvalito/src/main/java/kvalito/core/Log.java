package kvalito.core;

public class Log {

	public static void registrarAlerta(String valorParaRegistrar) {
		System.out.println("[KVALITO-ATENÇÃO] " + valorParaRegistrar);
	}
	
	public static void registrarDebug(String valorParaRegistrar) {
		System.out.println("[KVALITO-DEBUG] " + valorParaRegistrar);
	}

	public static void registrarErro(Exception ex) {
		System.out.println("[KVALITO-ERRO] ==================");
		System.out.println("TRACE: ");
		System.out.println(ex.toString());
		System.out.println("==========================");
	}

	public static void registrarErro(String valorParaRegistrar) {
		System.out.println("[KVALITO-ERRO] ==================");
		System.out.println("TRACE: " + valorParaRegistrar);
		System.out.println("==========================");
	}

	public static void registrarInformacao(String valorParaRegistrar) {
		System.out.println("[KVALITO-INFO] " + valorParaRegistrar);
	}
}
