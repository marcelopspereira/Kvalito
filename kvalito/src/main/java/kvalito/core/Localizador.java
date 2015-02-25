package kvalito.core;

public class Localizador {
	private TipoLocalizador tipoLocalizador;
	private String elementoLocalizar;
	
	/**
	 * Construtor para mapeamento do localizador de um elemento. <br>
	 * O elemento será localizado pelo valor da chave de configuração (localizar-por-default) do arquivo ConfiguracoesTeste.properties
	 * @param expressaoElementoLocalizar A expressão utilizada para localizar o elemento. (Ex.: txtNome (id ou name), div (tagname), /html/body/ (xpath))
	 */
	
	public Localizador(String expressaoElementoLocalizar) {
		this(null, expressaoElementoLocalizar);
	}
	
	/**
	 * Construtor para mapeamento do localizador de um elemento. <br>
	 * É possível utilizar as opções abaixo:<br>
	 * 		id - nome -	name - xpath - tagname - cssclass - cssselector
	 * @param localizarPor Localizador do elemento. Pode ser um dos valores (id, nome, name, xpath, tagname, cssclass, cssselector)<br>
	 * OBS.: O parâmetro não é Case Sensitive<br>
	 * @param expressaoElementoLocalizar A expressão utilizada para localizar o elemento. (Ex.: txtNome (id ou name), div (tagname), /html/body/ (xpath))
	 */
	
	public Localizador(String localizarPor, String expressaoElementoLocalizar) {
		try {
			if (localizarPor == null || localizarPor.isEmpty()) {
				localizarPor = Configuracoes.getConfiguracaoPrincipal("localizar-por-default");
			}
			
			if (expressaoElementoLocalizar == null || expressaoElementoLocalizar.isEmpty()) {
				throw new Exception("A expressão que deve ser localizada não foi informada.");
			}
			
			this.tipoLocalizador = TipoLocalizador.valueOf(localizarPor.toUpperCase());;
			this.elementoLocalizar = expressaoElementoLocalizar;
		}
		catch (Exception ex) {
			Log.registrarInformacao(String.format("Localizador inválido [Localizar por: %s = %s]", localizarPor, expressaoElementoLocalizar));
			Log.registrarErro(ex);
		}
	}

	public String getExpressaoElemento() {
		return this.elementoLocalizar;
	}

	public TipoLocalizador getLocalizarPor() {
		return this.tipoLocalizador;
	}

	@Override
	public String toString() {
		return "Localizar por: " + this.tipoLocalizador.toString() + " = " + this.elementoLocalizar;
	}
}
