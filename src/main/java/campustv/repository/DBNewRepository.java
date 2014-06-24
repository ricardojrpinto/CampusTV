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
import campustv.domain.New;
import campustv.domain.Video;
import campustv.repository.exceptions.PermissionException;
import campustv.repository.exceptions.RepositoryException;
import campustv.repository.exceptions.SessionException;

public class DBNewRepository implements NewRepository {

	public void addNew(New n, String accessToken) throws RepositoryException, SessionException, PermissionException {	
		try{
			if(DBRepositoryUtils.hasProducer(n.getProducerID())){
				if(DBRepositoryUtils.hasLoggedIn(n.getProducerID(), accessToken)){
					ResultSet rs = DBRepositoryUtils.insertContent(n);
					rs.next();
					long id_content = rs.getLong(1);
					n.setContentID(id_content);
					DBRepositoryUtils.insertNew(n);
					Iterator<Video> it =  n.getVideos();
					if(it.hasNext())
						DBRepositoryUtils.insertVideos(it, n);
					Iterator<Image> it2 = n.getImages();
					if(it2.hasNext())
						DBRepositoryUtils.insertImages(it2, n);
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

	public Iterator<New> listNews() throws RepositoryException {
		List<New> news = new ArrayList<New>();

		try{
			Statement st = JDBCUtils.getStatement();
			String query = "select * " +
		             "from news, contents " +
		             "where news.id_new = contents.id_content order by contents.insertion_date ";
			
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

	public New getNewByID(long newID) throws RepositoryException {
		try{
			Statement st = JDBCUtils.getStatement();
			String query = "select * " +
					"from news, contents " +
					"where news.id_new = contents.id_content and id_new = " + newID;
			
			ResultSet rs = JDBCUtils.executeQuery(st, query);
			if(rs.next()){
				int newID2 = rs.getInt(1);
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
				
				return  new New(newID2, insertion_date, initial_issue, final_issue, 
						description, category, subcategory, producerID, title, images, videos);
			}
			return null;
		}catch(SQLException e){
			throw new RepositoryException(e.getMessage());
		} catch (ParseException e) {
			throw new RepositoryException(e.getMessage());
		}
	}
	
	public void removeNew(long newID, long producerID, String accessToken) throws RepositoryException, PermissionException, SessionException {
		try{
			if(DBRepositoryUtils.hasProducer(producerID)){
				if(DBRepositoryUtils.hasLoggedIn(producerID, accessToken)){
					if(DBRepositoryUtils.hasOnwer(producerID, newID)){
						Statement st = JDBCUtils.getStatement();
						st.addBatch("delete from contents where id_content = " + newID);
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
	public Iterator<Video> listVideosOfNews() throws RepositoryException {
		List<Video> videos = new LinkedList<Video>();

		try{
			Statement st = JDBCUtils.getStatement();
			String query = "select * " +
		             "from videos, contents " +
		             "where videos.id_video = contents.id_content and videos.id_new is not NULL";
			
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
	public Iterator<Video> listVideosOfNew(long newID)
			throws RepositoryException {
		List<Video> videos = new LinkedList<Video>();

		try{
			Statement st = JDBCUtils.getStatement();
			String query = "select * " +
		             "from videos, contents " +
		             "where videos.id_video = contents.id_content and videos.id_new = " + newID;
			
			ResultSet rs = JDBCUtils.executeQuery(st, query);
			while(rs.next()){
				int videoID = rs.getInt(1);
				String title = rs.getString(2);
				String path = rs.getString(3);
				String checkpoint_I = SQLUtils.timestampToString(rs.getTimestamp(4));
				String checkpoint_F = SQLUtils.timestampToString(rs.getTimestamp(5));
				long newID2 = rs.getLong(6);
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
						producerID, title, checkpoint_I, checkpoint_F, path, newID2, eventID);
				videos.add(v);
			}
		}catch(SQLException e){
			throw new RepositoryException(e.getMessage());
		}
		return videos.iterator();
	}


	public Iterator<New> getNewsByAuthor(String author)
			throws RepositoryException {
		List<New> news = new LinkedList<New>();

		try{
			Statement st = JDBCUtils.getStatement();
			String query = "select * " +
		             "from news, contents, producers, users " +
		             "where news.id_new = contents.id_content and producers.id_producer = users.is_user and users.name = '" + author + "'";
			
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

	@Override
	public Iterator<Agenda> getNewsDay(String scheduleDay)
			throws RepositoryException {
		List<Agenda> agendas = new LinkedList<Agenda>();

		try{
			Statement st = JDBCUtils.getStatement();
			String query = "select * " +
		             "from agenda, contents, news" +
		             "where agenda.schedule_date = '" + scheduleDay + "' and agenda.id_content = contents.id_content and"
		             		+ "agenda.id_content = news.id_news";
			
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
				long newID = rs.getLong(13);
				String title = rs.getString(14);;
				List<Image> images = DBRepositoryUtils.getImages(newID, new New());
				List<Video> videos = DBRepositoryUtils.getVideos(newID, new New());
				Content c = new New(newID, insertion_date, initial_issue, final_issue, 
						description, category, subcategory, producerID, title, images, videos); 
				
				Agenda a = new Agenda(c, scheduleDay, estimated_time, time_transmission);
				agendas.add(a);
			}
		}catch(SQLException e){
			throw new RepositoryException(e.getMessage());
		} catch (ParseException e) {
			throw new RepositoryException(e.getMessage());
		}
		return agendas.iterator();
	}


}
