package testes.webdriver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import kvalito.componentes.Elemento;
import kvalito.core.Configuracoes;
import kvalito.core.NavegadorUtilizado;
import kvalito.core.Pagina;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TestesAlertaChrome extends Pagina {
	private String urlPagina;

	@Before
	public void iniciarTestes() throws Exception {
		executarTesteNo(NavegadorUtilizado.CHROME);
		urlPagina = Configuracoes.getConfiguracaoPagina("pagina-app-web-qatestengine");
		abrirUrl(urlPagina);
	}

	@After
	public void finalizarTeste() throws Exception {
		fechar();
	}
	
	@Test
	public void verificarMensagemAlerta() throws Exception {
		Elemento botao = localizarElemento("alert-lancar");
		botao.clicar();
		
		String mensagemAlerta = obterTextoAlerta();			
		String mensagemEsperada = "Exibiu alerta";
		assertEquals(mensagemEsperada, mensagemAlerta);
		
		esperarCarregamentoPor(2000);
		
		
		aceitarAlerta();
		
		esperarCarregamentoPor(3000);
	}
	
	@Test
	public void verificarMensagemAlerta2() throws Exception {
		Elemento botao = localizarElemento("alert-lancar");
		botao.clicar();
		
		String mensagemAlerta = obterTextoAlerta();			
		String mensagemEsperada = "Exibiu alerta";
		assertEquals(mensagemEsperada, mensagemAlerta);
		
		esperarCarregamentoPor(2000);
		
		aceitarAlerta();
		
		esperarCarregamentoPor(3000);
	}
	
	@Test
	public void localizarElementoPorCssSelector() throws Exception {
		String resultadoEsperado = "Por CSS-SELECTOR";
		String textoElemento = localizarElemento("cssselector", "#todos-find-elements-by-cssselector").getConteudo();
		assertEquals(resultadoEsperado, textoElemento);
	}

	@Test
	public void executarMouseOver() throws Exception {
		Elemento divPassarMousePorCima = localizarElemento("mouse-over-passe-mouse");
		Elemento divQueSeraExibido = localizarElemento("mouse-over-ira-aparecer");

		assertFalse(divQueSeraExibido.estaVisivel());
		divPassarMousePorCima.passarMouseSobre();
		assertTrue(divQueSeraExibido.estaVisivel());
	}
	
}
