package kvalito.core;

import java.util.List;

import kvalito.componentes.Elemento;
import kvalito.componentes.Select;

public abstract class Pagina {
	public static String getUrlAtual() {
		return Navegador.getUrlAtual();
	}

	private DiretorioDownload diretorioDownload;

	/**
	 * Abrir a página com a url configurada no PageObject.
	 * 
	 * @throws Exception
	 */
	public void abrir() throws Exception {
		this.abrirUrl(getUrl());
	}

	protected void abrirUrl(String url) throws Exception {
		Navegador.abrirUrl(url);
	}

	public void aceitarAlerta() {
		Log.registrarInformacao("Irá aceitar o alerta");
		Navegador.clicarAceitarNoAlerta();
	}

	public void aceitarAlertaSeguranca() {
		Log.registrarInformacao("Irá aceitar a mensagem de segurança");
		Navegador.clicarAceitarNoAlerta();
	}

	/**
	 * Método para atualizar página <br>
	 * <br>
	 * 
	 * @throws Exception
	 */
	public void atualizarPagina() throws Exception {
		Navegador.atualizarPagina();
	}

	public void capturarPrintTela(String nomeArquivo) throws Exception {
		Navegador.capturarPrintTela(nomeArquivo);
	}

	public String codigoFonte() throws Exception {
		return Navegador.codigoFonteDaPagina();
	}

	/**
	 * Método para contar quantidade de elemetos dado um localizador do HTML <br>
	 * <br>
	 * 
	 * @throws Exception
	 */

	public int contarElementos(String expressaoLocalizacao) throws Exception {
		Localizador localizador = new Localizador(null, expressaoLocalizacao);
		return Navegador.obterColecao(localizador).size();
	}

	/**
	 * Método para contar quantidade de elemetos dado um localizador do HTML <br>
	 * <br>
	 * 
	 * @throws Exception
	 */

	public int contarElementos(String localizarPor, String expressaoLocalizacao) throws Exception {
		Localizador localizador = new Localizador(localizarPor, expressaoLocalizacao);
		return Navegador.obterColecao(localizador).size();
	}

	public DiretorioDownload diretorioDownload() {
		if (diretorioDownload == null) {
			diretorioDownload = new DiretorioDownload();
		}
		return diretorioDownload;
	}

	/**
	 * Pausa a execução do teste em X milissegundos, onde X é o valor passado no
	 * parâmetro
	 * 
	 * @param tempoEsperar
	 * @throws InterruptedException
	 */
	public void esperarCarregamentoPor(int tempoEsperar) throws InterruptedException {
		Thread.sleep(tempoEsperar);
	}

	/**
	 * Executa scrool do mouse para o número de pixels informado.<br>
	 */
	public void executarScroolVertical(int numeroPixels) {
		Navegador.executarScroolY(numeroPixels);
	}

	/**
	 * Define qual navegador será utilizado para executar os testes. </i>Obs.: O
	 * default é Firefox</i>
	 * 
	 * @param navegadorUtilizado
	 */
	public void executarTesteNo(NavegadorUtilizado navegadorUtilizado) {
		Log.registrarInformacao("Iniciando caso de teste no " + navegadorUtilizado);
		Navegador.executarTesteNo(navegadorUtilizado);
	}

	/**
	 * Método para verificar a existência de um elemeto dado um localizador do
	 * HTML <br>
	 * <br>
	 * 
	 * @throws Exception
	 */

	public boolean existeElemento(String expressaoLocalizacao) {

		Localizador localizadorDoElemento = new Localizador(null, expressaoLocalizacao);

		try {

			localizarElemento(localizadorDoElemento);
			return true;
		}

		catch (Exception exception) {

			return false;

		}
	}

	/**
	 * Método para verificar a existência de um elemeto dado um localizador do
	 * HTML <br>
	 * <br>
	 * 
	 * @throws Exception
	 */

	public boolean existeElemento(String localizarPor, String expressaoLocalizacao) {
		Localizador localizadorDoElemento = new Localizador(localizarPor, expressaoLocalizacao);

		try {
			Navegador.obterElemento(localizadorDoElemento);
			return true;
		} catch (Exception exception) {
			return false;
		}
	}

	/**
	 * Fechar o navegador.
	 * 
	 * @throws Exception
	 */
	public void fechar() throws Exception {
		Navegador.fechar();
	}

