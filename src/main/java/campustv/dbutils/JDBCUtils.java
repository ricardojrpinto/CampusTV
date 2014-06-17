package campustv.dbutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtils {

	private static final String DBURL_KEY = "databaseURL";
	private static final String DBUSER_KEY = "dbuser";
	private static final String DBPASS_KEY = "dbpassword";
	private static final String DBCONFIG = "dbconfig.properties";
	
	private static Connection con = null;
	
	static{
		try {
			Class.forName("org.postgresql.Driver");
			Properties prop = new Properties();
			InputStream input = new FileInputStream(new File(DBCONFIG));
			prop.load(input);
			con = DriverManager.getConnection(prop.getProperty(DBURL_KEY), prop.getProperty(DBUSER_KEY), prop.getProperty(DBPASS_KEY));
			input.close();
		} catch (SQLException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static Statement getStatement() throws SQLException {
		if (con != null)
			return con.createStatement();
		else
			return null;
	}

	public static ResultSet executeQuery(Statement st, String query) throws SQLException{
		return st.executeQuery(query);
	}
	
	public static PreparedStatement getPrepareStatement(String sql, int keys) throws SQLException{
		if (con != null)
			return con.prepareStatement(sql, keys);
		else
			return null;
	}

	public static PreparedStatement getPrepareStatement(String sql) throws SQLException{
		if (con != null)
			return con.prepareStatement(sql);
		else
			return null;
	}

	public static void commit() throws SQLException {
		con.commit();
	}

}
