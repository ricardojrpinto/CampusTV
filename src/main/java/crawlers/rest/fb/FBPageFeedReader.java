package crawlers.rest.fb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.scribe.builder.api.FacebookApi;

import crawlers.rest.AppSecurityManager;
import crawlers.rest.FileSecurityManager;
import crawlers.rest.SecuritySourceException;

public class FBPageFeedReader {
	
	public static final String BASE_METHOD_URL = "https://graph.facebook.com/v2.0/";
	
	private String accessToken;
	private boolean initialized;
	
	public FBPageFeedReader() {
		accessToken = "";
		initialized = false;
	}
	

	/*
	 * Initializes the API service.
	 */
	protected void initService() {
		
		AppSecurityManager secManager= new FileSecurityManager();
		String appSecret = "", appID = "";
		try{
			secManager.loadSource();
			appSecret = secManager.getAppSecret();
			appID = secManager.getAppKey();
		
			if(appSecret == null || appID == null) {
				return;
			}
		
			URL requestUrl = new URL( new FacebookApi().getAccessTokenEndpoint() 
					+ "?client_id="+appID+
					"&client_secret="+appSecret+
					"&grant_type=client_credentials");
			HttpsURLConnection conn = (HttpsURLConnection) requestUrl.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			
			accessToken = getResponseBody(conn);
			
		} catch (SecuritySourceException | IOException e) {
			System.err.println(e.getMessage());
			return;
		}
		
		initialized = true;
	}


	/********PUBLIC METHODS**********/
	
//	/**
//	 * Requests the most recent statuses of a page given an optional limit of entries
//	 * to request. The limit may be used for efficiency purposes.
//	 * @param pageId - the ID of the page
//	 * @param limit - the limit of entries; set to 0 or less, to use the default value
//	 * @return a JSONObject with the statuses information
//	 * @throws IOException if an IO error occurs
//	 */
//	public JSONObject getPageStatuses(String pageId, int limit) throws IOException {
//		if(!initialized) {
//			initService();
//		}
//		FBQuery query = new FBQuery(pageId+"/statuses");
//		if(limit > 0) {
//			query.addParameter("limit", ""+limit);
//		}
//		query.build();
//		
//		return getPageFeed(query);
//	}
//	
//	/**
//	 * Same as getPageStatuses, but the request is limited by a timestamp.
//	 */
//	public JSONObject getPageStatusesSince(String pageId, Date date) throws IOException {
//		if(!initialized) {
//			initService();
//		}
//		FBQuery query = new FBQuery(pageId+"/statuses");
//		query.addParameter("since", ""+date.getTime());
//		query.build();
//		
//		return getPageFeed(query);
//	}
	
	/**
	 * Requests the most recent events of a page given an optional limit of entries
	 * to request. The limit may be used for efficiency purposes.
	 * @param pageId - the ID of the page
	 * @param limit - the limit of entries; set to 0 or less, to use the default value
	 * @return a JSONObject with the events information
	 * @throws IOException if an IO error occurs
	 */
	public JSONObject getPageEvents(String pageId, int limit) throws IOException{
		if(!initialized) {
			initService();
		}
		FBQuery query = new FBQuery(pageId+"/events");
		if(limit > 0) {
			query.addParameter("limit", ""+limit);
		}
		query.build();
		
		return getPageFeed(query);
	}
	
	/**
	 * Same as getPageEvents, but the request is limited by a timestamp.
	 */
	public JSONObject getPageEventsSince(String pageId, Date date) throws IOException{
		if(!initialized) {
			initService();
		}
		
		FBQuery query = new FBQuery(pageId+"/events");
		query.addParameter("since", ""+date.getTime());
		query.build();
		
		return getPageFeed(query);
	}

	/**
	 * Requests the most recent links of a page given an optional limit of entries
	 * to request. The limit may be used for efficiency purposes.
	 * @param pageId - the ID of the page
	 * @param limit - the limit of entries; set to 0 or less, to use the default value
	 * @return a JSONObject with the links information
	 * @throws IOException if an IO error occurs
	 */
	public JSONObject getPageLinks(String pageId, int limit) throws IOException{
		if(!initialized) {
			initService();
		}
		FBQuery query = new FBQuery(pageId+"/links");
		if(limit > 0) {
			query.addParameter("limit", ""+limit);
		}
		query.build();
		
		return getPageFeed(query);
	}
	
	/**
	 * Same as getPageLinks, but the request is limited by a timestamp.
	 */
	public JSONObject getPageLinksSince(String pageId, Date date) throws IOException{
		if(!initialized) {
			initService();
		}
		
		FBQuery query = new FBQuery(pageId+"/links");
		query.addParameter("since", ""+date.getTime());
		query.build();
		
		return getPageFeed(query);
	}
	
	/**
	 * Requests the most recent posts of a page given an optional limit of entries
	 * to request. This method doesn't differentiate between the types of post.
	 * The limit may be used for efficiency purposes.
	 * @param pageId - the ID of the page
	 * @param limit - the limit of entries; set to 0 or less, to use the default value
	 * @return a JSONObject with the posts information
	 * @throws IOException if an IO error occurs
	 */
	public JSONObject getPagePosts(String pageId, int limit) throws IOException{
		if(!initialized) {
			initService();
		}
		
		FBQuery query = new FBQuery(pageId+"/posts");
		if(limit > 0) {
			query.addParameter("limit", ""+limit);
		}
		query.build();
		
		return getPageFeed(query);
	}
	
	/**
	 * Same as getPagePosts, but the request is limited by a timestamp.
	 */
	public JSONObject getPagePostsSince(String pageId, Date date) throws IOException{
		if(!initialized) {
			initService();
		}
		
		FBQuery query = new FBQuery(pageId+"/posts");
		query.addParameter("since", ""+date.getTime());
		query.build();
		
		return getPageFeed(query);
	}

	
	/****** AUXILIARY METHODS ********/
	
	private JSONObject getPageFeed(FBQuery query) throws IOException {
		URL requestUrl;
		try {
			requestUrl = new URL(BASE_METHOD_URL +
									query.toString() +
									(query.hasParameters() ? "&" : "?")+
									accessToken);
		} catch (MalformedURLException e) {
			System.err.println(e.getMessage());
			return null;
		}
		System.out.println("REQUEST URL: "+requestUrl.toString());
		HttpsURLConnection conn = (HttpsURLConnection) requestUrl.openConnection();
		conn.setRequestMethod("GET");
		conn.connect();
		
		String body = this.getResponseBody(conn);
		JSONObject jsonbody;
		try {
			JSONParser jsonparser = new JSONParser();
			jsonbody = (JSONObject) jsonparser.parse(body);
		} catch (ParseException e) {
			System.err.println(e.getMessage());
			return null;
		}
		
		return jsonbody;
	}


	private String getResponseBody(HttpURLConnection conn) throws IOException {
		
		InputStream connInStream = (conn.getResponseCode() >= 400) ?
									conn.getErrorStream() :
										conn.getInputStream();
									
		BufferedReader connReader = new BufferedReader(
				new InputStreamReader(connInStream));
		StringWriter fout = new StringWriter();
		char[] buf = new char[2048];
		int r;
		while((r = connReader.read(buf,0,buf.length)) > -1) {
			fout.write(buf, 0 , r);
		}
		connReader.close();
		fout.close();
		return fout.toString();
	}

}
