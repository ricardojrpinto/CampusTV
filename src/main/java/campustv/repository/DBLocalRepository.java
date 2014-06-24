package campustv.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import campustv.dbutils.JDBCUtils;
import campustv.domain.Local;
import campustv.repository.exceptions.RepositoryException;

public class DBLocalRepository implements LocalRepository{

	public Iterator<Local> getLocations() throws RepositoryException {
		List<Local> locals = new LinkedList<Local>();
		try{
			Statement st = JDBCUtils.getStatement();
			String query = "select * " +
		             "from locations";
			
			ResultSet rs = JDBCUtils.executeQuery(st, query);
			while(rs.next()){
				String latitude = rs.getString(1);
				String longitude = rs.getString(2);
				String name = rs.getString(3);
				Local l = new Local(name, latitude, longitude);
			    locals.add(l);
			}
		}catch(SQLException e){
			throw new RepositoryException(e.getMessage());
		}
		return locals.iterator();
	}

}
