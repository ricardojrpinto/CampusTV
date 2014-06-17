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
import campustv.domain.Preference;
import campustv.repository.exceptions.RepositoryException;

public class DBUserRepository implements UserRepository{

	@Override
	public Iterator<Preference> listPreferences(long userID) throws RepositoryException {
		List<Preference> preferences = new ArrayList<Preference>();

		try{
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
		}catch(SQLException e){
			throw new RepositoryException(e.getMessage());
		}
		return preferences.iterator();
	}

	@Override
	public Iterator<Bookmark> listBookmarks(long userID)
			throws RepositoryException {
		List<Bookmark> bookmarks = new ArrayList<Bookmark>();

		try{
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
		}catch(SQLException e){
			throw new RepositoryException(e.getMessage());
		}
		return bookmarks.iterator();
	}

	@Override
	public void addBookmarks(long userID, Iterator<Bookmark> it) throws RepositoryException {
		try{
			DBRepositoryUtils.insertBookmarks(userID, it);
		} catch(SQLException e){
			throw new RepositoryException(e.getMessage());
		}
	}

	@Override
	public void removeBookmarks(long userID, Iterator<Bookmark> it)
			throws RepositoryException {
		try{
			DBRepositoryUtils.removeBookmarks(userID, it);
		} catch(SQLException e){
			throw new RepositoryException(e.getMessage());
		}
	}

	@Override
	public void setPreferences(long userID, Iterator<Preference> it)
			throws RepositoryException {
		try{
			DBRepositoryUtils.setPreferences(userID, it);
		} catch(SQLException e){
			throw new RepositoryException(e.getMessage());
		}
		
	}

}
