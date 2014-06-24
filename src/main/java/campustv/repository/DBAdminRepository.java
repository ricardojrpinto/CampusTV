package campustv.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import campustv.dbutils.JDBCUtils;
import campustv.dbutils.SQLUtils;
import campustv.domain.Admin;
import campustv.domain.Area;
import campustv.domain.Local;
import campustv.domain.Producer;
import campustv.repository.exceptions.PermissionException;
import campustv.repository.exceptions.RepositoryException;
import campustv.repository.exceptions.SessionException;

public class DBAdminRepository implements AdminRepository {


	public void addLocation(long userID, Local local, String accessToken) throws RepositoryException, PermissionException, SessionException{
		try{
			if(DBRepositoryUtils.hasAdmin(userID)){
				if(DBRepositoryUtils.hasLoggedIn(userID, accessToken)){
					String sql = "INSERT INTO LOCATIONS"
							+ "(LATITUDE, LONGITUDE, NAME) VALUES"
							+ "(?,?,?)";
					PreparedStatement ps = JDBCUtils.getPrepareStatement(sql);
					ps.setString(1, local.getLatitude());
					ps.setString(2, local.getLongitude());
					ps.setString(3, local.getName());
				    ps.execute();
				}
				else
					throw new SessionException("User not logged in");
			}
			else
				throw new PermissionException("Don't have Permissions");
		}catch(SQLException e){
			throw new RepositoryException(e.getMessage());
		}

	}

	public void addArea(long userID, Area a, String accessToken) throws RepositoryException, SessionException, PermissionException {
		try{
			if(DBRepositoryUtils.hasAdmin(userID)){
				if(DBRepositoryUtils.hasLoggedIn(userID, accessToken)){
					String sql = "INSERT INTO AREAS"
							+ "(CATEGORY, SUBCATEGORY, LATITUDE, LONGITUDE) VALUES"
							+ "(?,?,?,?)";
					PreparedStatement ps = JDBCUtils.getPrepareStatement(sql);
					ps.setString(1, a.getCategory());
					ps.setString(2, a.getSubcategory());
					ps.setString(3, a.getLatitude());
					ps.setString(4, a.getLongitude());
				    ps.execute();
				}
				else
					throw new SessionException("User not logged in");
			}
			else
				throw new PermissionException("Don't have Permissions");
		}catch(SQLException e){
			throw new RepositoryException(e.getMessage());
		}

	}


	public void addAdmin(long userID, Admin admin, String accessToken) throws RepositoryException, SessionException, PermissionException {
		try{
			if(DBRepositoryUtils.hasAdmin(userID)){
				if(DBRepositoryUtils.hasLoggedIn(userID, accessToken)){
					ResultSet rs = DBRepositoryUtils.insertUser(admin);
					rs.next();
					long id_user = rs.getLong(1);
					admin.setUserID(id_user);;
					DBRepositoryUtils.insertAdmin(admin);
				}
				else
					throw new SessionException("User not logged in");
			}
			else
				throw new PermissionException("Don't have Permissions");
		}catch(SQLException e){
			throw new RepositoryException(e.getMessage());
		}
	}

	public void addProducer(long userID, Producer producer, String accessToken)
			throws RepositoryException, SessionException, PermissionException {
		try{
			if(DBRepositoryUtils.hasAdmin(userID)){
				if(DBRepositoryUtils.hasLoggedIn(userID, accessToken)){
					ResultSet rs = DBRepositoryUtils.insertUser(producer);
					rs.next();
					long id_user = rs.getLong(1);
					producer.setUserID(id_user);;
					DBRepositoryUtils.insertProducer(producer);
				}
				else
					throw new SessionException("User not logged in");
			}
			else
				throw new PermissionException("Don't have Permissions");
		}catch(SQLException e){
			throw new RepositoryException(e.getMessage());
		}
	}

}
