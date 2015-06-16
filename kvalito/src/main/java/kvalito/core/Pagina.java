package kvalito.core;

import java.util.List;

import kvalito.componentes.Elemento;
import kvalito.componentes.Select;

public abstract class Pagina {

	/**
	 * Retorna a url atual da página.<br>
	 * 
	 * @return Url atual.
	 */
	public static String getUrlAtual() {
		return Navegador.getUrlAtual();
	}

	private DiretorioDownload diretorioDownload;

	/**
	 * Abre a página com a url configurada no PageObject.<br>
	 * 
	 * @throws Exception
	 */
	public void abrir() throws Exception {
		this.abrirUrl(getUrl());
	}

	/**
	 * Abre a url passada por parâmetro.<br>
	 * 
	 * @param url
	 *            Link a ser aberto.
	 * @throws Exception
	 */
	protected void abrirUrl(String url) throws Exception {
		Navegador.abrirUrl(url);
	}

	/**
	 * Aceita o alerta.<br>
	 * <i>JavaScript alert</i>
	 */
	public void aceitarAlerta() {
		Log.registrarInformacao("Irá aceitar o alerta");
		Navegador.clicarAceitarNoAlerta();
	}

	/**
	 * Aceita alerta de segurança.<br>
	 */
	public void aceitarAlertaSeguranca() {
		Log.registrarInformacao("Irá aceitar a mensagem de segurança");
		Navegador.clicarAceitarNoAlerta();
	}

	/**
	 * Atualiza página.<br>
	 * 
	 * @throws Exception
	 */
	public void atualizarPagina() throws Exception {
		Navegador.atualizarPagina();
	}

	/**
	 * Captura um print da tela e salva na pasta <i>target</i>.<br>
	 * 
	 * @param nomeArquivo
	 *            Nome do arquivo que será criado.
	 * @throws Exception
	 */
	public void capturarPrintTela(String nomeArquivo) throws Exception {
		Navegador.capturarPrintTela(nomeArquivo);
	}

	/**
	 * Retorna o HTML da página.<br>
	 * 
	 * @return HTML da página
	 * @throws Exception
	 */
	public String codigoFonte() throws Exception {
		return Navegador.codigoFonteDaPagina();
	}

	/**
	 * Conta a quantidade de elemetos dado um localizador do HTML.<br>
	 * 
	 * @param expressaoLocalizacao
	 *            Localizador default do elemento.
	 * @throws Exception
	 */
	public int contarElementos(String expressaoLocalizacao) throws Exception {
		Localizador localizador = new Localizador(null, expressaoLocalizacao);
		return Navegador.obterColecao(localizador).size();
	}

	/**
	 * Conta a quantidade de elemetos dado um localizador do HTML.<br>
	 * 
	 * @param localizarPor
	 *            Tipo do localizador.
	 * @param expressaoLocalizacao
	 *            Localizador do elemento.
	 * @throws Exception
	 */
	public int contarElementos(String localizarPor, String expressaoLocalizacao) throws Exception {
		Localizador localizador = new Localizador(localizarPor, expressaoLocalizacao);
		return Navegador.obterColecao(localizador).size();
	}

	/**
	 * Retorna o diretório de download.<br>
	 * 
	 * @return Diretório de download.
	 */
	public DiretorioDownload diretorioDownload() {
		if (diretorioDownload == null) {
			diretorioDownload = new DiretorioDownload();
		}
		return diretorioDownload;
	}

	/**
	 * Pausa a execução do teste em X milissegundos, onde X é o valor passado no
	 * parâmetro.<br>
	 * 
	 * @param tempoEsperar
	 *            Tempo que se deseja esperar.
	 * @throws InterruptedException
	 */
	public void esperarCarregamentoPor(int tempoEsperar) throws InterruptedException {
		Thread.sleep(tempoEsperar);
	}

	/**
	 * Executa scroll vertical na página para o número de pixels informado.<br>
	 * 
	 * @param numeroPixels
	 *            Número de pixels a serem passados.
	 */
	public void executarScroolVertical(int numeroPixels) {
		Navegador.executarScroolY(numeroPixels);
	}

