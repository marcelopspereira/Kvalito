package kvalito.core;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class AcoesTeclado {
	private WebElement elemento;

	public AcoesTeclado(WebElement elemento) {
		this.elemento = elemento;
	}

	/**
	 * Pressiona a tecla ENTER.
	 */
	public void pressionarEnter() {
		this.elemento.sendKeys(Keys.ENTER);
	}

	/**
	 * Pressiona a tecla ESC.
	 */
	public void pressionarEsc() {
		this.elemento.sendKeys(Keys.ESCAPE);
	}
}
