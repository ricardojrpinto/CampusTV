package campustv.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import campustv.dbutils.JDBCUtils;
import campustv.dbutils.SQLUtils;
import campustv.domain.Bookmark;
import campustv.domain.Content;
import campustv.domain.Event;
import campustv.domain.Image;
import campustv.domain.Menu;
import campustv.domain.New;
import campustv.domain.Preference;
import campustv.domain.Video;

public class DBRepositoryUtils {

	
	 public static ResultSet insertContent(Object obj) throws SQLException, ParseException {
	    	Content c = (Content) obj;
	    	
			String sql = "INSERT INTO CONTENTS"
					+ "(ID_CONTENT, INSERTION_DATE, INITIAL_ISSUE, FINAL_ISSUE, DESCRIPTION, ID_PRODUCER, CATEGORY, SUBCATEGORY) VALUES"
					+ "(nextval('SEQ_CONTENTS'),?,?,?,?,?,?,?)";	
			
			PreparedStatement ps = JDBCUtils.getPrepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		 
			ps.setTimestamp(1, SQLUtils.getCurrentTimeStamp());
			ps.setTimestamp(2, SQLUtils.stringToTimestamp(c.getInitial_issue()));
			ps.setTimestamp(3, SQLUtils.stringToTimestamp(c.getFinal_issue()));
			ps.setString(4, c.getDescription());
			ps.setLong(5, c.getProducerID());
			ps.setString(6, c.getCategory());
			ps.setString(7, c.getSubcategory());
			ps.execute();
			
			return ps.getGeneratedKeys();
			
		}

	public static void insertEvent(Event event) throws SQLException, ParseException {
		String sql = "INSERT INTO EVENTS"
				+ "(ID_EVENT, EVENT_DATE, TITLE, LATITUDE, LONGITUDE) VALUES"
				+ "(?,?,?,?,?)";
		PreparedStatement ps = JDBCUtils.getPrepareStatement(sql);
		ps.setLong(1,event.getContentID());
		ps.setTimestamp(2, SQLUtils.stringToTimestamp(event.getEventDate()));
		ps.setString(3, event.getTitle());
		ps.setString(4, event.getLatitude());
		ps.setString(5, event.getLongitude());
	    ps.execute();
	}

	public static void insertVideos(Iterator<Video> it, Object obj) throws SQLException, ParseException {
		String sql = ""; long id = 0;
		if(obj instanceof Event){
			sql =  "INSERT INTO VIDEOS"
					+ "(ID_VIDEO, TITLE, VIDEO_PATH, CHECKPOINT_I, CHECKPOINT_F, ID_NEW, ID_EVENT) VALUES"
					+ "(?,?,?,?,?,NULL,?)";
			Event e = (Event) obj;
			id = e.getContentID();
		}else if(obj instanceof New){
			sql =  "INSERT INTO VIDEOS"
					+ "(ID_VIDEO, TITLE, VIDEO_PATH, CHECKPOINT_I, CHECKPOINT_F, ID_NEW, ID_EVENT) VALUES"
					+ "(?,?,?,?,?,?,NULL)";
			New n = (New) obj;
			id = n.getContentID();
		}else if(obj instanceof Video){
			sql =  "INSERT INTO VIDEOS"
					+ "(ID_VIDEO, TITLE, VIDEO_PATH, CHECKPOINT_I, CHECKPOINT_F, ID_NEW, ID_EVENT) VALUES"
					+ "(?,?,?,?,?,NULL,NULL)";
			
		}
		Content c = (Content) obj;
		while(it.hasNext()){
			Video v = it.next();
			PreparedStatement ps = JDBCUtils.getPrepareStatement(sql);
			v.setInitial_issue(c.getInitial_issue());
			v.setFinal_issue(c.getFinal_issue());
			v.setCategory(c.getCategory());
			v.setSubcategory(c.getSubcategory());
			v.setProducerID(c.getProducerID());
			ResultSet rs = insertContent(v);
			rs.next();
			v.setContentID(rs.getLong(1));
			ps.setLong(1, v.getContentID());
			ps.setString(2, v.getTitle());
			ps.setString(3, v.getPath());
			ps.setTimestamp(4, SQLUtils.stringToTimestamp(v.getCheckPoint_I()));
			ps.setTimestamp(5, SQLUtils.stringToTimestamp(v.getCheckPoint_F()));
			if(id != 0)
				ps.setLong(6, id);
			ps.execute();
		}
	}

	public static void insertImages(Iterator<Image> it, Object obj ) throws SQLException {
		String sql = ""; long id = 0;
		if(obj instanceof Event){
			sql =  "INSERT INTO IMAGES"
					+ "(ID_IMAGE, IMAGE_PATH, ID_NEW, ID_EVENT) VALUES"
					+ "(nextval('SEQ_IMAGES'),?,NULL,?)";
			Event e = (Event) obj;
			id = e.getContentID();
		}else if(obj instanceof New){
			sql =  "INSERT INTO IMAGES"
					+ "(ID_IMAGE, IMAGE_PATH, ID_NEW, ID_EVENT) VALUES"
					+ "(nextval('SEQ_IMAGES'),?,?,NULL)";
			New n = (New) obj;
			id = n.getContentID();
		}
		while(it.hasNext()){
			Image img = it.next();
			PreparedStatement ps = JDBCUtils.getPrepareStatement(sql);
			ps.setString(1, img.getPath());
			ps.setLong(2, id);
			ps.execute();
		}
	}

