package kvalito.core.requisicaoHttp;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import kvalito.core.Log;
import kvalito.utilitarios.UtilitarioTexto;

import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;



public class RequisicaoHttp {

	private String url;
	private String metodo;
	private List<NameValuePair> parametros;
	private String parametro;
	private boolean unicoParametro;
	private List<EnderecoHttp> redirecionamentos;
	private List<CabecalhoHttp> cabecalhosHttp;
	private String corpoResposta;

	/**
	 * Cria uma nova requisição HTTP para um determinado endereco
	 * 
	 * @param url  URL do Endereço Http
	 */
	public RequisicaoHttp(String url) {
		this.url = url;
		this.metodo = "GET";
		this.parametros = new ArrayList<NameValuePair>();
		this.parametro = "";
		this.unicoParametro = false;
		this.redirecionamentos = new ArrayList<EnderecoHttp>();
		this.cabecalhosHttp = new ArrayList<CabecalhoHttp>();
		Log.registrarInformacao(String.format("Iniciando uma nova requisição HTTP [%s]", url));
	}

	
	/**
	 * Adiciona um cabeçalho Http personalizado na Rquisição
	 * 
	 * @param nomeCabecalho  nome do cabeçalho Http
	 * @param valorCabecalho  valor do cabeçalho
	 */
	public void adicionarCabecalhoHttp(String nomeCabecalho, String valorCabecalho) {
		CabecalhoHttp cabecalho = new CabecalhoHttp(nomeCabecalho, valorCabecalho);
		String mensagem = "Incluiu um cabeçalho na requisição [%s]";
		Log.registrarInformacao(String.format(mensagem, cabecalho.toString()));
		this.cabecalhosHttp.add(cabecalho);
	}

	/**
	 * Adiciona um cabeçalho Http personalizado com User-Agent na Rquisição
	 * 
	 * @param valor  valor do cabeçalho User-Agent
	 */
	public void adicionarUserAgent(String valor) {
		adicionarCabecalhoHttp("User-Agent", valor);
	}

	/**
	 * Recupera o conteudo do Endereço Http
	 * 
	 * @param connection  HttpURLConnection ativa
	 * @return      String com o conteudo do endereço Http
	 * @throws IOException
	 */
	private String carregarBody(HttpURLConnection connection) throws IOException {
		if (connection != null) {
			String encoding = connection.getContentEncoding();
			encoding = encoding == null ? "UTF-8" : encoding;
			corpoResposta = IOUtils.toString(obterStreamDeAcordoComStatus(connection), encoding);
		}
		return corpoResposta;
	}

	/**
	 * Retorna o conteudo do endereço de destino da Requisição 
	 */
	public String corpoResposta() {
		return corpoResposta;
	}

	/**
	 * Processa a requisição HTTP
	 * 
	 * @throws IOException
	 */
	public void executar() throws IOException {
		Log.registrarInformacao(String.format("Executando a requisição"));
		URL resourceUrl;
		HttpURLConnection.setFollowRedirects(false);
		HttpURLConnection connection;
		
		CookieManager cookieManager = new CookieManager();
		cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
		CookieHandler.setDefault(cookieManager);
		CookieStore cookieStore = null;
		String body = null;

		while (true) {

			resourceUrl = new URL(url);
			connection = (HttpURLConnection) resourceUrl.openConnection();
			connection.setConnectTimeout(30000); //PADRAO 15000
			connection.setReadTimeout(30000); //PADRAO 15000
			connection.setRequestMethod(this.metodo);
			
			for (CabecalhoHttp cabecalho : this.cabecalhosHttp) {
				connection.setRequestProperty(cabecalho.getNome(), cabecalho.getValor());
			}
			
			if (this.metodo.equals("POST")) {
				
				connection.setDoInput(true);
				connection.setDoOutput(true);
				
				OutputStream os = connection.getOutputStream();
				BufferedWriter writer = new BufferedWriter(
				        new OutputStreamWriter(os, "UTF-8"));
				
				writer.write(this.unicoParametro ? this.parametro : gerarQuery());
				writer.flush();
				writer.close();
				os.close();
				
			}

			cookieStore = cookieManager.getCookieStore();

			body = carregarBody(connection);

			this.redirecionamentos.add(new EnderecoHttp(url, connection.getResponseCode(), cookieStore.getCookies(), body));

			switch (connection.getResponseCode()) {
			case HttpURLConnection.HTTP_MOVED_PERM:
			case HttpURLConnection.HTTP_MOVED_TEMP:
				url = connection.getHeaderField("Location");
				continue;
			}

			break;
		}
		String mensagem = "Requisição executada [Status: %s]";
		Log.registrarInformacao(String.format(mensagem,this.status()));
	}

