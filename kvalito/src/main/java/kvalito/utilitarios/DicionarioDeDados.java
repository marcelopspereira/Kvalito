package kvalito.utilitarios;

import java.util.HashMap;
import java.util.Map;

public final class DicionarioDeDados {
	private static Map<String, String> dicionario = new HashMap<String, String>();

	public static String obterValor(String chave) {
		return dicionario.get(chave);
	}
	
	public static void incluirDados(String chave, String valor){
		dicionario.put(chave, valor);
	}

	public static int numeroDeChavesExistentes() {
		return dicionario.size();
	}

	public static void limpar() {
		dicionario.clear();
	}

	public static void apagarChave(String chave) {
		dicionario.remove(chave);
	}

	public static boolean possuiChave(String chave) {
		return dicionario.containsKey(chave);
	}
}