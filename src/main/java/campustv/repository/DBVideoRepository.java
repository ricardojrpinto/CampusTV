package campustv.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import campustv.dbutils.JDBCUtils;
import campustv.dbutils.SQLUtils;
import campustv.domain.Agenda;
import campustv.domain.Content;
import campustv.domain.Event;
import campustv.domain.Image;
import campustv.domain.Video;
import campustv.repository.exceptions.PermissionException;
import campustv.repository.exceptions.RepositoryException;
import campustv.repository.exceptions.SessionException;

public class DBVideoRepository implements VideoRepository {

	public void addVideo(Video v, String accessToken) throws RepositoryException, SessionException, PermissionException {
		try{
			if(DBRepositoryUtils.hasProducer(v.getProducerID())){
				if(DBRepositoryUtils.hasLoggedIn(v.getProducerID(), accessToken)){
					ResultSet rs = DBRepositoryUtils.insertContent(v);
					rs.next();
					long id_content = rs.getLong(1);
					v.setContentID(id_content);
					DBRepositoryUtils.insertVideo(v);
				}
				else
					throw new SessionException("User not logged in");
			}
			else
				throw new PermissionException("Don't have permissions");
		} catch(SQLException e){
			throw new RepositoryException(e.getMessage());
			
		} catch (ParseException e) {
			throw new RepositoryException(e.getMessage());
		}
	}

	public Iterator<Video> listVideos() throws RepositoryException {
		List<Video> videos = new ArrayList<Video>();

		try{
			Statement st = JDBCUtils.getStatement();
			String query = "select * " +
		             "from videos, contents " +
		             "where videos.id_video = contents.id_content and videos.id_event is NULL and videos.id_new is NULL order by contents.insertion_date ";
			
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

	@Override
	public Video getVideoByID(long videoID) throws RepositoryException {
		try{
			Statement st = JDBCUtils.getStatement();
			String query = "select * " +
					"from videos, contents " +
					"where videos.id_video = contents.id_content and id_video = " + videoID;
			
			ResultSet rs = JDBCUtils.executeQuery(st, query);
			if(rs.next()){
				int videoID2 = rs.getInt(1);
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
				return new Video(videoID2, insertion_date, initial_issue, final_issue, description, category, subcategory,
						producerID, title, checkpoint_I, checkpoint_F, path, newID, eventID);	    
			}
			return null;
		}catch(SQLException e){
			throw new RepositoryException(e.getMessage());
		} 
	}
	
	public void removeVideo(long videoID, long producerID, String accessToken) throws RepositoryException, PermissionException, SessionException {
		try{
			if(DBRepositoryUtils.hasProducer(producerID)){
				if(DBRepositoryUtils.hasLoggedIn(producerID, accessToken)){
					if(DBRepositoryUtils.hasOnwer(producerID, videoID)){
						Statement st = JDBCUtils.getStatement();
						st.addBatch("delete from contents where id_content = " + videoID);
						st.executeBatch();
					}
					else
						throw new PermissionException("User not owner of content");
				}
				else
					throw new SessionException("User not logged in");
			}
			else
				throw new PermissionException("Don't have permissions");
		} catch(SQLException e){
			throw new RepositoryException(e.getMessage());
		}
	}

	@Override
	public Iterator<Video> getVideosByAuthor(String author)
			throws RepositoryException {
		List<Video> videos = new ArrayList<Video>();

		try{
			Statement st = JDBCUtils.getStatement();
			String query = "select * " +
		             "from videos, contents, producers, users " +
		             "where videos.id_video = contents.id_content and producers.id_producer = users.is_user and users.name = '" + author + "'";
			
			
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

	@Override
	public Iterator<Agenda> getVideosDay(String scheduleDay)
			throws RepositoryException {
		
		List<Agenda> agendas = new LinkedList<Agenda>();

		try{
			Statement st = JDBCUtils.getStatement();
			String query = "select * " +
		             "from agenda, contents, videos" +
		             "where agenda.schedule_date = '" + scheduleDay + "' and agenda.id_content = contents.id_content and"
		             		+ "agenda.id_content = videos.id_video";
			
			ResultSet rs = JDBCUtils.executeQuery(st, query);
			while(rs.next()){
				long contentID = rs.getLong(1);
				
				String estimated_time = SQLUtils.timestampToString(rs.getTimestamp(3));
				String time_transmission = SQLUtils.timestampToString(rs.getTimestamp(4));
				
				String insertion_date = SQLUtils.timestampToString(rs.getTimestamp(6));
				String initial_issue = SQLUtils.timestampToString(rs.getTimestamp(7));
				String final_issue = SQLUtils.timestampToString(rs.getTimestamp(8));
				String description = rs.getString(9);
				long producerID = rs.getLong(10);
				String category = rs.getString(11);
				String subcategory = rs.getString(12);
				int videoID = rs.getInt(13);
				String title = rs.getString(14);
				String path = rs.getString(15);
				String checkpoint_I = SQLUtils.timestampToString(rs.getTimestamp(16));
				String checkpoint_F = SQLUtils.timestampToString(rs.getTimestamp(17));
				long newID = rs.getLong(18);
				long eventID = rs.getInt(19);
	
				Content c = new Video(videoID, insertion_date, initial_issue, final_issue, description, category, subcategory,
						producerID, title, checkpoint_I, checkpoint_F, path, newID, eventID);
				
				Agenda a = new Agenda(c, scheduleDay, estimated_time, time_transmission);
				agendas.add(a);
			}
		}catch(SQLException e){
			throw new RepositoryException(e.getMessage());
		}
		return agendas.iterator();
	}

}