	public static List<Image> getImages(long id, Object obj) throws SQLException {
		List<Image> images = new ArrayList<Image>();
		String sql = "";
		if(obj instanceof Event)
			sql = "select * from images where id_event = " + id;
		else if(obj instanceof New)
			sql = "select * from images where id_new = " + id;
		
		Statement st = JDBCUtils.getStatement();
		ResultSet rs = JDBCUtils.executeQuery(st, sql);
		while(rs.next()){
			long imageID = rs.getLong(1);
			String path = rs.getString(2);
			long newID = rs.getLong(3);
			long eventID = rs.getInt(4);
			Image img = new Image(imageID, path, newID, eventID);
		    images.add(img);
		}	
		return images;
	}

	public static List<Video> getVideos(long id, Object obj) throws SQLException, ParseException {
		List<Video> videos = new ArrayList<Video>();
		String sql = "";
		if(obj instanceof Event)
			sql = "select * from videos, contents where  id_content = id_video and id_event = " + id;
		else if(obj instanceof New)
			sql = "select * from videos, contents where  id_content = id_video and id_new = " + id;
		else if(obj instanceof Video)
			sql = "select * from videos, contents where  id_content = id_video and id_video = " + id;
		
		Statement st = JDBCUtils.getStatement();
		ResultSet rs = JDBCUtils.executeQuery(st, sql);
		while(rs.next()){
			int videoID = rs.getInt(1);
			String title = rs.getString(2);
			String path = rs.getString(3);
			String checkpoint_I = SQLUtils.timestampToString(rs.getTimestamp(4));
			String checkpoint_F = SQLUtils.timestampToString(rs.getTimestamp(5));
			long newID = rs.getLong(6);
			long eventID = rs.getInt(7);
			@SuppressWarnings("unused")
			int contentID = rs.getInt(8);
			String insertion_date = SQLUtils.timestampToString(rs.getTimestamp(9));
			String initial_issue = SQLUtils.timestampToString(rs.getTimestamp(10));
			String final_issue = SQLUtils.timestampToString(rs.getTimestamp(11));
			String description = rs.getString(12);
			long producerID = rs.getLong(13);
			String category = rs.getString(14);
			String subcategory = rs.getString(15);	
			Video v = new Video(videoID, insertion_date, initial_issue, final_issue, description, category, subcategory,
					producerID, title, checkpoint_I, checkpoint_F, path, newID, eventID);
			videos.add(v);
		}
		return videos;
	}

	public static void insertNew(New n) throws SQLException {
		String sql = "INSERT INTO NEWS"
				+ "(ID_NEW, TITLE) VALUES"
				+ "(?,?)";
		PreparedStatement ps = JDBCUtils.getPrepareStatement(sql);
		ps.setLong(1,n.getContentID());
		ps.setString(2, n.getTitle());
	    ps.execute();
	}

	public static void insertMenu(Menu m) throws SQLException {
		String sql = "INSERT INTO MENUS"
				+ "(ID_MENU, RESTAURANT_NAME) VALUES"
				+ "(?,?)";
		PreparedStatement ps = JDBCUtils.getPrepareStatement(sql);
		ps.setLong(1,m.getContentID());
		ps.setString(2, m.getRestaurantName());
	    ps.execute();
	}

	public static void insertVideo(Video v) throws SQLException, ParseException {
		String sql =  "INSERT INTO VIDEOS"
				+ "(ID_VIDEO, TITLE, VIDEO_PATH, CHECKPOINT_I, CHECKPOINT_F, ID_NEW, ID_EVENT) VALUES"
				+ "(?,?,?,?,?,NULL,NULL)";
		
		PreparedStatement ps = JDBCUtils.getPrepareStatement(sql);
		ps.setLong(1, v.getContentID());
		ps.setString(2, v.getTitle());
		ps.setString(3, v.getPath());
		ps.setTimestamp(4, SQLUtils.stringToTimestamp(v.getCheckPoint_I()));
		ps.setTimestamp(5, SQLUtils.stringToTimestamp(v.getCheckPoint_F()));
		ps.execute();
	}

	public static void insertBookmarks(long userID, Iterator<Bookmark> it) throws SQLException{
		String sql = "INSERT INTO BOOKMARKS"
				+ "(ID_CONTENT, ID_USER, TIME_STAMP) VALUES"
				+ "(?,?,?)";
		
		while(it.hasNext()){
			Bookmark b = it.next();
			try{
				PreparedStatement ps = JDBCUtils.getPrepareStatement(sql);
				ps.setLong(1, b.getContentID());
				ps.setLong(2, userID);
				ps.setTimestamp(3, SQLUtils.stringToTimestamp(b.getTimeStamp()));
				ps.execute();
			}catch(SQLException e){
				;
			}
		}
	}

	public static void removeBookmarks(long userID, Iterator<Bookmark> it) throws SQLException{
		String sql = "";
		while(it.hasNext()){
			Bookmark b = it.next();
			try{
				Statement st = JDBCUtils.getStatement();
				sql = "delete from bookmarks where id_user = " + userID + " and id_content = " + b.getContentID();
				st.addBatch(sql);
				st.executeBatch();
			}catch(SQLException e){
				;
			}
		}
	}

	public static void setPreferences(long userID, Iterator<Preference> it) throws SQLException{
		String sql = "";
		while(it.hasNext()){
			Preference p = it.next();
			try{
				Statement st = JDBCUtils.getStatement();
				sql = "update preferences set relevance = '" + p.getRelevance() + "' where user_id = " + userID + 
							" and category = '" + p.getCategory() + "' and subcategory = '" + p.getSubCategory() + "'";
				st.addBatch(sql);
				st.executeBatch();
			}catch(SQLException e){
				;
			}
		}
	}

}