	/**
	 * Define qual navegador será utilizado para executar os testes.<br>
	 * <i>Obs.: O default é Firefox</i>
	 * 
	 * @param navegadorUtilizado
	 */
	public void executarTesteNo(NavegadorUtilizado navegadorUtilizado) {
		Log.registrarInformacao("Iniciando caso de teste no " + navegadorUtilizado);
		Navegador.executarTesteNo(navegadorUtilizado);
	}

	/**
	 * Verifica a existência de um elemeto dado um localizador do HTML.<br>
	 * 
	 * @param expressaoLocalizacao
	 *            Localizador do elemento.
	 * @return true Se o elemento existir.
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
	 * Verifica a existência de um elemeto dado um localizador do HTML.<br>
	 * 
	 * @param localizarPor
	 *            Tipo do localizador.
	 * @param expressaoLocalizacao
	 *            Localizador do elemento.
	 * @return true Se o elemento existir.
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
	 * Fecha o navegador.<br>
	 * 
	 * @throws Exception
	 */
	public void fechar() throws Exception {
		Navegador.fechar();
	}

	/**
	 * Retorna o titulo da página atual.<br>
	 * 
	 * @return Titulo da página.
	 */
	public String getTituloDaPaginaAtual() {
		return Navegador.getTituloDaPaginaAtual();
	}

	/**
	 * Retorna a Url do Page Object.<br>
	 * 
	 * @return Url do PageObject
	 * @throws Exception
	 */
	protected String getUrl() throws Exception {
		Log.registrarInformacao("PageObject utilizado: " + this.getClass().getSimpleName());
		return Configuracoes.getUrlConfigurada(this.getClass().getSimpleName());
	}
	
	/**
	 * Inclui cookie nos cookies da página.<br>
	 */
	
	public void injetarCookie(String nome, String valor){
		Navegador.injetarCookie(nome, valor);
	}

	/**
	 * Limpa os Cookies do navegador.<br>
	 */
	public void limparTodosOsCookies() {
		Navegador.limparTodosCookies();
	}
	

	/**
	 * Localiza o elemento HTML para manipulação.<br>
	 * 
	 * @param localizadorDoElemento
	 *            Localizador do elemento.
	 * @return Novo elemento.
	 * @throws Exception
	 */
	protected Elemento localizarElemento(Localizador localizadorDoElemento) throws Exception {
		return new Elemento(localizadorDoElemento);
	}

	/**
	 * Localiza o elemento HTML para manipulação.<br>
	 * Ele utiliza o valor da chave localizar-por-default configurado no arquivo <br>
	 * ConfiguraçõesTeste.properties e a expressão (parâmetro) para localizar o
	 * elemento
	 * 
	 * @param expressaoLocalizacao
	 *            Expressão de localização do elemento.
	 * @return Novo elemento.
	 */
	protected Elemento localizarElemento(String expressaoLocalizacao) throws Exception {
		return new Elemento(expressaoLocalizacao);
	}

	/**
	 * Localiza o elemento HTML para manipulação.<br>
	 * Ele utiliza o valor da chave localizar-por-default configurado no arquivo <br>
	 * ConfiguraçõesTeste.properties e a expressão (parâmetro) para localizar o
	 * elemento
	 * 
	 * @param localizarPor
	 *            Tipo do localizador.
	 * @param expressaoLocalizacao
	 *            Expressão de localização do elemento.
	 * @return Novo elemento.
	 */
	protected Elemento localizarElemento(String localizarPor, String expressaoLocalizacao) throws Exception {
		return new Elemento(localizarPor, expressaoLocalizacao);
	}

	/**
	 * Retorna uma lista de Elementos que possuem esse localizador.<br>
	 * Ele utiliza o valor da chave localizar-por-default configurado no arquivo <br>
	 * ConfiguraçõesTeste.properties e a expressão (parâmetro) para localizar o
	 * elemento.<br>
	 * 
	 * @param expressaoLocalizacao
	 *            Expressão de localização do elemento.
	 * @return Lista de elementos.
	 */
	protected List<Elemento> localizarElementos(String expressaoLocalizacao) throws Exception {
		Localizador localizador = new Localizador(null, expressaoLocalizacao);
		return Navegador.localizarElementos(localizador);
	}

