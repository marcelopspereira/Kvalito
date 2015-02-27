package kvalito.utilitarios;

import java.io.File;
import java.io.IOException;

public class UtilitarioArquivo {

	
	/**
	 * Retorna o caminho relativo de um arquivo presente na pasta <i>src\test\resources</i>. <br>
	 * @param nomeArquivo Nome do arquivo.
	 * @return Caminho relativo do arquivo.
	 * @throws IOException
	 */
	public static String obterCaminho(String nomeArquivo) throws IOException {
		String caminhoRelativo = new File("src\\test\\resources\\" + nomeArquivo).getCanonicalPath();

		return caminhoRelativo;
	}
}
