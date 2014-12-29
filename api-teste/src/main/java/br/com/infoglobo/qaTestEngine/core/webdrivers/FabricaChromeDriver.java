package br.com.infoglobo.qaTestEngine.core.webdrivers;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import br.com.infoglobo.qaTestEngine.core.Configuracoes;
import br.com.infoglobo.qaTestEngine.core.Log;
import br.com.infoglobo.qaTestEngine.core.recursos.RecursosParaDriver;

public class FabricaChromeDriver {

	public static WebDriver instanciar() throws Exception {
		String diretorioChromeDriver = Configuracoes.getConfiguracaoPrincipal("diretorio-chrome-driver");
		Log.registrarInformacao(String.format("Diretório do ChromeDriver [%s]",diretorioChromeDriver));
		System.setProperty("webdriver.chrome.driver",diretorioChromeDriver);
		ChromeDriver driverTemporario = new ChromeDriver(recursosEspecificos());
		driverTemporario.manage().window().maximize();
		return driverTemporario;
	}
	
	public static DesiredCapabilities recursosEspecificos() throws Exception{
		DesiredCapabilities recursos = RecursosParaDriver.carregar();	
		String diretorioDownload = Configuracoes.getConfiguracaoPrincipal("diretorio-default-download");
		Log.registrarInformacao(String.format("Diretório Default de Download [%s]", diretorioDownload));
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("download.default_directory", diretorioDownload);
		ChromeOptions options = new ChromeOptions();
		HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
		options.setExperimentalOption("prefs", chromePrefs);
		recursos.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
		recursos.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		recursos.setCapability(ChromeOptions.CAPABILITY, options);
		return recursos;		
	}
	
}
