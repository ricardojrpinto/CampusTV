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
import campustv.domain.Image;
import campustv.domain.New;
import campustv.domain.Video;
import campustv.repository.exceptions.RepositoryException;

public class DBNewRepository implements NewRepository {

	public void addNew(New n) throws RepositoryException {	
		try{
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
		             "where news.id_new = contents.id_content";
			
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
	
	public void removeNew(long newID) throws RepositoryException {
		try{
			Statement st = JDBCUtils.getStatement();
			st.addBatch("delete from contents where id_content = " + newID);
			st.executeBatch();
		} catch(SQLException e){
			throw new RepositoryException(e.getMessage());
		}
	}

	@Override
	public Iterator<Video> listVideosOfNews() throws RepositoryException {
		List<Video> videos = new ArrayList<Video>();

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
		List<Video> videos = new ArrayList<Video>();

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


}
