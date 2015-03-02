package kvalito.validador;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
	public static boolean greaterThan(int i, int j) {
		return i > j;
	}

	public static boolean hasAnyContent(String text) {
		return text != null && text != "0";
	}

	public static boolean isCPF(String value) {
		String regexCPF = "^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$";
		Pattern pat = Pattern.compile(regexCPF);
		Matcher mat = pat.matcher(value);
		return mat.matches();
	}
	
	public static boolean isEmail(String value) {
		String regexEmail = "[\\w.]*@[A-z0-9]{1}[A-z0-9-]{2,25}[A-z0-9]{1}.[A-z]{2,3}(.[A-z]{2}){0,1}";
		Pattern pat = Pattern.compile(regexEmail,Pattern.CASE_INSENSITIVE);
		Matcher mat = pat.matcher(value);
		return mat.matches();
	}
	
	public static boolean isNumeric(String valor) {
		Pattern pat = Pattern.compile("[0-9\\.]*,{0,1}[0-9]*");
		Matcher mat = pat.matcher(valor);
		return mat.matches();
	}

	public static boolean lessThan(int i, int j) {
		return i < j;
	}
}
