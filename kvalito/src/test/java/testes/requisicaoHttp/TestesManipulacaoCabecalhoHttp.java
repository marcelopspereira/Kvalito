package testes.requisicaoHttp;

import static org.junit.Assert.*;
import kvalito.core.Configuracoes;
import kvalito.core.requisicaoHttp.RequisicaoHttp;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

public class TestesManipulacaoCabecalhoHttp {

	/*
	 * String userCredentials = "username:password"; String basicAuth = "Basic "
	 * + new String(new Base64().encode(userCredentials.getBytes())); FAZ -
	 * myURLConnection.setRequestProperty ("Authorization", basicAuth); FAZ -
	 * myURLConnection.setRequestProperty("Content-Type",
	 * "application/x-www-form-urlencoded"); FAZ -
	 * myURLConnection.setRequestProperty("Content-Length", "" +
	 * Integer.toString(postData.getBytes().length)); FAZ -
	 * myURLConnection.setRequestProperty("Content-Language", "en-US"); NÃO FAZ
	 * - myURLConnection.setRequestMethod("POST"); NÃO FAZ -
	 * myURLConnection.setUseCaches(false); NÃO FAZ -
	 * myURLConnection.setDoInput(true); NÃO FAZ -
	 * myURLConnection.setDoOutput(true);
	 */

	@Test
	public void fazerRequisicaoComCabecalhoHttpPersonalizado() throws Exception {

		// "http://infotomdsv2:8180/qa-api-testes-webapp/"

		String url = Configuracoes
				.getConfiguracaoPagina("pagina-app-web-qatestengine");

		String userCredentials = "username:password";
		String basicAuth = "Basic "
				+ new String(new Base64().encode(userCredentials.getBytes()));

		RequisicaoHttp requisicao = new RequisicaoHttp(url);
		requisicao.adicionarCabecalhoHttp("Authorization", basicAuth);
		requisicao.adicionarCabecalhoHttp("Content-Type",
				"application/x-www-form-urlencoded");
		requisicao.adicionarCabecalhoHttp("Content-Length", "10");
		requisicao.adicionarCabecalhoHttp("Content-Language", "en-US");
		requisicao
				.adicionarCabecalhoHttp("User-Agent",
						"Mozilla/5.0 (Windows NT x.y; rv:10.0) Gecko/20100101 Firefox/10.0");
		requisicao.executar();

		assertEquals(200, requisicao.status());

	}

	@Test
	public void fazerRequisicaoComUserAgentDesktop() throws Exception {

		String url = Configuracoes
				.getConfiguracaoPagina("pagina-app-web-qatestengine")
				+ "pagina-exibe-cabecalho-http.jsp";
		String resultadoEsperado = "Mozilla/5.0 (Windows NT x.y; rv:10.0) Gecko/20100101 Firefox/10.0";

		RequisicaoHttp requisicao = new RequisicaoHttp(url);
		requisicao.adicionarUserAgent(resultadoEsperado);
		requisicao.executar();

		assertTrue("USER-AGENT " + resultadoEsperado
				+ " não foi localizado no header!", requisicao
				.getEnderecoDestino().getBody().contains(resultadoEsperado));
		
	}
	
	@Test
	public void consultarInformacoesDoCabecalhoHttp() throws Exception {

		String url = Configuracoes
				.getConfiguracaoPagina("pagina-app-web-qatestengine");

		RequisicaoHttp requisicao = new RequisicaoHttp(url);
		
		requisicao.executar();

		assertTrue(requisicao.getEnderecoDestino().getHeaderFields().toString().contains("Content-Type=[text/html]"));
		
	}

}