	/**
	 * Extrai informações do conteudo da requisição de acordo com Expressão Regular
	 * @return      String com o resultado da extração 
	 */
	public String extrairConteudoCorpoResposta(String regexExtracao) {
		return UtilitarioTexto.extrair(corpoResposta(), regexExtracao);
	}

	/**
	 * Retorna o endereço de destino da Requisição 
	 */
	public EnderecoHttp getEnderecoDestino() {
		return (redirecionamentos != null && redirecionamentos.size() > 0) ? redirecionamentos.get(redirecionamentos.size() - 1) : null;
	}

	/**
	 * Em caso de redirecionamento, retorna o endereço de origem da Requisição 
	 */
	public EnderecoHttp getEnderecoOrigem() {
		return redirecionamentos != null ? redirecionamentos.get(0) : null;
	}

	/**
	 * Em caso de redirecionamento, retorna a lista dos endereços envolvidos na Requisição Http 
	 */
	public List<EnderecoHttp> getRedirecionamentos() {
		return redirecionamentos;
	}

	/**
	 * Verifica se houve redirecionamento na Requisição Http 
	 * @return      verdadeiro ou falso;
	 */
	public boolean houveRedirecionamento() {
		return redirecionamentos.size() > 1;
	}

	/**
	 * Recupera um conteudo independente do Status Http
	 * 
	 * @param connection  HttpURLConnection ativa
	 * @return      String com o conteudo do endereço Http
	 * @throws IOException
	 */
	private InputStream obterStreamDeAcordoComStatus(HttpURLConnection connection) throws IOException {
		if (connection.getResponseCode() < 400) {
			return connection.getInputStream();
		}
		return connection.getErrorStream();
	}

	/**
	 * Retorna o Status Http do endereço de destino da Requisição
	 * 
	 * @return      codigo do Status Http
	 */
	public int status() {
		return getEnderecoDestino().getHttpStatusCode();
	}

	/**
	 * Retorna o valor de um cookie do endereço de destino
	 * 
	 * @param nomeCookie  nome do cookie
	 * @return      String com o valor do cookie especificado
	 */
	public String valorCookie(String nomeCookie) {
		return getEnderecoDestino().getCookie(nomeCookie).getValue();
	}
	
	
	/**
	 * Altera o metodo de Requisicao HTTP para POST. 
	 * Por padrao o metodo e GET
	 */
	public void realizarPOST() {
		
		this.metodo = "POST";
		
	}
	
	/**
	 * Adiciona um unico parametro para serem enviado no corpo da Requisicao pelo metodo POST
	 * 
	 * @param valor  valor do parametro
	 */
	public void adicionarParametroPOST(String valor) {
		
		this.unicoParametro = true;
		this.parametro = valor;
		
	}
	
	/**
	 * Adiciona parametros para serem enviados pelo metodo POST
	 * 
	 * @param nome  nome do parametro
	 * @param valor  valor do parametro
	 */
	public void adicionarParametroPOST(String nome, String valor) {
		
		this.parametros.add(new BasicNameValuePair(nome,valor));
		
	}
	
	
	/**
	 * Formata os parametros POST para serem adicionados na Requisicao HTTP 
	 * 
	 * @param parametros  Lista de parametros POST
	 */
	private String gerarQuery() throws UnsupportedEncodingException
	{
	    StringBuilder result = new StringBuilder();
	    boolean first = true;

	    for (NameValuePair pair : parametros)
	    {
	        if (first)
	            first = false;
	        else
	            result.append("&");

	        result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
	        result.append("=");
	        result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
	    }

	    return result.toString();
	}

}
