package kvalito.core;

import java.io.File;

public class DiretorioDownload {

	/**
	 * Obtém o caminho diretório de download
	 * 
	 * @return Retorna o conteúdo da chave "<i>diretorio-default-download</i>"
	 * @throws Exception
	 */
	public String diretorioDownload() throws Exception {
		return Configuracoes.getConfiguracaoPrincipal("diretorio-default-download");
	}

	/**
	 * Exclui o arquivo da pasta de downloads.
	 * 
	 * @param nomeArquivo
	 * @throws Exception
	 */
	public void excluirArquivo(String nomeArquivo) throws Exception {
		File arquivo = new File(diretorioDownload() + "\\" + nomeArquivo);
		arquivo.delete();
	}

	/**
	 * Verifica a existência do arquivo na pasta de downloads.
	 * 
	 * @param nomeArquivo
	 * @return
	 * @throws Exception
	 */
	public boolean existeArquivo(String nomeArquivo) throws Exception {
		File arquivo = new File(diretorioDownload() + "\\" + nomeArquivo);
		return arquivo.exists();
	}

}
