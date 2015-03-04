package kvalito.conversores;

import java.security.InvalidParameterException;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.support.Color;

public class ConversorCores {

	/**
	 * Encapsula funções da biblioteca org.openqa.selenium.support.Color para \n
	 * converter o uma cor para Hexadecimal.
	 * 
	 * @param cor
	 * @return
	 * @throws InvalidParameterException
	 */
	public static String converterParaHexadecimal(String cor) throws InvalidParameterException {

		if (StringUtils.isBlank(cor)) {
			throw new InvalidParameterException("Favor informar uma cor válida!");
		}

		return Color.fromString(cor).asHex();

	}

	/**
	 * Encapsula funções da biblioteca org.openqa.selenium.support.Color para \n
	 * converter o uma cor para RGB.
	 * 
	 * @param cor
	 * @return
	 * @throws InvalidParameterException
	 */
	public static String converterParaRgb(String cor) throws InvalidParameterException {

		if (StringUtils.isBlank(cor)) {
			throw new InvalidParameterException("Favor informar uma cor válida!");
		}

		return Color.fromString(cor).asRgb();

	}
	
	
	/**
	 * Encapsula funções da biblioteca org.openqa.selenium.support.Color para \n
	 * converter o uma cor para RGBA
	 * 
	 * @param cor
	 * @return
	 * @throws InvalidParameterException
	 */
	public static String converterParaRgba(String cor) throws InvalidParameterException {

		if (StringUtils.isBlank(cor)) {
			throw new InvalidParameterException("Favor informar uma cor válida!");
		}
		
		return Color.fromString(cor).asRgba();

	}

}
