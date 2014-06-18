package crawlers.rest.fb;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class FBPageReaderTest {

	public static void main(String[] args) throws IOException {
		FBPageFeedReader pfr = new FBPageFeedReader();
		
		String pageId = "AEFCT";
		boolean limitByDate = false;
		int limit = -1;
		Date date = new GregorianCalendar(2014, Calendar.MAY, 18).getTime();
		
		
		System.out.println("Getting events...");
		JSONObject json = limitByDate ?
				pfr.getPageEventsSince(pageId, date) :
					pfr.getPageEvents(pageId, limit);
		JSONArray jsonArray = (JSONArray)json.get("data");
		//System.out.println(jsonArray.size()+" entries");
		PrintWriter pw = new PrintWriter("fb/events.json");
		String jsonString = json.toString();
		//System.out.println("string size: "+jsonString.length());
		pw.write(json.toString());
		pw.close();
		
//		System.out.println("Getting statuses...");
//		json = limitByDate ?
//				pfr.getPageStatusesSince(pageId, date) :
//					pfr.getPageStatuses(pageId, limit);
//		jsonArray = (JSONArray)json.get("data");
//		//System.out.println(jsonArray.size()+" entries");
//		pw = new PrintWriter("fb/statuses.json");
//		jsonString = json.toString();
//		//System.out.println("string size: "+jsonString.length());
//		pw.write(json.toString());
//		pw.close();
				
		System.out.println("Getting links...");
		json = limitByDate ?
				pfr.getPageLinksSince(pageId, date) :
					pfr.getPageLinks(pageId, limit);
		jsonArray = (JSONArray)json.get("data");
		//System.out.println(jsonArray.size()+" entries");
		pw = new PrintWriter("fb/links.json");
		jsonString = json.toString();
		//System.out.println("string size: "+jsonString.length());
		pw.write(json.toString());
		pw.close();
		
		System.out.println("Getting posts...");
		json = limitByDate ?
				pfr.getPagePostsSince(pageId, date) :
					pfr.getPagePosts(pageId, limit);
		jsonArray = (JSONArray)json.get("data");
		//System.out.println(jsonArray.size()+" entries");
		pw = new PrintWriter("fb/posts.json");
		jsonString = json.toString();
		//System.out.println("string size: "+jsonString.length());
		pw.write(json.toString());
		pw.close();
		
		System.out.println("Done!");
	}
	
	

}
