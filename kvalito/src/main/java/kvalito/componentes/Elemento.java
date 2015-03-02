package kvalito.componentes;

import java.util.Random;

import kvalito.core.AcoesTeclado;
import kvalito.core.Localizador;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import de.svenjacobs.loremipsum.LoremIpsum;

public class Elemento extends ElementoCore {
	private AcoesTeclado teclado;

	public Elemento() throws Exception {
	}

	public Elemento(Localizador localizadorDoElemento) throws Exception {
		super.carregarElemento(localizadorDoElemento);
	}

	public Elemento(String expressaoLocalizacao) throws Exception {
		Localizador localizador = new Localizador(expressaoLocalizacao);
		super.carregarElemento(localizador);
	}

	public Elemento(String localizarPor, String expressaoLocalizacao) throws Exception {
		Localizador localizador = new Localizador(localizarPor, expressaoLocalizacao);
		super.carregarElemento(localizador);
	}

	public Elemento(WebElement webElement) {
		this.elemento = webElement;
	}

	/**
	 * Possibilita a execução de ações do teclado.
	 * 
	 * @return
	 */
	public AcoesTeclado acoesTeclado() {
		if (this.teclado == null) {
			this.teclado = new AcoesTeclado(this.elemento);
		}
		return this.teclado;
	}

	/**
	 * Retorna o texto de um elemento HTML. <br>
	 * 
	 */
	public String conteudo() {
		return elemento.getText();
	}

	/**
	 * Verifica a existencia do texto no conteúdo do elemento. <br>
	 * 
	 * @param valorVerificarExistencia
	 * 
	 */
	public boolean conteudoContem(String valorVerificarExistencia) {
		return elemento.getText().contains(valorVerificarExistencia);
	}

	/**
	 * Verifica se o elemento está habilitado. <br>
	 * 
	 */
	public boolean estaHabilitado() throws Exception {
		return elemento.isEnabled();
	}

	/**
	 * Verifica se o elemento está preenchido. <br>
	 * 
	 */
	public boolean estaPreenchido() {
		return possuiConteudo();
	}

	/**
	 * Verifica se o elemento(input/checkbox/radio button) está selecionado. <br>
	 * 
	 */
	public boolean estaSelecionado() {
		return elemento.isSelected();
	}

	/**
	 * Verifica se o elemento está visível. <br>
	 * 
	 */

	public boolean estaVisivel() throws Exception {
		return elemento.isDisplayed();
	}

	/**
	 * Retorna o texto de um elemento HTML <br>
	 * 
	 */
	public String getConteudo() {
		return elemento.getText();
	}

	@Override
	protected String getNomeTag() {
		return "generico";
	}

	/**
	 * Limpa o conteúdo de um input. <br>
	 * 
	 */
	public void limpar() {
		this.elemento.clear();
	}

	/**
	 * Retorna a posição (x, y) do elemento na página. <br>
	 * 
	 */
	public Point posicao() {
		return elemento.getLocation();
	}

	/**
	 * Preenche o input com o texto passado por parâmetro. <br>
	 * 
	 * @param texto
	 * 
	 */
	public void preencherCom(String texto) {
		this.elemento.sendKeys(texto);
	}

	/**
	 * Preenche o input com um caracteres alfanuméricos aleatórios. <br>
	 * 
	 * @param quantidadeCaracteres
	 * 
	 */

	public String preencherComCaracteresAleatorios(int quantidadeCaracteres) {
		String caracteres = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
		Random aleatorio = new Random();
		String texto = "";
		for (int i = 0; i < quantidadeCaracteres; i++) {
			texto += caracteres.charAt(aleatorio.nextInt(caracteres.length()));
		}
		this.preencherCom(texto);
		return texto;
	}

	/**
	 * Preenche o input com um números aleatórios. <br>
	 * 
	 * @param quantidadeNumeros
	 */

	public String preencherComNumerosAleatorios(int quantidadeNumeros) {
		Random rand = new Random();
		int min = 0;
		int max = 9;
		String numeroGerado = "";
		for (int i = 0; i < quantidadeNumeros; i++) {
			numeroGerado += rand.nextInt((max - min) + 1) + min;
		}
		this.preencherCom(numeroGerado);
		return numeroGerado.toString();
	}

	/**
	 * Preenche o input com um texto aleatório com a quantidade de palavras
	 * passadas por parâmetro. <br>
	 * 
	 * @param quantidadePalavras
	 * 
	 */
	public String preencherComTextoAleatorio(int quantidadePalavras) {
		LoremIpsum geradorLoremIpsum = new LoremIpsum();
		String texto = geradorLoremIpsum.getWords(quantidadePalavras);
		this.preencherCom(texto);
		return texto;
	}

	/**
	 * Seleciona o elemento(input/checkbox/radio button). <br>
	 * 
	 */
	public void selecionar() {
		if (!this.estaSelecionado()) {
			elemento.click();
		}
	}

	/**
	 * Retorna o valor (atributo VALUE) de um elemento HTML <br>
	 * 
	 */
	public String valor() {
		return valorAtributo("value");
	}

	/**
	 * Retorna o valor de um atributo do elemento HTML <br>
	 * 
	 * @param nomeDoAtributo
	 */
	public String valorAtributo(String nomeDoAtributo) {
		return elemento.getAttribute(nomeDoAtributo);
	}

	/**
	 * Retorna o valor (atributo VALUE) do CSS de um elemento <br>
	 */
	public String valorAtributoCss(String nomeDoAtributo) {
		return elemento.getCssValue(nomeDoAtributo);
	}

	/**
	 * Retorna o webElement equivalente ao elemento. <br>
	 * 
	 * @return WebElement.
	 */
	public WebElement webElement() {
		return elemento;
	}

}
