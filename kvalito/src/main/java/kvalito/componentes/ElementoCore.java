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

	protected abstract String getNomeTag();

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
				Log.registrarInformacao("O elemento pode estar sendo atualizado por um Refresh ou JavaScript. Ele será recarregado. Tentativa: " + quantidadeVezesObteveElemento);
				Navegador.esperarPor(TEMPO_ESPERAR_RECARREGAMENTO_ELEMENTO);
			} catch (Exception ex) {
				String mensagemErro = String.format("Houve um erro ao carregar o elemento [%s]", localizador.toString());
				Log.registrarInformacao(mensagemErro);
				Log.registrarErro(ex);
				throw ex;
			}
		}
	}

	private void executarComandosQueGeramStaleElementParaUmSelect() {
		if (elemento !=null && elemento.getTagName().toLowerCase().equals("select")) {
			new org.openqa.selenium.support.ui.Select(elemento);
		}
	}

	private void executarComandosQueGeramStaleElement() {
		if (elemento !=null) {
			elemento.getText();
			elemento.getTagName();
		}
	}

	public void clicar() throws Exception {
		Log.registrarInformacao(String.format("O elemento [%s] será clicado.", elemento.getTagName()));

		elemento.click();

		Log.registrarInformacao(String.format(" - Url atual: [%s]", Navegador.getUrlAtual()));
	}

	/**
	 * Executa um duplo clique no elemento.
	 */
	public void clicarDuasVezes() {
		Log.registrarInformacao(String.format("O elemento [%s] será clicado duas vezes.", elemento.getTagName()));

		Navegador.clicarDuasVezes(elemento);

		Log.registrarInformacao(String.format(" - Url atual: [%s]", Navegador.getUrlAtual()));
	}

	/**
	 * Arrasta o elemento para outro elemento de destino.
	 * 
	 * @param destino
	 */
	public void arrastarElementoPara(Elemento destino) {
		Navegador.arrastarElementoPara(elemento, destino.webElement());
	}

	public boolean aguardarAteQueEstejaVisivel() {
		try {
			Navegador.aguardarAteQueEstejaVisivel(elemento);
			return elemento.isDisplayed();
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean aguardarAteQueAtributoEstejaPreenchido(String atributo) {
		try {
			Navegador.aguardarAteQueAtributoEstejaPreenchido(elemento, atributo);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public void passarMouseSobre() {
		Navegador.passarMouseSobre(elemento);
	}

	public boolean possuiConteudo() {
		return elemento.getText() != null && !elemento.getText().isEmpty();
	}

	public void preencherJanelaDialogoCaminhoArquivoCom(String caminhoArquivo) throws AWTException, InterruptedException {
		Navegador.preencherJanelaDialogoCaminhoArquivoCom(caminhoArquivo);
	}

	public void alterarValorAtributo(String nomeAtriburo, String novoValor) {
		Navegador.manipularValorAtributo(elemento, nomeAtriburo, novoValor);
	}

	public void preencherCampoNumberCom(String valor) {
		this.alterarValorAtributo("value", valor);
	}

	public void preencherCampoNumberCom(int valor) {
		this.preencherCampoNumberCom(String.valueOf(valor));
	}

	public void tornarInvisivel() {
		this.alterarValorAtributo("style", "display:none;");
	}

}
