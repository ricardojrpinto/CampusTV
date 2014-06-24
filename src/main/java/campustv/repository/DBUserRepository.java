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
import campustv.domain.Bookmark;
import campustv.domain.Consumer;
import campustv.domain.Event;
import campustv.domain.GPS;
import campustv.domain.Preference;
import campustv.repository.exceptions.RepositoryException;
import campustv.repository.exceptions.SessionException;

public class DBUserRepository implements UserRepository{

	@Override
	public Iterator<Preference> listPreferences(long userID, String accessToken) throws RepositoryException, SessionException {
		
		List<Preference> preferences = new ArrayList<Preference>();

		try{
			if(DBRepositoryUtils.hasLoggedIn(userID, accessToken)){
				Statement st = JDBCUtils.getStatement();
				String query = "select * " +
			             "from preferences " +
			             "where id_user = " + userID;
				
				ResultSet rs = JDBCUtils.executeQuery(st, query);
				while(rs.next()){
					char relevance = (char) rs.getByte(2);
					String category = rs.getString(3);
					String subCategory = rs.getString(4);
					
					Preference p = new Preference(userID, relevance, category, subCategory);
					preferences.add(p);
					
				}
			}
			else
				throw new SessionException("User not logged in");
			
		}catch(SQLException e){
			throw new RepositoryException(e.getMessage());
		}
		return preferences.iterator();
	}

	@Override
	public Iterator<Bookmark> listBookmarks(long userID, String accessToken)
			throws RepositoryException, SessionException {
		List<Bookmark> bookmarks = new ArrayList<Bookmark>();
		try{
			if(DBRepositoryUtils.hasLoggedIn(userID, accessToken)){
				Statement st = JDBCUtils.getStatement();
				String query = "select * " +
			             "from bookmarks " +
			             "where id_user = " + userID;
				
				ResultSet rs = JDBCUtils.executeQuery(st, query);
				while(rs.next()){
					long contentID = rs.getLong(1);
					String timestamp = SQLUtils.timestampToString(rs.getTimestamp(3)); 			
					Bookmark b = new Bookmark(contentID, userID, timestamp);
					bookmarks.add(b);
				}
			}
			else
				throw new SessionException("User not logged in");
		}catch(SQLException e){
			throw new RepositoryException(e.getMessage());
		}
		return bookmarks.iterator();
	}

	@Override
	public void addBookmarks(long userID, Iterator<Bookmark> it, String accessToken) throws RepositoryException, SessionException {
		try{
			if(DBRepositoryUtils.hasLoggedIn(userID, accessToken)){
				DBRepositoryUtils.insertBookmarks(userID, it);
			}
			else
				throw new SessionException("User not logged in");
		} catch(SQLException e){
			throw new RepositoryException(e.getMessage());
		}
	}

	@Override
	public void removeBookmarks(long userID, Iterator<Bookmark> it, String accessToken)
			throws RepositoryException, SessionException {
		try{
			if(DBRepositoryUtils.hasLoggedIn(userID, accessToken)){
				DBRepositoryUtils.removeBookmarks(userID, it);
			}
			else
				throw new SessionException("User not logged in");
		} catch(SQLException e){
			throw new RepositoryException(e.getMessage());
		}
	}

	@Override
	public void setPreferences(long userID, Iterator<Preference> it, String accessToken)
			throws RepositoryException, SessionException {
		try{
			if(DBRepositoryUtils.hasLoggedIn(userID, accessToken)){
				DBRepositoryUtils.setPreferences(userID, it);
			}
			else
				throw new SessionException("User not logged in");
		} catch(SQLException e){
			throw new RepositoryException(e.getMessage());
		}
		
	}

	@Override
	public Iterator<Event> getNotifications(long userID, GPS coord, String accessToken)
			throws RepositoryException, SessionException {
		try{
			if(DBRepositoryUtils.hasLoggedIn(userID, accessToken)){
				return DBRepositoryUtils.getNotifications(userID, coord);
			}
			else
				throw new SessionException("User not logged in");
		} catch(SQLException e){
			throw new RepositoryException(e.getMessage());
		} catch (ParseException e) {
			throw new RepositoryException(e.getMessage());
		}
	}

	@Override
	public void registerConsumer(Consumer consumer) throws RepositoryException {
		try{
			ResultSet rs = DBRepositoryUtils.insertUser(consumer);
			rs.next();
			long id_user = rs.getLong(1);
			consumer.setUserID(id_user);;
			DBRepositoryUtils.insertConsumer(consumer);
		}catch(SQLException e){
			throw new RepositoryException(e.getMessage());
		}
	}
}
