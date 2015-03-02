package kvalito.core.recursos;

import kvalito.core.Configuracoes;
import kvalito.core.Log;

import org.openqa.selenium.Proxy;

public class ProxyParaDriver {
	
	
	/**
	 * Verifica se o proxy configurado está habilitado.
	 * @return true Se o proxy estiver habilitado.
	 * @throws Exception
	 */
	public static boolean habilitado() throws Exception {
		String habilitado = Configuracoes.getConfiguracaoPrincipal("proxy-habilitado");
		return Boolean.parseBoolean(habilitado);
	}
	/**
	 * Retorna o proxy utilizado nas configurações do teste. <br>
	 * @return 
	 * @throws Exception
	 */
	public Proxy obterProxy() throws Exception {
		Log.registrarInformacao("Carregando as configurações do proxy");
		String proxyHost = Configuracoes.getConfiguracaoPrincipal("proxy-host"); 
		String semProxy = Configuracoes.getConfiguracaoPrincipal("sem-proxy-para"); 
		
		Proxy proxy = new Proxy();
		proxy.setHttpProxy(proxyHost)
			.setFtpProxy(proxyHost)
			.setSslProxy(proxyHost)
			.setAutodetect(false)
			.setNoProxy(semProxy);
		
		return proxy;
	}
}
