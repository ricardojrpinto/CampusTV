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
import campustv.domain.Menu;
import campustv.repository.exceptions.PermissionException;
import campustv.repository.exceptions.RepositoryException;
import campustv.repository.exceptions.SessionException;

public class DBMenuRepository implements MenuRepository {


	public void addMenu(Menu m, String accessToken) throws RepositoryException, SessionException, PermissionException {
		try{
			if(DBRepositoryUtils.hasProducer(m.getProducerID())){
				if(DBRepositoryUtils.hasLoggedIn(m.getProducerID(), accessToken)){
					ResultSet rs = DBRepositoryUtils.insertContent(m);
					rs.next();
					long id_content = rs.getLong(1);
					m.setContentID(id_content);
					DBRepositoryUtils.insertMenu(m);
				}
				else
					throw new SessionException("User not logged in");
			}
			else
				throw new PermissionException("Don't have permissions");
		} catch(SQLException e){
			throw new RepositoryException(e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public Iterator<Menu> listMenus() throws RepositoryException {
		List<Menu> menus = new ArrayList<Menu>();

		try{
			Statement st = JDBCUtils.getStatement();
			String query = "select * " +
		             "from menus, contents " +
		             "where menus.id_menu = contents.id_content";
			
			ResultSet rs = JDBCUtils.executeQuery(st, query);
			while(rs.next()){
				int menuID = rs.getInt(1);
				String restaurantName = rs.getString(2);;
				@SuppressWarnings("unused")
				int contentID = rs.getInt(3);
				String insertion_date = SQLUtils.timestampToString(rs.getTimestamp(4));;
				String initial_issue = SQLUtils.timestampToString(rs.getTimestamp(5));;
				String final_issue = SQLUtils.timestampToString(rs.getTimestamp(6));
				String description = rs.getString(7);
				long producerID = rs.getLong(8);
				String category = rs.getString(9);
				String subcategory = rs.getString(10);
				
				Menu m = new Menu(menuID, insertion_date, initial_issue, final_issue, 
						description, category, subcategory, producerID, restaurantName); 
			    menus.add(m);
			}
		}catch(SQLException e){
			throw new RepositoryException(e.getMessage());
		}
		return menus.iterator();
	}

	public Menu getMenuByID(long menuID) throws RepositoryException {	
		try{
			Statement st = JDBCUtils.getStatement();
			String query = "select * " +
					"from menus, contents " +
					"where menus.id_menu = contents.id_content and id_menu = " + menuID;
			
			ResultSet rs = JDBCUtils.executeQuery(st, query);
			if(rs.next()){
				int menuID2 = rs.getInt(1);
				String restaurantName = rs.getString(2);;
				@SuppressWarnings("unused")
				int contentID = rs.getInt(3);
				String insertion_date = SQLUtils.timestampToString(rs.getTimestamp(4));;
				String initial_issue = SQLUtils.timestampToString(rs.getTimestamp(5));;
				String final_issue = SQLUtils.timestampToString(rs.getTimestamp(6));
				String description = rs.getString(7);
				long producerID = rs.getLong(8);
				String category = rs.getString(9);
				String subcategory = rs.getString(10);
				
				return  new Menu(menuID2, insertion_date, initial_issue, final_issue, 
						description, category, subcategory, producerID, restaurantName);
			}
			return null;
		}catch(SQLException e){
			throw new RepositoryException(e.getMessage());
		}
	}

	public void removeMenu(long menuID, long producerID, String accessToken) throws RepositoryException, PermissionException, SessionException {
		try{
			if(DBRepositoryUtils.hasProducer(producerID)){
				if(DBRepositoryUtils.hasLoggedIn(producerID, accessToken)){
					if(DBRepositoryUtils.hasOnwer(producerID, menuID)){
						Statement st = JDBCUtils.getStatement();
						st.addBatch("delete from contents where id_content = " + menuID);
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

}
