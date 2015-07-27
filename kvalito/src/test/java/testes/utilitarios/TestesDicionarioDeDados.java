package testes.utilitarios;

import static org.junit.Assert.*;
import kvalito.utilitarios.DicionarioDeDados;

import org.junit.Test;

public class TestesDicionarioDeDados {
	
	@Test
	public void incluirDadosNoDicionario(){
		DicionarioDeDados.incluirDados("inclusao", "teste");
		assertTrue(DicionarioDeDados.possuiChave("inclusao"));
	}
	
	@Test
	public void lerDadosDoDicionario(){
		DicionarioDeDados.incluirDados("leitura", "teste");
		String resultadoEsperado = "teste";
		String resultadoObtido = DicionarioDeDados.obterValor("leitura");
		assertEquals(resultadoEsperado, resultadoObtido);
	}
	
	@Test
	public void limparDadosDoDicionario(){
		DicionarioDeDados.limpar();
		int resultadoEsperado = 0;
		int resultadoObtido = DicionarioDeDados.numeroDeChavesExistentes();
		assertEquals(resultadoEsperado, resultadoObtido);
	}
	
	@Test
	public void limparChaveDoDicionario(){
		DicionarioDeDados.incluirDados("limpeza", "teste");
		assertTrue(DicionarioDeDados.possuiChave("limpeza"));
		DicionarioDeDados.apagarChave("limpeza");
		assertFalse(DicionarioDeDados.possuiChave("limpeza"));
	}

}
