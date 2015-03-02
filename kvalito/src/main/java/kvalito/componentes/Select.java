package kvalito.componentes;

import kvalito.core.Localizador;
import kvalito.core.Log;

import org.openqa.selenium.StaleElementReferenceException;

public class Select extends ElementoCore {
	private Localizador localizador;

	public Select(Localizador localizadorDoElemento) throws Exception {
		this.localizador = localizadorDoElemento;
		super.carregarElemento(localizadorDoElemento);
	}

	public Select(String expressaoElementoLocalizar) throws Exception {
		this.localizador = new Localizador(expressaoElementoLocalizar);
		super.carregarElemento(this.localizador);
	}

	public Select(String localizarPor, String expressaoElementoLocalizar) throws Exception {
		this.localizador = new Localizador(localizarPor, expressaoElementoLocalizar);
		super.carregarElemento(this.localizador);
	}

	private org.openqa.selenium.support.ui.Select coverterWebElementParaSelect() {
		return new org.openqa.selenium.support.ui.Select(elemento);
	}

	@Override
	protected String getNomeTag() {
		return "select";
	}

	public Elemento opcaoSelecionada() {
		select = coverterWebElementParaSelect();
		return new Elemento(select.getFirstSelectedOption());
	}

	private void selecionar(int por, String oQueSelecionador) throws Exception {
		int quantidadeTentativas = 0;
		int maximoTentativaTratarStaleElement = 5;

		while (quantidadeTentativas <= maximoTentativaTratarStaleElement) {
			quantidadeTentativas++;

			try {
				select = coverterWebElementParaSelect();

				if (quantidadeTentativas > maximoTentativaTratarStaleElement) {
					throw new Exception("Não conseguiu tratar o Stale Element");
				}

				switch (por) {
				case 1:
					select.selectByIndex(Integer.parseInt(oQueSelecionador));
					break;
				case 2:
					select.selectByVisibleText(oQueSelecionador);
					break;
				case 3:
					select.selectByValue(oQueSelecionador);
					break;
				default:
					throw new Exception("Não é possível selecionar por [" + por + "]");
				}
				break;
			} catch (StaleElementReferenceException e) {
				String mensagem = String.format("Lançou o erro StaleElement ao tentar selecionar uma opção [%s - %s]. Irá tentar novamente [Tentativa: %s]",
						por, oQueSelecionador, quantidadeTentativas);
				Log.registrarInformacao(mensagem);
				super.carregarElemento(this.localizador);
			} catch (Exception e) {
				Log.registrarInformacao("Erro ao tentar selecionar um elemento");
				Log.registrarErro(e);
				throw e;
			}
		}
	};

	/**
	 * Selecionar uma opção de um Select por Indice. <br>
	 * 
	 * @param indiceParaSelecionar
	 *            Indice da opção que deverá ser selecionada
	 * @throws Exception
	 */
	public void selecionarPorIndice(int indiceParaSelecionar) throws Exception {
		Log.registrarInformacao("Escolhendo opção do SelectBox por INDICE [" + indiceParaSelecionar + "] - SelectBox [" + this.toString() + "]");
		this.selecionar(1, Integer.toString(indiceParaSelecionar));
		Log.registrarInformacao("Valor selecionado");
	}

	/**
	 * Selecionar uma opção de um Select por Texto. <br>
	 * 
	 * @param textoParaSelecionar
	 *            Texto da opção que deverá ser selecionada
	 * @throws Exception
	 */
	public void selecionarPorTexto(String textoParaSelecionar) throws Exception {
		String mensagem = String.format("Escolhendo opção do SelectBox por TEXTO [%s] - SelectBox [%s]", textoParaSelecionar, this.toString());
		Log.registrarInformacao(mensagem);
		this.selecionar(2, textoParaSelecionar);
		Log.registrarInformacao("Valor selecionado");
	}

	/**
	 * Selecionar uma opção de um Select por Valor. <br>
	 * 
	 * @param valorParaSelecionar
	 *            Valor da opção que deverá ser selecionada
	 * @throws Exception
	 */
	public void selecionarPorValor(String valorParaSelecionar) throws Exception {
		Log.registrarInformacao("Escolhendo opção do SelectBox por VALOR [" + valorParaSelecionar + "] - SelectBox [" + this.toString() + "]");
		this.selecionar(3, valorParaSelecionar);
		Log.registrarInformacao("Valor selecionado");
	}

	@Override
	public String toString() {
		return String.format("Localizado por %s = %s", this.localizador.getLocalizarPor(), this.localizador.getExpressaoElemento());
	}
}
