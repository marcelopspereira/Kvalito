package kvalito.utilitarios;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilitarioTexto {

	/**
	 * Grava informações em um arquivo
	 * 
	 * @param caminhoArquivo  Local de armazenamento do arquivo
	 * @param conteudo  Conteudo do Arquivo
	 * @throws Exception
	 */
	public static void escreverArquivo(String caminhoArquivo, String conteudo) throws Exception {
		BufferedWriter writer = null;
		File arquivo = new File(caminhoArquivo);
		writer = new BufferedWriter(new FileWriter(arquivo));
		writer.write(conteudo);
		writer.close();
		
		
	}

	/**
	 * Extrai informações de um conteudo de acordo com uma Expressão Regular especificada
	 * 
	 * @param texto  String com informações a serem extraídas
	 * @param regexExtracao  Padrão Regex utilizado 
	 * @return      String com o resultado da extração
	 */
	public static String extrair(String texto, String regexExtracao) {
		Pattern pattern = Pattern.compile(regexExtracao);
		Matcher matcher = pattern.matcher(texto);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}
	
	

}
