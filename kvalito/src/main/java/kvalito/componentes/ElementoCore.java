package kvalito.componentes;

import java.awt.AWTException;

import kvalito.core.Localizador;
import kvalito.core.Log;
import kvalito.core.Navegador;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

public abstract class ElementoCore {
	private static final int MAXIMO_TENTATIVAS = 10;
	protected static final long TEMPO_ESPERAR_RECARREGAMENTO_ELEMENTO = 500;
	protected WebElement elemento;
	protected org.openqa.selenium.support.ui.Select select;
	private Localizador localizador;

	/**
	 * Aguarda até que o elemento esteja preenchido. <br>
	 * <i>Tempo configurado na chave "tempo-esperava-elemento-visivel" </i>
	 * 
	 */
	public boolean aguardarAteQueAtributoEstejaPreenchido(String atributo) {
		try {
			Navegador.aguardarAteQueAtributoEstejaPreenchido(elemento, atributo);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Aguarda até que o elemento esteja invisível na tela. <br>
	 * <i>Tempo configurado na chave "tempo-esperava-elemento-invisivel" </i>
	 * 
	 */
	public boolean aguardarAteQueEstejaInvisivel() {
		try {
			Navegador.aguardarAteQueEstejaInvisivel(elemento);
			return !elemento.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Aguarda até que o elemento esteja visível na tela. <br>
	 * <i>Tempo configurado na chave "tempo-esperava-elemento-visivel" </i>
	 * 
	 */
	public boolean aguardarAteQueEstejaVisivel() {
		try {
			Navegador.aguardarAteQueEstejaVisivel(elemento);
			return elemento.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Altera um atributo do elemento no código HTML.
	 * 
	 * @param nomeAtriburo
	 *            Atributo a ser modificado.
	 * @param novoValor
	 *            Novo valor para o atributo.
	 */
	public void alterarValorAtributo(String nomeAtriburo, String novoValor) {
		Navegador.manipularValorAtributo(elemento, nomeAtriburo, novoValor);
	}

	/**
	 * Arrasta o elemento para outro elemento de destino.<br>
	 * 
	 * @param destino
	 */
	public void arrastarElementoPara(Elemento destino) {
		Navegador.arrastarElementoPara(elemento, destino.webElement());
	}

	public void carregarElemento(Localizador localizador) throws Exception {
		int quantidadeVezesObteveElemento = 0;
		this.localizador = localizador;

		while (quantidadeVezesObteveElemento <= MAXIMO_TENTATIVAS && elemento == null) {
			try {
				elemento = Navegador.obterElemento(localizador);
				executarComandosQueGeramStaleElement();
				executarComandosQueGeramStaleElementParaUmSelect();
				quantidadeVezesObteveElemento++;

			} catch (StaleElementReferenceException ex) {
				Log.registrarInformacao("O elemento pode estar sendo atualizado por um Refresh ou JavaScript. Ele será recarregado. Tentativa: "
						+ quantidadeVezesObteveElemento);
				Navegador.esperarPor(TEMPO_ESPERAR_RECARREGAMENTO_ELEMENTO);
			} catch (Exception ex) {
				String mensagemErro = String.format("Houve um erro ao carregar o elemento [%s]", localizador.toString());
				Log.registrarInformacao(mensagemErro);
				Log.registrarErro(ex);
				throw ex;
			}
		}
	}

	/**
	 * Executa um clique no elemento. <br>
	 */
	public void clicar() throws Exception {
		Log.registrarInformacao(String.format("O elemento [%s] será clicado.", elemento.getTagName()));

		elemento.click();

		Log.registrarInformacao(String.format(" - Url atual: [%s]", Navegador.getUrlAtual()));
	}

	/**
	 * Executa um duplo clique no elemento.<br>
	 */
	public void clicarDuasVezes() {
		Log.registrarInformacao(String.format("O elemento [%s] será clicado duas vezes.", elemento.getTagName()));

		Navegador.clicarDuasVezes(elemento);

		Log.registrarInformacao(String.format(" - Url atual: [%s]", Navegador.getUrlAtual()));
	}

	private void executarComandosQueGeramStaleElement() {
		if (elemento != null) {
			elemento.getText();
			elemento.getTagName();
		}
	}

	private void executarComandosQueGeramStaleElementParaUmSelect() {
		if (elemento != null && elemento.getTagName().toLowerCase().equals("select")) {
			new org.openqa.selenium.support.ui.Select(elemento);
		}
	}

	protected abstract String getNomeTag();

	/**
	 * Passa o mouse sobre o elemento. <br>
	 * 
	 */
	public void passarMouseSobre() {
		Navegador.passarMouseSobre(elemento);
	}

	/**
	 * Verifica se o elemento possui conteúdo.<br>
	 * 
	 * @return True se o conteúdo for diferente de <i>null</i> e não for vazio.
	 */
	public boolean possuiConteudo() {
		return elemento.getText() != null && !elemento.getText().isEmpty();
	}

	/**
	 * Preenche o campo value de um elemento com um valor inteiro. <br>
	 * 
	 * @param valor
	 */
	public void preencherCampoNumberCom(int valor) {
		this.preencherCampoNumberCom(String.valueOf(valor));
	}

	/**
	 * Preenche o campo value de um elemento com um valor string. <br>
	 * 
	 * @param valor
	 */
	public void preencherCampoNumberCom(String valor) {
		this.alterarValorAtributo("value", valor);
	}

	/**
	 * Preenche a janela de diálogo com o path do arquivo.<br>
	 * 
	 * @param caminhoArquivo
	 *            Path onde se encontra o arquivo
	 */
	public void preencherJanelaDialogoCaminhoArquivoCom(String caminhoArquivo) throws AWTException, InterruptedException {
		Navegador.preencherJanelaDialogoCaminhoArquivoCom(caminhoArquivo);
	}

	/**
	 * Preenche o elemento simulando a digitação pelo teclado. <br>
	 * Utilizar esse método para preenchimento de elementos tipo <i>textarea</i>
	 * ou <i>fake inputs</i>. <br>
	 * 
	 * @param texto
	 *            Texto a ser digitado.
	 * @throws Exception
	 */
	public void preencherSimulandoDigitacao(String texto) throws Exception {
		this.clicar();
		Navegador.preencherSimulandoDigitacao(texto);
	}

	/**
	 * Altera o valor do atributo style para <i>display:none</i>, tornando o
	 * elemento invisível. <br>
	 * 
	 */
	public void tornarInvisivel() {
		this.alterarValorAtributo("style", "display:none;");
	}

}
