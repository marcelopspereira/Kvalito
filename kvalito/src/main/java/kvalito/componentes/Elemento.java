package kvalito.componentes;

import java.util.Random;

import kvalito.core.AcoesTeclado;
import kvalito.core.Localizador;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import de.svenjacobs.loremipsum.LoremIpsum;

public class Elemento extends ElementoCore {
	private AcoesTeclado teclado;

	public Elemento() throws Exception { }
	
	public Elemento(Localizador localizadorDoElemento) throws Exception {
		super.carregarElemento(localizadorDoElemento);
	}

	public Elemento(String localizarPor, String expressaoLocalizacao) throws Exception {
		Localizador localizador = new Localizador(localizarPor, expressaoLocalizacao);
		super.carregarElemento(localizador);
	}

	public Elemento(String expressaoLocalizacao) throws Exception {
		Localizador localizador = new Localizador(expressaoLocalizacao);
		super.carregarElemento(localizador);
	}
	
	public Elemento(WebElement webElement) {
		this.elemento = webElement;
	}
	
	public WebElement webElement() {
		return elemento;
	}
	
	public AcoesTeclado acoesTeclado() {
		if (this.teclado == null) {
			this.teclado = new AcoesTeclado(this.elemento);
		}
		return this.teclado;
	}
	
	public boolean estaVisivel() throws Exception {
		return elemento.isDisplayed();
	}
		
	public boolean estaHabilitado() throws Exception {
		return elemento.isEnabled();
	}
	
	public boolean estaSelecionado() {
		return elemento.isSelected();
	}
	
	public void selecionar() {
		if (!this.estaSelecionado()){
			elemento.click();
		}	
	}
	
	/**
	 * Obtém o valor de um atributo do elemento HTML <br>
	 */
	public String valorAtributo(String nomeDoAtributo) {
		return elemento.getAttribute(nomeDoAtributo);
	}
	
	/**
	 * Obtém o valor (atributo VALUE) de um elemento HTML <br>
	 */
	public String valor() {
		return valorAtributo("value");
	}
	
	public String getConteudo() {
		return elemento.getText();
	}
	
	public String conteudo() {
		return elemento.getText();
	}
	
	public boolean conteudoContem(String valorVerificarExistencia) {
		return elemento.getText().contains(valorVerificarExistencia);
	}
	
	public boolean estaPreenchido() {
		return possuiConteudo();
	}
		
	public void preencherCom(String texto) {
		this.elemento.sendKeys(texto);
	}
	
	public String preencherComTextoAleatorio(int quantidadePalavras) {
		LoremIpsum geradorLoremIpsum = new LoremIpsum();
		String texto = geradorLoremIpsum.getWords(quantidadePalavras);
		this.preencherCom(texto);
		return texto;
	}
	
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
	
	public String preencherComNumerosAleatorios(int quantidadeNumeros) {
		Random rand = new Random();
		int min = 0;
		int max = 9;
		String numeroGerado = "";
		for (int i = 0; i < quantidadeNumeros; i++) {
			numeroGerado += rand.nextInt((max  - min) + 1) + min;
		}
		this.preencherCom(numeroGerado);
		return numeroGerado.toString();
	}
		
	public void limpar() {
		this.elemento.clear();
	}
	
	@Override
	protected String getNomeTag() {
		return "generico";
	}
	
	/**
	 * Retorna a posição (x, y) do elemento na página. 
	 * @return
	 */
	public Point posicao() {		
		return elemento.getLocation();
	}

	
	/**
	 * Obtém o valor (atributo VALUE) do CSS de um elemento <br>
	 */
	public String valorAtributoCss(String nomeDoAtributo) {
		return elemento.getCssValue(nomeDoAtributo);
	}
	

	
}
