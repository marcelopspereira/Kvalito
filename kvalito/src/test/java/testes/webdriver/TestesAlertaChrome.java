package testes.webdriver;

import static org.junit.Assert.assertEquals;
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
	
	@Ignore
	@Test
	public void verificarMensagemAlerta() throws Exception {
		Elemento botao = localizarElemento("alert-lancar");
		botao.clicar();
		
		String mensagemAlerta = obterTextoAlerta();			
		String mensagemEsperada = "Exibiu alerta";
		assertEquals(mensagemEsperada, mensagemAlerta);
		
		aceitarAlerta();
	}
}
