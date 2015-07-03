package kvalito.core.requisicaoHttp;


public class CabecalhoHttp {
	
	private String nome;
	private String valor;
	
	/**
	 * Armazena atributos de Http Header
	 * 
	 * @param nome nome de um Http Header Ex.: Content-Type, Authorization, User-Agent
	 * @param valor valor do Http Header
	 */
	public CabecalhoHttp(String nome, String valor) {
		this.nome=nome;
		this.valor=valor;
	}

	public String getNome() {
		return nome;
	}

	public String getValor() {
		return valor;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return this.nome + ": " + this.valor;
	}
}
