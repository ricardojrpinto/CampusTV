package campustv.dbutils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SQLUtils {

	public static java.sql.Timestamp getCurrentTimeStamp() {
		 
		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());
	}
	
	
	public static java.sql.Date stringToDate(String str) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm");
		return new java.sql.Date(sdf.parse(str).getTime());
	}
	
	public static String dateToString(java.sql.Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm");
		Date d = new Date(date.getTime());
		return sdf.format(d);
	}
	
	public static java.sql.Time stringToTime(String str) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		return new java.sql.Time(sdf.parse(str).getTime());
	}
	
	public static String timeToString(java.sql.Time time) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		Date d = new Date(time.getTime());
		return sdf.format(d);
	}


	public static String timestampToString(Timestamp ts) {
		return ts.toString();
	}
	
	public static Timestamp stringToTimestamp(String str){
		return Timestamp.valueOf(str);
	}
}
