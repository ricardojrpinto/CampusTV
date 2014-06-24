package crawlers.rest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.scribe.builder.api.FacebookApi;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

public class FBPageFeedReader {
	
	
	private OAuthService oauthService;
	private Token accessToken;
	

	public boolean initService() {
		
		FBAppSecurityManager secManager= new FileSecurityManager();
		String appSecret = "", appID = "";
		try{
			secManager.loadSource();
			appSecret = secManager.getAppSecret();
			appID = secManager.getAppKey();
		
			if(appSecret == null || appID == null) {
				return false;
			}
		
			URL requestUrl = new URL( new FacebookApi().getAccessTokenEndpoint() 
					+ "?client_id="+appID+
					"&client_secret="+appSecret+
					"&grant_type=client_credentials");
			HttpsURLConnection conn = (HttpsURLConnection) requestUrl.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			System.out.println(conn.getResponseMessage());
			System.out.println(conn.getContentType());
			Map<String,List<String>> headerFields = conn.getHeaderFields();
			for(String hf: headerFields.keySet()) {
				System.out.print(hf +": ");
				System.out.println(conn.getHeaderField(hf));
			}
			
			InputStream connInStream = conn.getInputStream();
			FileOutputStream fout = new FileOutputStream("accessToken");
			byte[] buf = new byte[2048];
			int r;
			while((r = connInStream.read(buf,0,buf.length)) > -1) {
				fout.write(buf,0,r);
			}
			connInStream.close();
			fout.close();
				
			
			
		} catch (SecuritySourceException | IOException e) {
			System.err.println(e.getMessage());
			return false;
		}
//		oauthService = new ServiceBuilder().provider(FacebookApi.class).apiKey(appID)
//				.apiSecret(appSecret).build();
//		this.getAccessToken();
		
		
		return true;
	}


	private void getAccessToken() {
		String endpoint = new FacebookApi().getAccessTokenEndpoint();
		
	}

}
