package crawlers.rest;

import java.io.IOException;
import java.io.FileReader;
import java.util.Properties;

class FileSecurityManager implements FBAppSecurityManager{
	
	static final String DEFAULT_CONF_FILE = "config/fb/app_credentials.conf";
	
	private Properties fbconf;
	private boolean loaded;

	public FileSecurityManager(){
		fbconf = new Properties();
		loaded = false;
	}
	
	public void loadSource() throws IOException{
		fbconf.load(new FileReader(DEFAULT_CONF_FILE));
		loaded = true;
	}
	
	public void loadSource(String path) throws IOException{
		fbconf.load(new FileReader(path));
		loaded = true;
	}
	
	public String getAppKey() throws SecuritySourceException {
		if(!loaded) {
			throw new SecuritySourceException("The security source is not loaded.");
		}
		return (String) fbconf.get("app_id");
	}
	
	public String getAppSecret() throws SecuritySourceException {
		if(!loaded) {
			throw new SecuritySourceException("The security source is not loaded.");
		}
		return (String) fbconf.get("app_secret");
	}
}
