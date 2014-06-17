package crawlers.rest;

import java.io.IOException;

public interface FBAppSecurityManager {

	 void loadSource() 
			 throws IOException;
	 
	 void loadSource(String path) 
			 throws IOException;
	 
	 /**
	  * 
	  * @return the app key, or null if not found
	  * @throws SecuritySourceException if any problem with 
	  * the source (e.g.file or database) is detected
	  */
	 String getAppKey() 
			 throws SecuritySourceException;
	 
	 /**
	  * 
	  * @return the app secret, or null if not found
	  * @throws SecuritySourceException if any problem with 
	  * the source (e.g.file or database) is detected
	  */
	 String getAppSecret() 
			 throws SecuritySourceException;
}
