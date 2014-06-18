package crawlers.mail;

import java.io.IOException;

import crawlers.ConfigurationException;

public interface EmailService {

	void loadCredentials() throws IOException, ConfigurationException;
	
	void loadCredentials(String sourcePath) throws IOException,ConfigurationException;
	
	void initService() throws IOException;
}
