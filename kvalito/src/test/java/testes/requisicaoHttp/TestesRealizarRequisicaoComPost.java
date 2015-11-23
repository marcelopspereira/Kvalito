package testes.requisicaoHttp;

import static org.junit.Assert.assertEquals;
import kvalito.core.Configuracoes;
import kvalito.core.requisicaoHttp.RequisicaoHttp;

import org.junit.Test;

public class TestesRealizarRequisicaoComPost {

	@Test
	public void submeterRequisicaoPostComVariosParametros() throws Exception {
		String url = Configuracoes.getConfiguracaoPagina("pagina-post");
		int resultadoEsperado = 200;
		
		RequisicaoHttp requisicao = new RequisicaoHttp(url);
		requisicao.utilizarMetodo("POST");
		
		requisicao.adicionarCabecalhoHttp("Content-Type", "application/json");
		requisicao.adicionarParametroPOST("teste", "valor teste");
		requisicao.adicionarParametroPOST("numero", "3");
		
		requisicao.executar();
		
		assertEquals(resultadoEsperado,requisicao.status());
	}
	
	@Test
	public void submeterRequisicaoPostComParametroSimples() throws Exception {
		String url = Configuracoes.getConfiguracaoPagina("pagina-post");
		int resultadoEsperado = 200;
		
		RequisicaoHttp requisicao = new RequisicaoHttp(url);
		requisicao.utilizarMetodo("POST");
		
		requisicao.adicionarCabecalhoHttp("Content-Type", "application/json");
		requisicao.adicionarParametroPOST("{\"vote\": {\"value\": false,\"objectId\": \"3\",\"userId\": \"leandro\"}}");
		
		requisicao.executar();
		
		assertEquals(resultadoEsperado,requisicao.status());
	}
	
}
