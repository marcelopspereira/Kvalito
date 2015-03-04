package testes.conversores;

import static org.junit.Assert.assertEquals;

import java.security.InvalidParameterException;

import kvalito.conversores.ConversorCores;

import org.junit.Test;

public class TestesConverterParaRgba {

	@Test
	public void verificarConversaoHexadecimalParaRgba() throws Exception {
		String cor = "#0000ff";
		String resultadoEsperado = "rgba(0, 0, 255, 1)";

		assertEquals(resultadoEsperado,
				ConversorCores.converterParaRgba(cor));

	}

	@Test
	public void verificarConversaoHexadecimalComEspacoADireitaParaRgba()
			throws Exception {
		String cor = "#c0c0c0 ";
		String resultadoEsperado = "rgba(192, 192, 192, 1)"; 

		assertEquals(resultadoEsperado,
				ConversorCores.converterParaRgba(cor));

	}

	@Test
	public void verificarConversaoHexadecimalComEspacoAEsquerdaParaRgba()
			throws Exception {
		String cor = " #00ff00";
		String resultadoEsperado = "rgba(0, 255, 0, 1)";

		assertEquals(resultadoEsperado,
				ConversorCores.converterParaRgba(cor));

	}

	@Test
	public void verificarConversaoDeValorNulo() {

		String resultadoEsperado = "Favor informar uma cor válida!";

		try {

			ConversorCores.converterParaRgba(null);

		} catch (InvalidParameterException e) {
			assertEquals("Falhou ao tratar valor NULO", resultadoEsperado,
					e.getMessage());
		}

	}

	@Test
	public void verificarConversaoDeValorVazio() {

		String resultadoEsperado = "Favor informar uma cor válida!";

		try {

			ConversorCores.converterParaRgba("");

		} catch (InvalidParameterException e) {
			assertEquals("Falhou ao tratar String vazia!", resultadoEsperado,
					e.getMessage());
		}

	}

	@Test
	public void verificarConversaoDeEspacoEmBranco() {

		String resultadoEsperado = "Favor informar uma cor válida!";

		try {

			ConversorCores.converterParaRgba(" ");

		} catch (InvalidParameterException e) {
			assertEquals("Falhou ao tratar espacos em branco!",
					resultadoEsperado, e.getMessage());
		}

	}

	@Test
	public void verificarConversaoDeTABEmBranco() {

		String resultadoEsperado = "Favor informar uma cor válida!";

		try {

			ConversorCores.converterParaRgba("	");

		} catch (InvalidParameterException e) {
			assertEquals("Falhou ao tratar TAB em branco!", resultadoEsperado,
					e.getMessage());
		}

	}

}
