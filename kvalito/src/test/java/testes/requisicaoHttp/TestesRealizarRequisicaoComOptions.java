package testes.requisicaoHttp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import kvalito.core.Configuracoes;
import kvalito.core.requisicaoHttp.RequisicaoHttp;

import org.junit.Test;

public class TestesRealizarRequisicaoComOptions {
	
	@Test
	public void submeterRequisicaoOptions() throws Exception {
		String url = Configuracoes.getConfiguracaoPagina("pagina-post");
		int resultadoEsperado = 200;
		
		RequisicaoHttp requisicao = new RequisicaoHttp(url);
		requisicao.utilizarMetodo("OPTIONS");
		
		requisicao.executar();
		
		assertEquals(resultadoEsperado,requisicao.status());
		assertTrue(requisicao.getEnderecoDestino().getHeaderFields().containsKey("Access-Control-Allow-Methods"));
		assertTrue(requisicao.getEnderecoDestino().getHeaderFields().get("Access-Control-Allow-Methods").toString().contains("OPTIONS"));
	}

}
