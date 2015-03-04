package testes.conversores;

import static org.junit.Assert.assertEquals;

import java.security.InvalidParameterException;

import kvalito.conversores.ConversorCores;

import org.junit.Test;

public class TestesConverterParaHexadecimal {

	@Test
	public void verificarConversaoRgbParaHexadecimal() throws Exception {
		String cor = "rgb(0,0,255)";
		String resultadoEsperado = "#0000ff";

		assertEquals(resultadoEsperado,
				ConversorCores.converterParaHexadecimal(cor));

	}

	@Test
	public void verificarConversaoRgbParaHexadecimalComEspacoADireita()
			throws Exception {
		String cor = " rgb(192,192,192)";
		String resultadoEsperado = "#c0c0c0";

		assertEquals(resultadoEsperado,
				ConversorCores.converterParaHexadecimal(cor));

	}

	@Test
	public void verificarConversaoRgbParaHexadecimalComEspacoAEsquerda()
			throws Exception {
		String cor = "rgb(0,255,0) ";
		String resultadoEsperado = "#00ff00";

		assertEquals(resultadoEsperado,
				ConversorCores.converterParaHexadecimal(cor));

	}

	@Test
	public void verificarConversaoDeValorNulo() {

		String resultadoEsperado = "Favor informar uma cor válida!";

		try {

			ConversorCores.converterParaHexadecimal(null);

		} catch (InvalidParameterException e) {
			assertEquals("Falhou ao tratar valor NULO", resultadoEsperado,
					e.getMessage());
		}

	}

	@Test
	public void verificarConversaoDeValorVazio() {

		String resultadoEsperado = "Favor informar uma cor válida!";

		try {

			ConversorCores.converterParaHexadecimal("");

		} catch (InvalidParameterException e) {
			assertEquals("Falhou ao tratar String vazia!", resultadoEsperado,
					e.getMessage());
		}

	}

	@Test
	public void verificarConversaoDeEspacoEmBranco() {

		String resultadoEsperado = "Favor informar uma cor válida!";

		try {

			ConversorCores.converterParaHexadecimal(" ");

		} catch (InvalidParameterException e) {
			assertEquals("Falhou ao tratar espacos em branco!",
					resultadoEsperado, e.getMessage());
		}

	}

	@Test
	public void verificarConversaoDeTABEmBranco() {

		String resultadoEsperado = "Favor informar uma cor válida!";

		try {

			ConversorCores.converterParaHexadecimal("	");

		} catch (InvalidParameterException e) {
			assertEquals("Falhou ao tratar TAB em branco!", resultadoEsperado,
					e.getMessage());
		}

	}

}
