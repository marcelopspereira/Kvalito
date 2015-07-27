package kvalito.core.requisicaoHttp;

import java.net.HttpCookie;
import java.util.List;

public class EnderecoHttp {

	private String url;
	private String body;
	private int httpStatusCode;
	private List<HttpCookie> cookies;

	/**
	 * Armazena informações de um endereço Http
	 * 
	 */
	public EnderecoHttp() {
		this.url = "";
		this.httpStatusCode = 0;
	}

	/**
	 * Armazena informações de um endereço Http e seu Status Code
	 * 
	 * @param url  URL do Endereço Http
	 * @param httpStatusCode Status Code do Endereço Http Ex.: 200, 404, 500
	 */
	public EnderecoHttp(String url, int httpStatusCode) {
		this.url = url;
		this.httpStatusCode = httpStatusCode;
	}

	/**
	 * Armazena informações de um endereço Http, Status Code, Cookies e Conteudo
	 * 
	 * @param url  URL do Endereço Http
	 * @param httpStatusCode Status Code do Endereço Http Ex.: 200, 404, 500
	 * @param cookies lista de HttpCookies do endereço Http
	 * @param body conteudo do endereço Http
	 */
	public EnderecoHttp(String url, int httpStatusCode, List<HttpCookie> cookies, String body) {
		this.url = url;
		this.httpStatusCode = httpStatusCode;
		this.cookies = cookies;
		this.body = body;
	}

	public String getBody() {
    	return this.body;
    }

	/**
	 * Retorna um HttpCookie especifico de um EnderecoHttp
	 * 
	 * @param nome  Nome do Cookie
	 * @return      HttpCookie especifico
	 */
	public HttpCookie getCookie(String nome) {

		for (HttpCookie cookie : this.cookies) {
			if (cookie.getName().equals(nome)) {
				return cookie;
			}
		}

		return new HttpCookie("COOKIE_NAO_ENCONTRADO","COOKIE_NAO_ENCONTRADO");

	}

	public List<HttpCookie> getCookies() {
		return this.cookies;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public String getUrl() {
		return url;
	}

	public void setBody(String body) {
    	this.body = body;
    }
	
    public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}
	
    public void setUrl(String url) {
		this.url = url;
	}

}

