package kvalito.core;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import kvalito.componentes.Elemento;
import kvalito.core.webdrivers.FabricaChromeDriver;
import kvalito.core.webdrivers.FabricaFirefoxDriver;
import kvalito.core.webdrivers.FabricaGhostDriver;
import kvalito.core.webdrivers.FabricaHtmlUnitDriver;
import kvalito.utilitarios.UtilitarioTexto;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Navegador {

	private static WebDriver driver;
	private static NavegadorUtilizado navegadorUtilizado;
	private static int maximoTentativasTratarElementoVelho;

	public static void abrirUrl(String url) throws Exception {
		Log.registrarInformacao("Abrindo URL " + url);
		long tempoInicial = System.currentTimeMillis();
		getDriver().get(url);
		long tempoFinal = System.currentTimeMillis() - tempoInicial;
		Log.registrarInformacao(String.format("Tempo de carregamento da url [Tempo: %sms | Url: %s]", tempoFinal, url));
	}

	public static void aguardarAteQueAtributoEstejaPreenchido(
			final WebElement elemento, final String atributo) throws Exception {
		String tempoQueIraAguardar = Configuracoes
				.getConfiguracaoPrincipal("tempo-esperava-elemento-visivel");
		Log.registrarInformacao(String
				.format("Aguandando que o atributo [%s] do elemento [%s] esteja preenchido [%s segundos]",
						atributo, elemento.getTagName(), tempoQueIraAguardar));
		WebDriverWait wait = new WebDriverWait(driver,
				Long.parseLong(tempoQueIraAguardar));

		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				String valor = elemento.getAttribute(atributo);
				Log.registrarDebug(String.format(
						"Valor do atributo [%s] é [%s]", atributo, valor));
				return valor != null && valor != "";
			}
		});
	}

	public static void aguardarAteQueEstejaInvisivel(final WebElement elemento)
			throws Exception {
		String tempoQueIraAguardar = Configuracoes
				.getConfiguracaoPrincipal("tempo-esperava-elemento-invisivel");
		Log.registrarInformacao(String.format(
				"Aguandando que o elemento esteja invisível [%s segundos]",
				tempoQueIraAguardar));
		WebDriverWait wait = new WebDriverWait(driver,
				Long.parseLong(tempoQueIraAguardar));
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				Log.registrarDebug(String.format(
						"O elemento [%s] ainda está visível [%s]",
						elemento.getTagName(), elemento.isDisplayed()));
				return !elemento.isDisplayed();
			}
		});

	}

	public static void aguardarAteQueEstejaVisivel(final WebElement elemento)
			throws Exception {
		String tempoQueIraAguardar = Configuracoes
				.getConfiguracaoPrincipal("tempo-esperava-elemento-visivel");
		Log.registrarInformacao(String.format(
				"Aguandando que o elemento esteja visível [%s segundos]",
				tempoQueIraAguardar));
		WebDriverWait wait = new WebDriverWait(driver,
				Long.parseLong(tempoQueIraAguardar));

		wait.until(ExpectedConditions.visibilityOf(elemento));

		/*
		 * wait.until(new ExpectedCondition<Boolean>() { public Boolean
		 * apply(WebDriver driver) { Log.registrarDebug(String.format(
		 * "O elemento [%s] ainda está visível [%s]", elemento.getTagName(),
		 * elemento.isDisplayed())); return elemento.isDisplayed(); } });
		 */
	}

	public static void arrastarElementoPara(WebElement elemento,
			WebElement destino) {
		Actions acoes = new Actions(driver);
		acoes.dragAndDrop(elemento, destino).build().perform();
	}

	public static void atualizarPagina() throws Exception {
		Log.registrarInformacao("Atualizando a Página");
		getDriver().navigate().refresh();
	}

	public static void capturarPrintTela(String nomeArquivo) throws Exception {
		try {
			String nomeCompletoArquivo = "target\\" + nomeArquivo + ".jpg";
			Log.registrarInformacao(String.format(
					"Granvando print de tela [%s]", nomeCompletoArquivo));
			File file = ((TakesScreenshot) getDriver())
					.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(file, new File(nomeCompletoArquivo));
		} catch (Exception ex) {
			Log.registrarAlerta("Erro ao capturar um print da tela");
			Log.registrarErro(ex);
			throw ex;
		}
	}

	public static void clicarAceitarNoAlerta() {
		driver.switchTo().alert().accept();
	}

	public static void clicarDuasVezes(WebElement elemento) {
		Actions acoes = new Actions(driver);
		acoes.doubleClick(elemento);
		acoes.perform();
	}

	public static String codigoFonteDaPagina() throws Exception {
		return getDriver().getPageSource();
	}

	public static void esperarPor(long tempoEsperarRecarregamentoElemento) {
		try {
			Thread.sleep(tempoEsperarRecarregamentoElemento);
		} catch (InterruptedException e) {
			String mensagem = String
					.format("Houve um erro ao executar uma espera [%s]. O erro foi ignorado ",
							tempoEsperarRecarregamentoElemento);
			Log.registrarInformacao(mensagem);
		}
	}

	public static void executarJavaScript(String comandoJavaScript) {
		Log.registrarInformacao(String.format(
				"Executando comando javacript [%s].", comandoJavaScript));
		JavascriptExecutor executorJs = (JavascriptExecutor) driver;
		executorJs.executeScript(comandoJavaScript);
	}

	private static void executarJavaScript(String comandoJavaScript,
			WebElement elemento) {
		Log.registrarInformacao(String.format(
				"Executando comando javacript [%s] no elemento [%s]",
				comandoJavaScript, elemento.getTagName()));
		JavascriptExecutor executorJs = (JavascriptExecutor) driver;
		executorJs.executeScript(comandoJavaScript, elemento);
	}

	public static void executarScrool(int x, int y) {
		String comandoJs = String.format("window.scrollBy(%s,%s)", x, y);
		Navegador.executarJavaScript(comandoJs);
	}

	public static void executarScroolX(int x) {
		String comandoJs = String.format("window.scrollBy(%s,0)", x);
		Navegador.executarJavaScript(comandoJs);
	}

	public static void executarScroolY(int y) {
		String comandoJs = String.format("window.scrollBy(0,%s)", y);
		Navegador.executarJavaScript(comandoJs);
	}

	/**
	 * Define qual navegador será utilizado para executar os testes. </i>Obs.: O
	 * default é Firefox</i>
	 * 
	 * @param navegadorUtilizado
	 */
	public static void executarTesteNo(NavegadorUtilizado navegador) {
		navegadorUtilizado = navegador;
	}

	public static void fechar() throws Exception {
		Log.registrarInformacao("Fechando o " + navegadorUtilizado.toString());
		getDriver().quit();
		driver = null;
	}

	public static WebDriver getDriver() throws Exception {
		if (driver == null) {
			driver = iniciarNavegador();
		}
		return driver;
	}

	public static String getTituloDaPaginaAtual() {
		return driver.getTitle();
	}

	public static String getUrlAtual() {
		return driver.getCurrentUrl();
	}

	public static void gravarCodigoFontePagina(String nomeArquivo)
			throws Exception {
		try {
			String nomeCompletoArquivo = "target\\" + nomeArquivo + ".html";
			Log.registrarInformacao(String
					.format("Salvando código fonte da página [%s]",
							nomeCompletoArquivo));
			String codigoPagina = codigoFonteDaPagina();
			UtilitarioTexto.escreverArquivo(nomeCompletoArquivo, codigoPagina);
		} catch (Exception ex) {
			Log.registrarAlerta("Erro ao salvar código fonte da página");
			Log.registrarErro(ex);
			throw ex;
		}
	}

	private static WebDriver iniciarNavegador() throws Exception {
		WebDriver driverTemporario = null;
		int tempodDeEspera = Integer.parseInt(Configuracoes
				.getConfiguracaoPrincipal("tempo-de-espera-elemeto-implicito"));
		if (navegadorUtilizado == null) {
			String chaveNavegador = Configuracoes
					.getConfiguracaoPrincipal("navegador-default");
			navegadorUtilizado = NavegadorUtilizado.valueOf(chaveNavegador);
		}
		try {
			switch (navegadorUtilizado) {
			case FIREFOX:
				driverTemporario = FabricaFirefoxDriver.instanciar();
				break;
			case HTMLUNIT:
				driverTemporario = FabricaHtmlUnitDriver.instanciar();
				break;
			case GHOST_DRIVER:
				driverTemporario = FabricaGhostDriver.instanciar();
				break;
			case CHROME:
				driverTemporario = FabricaChromeDriver.instanciar();
				break;
			default:
				throw new Exception("A implementação para o navegador "
						+ navegadorUtilizado.toString() + " não foi feita");
			}
			driverTemporario.manage().deleteAllCookies();
			driverTemporario.manage().timeouts()
					.implicitlyWait(tempodDeEspera, TimeUnit.SECONDS);
		} catch (Exception ex) {
			Log.registrarInformacao("Houve um erro na criação do driver.");
			Log.registrarErro(ex);
		}

		return driverTemporario;
	}

	public static void injetarDriver(WebDriver driverInjetado) {
		driver = driverInjetado;
	}

	public static void limparTodosCookies() {
		driver.manage().deleteAllCookies();
	}

	public static List<Elemento> localizarElementos(Localizador localizador)
			throws Exception {
		List<WebElement> webElementsLocalizados = obterColecao(localizador);
		List<Elemento> listaDeElementos = new ArrayList<Elemento>();
		for (WebElement webElement : webElementsLocalizados) {
			listaDeElementos.add(new Elemento(webElement));
		}
		return listaDeElementos;
	}

	public static void manipularValorAtributo(WebElement elemento,
			String nomeAtriburo, String novoValor) {
		String comando = String.format("arguments[0].setAttribute('%s', '%s')",
				nomeAtriburo, novoValor);
		executarJavaScript(comando, elemento);
	}

	public static NavegadorUtilizado navegandoNo() {
		return navegadorUtilizado;
	}

	public static List<WebElement> obterColecao(Localizador localizador)
			throws Exception {

		List<WebElement> elementosLocalizados;

		switch (localizador.getLocalizarPor()) {
		case NOME:

		case NAME:
			elementosLocalizados = driver.findElements(By.name(localizador
					.getExpressaoElemento()));
			break;
		case ID:
			elementosLocalizados = driver.findElements(By.id(localizador
					.getExpressaoElemento()));
			break;
		case XPATH:
			elementosLocalizados = driver.findElements(By.xpath(localizador
					.getExpressaoElemento()));
			break;
		case TAGNAME:
			elementosLocalizados = driver.findElements(By.tagName(localizador
					.getExpressaoElemento()));
			break;
		case CSSCLASS:
			elementosLocalizados = driver.findElements(By.className(localizador
					.getExpressaoElemento()));
			break;
		case CSSSELECTOR:
			elementosLocalizados = driver.findElements(By
					.cssSelector(localizador.getExpressaoElemento()));
			break;
		default:
			String mensagem = "O localizador ["
					+ localizador
					+ "] do elemento não é válido! Não será possível localizá-lo.";
			throw new Exception(mensagem);
		}

		return elementosLocalizados;
	}

	private static List<WebElement> obterColecaoPor(By localizador) {
		return driver.findElements(localizador);
	}

	public static List<WebElement> obterColecaoPorXpath(String xpathDaColecao) {
		return obterColecaoPor(By.xpath(xpathDaColecao));
	}

	public static WebElement obterElemento(Localizador localizador)
			throws Exception {
		Log.registrarInformacao("Localizando o elemento [" + localizador + "]");
		WebElement elementoLocalizado = null;

		switch (localizador.getLocalizarPor()) {
		case NOME:
		case NAME:
			elementoLocalizado = driver.findElement(By.name(localizador
					.getExpressaoElemento()));
			break;
		case ID:
			elementoLocalizado = driver.findElement(By.id(localizador
					.getExpressaoElemento()));
			break;
		case XPATH:
			elementoLocalizado = driver.findElement(By.xpath(localizador
					.getExpressaoElemento()));
			break;
		case TAGNAME:
			elementoLocalizado = driver.findElement(By.tagName(localizador
					.getExpressaoElemento()));
			break;
		case CSSCLASS:
			elementoLocalizado = driver.findElement(By.className(localizador
					.getExpressaoElemento()));
			break;
		case CSSSELECTOR:
			elementoLocalizado = driver.findElement(By.cssSelector(localizador
					.getExpressaoElemento()));
			break;
		default:
			String mensagem = "O localizador ["
					+ localizador
					+ "] do elemento não é válido! Não será possível localizá-lo. Utilize 'nome', 'id' ou 'xpath' como localizador.";
			throw new Exception(mensagem);
		}

		return elementoLocalizado;
	}

	private static WebElement obterElementoPor(By localizador) {
		return driver.findElement(localizador);
	}

	public static WebElement obterElementoPorId(String idDoElemento) {
		return obterElementoPor(By.id(idDoElemento));
	}

	public static WebElement obterElementoPorNome(String nomeDoElemento) {
		return obterElementoPor(By.name(nomeDoElemento));
	}

	public static WebElement obterElementoPorXpath(String xpathDoElemento) {
		return obterElementoPor(By.xpath(xpathDoElemento));
	}

	public static void passarMouseSobre(WebElement elemento) {
		Actions acoes = new Actions(driver);
		acoes.moveToElement(elemento).build().perform();
	}

	public static void preencherJanelaDialogoCaminhoArquivoCom(
			final String caminhoArquivo) throws AWTException,
			InterruptedException {
		driver.switchTo().window(driver.getWindowHandle());

		Robot robo = new Robot();

		for (int i = 0; i < caminhoArquivo.length(); i++) {
			int codigoChar = (int) caminhoArquivo.toUpperCase().charAt(i);
			Log.registrarDebug(String
					.format("Preenchendo com texto utilizando o Robot [Char: %s - Cód: %s]",
							caminhoArquivo.charAt(i), codigoChar));

			if (codigoChar == 58) {
				robo.keyPress(java.awt.event.KeyEvent.VK_SHIFT);
				robo.keyPress(java.awt.event.KeyEvent.VK_SEMICOLON);
				robo.keyRelease(java.awt.event.KeyEvent.VK_SEMICOLON);
				robo.keyRelease(java.awt.event.KeyEvent.VK_SHIFT);
			} else {
				robo.keyPress(codigoChar);
				robo.keyRelease(codigoChar);
			}
		}

		robo.keyPress(java.awt.event.KeyEvent.VK_ENTER);
		robo.keyRelease(java.awt.event.KeyEvent.VK_ENTER);
	}

	public static void preencherSimulandoDigitacao(String texto)
			throws AWTException {
		Robot robo = new Robot();
		Log.registrarDebug(String.format(
				"Preenchendo com texto utilizando o Robot [%s]", texto));
		for (int i = 0; i < texto.length(); i++) {
			robo.keyPress(KeyEvent.VK_ALT);
			int codigoChar = texto.charAt(i);
			for (int j = 3; j >= 0; --j) {
				int numpad_kc = codigoChar / (int) (Math.pow(10, j)) % 10
						+ KeyEvent.VK_NUMPAD0;

				robo.keyPress(numpad_kc);
				robo.keyRelease(numpad_kc);
			}

			robo.keyRelease(KeyEvent.VK_ALT);
		}
	}

	/**
	 * Navega para dentro de um iframe.
	 * 
	 * @param iframe
	 *            Elemento iframe.
	 */
	public static void usarIFrame(Elemento iframe) {
		Log.registrarInformacao("Navegando para dentro de um IFrame");
		driver.switchTo().frame(iframe.webElement());
	}

	/**
	 * Volta para o frame principal.
	 */
	public static void voltarParaFramePrincipal() {
		Log.registrarInformacao("Voltando a navegação para Frame principal");
		driver.switchTo().defaultContent();
	}

	public static String valorCookie(String cookieId) {
		Cookie cookie = driver.manage().getCookieNamed(cookieId);
		Log.registrarInformacao("Obtendo cookie["+cookie.getName()+"] com valor["+cookie.getValue()+"]");
		return cookie.getValue();
	}

	public static String obterTextoAlerta() {
		Log.registrarInformacao("Obtendo o texto exibido no alerta");
		return driver.switchTo().alert().getText();
	}
	
	public static void injetarCookie(String nome, String valor){
		Cookie cookie = new Cookie(nome, valor);
		driver.manage().addCookie(cookie);
	}

}
