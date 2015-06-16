package testes.webdriver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import kvalito.componentes.Elemento;
import kvalito.componentes.Select;
import kvalito.core.Configuracoes;
import kvalito.core.NavegadorUtilizado;
import kvalito.core.Pagina;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestesHtmlUnit extends Pagina {

	@Before
	public void iniciarTeste() throws Exception {
		executarTesteNo(NavegadorUtilizado.HTMLUNIT);
		abrirUrl(Configuracoes.getConfiguracaoPagina("pagina-app-web-qatestengine"));
	}

	@After
	public void finalizarTeste() throws Exception {
		fechar();
	}
	
	
	@Test
	public void preencherComCaracteresAleatorios() throws Exception{
		Elemento elemento = localizarElemento("caixa-texto");
		String textoEsperado = elemento.preencherComCaracteresAleatorios(10);
		assertEquals(textoEsperado, elemento.valorAtributo("value"));
	}
	
	@Test
	public void listarElementosComUmLocalizador() throws Exception {
		List<Elemento> listaDeElementos = super.localizarElementos("xpath","//*[@id='todos-find-elements-by']/p");
		int numeroDeElementos = 6;
		assertEquals(numeroDeElementos , listaDeElementos.size());
//		https://lh5.googleusercontent.com/-IuSHjaTQHK0/AAAAAAAAAAI/AAAAAAAAAE0/jsW9vT-SpM8/s46-c-k-no/photo.jpg
	}
	
	@Test
	public void naoListarElementosQuandoLocalizadorNaoExistir() throws Exception {
		List<Elemento> listaDeElementos = super.localizarElementos("esse-localizador-nao-existe");
		int numeroDeElementos = 0;
		assertEquals(numeroDeElementos , listaDeElementos.size());
	}
	
	@Test
	public void preencherComNumeros() throws Exception {
		int quantidadeNumeros = 5;
		Elemento elemento = super.localizarElemento("caixa-texto");
		elemento.preencherComNumerosAleatorios(quantidadeNumeros);
		assertEquals(quantidadeNumeros, elemento.valorAtributo("value").length());
	}
	
	@Test(expected=Exception.class)
	public void tornarElementoInvisivel() throws Exception {
		Elemento elemento = super.localizarElemento("xpath", "//*[@id=\"todos-find-elements-by\"]");
		elemento.tornarInvisivel();
		assertFalse(elemento.estaVisivel());
	}
		
	@Test(expected=Exception.class)
	public void preencherCampoNumber() throws Exception {
		Elemento campoNumber = super.localizarElemento("id", "campo-number");
		campoNumber.preencherCampoNumberCom(120);
		assertEquals("120", campoNumber.valorAtributo("value"));
	}	

	@Test
	public void navegarParaUmIFrameDepoisVoltarFramePrincipal() throws Exception {
		Elemento iframe = super.localizarElemento("id", "iframe-teste");
		super.usarIFrame(iframe);
		Elemento iframeBody = super.localizarElemento("xpath", "/html/body/h3");
		assertEquals("IFRAME!!!", iframeBody.conteudo());
		super.voltarParaFramePrincipal();
		assertTrue(super.existeElemento("xpath", "/html/body/div[1]/p[5]"));
	}

	@Test
	public void obterOpcaoSeleciodaNoSelect() throws Exception {
		Select select = localizarSelect("stale-element-estado");
		Elemento elemento = select.opcaoSelecionada();
		assertEquals("Selecione", elemento.conteudo());
	}

	@Test
	public void atualizarPagina() throws Exception {
		super.atualizarPagina();
		Select select = localizarSelect("stale-element-estado");
		Elemento elemento = select.opcaoSelecionada();
		assertEquals("Selecione", elemento.conteudo());
	}

	@Test
	public void contarElementosPorXpath() throws Exception {
		int quantidadeElementosEsperados = 6;
		int quantidadeRetornada = contarElementos("xpath", "//*[@id=\"todos-find-elements-by\"]/p");
		assertEquals(quantidadeElementosEsperados, quantidadeRetornada);
	}

	@Test
	public void contarElementosPorId() throws Exception {
		int quantidadeElementosEsperados = 1;
		int quantidadeRetornada = contarElementos("id", "todos-find-elements-by");
		assertEquals(quantidadeElementosEsperados, quantidadeRetornada);
	}

	@Test
	public void contarElementosPorName() throws Exception {
		int quantidadeElementosEsperados = 1;
		int quantidadeRetornada = contarElementos("name", "todos-find-elements-by-name");
		assertEquals(quantidadeElementosEsperados, quantidadeRetornada);
	}

	@Test
	public void contarElementosPorTagName() throws Exception {
		int quantidadeElementosEsperados = 6;
		int quantidadeRetornada = contarElementos("tagname", "p");
		assertEquals(quantidadeElementosEsperados, quantidadeRetornada);
	}

	@Test
	public void contarElementosPorCssClass() throws Exception {
		int quantidadeElementosEsperados = 1;
		int quantidadeRetornada = contarElementos("cssclass", "todos-find-elements-by-class");
		assertEquals(quantidadeElementosEsperados, quantidadeRetornada);
	}

	@Test
	public void contarElementosPorCssSelector() throws Exception {
		int quantidadeElementosEsperados = 1;
		int quantidadeRetornada = contarElementos("cssselector", "#todos-find-elements-by-cssselector");
		assertEquals(quantidadeElementosEsperados, quantidadeRetornada);
	}

	@Test
	public void exibiuElementoPorXpath() throws Exception {

		boolean exibiu = existeElemento("xpath", "//*[@id=\"todos-find-elements-by\"]/p[5]");
		assertTrue(exibiu);

	}

	@Test
	public void naoExibiuElementoPorXpath() throws Exception {

		boolean exibiu = existeElemento("xpath", "elementonaoexistente");
		assertFalse(exibiu);

	}

	@Test
	public void localizarElementoPorTagName() throws Exception {
		String resultadoEsperado = "Por TAG-NAME";
		String textoElemento = localizarElemento("tagname", "p").getConteudo();
		assertEquals(resultadoEsperado, textoElemento);
	}

	@Test
	public void localizarElementoPorName() throws Exception {
		String resultadoEsperado = "Por NAME";
		String textoElemento = localizarElemento("name", "todos-find-elements-by-name").getConteudo();
		assertEquals(resultadoEsperado, textoElemento);
	}

	@Test
	public void localizarElementoPorId() throws Exception {
		String resultadoEsperado = "Por ID";
		String textoElemento = localizarElemento("id", "todos-find-elements-by-id").getConteudo();
		assertEquals(resultadoEsperado, textoElemento);
	}

	@Test
	public void localizarElementoPorCssClass() throws Exception {
		String resultadoEsperado = "Por CLASS";
		String textoElemento = localizarElemento("cssclass", "todos-find-elements-by-class").getConteudo();
		assertEquals(resultadoEsperado, textoElemento);
	}

	@Test
	public void localizarElementoPorCssSelector() throws Exception {
		String resultadoEsperado = "Por CSS-SELECTOR";
		String textoElemento = localizarElemento("cssselector", "#todos-find-elements-by-cssselector").getConteudo();
		assertEquals(resultadoEsperado, textoElemento);
	}
}
