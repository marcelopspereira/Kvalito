package testes.conversores;

import static org.junit.Assert.assertEquals;

import java.security.InvalidParameterException;

import kvalito.conversores.ConversorCores;

import org.junit.Test;

public class TestesConverterParaRgb {

	@Test
	public void verificarConversaoHexadecimalParaRgb() throws Exception {
		String cor = "#0000ff";
		String resultadoEsperado = "rgb(0, 0, 255)";

		assertEquals(resultadoEsperado,
				ConversorCores.converterParaRgb(cor));

	}

	@Test
	public void verificarConversaoHexadecimalComEspacoADireitaParaRgb()
			throws Exception {
		String cor = "#c0c0c0 ";
		String resultadoEsperado = "rgb(192, 192, 192)"; 

		assertEquals(resultadoEsperado,
				ConversorCores.converterParaRgb(cor));

	}

	@Test
	public void verificarConversaoHexadecimalComEspacoAEsquerdaParaRgb()
			throws Exception {
		String cor = " #00ff00";
		String resultadoEsperado = "rgb(0, 255, 0)";

		assertEquals(resultadoEsperado,
				ConversorCores.converterParaRgb(cor));

	}

	@Test
	public void verificarConversaoDeValorNulo() {

		String resultadoEsperado = "Favor informar uma cor válida!";

		try {

			ConversorCores.converterParaRgb(null);

		} catch (InvalidParameterException e) {
			assertEquals("Falhou ao tratar valor NULO", resultadoEsperado,
					e.getMessage());
		}

	}

	@Test
	public void verificarConversaoDeValorVazio() {

		String resultadoEsperado = "Favor informar uma cor válida!";

		try {

			ConversorCores.converterParaRgb("");

		} catch (InvalidParameterException e) {
			assertEquals("Falhou ao tratar String vazia!", resultadoEsperado,
					e.getMessage());
		}

	}

	@Test
	public void verificarConversaoDeEspacoEmBranco() {

		String resultadoEsperado = "Favor informar uma cor válida!";

		try {

			ConversorCores.converterParaRgb(" ");

		} catch (InvalidParameterException e) {
			assertEquals("Falhou ao tratar espacos em branco!",
					resultadoEsperado, e.getMessage());
		}

	}

	@Test
	public void verificarConversaoDeTABEmBranco() {

		String resultadoEsperado = "Favor informar uma cor válida!";

		try {

			ConversorCores.converterParaRgb("	");

		} catch (InvalidParameterException e) {
			assertEquals("Falhou ao tratar TAB em branco!", resultadoEsperado,
					e.getMessage());
		}

	}

}