	/**
	 * Retorna uma lista de Elementos que possuem esse localizador.<br>
	 * 
	 * @param localizarPor
	 *            Tipo de localizador do elemento.
	 * @param expressaoLocalizacao
	 *            Expressão de localização do elemento.
	 * @return Lista de elementos.
	 */
	protected List<Elemento> localizarElementos(String localizarPor, String expressaoLocalizacao) throws Exception {
		Localizador localizador = new Localizador(localizarPor, expressaoLocalizacao);
		return Navegador.localizarElementos(localizador);
	}

	/**
	 * Localiza o elemento Select para manipulação.<br>
	 * Ele utiliza o valor da chave localizar-por-default configurado no arquivo <br>
	 * ConfiguraçõesTeste.properties e a expressão (parâmetro) para localizar o
	 * elemento.<br>
	 * 
	 * @param localizadorDoElemento
	 *            Localizador do elemento.
	 * @return Novo elemento Select.
	 */
	protected Select localizarSelect(Localizador localizadorDoElemento) throws Exception {
		return new Select(localizadorDoElemento);
	}

	/**
	 * Localiza o elemento Select para manipulação.<br>
	 * Ele utiliza o valor da chave localizar-por-default configurado no arquivo <br>
	 * ConfiguraçõesTeste.properties e a expressão (parâmetro) para localizar o
	 * elemento.<br>
	 * 
	 * @param expressaoLocalizacao
	 *            Expressão de localização do elemento.
	 * @return Novo elemento Select.
	 */
	protected Select localizarSelect(String expressaoLocalizacao) throws Exception {
		return new Select(expressaoLocalizacao);
	}

	/**
	 * Localiza o elemento Select para manipulação.<br>
	 * Ele utiliza o valor da chave localizar-por-default configurado no arquivo <br>
	 * ConfiguraçõesTeste.properties e a expressão (parâmetro) para localizar o
	 * elemento.<br>
	 * 
	 * @param localizarPor
	 *            Tipo de Localizador.
	 * @param expressaoLocalizacao
	 *            Expressão de localização do elemento.
	 * @return Novo elemento Select.
	 */
	protected Select localizarSelect(String localizarPor, String expressaoLocalizacao) throws Exception {
		return new Select(localizarPor, expressaoLocalizacao);
	}
	
	/**
	 * Retorna o texto exibido em um alerta.<br>
	 *
	 * @return Mensagem alerta
	 * @throws Exception
	 */
	
	public String obterTextoAlerta() throws Exception{
		return Navegador.obterTextoAlerta();
	}
	
	/**
	 * Retorna os dados de um cookie.<br>
	 * 
	 * @param cookieId
	 * 			ID do cookie
	 * @return Dados do cookie
	 * @throws Exception
	 */
	
	public String valorCookie(String cookieId) throws Exception{
		return Navegador.valorCookie(cookieId);
	}

	/**
	 * Localiza o elemento Select para manipulação.<br>
	 * Ele utiliza o valor da chave localizar-por-default configurado no arquivo <br>
	 * ConfiguraçõesTeste.properties e a expressão (parâmetro) para localizar o
	 * elemento.<br>
	 * 
	 * @param localizadorDoElemento
	 *            Localizador do elemento.
	 * @return Novo elemento Select.
	 */
	
	protected Select Select(Localizador localizadorDoElemento) throws Exception {
		return new Select(localizadorDoElemento);
	}

	/**
	 * Permite que a localização seja feita dentro do IFrame informado.<br>
	 * 
	 * @param iframe
	 *            Elemento iframe.
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
	 * Deve ser utilizado depois de ter navegado para um IFrame
	 * (método:usarIFrame) <br>
	 * 
	 * @throws Exception
	 */
	public void voltarParaFramePrincipal() {
		Navegador.voltarParaFramePrincipal();
	}

}
