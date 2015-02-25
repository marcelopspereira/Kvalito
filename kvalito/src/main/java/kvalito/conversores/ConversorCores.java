package kvalito.conversores;

import java.security.InvalidParameterException;

import org.apache.commons.lang3.StringUtils;

public class ConversorCores {

	/**
	 * Converte o uma String com o valor RGB para Hexadecimal.
	 * 
	 * @param corRgb
	 * @return
	 * @throws InvalidParameterException
	 *             , NumberFormatException
	 */
	public static String converterRgbParaHexadecimal(String corRgb) throws InvalidParameterException {

		if (StringUtils.isBlank(corRgb)) {
			throw new InvalidParameterException("Favor informar a cor RGB! Exemplo: rgb(0,0,0)");
		}

		corRgb = corRgb.trim();
		corRgb = corRgb.replaceAll("(rgba)|(rgb)|(\\()|(\\s)|(\\))", "");

		String rgb[] = corRgb.split(",");

		if (rgb.length > 3) {
			throw new InvalidParameterException("Cor RGB invalida!");
		}

		String hexadecimal = "#";
		for (String cor : rgb) {

			hexadecimal += extrairHexadecimal(Integer.parseInt(cor));

		}

		return hexadecimal;

	}

	/**
	 * Extrai o valor Hexadecimal de um numero RGB (0-255).
	 * 
	 * @param numero
	 * @return
	 * @throws InvalidParameterException
	 *             , NumberFormatException
	 */
	private static String extrairHexadecimal(int numero) {

		String codigoHexadecimal = Integer.toHexString(numero & 0xff);
		StringBuilder builder = new StringBuilder(codigoHexadecimal);

		while (builder.length() < 2) {
			builder.append("0");
		}

		return builder.toString().toUpperCase();
	}

}
