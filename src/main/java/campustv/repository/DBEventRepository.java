package campustv.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import campustv.dbutils.JDBCUtils;
import campustv.dbutils.SQLUtils;
import campustv.domain.Event;
import campustv.domain.Image;
import campustv.domain.Video;
import campustv.repository.exceptions.RepositoryException;

public class DBEventRepository implements EventRepository {

	public void addEvent(Event event) throws RepositoryException{
		try{
			ResultSet rs = DBRepositoryUtils.insertContent(event);
			rs.next();
			long id_content = rs.getLong(1);
			event.setContentID(id_content);
			DBRepositoryUtils.insertEvent(event);
			Iterator<Video> it =  event.getVideos();
			if(it.hasNext())
				DBRepositoryUtils.insertVideos(it, event);
			Iterator<Image> it2 = event.getImages();
			if(it2.hasNext())
				DBRepositoryUtils.insertImages(it2, event);
		} catch(SQLException e){
			throw new RepositoryException(e.getMessage());
			
		} catch (ParseException e) {
			throw new RepositoryException(e.getMessage());
		}
	}
	

	public Iterator<Event> listEvents() throws RepositoryException{
		
		List<Event> events = new ArrayList<Event>();

		try{
			Statement st = JDBCUtils.getStatement();
			String query = "select * " +
		             "from events, contents " +
		             "where events.id_event = contents.id_content";
			
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
	
	public Event getEventByID(long eventID) throws RepositoryException{
		try{
			Statement st = JDBCUtils.getStatement();
			String query = "select * " +
					"from events, contents " +
					"where events.id_event = contents.id_content and id_event = " + eventID;
			
			ResultSet rs = JDBCUtils.executeQuery(st, query);
			if(rs.next()){
				int eventID2 = rs.getInt(1);
				String eventDate = SQLUtils.timestampToString(rs.getTimestamp(2));
				String title = rs.getString(3);
				String latitude = rs.getString(4);
				String longitude = rs.getString(5);
				String local = DBEventRepository.getLocal(latitude, longitude);
				System.out.println("LOCATION");
				System.out.println(local);
				System.out.println(latitude);
				System.out.println(longitude);
				@SuppressWarnings("unused")
				int contentID = rs.getInt(6);
				String insertion_date = SQLUtils.timestampToString(rs.getTimestamp(7));
				String initial_issue = SQLUtils.timestampToString(rs.getTimestamp(8));
				String final_issue = SQLUtils.timestampToString(rs.getTimestamp(9));
				String description = rs.getString(10);
				long producerID = rs.getLong(11);
				String category = rs.getString(12);
				String subcategory = rs.getString(13);
				List<Image> images = DBRepositoryUtils.getImages(eventID2, new Event());
				List<Video> videos = DBRepositoryUtils.getVideos(eventID2, new Event());
				
				return new Event(eventID2, insertion_date, initial_issue,final_issue,description,category,subcategory,
						producerID,title,eventDate,latitude,longitude, local, images, videos);
			    
			}
			return null;
		}catch(SQLException e){
			throw new RepositoryException(e.getMessage());
		} catch (ParseException e) {
			throw new RepositoryException(e.getMessage());
		}
	}
	
	public void removeEvent(long eventID) throws RepositoryException{
		try{
			Statement st = JDBCUtils.getStatement();
			st.addBatch("delete from contents where id_content = " + eventID);
			st.executeBatch();
		} catch(SQLException e){
			throw new RepositoryException(e.getMessage());
		}
	}


	@Override
	public Iterator<Video> getVideosOfEvents() throws RepositoryException {
		List<Video> videos = new ArrayList<Video>();

		try{
			Statement st = JDBCUtils.getStatement();
			String query = "select * " +
		             "from videos, contents " +
		             "where videos.id_video = contents.id_content and videos.id_event is not NULL";
			
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

	
	public static String getLocal(String latitude, String longitude){
		try{
			Statement st = JDBCUtils.getStatement();
			String query = "select name " +
		             "from locations " +
		             "where latitude = '" + latitude + "' and longitude = '" + longitude + "'";
			
			ResultSet rs = JDBCUtils.executeQuery(st, query);
			if(rs.next()){
				return rs.getString(1);
			}
		}
		catch(SQLException e){
			;
		}
		return "";
	}
	
	@Override
	public Iterator<Video> getVideosOfEvent(long eventID)
			throws RepositoryException {
		
		List<Video> videos = new ArrayList<Video>();

		try{
			Statement st = JDBCUtils.getStatement();
			String query = "select * " +
		             "from videos, contents " +
		             "where videos.id_video = contents.id_content and videos.id_event = " + eventID;
			
			ResultSet rs = JDBCUtils.executeQuery(st, query);
			while(rs.next()){
				int videoID = rs.getInt(1);
				String title = rs.getString(2);
				String path = rs.getString(3);
				String checkpoint_I = SQLUtils.timestampToString(rs.getTimestamp(4));
				String checkpoint_F = SQLUtils.timestampToString(rs.getTimestamp(5));
				long newID = rs.getLong(6);
				long eventID2 = rs.getInt(7);
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
						producerID, title, checkpoint_I, checkpoint_F, path, newID, eventID2);
				videos.add(v);
			}
		}catch(SQLException e){
			throw new RepositoryException(e.getMessage());
		}
		return videos.iterator();
	}

}
