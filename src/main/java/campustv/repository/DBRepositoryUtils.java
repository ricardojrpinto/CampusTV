package campustv.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import campustv.dbutils.JDBCUtils;
import campustv.dbutils.SQLUtils;
import campustv.domain.Admin;
import campustv.domain.Bookmark;
import campustv.domain.Consumer;
import campustv.domain.Content;
import campustv.domain.Event;
import campustv.domain.GPS;
import campustv.domain.Image;
import campustv.domain.Menu;
import campustv.domain.New;
import campustv.domain.Preference;
import campustv.domain.Producer;
import campustv.domain.Session;
import campustv.domain.User;
import campustv.domain.Video;
import campustv.repository.exceptions.RepositoryException;

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

	public static Iterator<Event> getNotifications(long userID, GPS coord) throws SQLException, ParseException {
		List<Event> events = new LinkedList<Event>();
		
		Statement st = JDBCUtils.getStatement();
		String query = "select " +
				"from events, contents, preferences " +
				"where events.id_event = contents.id_content and events.latitude = '" + coord.getLatitude() + 
				"' and events.longitude = '" + coord.getLongitude() + "' preferences.id_user = " + userID + "contents.category = preferences.category "
						+ " and contents.subcategory = preferences.category and preferences.relevance = M or preferences.relevance = H" ;
		
		ResultSet rs = JDBCUtils.executeQuery(st, query);
		while(rs.next()){
			int eventID = rs.getInt(1);
			String eventDate = SQLUtils.timestampToString(rs.getTimestamp(2));
			String title = rs.getString(3);
			String latitude = rs.getString(4);
			String longitude = rs.getString(5);
			String local = DBEventRepository.getLocal(latitude, longitude);
			@SuppressWarnings("unused")
			int contentID = rs.getInt(6);
			String insertion_date = SQLUtils.timestampToString(rs.getTimestamp(7));
			String initial_issue = SQLUtils.timestampToString(rs.getTimestamp(8));
			String final_issue = SQLUtils.timestampToString(rs.getTimestamp(9));
			String description = rs.getString(10);
			long producerID = rs.getLong(11);
			String category = rs.getString(12);
			String subcategory = rs.getString(13);
			List<Image> images = DBRepositoryUtils.getImages(eventID, new Event());
			List<Video> videos = DBRepositoryUtils.getVideos(eventID, new Event());
			
			Event e =  new Event(eventID, insertion_date, initial_issue,final_issue,description,category,subcategory,
					producerID,title,eventDate,latitude,longitude, local, images, videos);
			events.add(e);
		}
		return events.iterator();
	
	}

	public static ResultSet insertUser(Object obj) throws SQLException{
		User user = (User) obj;
    	
		String sql = "INSERT INTO USERS"
				+ "(ID_USER, NAME, EMAIL) VALUES"
				+ "(nextval('SEQ_USERS'),?,?)";	
		
		PreparedStatement ps = JDBCUtils.getPrepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, user.getName());
		ps.setString(2, user.getEmail());
		ps.execute();
		
		return ps.getGeneratedKeys();
	}

	public static void insertAdmin(Admin admin) throws SQLException {
		String sql = "INSERT INTO ADMINS"
				+ "(ID_ADMIN) VALUES"
				+ "(?)";	
		PreparedStatement ps = JDBCUtils.getPrepareStatement(sql);
		ps.setLong(1, admin.getUserID());
		ps.execute();
	}
	
	public static void insertProducer(Producer producer) throws SQLException {
		String sql = "INSERT INTO PRODUCERS"
				+ "(ID_PRODUCER) VALUES"
				+ "(?)";	
		PreparedStatement ps = JDBCUtils.getPrepareStatement(sql);
		ps.setLong(1, producer.getUserID());
		ps.execute();
	}
	
	public static void insertConsumer(Consumer consumer) throws SQLException {
		String sql = "INSERT INTO CONSUMERS"
				+ "(ID_CONSUMER) VALUES"
				+ "(?)";	
		PreparedStatement ps = JDBCUtils.getPrepareStatement(sql);
		ps.setLong(1, consumer.getUserID());
		ps.execute();
	}

	public static long login(Session session) throws SQLException {
		Statement st = JDBCUtils.getStatement();
		String query = "select id_user from users where email = '" + session.getEmail() + "'";
		ResultSet rs = JDBCUtils.executeQuery(st, query);
		int userID = 0; 
		if(rs.next())
			userID = rs.getInt(1);
		String sql = "INSERT INTO SESSIONS"
				+ "(ID_SESSION, ID_USER, ACCESSTOKEN, EMAIL, CREATE_TIME, TOKEN_TTL) VALUES"
				+ "(nextval('SEQ_SESSIONS'),?,?,?,?,?)";	
		
		PreparedStatement ps = JDBCUtils.getPrepareStatement(sql);
		ps.setLong(1, userID);
		ps.setString(2, session.getAccessToken());
		ps.setString(3, session.getEmail());
		ps.setTimestamp(4, SQLUtils.stringToTimestamp(session.getCreateTime()));
		ps.setTimestamp(5, SQLUtils.stringToTimestamp(session.getTokenTTL()));
		ps.execute();
		
		return userID;
	}

	public static boolean verifyAccessToken(Session session) throws SQLException {
		Statement st = JDBCUtils.getStatement();
		String query = "select * from users where email = '" + session.getEmail() + "'";
		ResultSet rs = JDBCUtils.executeQuery(st, query);
		if(rs.next()){
			Timestamp tokenTTL = rs.getTimestamp(6);
			Date date= new Date();
			Timestamp current = new Timestamp(date.getTime());
			if(current.before(tokenTTL))
				return true;
			else
				return false;
		}
		return false;
		
	}

	public static int hasLoggedIn(Session session) throws SQLException {
		Statement st = JDBCUtils.getStatement();
		String query = "select count(*) from sessions where email= '" + session.getEmail() + "'";
		ResultSet rs = JDBCUtils.executeQuery(st, query);
		if(rs.next()){
			return rs.getInt(1);
		}
		return -1;
	}

	public static void removeAccessToken(String accessToken) throws SQLException {
		Statement st = JDBCUtils.getStatement();
		String sql = "delete from sessions where acesstoken = '" + accessToken + "'";
		st.addBatch(sql);
		st.executeBatch();
		
	}


	public static boolean hasAdmin(long userID) throws SQLException {
		Statement st = JDBCUtils.getStatement();
		String query = "select count(*) from admins where id_admin = " + userID;
		ResultSet rs = JDBCUtils.executeQuery(st, query);
		if(rs.next()){
			int res = rs.getInt(1);
			if(res == 1)
				return true;
			else 
				return false;
		}
		return false;
	}

	public static boolean hasLoggedIn(long userID, String accessToken) throws SQLException {
		Statement st = JDBCUtils.getStatement();
		String query = "select count(*) from sessions where id_user = " + userID + "and acesstoken = '" + accessToken + "'";
		ResultSet rs = JDBCUtils.executeQuery(st, query);
		if(rs.next()){
			int res = rs.getInt(1);
			if(res == 1)
				return true;
			else 
				return false;
		}
		return false;
	}

	public static boolean hasProducer(long userID) throws SQLException {
		Statement st = JDBCUtils.getStatement();
		String query = "select count(*) from producers where id_producer = " + userID;
		ResultSet rs = JDBCUtils.executeQuery(st, query);
		if(rs.next()){
			int res = rs.getInt(1);
			if(res == 1)
				return true;
			else 
				return false;
		}
		return false;
	}

	public static boolean hasOnwer(long producerID, long contentID) throws SQLException {
		Statement st = JDBCUtils.getStatement();
		String query = "select count(*) from contents where id_producer = " + producerID + "and id_content = " + contentID;
		ResultSet rs = JDBCUtils.executeQuery(st, query);
		if(rs.next()){
			int res = rs.getInt(1);
			if(res == 1)
				return true;
			else 
				return false;
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public static int countContentsBetweenDay(Timestamp day) throws SQLException {
		Statement st = JDBCUtils.getStatement();
		String query = "select count(*) from contents where '" + day + "' between initial_issue and final_issue" ;
		ResultSet rs = JDBCUtils.executeQuery(st, query);
		if(rs.next()){
			return rs.getInt(1);
		}
		return 0;
	}

	public static Iterator<New> getNewsOrdeyByInsertion() throws RepositoryException {
		NewRepository nr= new DBNewRepository();
		return nr.listNews();
	}

	public static Iterator<Video> getVideosOrderByInsertion() throws RepositoryException {
		VideoRepository vr= new DBVideoRepository();
		return vr.listVideos();
	}

	public static Iterator<New> getNewsBetweenDay(Timestamp day) throws RepositoryException {
		List<New> news = new ArrayList<New>();

		try{
			Statement st = JDBCUtils.getStatement();
			String query = "select * " +
		             "from news, contents " +
		             "where news.id_new = contents.id_content and '" + day + "' between initial_issue and final_issue"	;
			
			ResultSet rs = JDBCUtils.executeQuery(st, query);
			while(rs.next()){
				int newID = rs.getInt(1);
				String title = rs.getString(2);;
				@SuppressWarnings("unused")
				int contentID = rs.getInt(3);
				String insertion_date = SQLUtils.timestampToString(rs.getTimestamp(4));
				String initial_issue = SQLUtils.timestampToString(rs.getTimestamp(5));
				String final_issue = SQLUtils.timestampToString(rs.getTimestamp(6));
				String description = rs.getString(7);
				long producerID = rs.getLong(8);
				String category = rs.getString(9);
				String subcategory = rs.getString(10);
				List<Image> images = DBRepositoryUtils.getImages(newID, new New());
				List<Video> videos = DBRepositoryUtils.getVideos(newID, new New());
				
				New n = new New(newID, insertion_date, initial_issue, final_issue, 
						description, category, subcategory, producerID, title, images, videos); 
			    news.add(n);
			}
		}catch(SQLException e){
			throw new RepositoryException(e.getMessage());
		} catch (ParseException e) {
			throw new RepositoryException(e.getMessage());
		}
		return news.iterator();
	}

	public static Iterator<Video> getVideosBetweenDay(Timestamp day) throws RepositoryException {
		List<Video> videos = new ArrayList<Video>();

		try{
			Statement st = JDBCUtils.getStatement();
			String query = "select * " +
		             "from videos, contents " +
		             "where videos.id_video = contents.id_content and videos.id_event is NULL and videos.id_new is NULL "
		             + "and '" + day + "' between initial_issue and final_issue"	;
			
			ResultSet rs = JDBCUtils.executeQuery(st, query);
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
		}catch(SQLException e){
			throw new RepositoryException(e.getMessage());
		} 
		return videos.iterator();
	}

	public static Iterator<Event> getEventsBetweenDay(Timestamp day) throws RepositoryException {
		
		List<Event> events = new ArrayList<Event>();

		try{
			Statement st = JDBCUtils.getStatement();
			String query = "select * " +
		             "from events, contents" +
		             "where events.id_event = contents.id_content and '" + day + "' between initial_issue and final_issue"	;
			
			ResultSet rs = JDBCUtils.executeQuery(st, query);
			while(rs.next()){
				int eventID = rs.getInt(1);
				String eventDate = SQLUtils.timestampToString(rs.getTimestamp(2));;
				String title = rs.getString(3);
				String latitude = rs.getString(4);
				String longitude = rs.getString(5);
				String local = DBEventRepository.getLocal(latitude, longitude);
				@SuppressWarnings("unused")
				int contentID = rs.getInt(6);
				String insertion_date = SQLUtils.timestampToString(rs.getTimestamp(7));
				String initial_issue = SQLUtils.timestampToString(rs.getTimestamp(8));
				String final_issue = SQLUtils.timestampToString(rs.getTimestamp(9));
				String description = rs.getString(10);
				long producerID = rs.getLong(11);
				String category = rs.getString(12);
				String subcategory = rs.getString(13);
				List<Image> images = DBRepositoryUtils.getImages(eventID, new Event());
				List<Video> videos = DBRepositoryUtils.getVideos(eventID, new Event());
				
			    Event e = new Event(eventID, insertion_date, initial_issue,final_issue,description,category,subcategory,
						producerID,title,eventDate,latitude,longitude, local,  images, videos);
			    
			    events.add(e);
			}
		}catch(SQLException e){
			throw new RepositoryException(e.getMessage());
		} catch (ParseException e) {
			throw new RepositoryException(e.getMessage());
		}
		return events.iterator();
	}

	public static void insertProgramacao(Timestamp day) {
		
	}
	

}
