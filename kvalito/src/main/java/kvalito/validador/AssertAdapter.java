package kvalito.validador;

import org.junit.Assert;

public class AssertAdapter extends Assert {
	static public void assertGreatherThan(int actual, int compare) {
		if (!Validator.greaterThan(actual, compare)) {
			String mensagem = "The actual [%s] value is less than compare [%s] value";
			fail(String.format(mensagem, actual, compare));
		}
	}
	
	static public void assertHasAnyContent(String text) {
		if (!Validator.hasAnyContent(text)) {
			String mensagem = "O valor [%s] não é um número válido";
			fail(String.format(mensagem, text));
		}
	}
	
	static public void assertIsEmail(String valor) {
		if (!Validator.isEmail(valor)) {
			String mensagem = "O valor [%s] não é um e-mail válido";
			fail(String.format(mensagem, valor));
		}
	}
	
	static public void assertIsNumeric(String valor) {
		if (!Validator.isNumeric(valor)) {
			String mensagem = "O valor [%s] não é um número válido";
			fail(String.format(mensagem, valor));
		}
	}

	static public void assertLessThan(int actual, int compare) {
		if (!Validator.lessThan(actual, compare)) {
			String message = "The actual [%s] value is greater than compare [%s] value";
			fail(String.format(message, actual, compare));
		}
	}
}
