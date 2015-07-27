package kvalito.core.webdrivers;

import kvalito.core.Configuracoes;
import kvalito.core.Log;
import kvalito.core.recursos.RecursosParaDriver;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

public class FabricaFirefoxDriver {
	public static FirefoxDriver instanciar() throws Exception {
		FirefoxDriver driverTemporario = new FirefoxDriver(recursosEspecificos());	
		driverTemporario.manage().window().maximize();
		return driverTemporario;
	}
	
	public static DesiredCapabilities recursosEspecificos() throws Exception{
		DesiredCapabilities recursos = RecursosParaDriver.carregar();		
		String diretorioDownload = Configuracoes.getConfiguracaoPrincipal("diretorio-default-download");
		Log.registrarInformacao(String.format("Diretório Default de Download [%s]", diretorioDownload));
		FirefoxProfile perfilFirefox = new FirefoxProfile();
		perfilFirefox.setPreference("browser.download.dir", diretorioDownload);
		perfilFirefox.setPreference("browser.download.folderList", 2);
		perfilFirefox.setPreference("browser.helperApps.alwaysAsk.force", false);
		perfilFirefox.setPreference("browser.helperApps.neverAsk.saveToDisk", tiposParaSalvarSemPerguntar());
		recursos.setCapability(FirefoxDriver.PROFILE, perfilFirefox);
		return recursos;		
	}
	
	public static String tiposParaSalvarSemPerguntar(){
		StringBuilder tipos = new StringBuilder();
			tipos.append("text/plain,");
			tipos.append("application/zip,");
			tipos.append("application/msword,");
			tipos.append("application/octet-stream,");
			tipos.append("application/powerpoint,");
			tipos.append("application/excel,");
			tipos.append("video/avi,");
			tipos.append("video/quicktime,");
			tipos.append("video/mp4,");
			tipos.append("video/mpeg,");
			tipos.append("image/png,");
			tipos.append("image/bmp,");
			tipos.append("image/jpeg,");
			tipos.append("audio/midi,");
			tipos.append("audio/mpeg3");
			
		return tipos.toString();
		
	}
	
}