	public String getTituloDaPaginaAtual() {
		return Navegador.getTituloDaPaginaAtual();
	}

	protected String getUrl() throws Exception {
		Log.registrarInformacao("PageObject utilizado: " + this.getClass().getSimpleName());
		return Configuracoes.getUrlConfigurada(this.getClass().getSimpleName());
	}

	public void limparTodosOsCookies() {
		Navegador.limparTodosCookies();
	}

	/**
	 * Método para manipular qualquer os elementos HTML <br>
	 * <br>
	 */
	protected Elemento localizarElemento(Localizador localizadorDoElemento) throws Exception {
		return new Elemento(localizadorDoElemento);
	}

	/**
	 * Método para manipular qualquer os elementos HTML <br>
	 * <br>
	 * Ele utiliza o valor da chave localizar-por-default configurado no arquivo <br>
	 * ConfiguraçõesTeste.properties e a expressão (parâmetro) para localizar o
	 * elemento
	 */
	protected Elemento localizarElemento(String expressaoLocalizacao) throws Exception {
		return new Elemento(expressaoLocalizacao);
	}

	/**
	 * Método para manipular qualquer os elementos HTML <br>
	 * <br>
	 */
	protected Elemento localizarElemento(String localizarPor, String expressaoLocalizacao) throws Exception {
		return new Elemento(localizarPor, expressaoLocalizacao);
	}

	/**
	 * Retorna uma lista de Elementos que possuem esse localizador <br>
	 * <br>
	 * Ele utiliza o valor da chave localizar-por-default configurado no arquivo <br>
	 * ConfiguraçõesTeste.properties e a expressão (parâmetro) para localizar o
	 * elemento
	 */
	protected List<Elemento> localizarElementos(String expressaoLocalizacao) throws Exception {
		Localizador localizador = new Localizador(null, expressaoLocalizacao);
		return Navegador.localizarElementos(localizador);
	}

	/**
	 * Retorna uma lista de Elementos que possuem esse localizador <br>
	 * <br>
	 */
	protected List<Elemento> localizarElementos(String localizarPor, String expressaoLocalizacao) throws Exception {
		Localizador localizador = new Localizador(localizarPor, expressaoLocalizacao);
		return Navegador.localizarElementos(localizador);
	}

	/**
	 * Método para manipular qualquer os elementos HTML <br>
	 * <br>
	 */
	protected Select localizarSelect(Localizador localizadorDoElemento) throws Exception {
		return new Select(localizadorDoElemento);
	}

	/**
	 * Método para manipular qualquer os elementos HTML <br>
	 * <br>
	 * Ele utiliza o valor da chave localizar-por-default configurado no arquivo <br>
	 * ConfiguraçõesTeste.properties e a expressão (parâmetro) para localizar o
	 * elemento
	 */
	protected Select localizarSelect(String expressaoLocalizacao) throws Exception {
		return new Select(expressaoLocalizacao);
	}

	/**
	 * Método para manipular qualquer os elementos HTML <br>
	 * <br>
	 * Ele utiliza o valor da chave localizar-por-default configurado no arquivo <br>
	 * ConfiguraçõesTeste.properties e a expressão (parâmetro) para localizar o
	 * elemento
	 */
	protected Select localizarSelect(String localizarPor, String expressaoLocalizacao) throws Exception {
		return new Select(localizarPor, expressaoLocalizacao);
	}

	/**
	 * Método para manipular qualquer os elementos HTML <br>
	 * <br>
	 */
	protected Select Select(Localizador localizadorDoElemento) throws Exception {
		return new Select(localizadorDoElemento);
	}

	/**
	 * Permite que a localização seja feita dentro do IFrame informado<br>
	 * <br>
	 * 
	 * @throws Exception
	 */
	public void usarIFrame(Elemento iframe) throws Exception {
		if (!iframe.webElement().getTagName().equals("iframe")) {
			throw new Exception("O elemento NÃO é uma IFRAME!");
		}
		Navegador.usarIFrame(iframe);
	}

	/**
	 * Permite que a localização seja feita no conteúdo principal (frame) da
	 * página.<br>
	 * Deve ser utilizado depois de ter navegado para um IFrame (método:
	 * usarIFrame) <br>
	 * 
	 * @throws Exception
	 */
	public void voltarParaFramePrincipal() {
		Navegador.voltarParaFramePrincipal();
	}

}
