package campustv.control;

import java.util.Iterator;

import campustv.domain.Bookmark;
import campustv.domain.Consumer;
import campustv.domain.Event;
import campustv.domain.GPS;
import campustv.domain.Preference;
import campustv.repository.DBUserRepository;
import campustv.repository.UserRepository;
import campustv.repository.exceptions.RepositoryException;
import campustv.repository.exceptions.SessionException;

public class UserController {

	public Iterator<Preference> getPreferences(long userID, String accessToken) throws RepositoryException, SessionException{
		UserRepository ur = new DBUserRepository();
		return ur.listPreferences(userID, accessToken);
	}

	public Iterator<Bookmark> getBookmarks(long userID, String accessToken) throws RepositoryException, SessionException{
		UserRepository ur = new DBUserRepository();
		return ur.listBookmarks(userID, accessToken);
	}

	public void addBookmarks(long userID, Iterator<Bookmark> it, String accessToken) throws RepositoryException, SessionException{
		UserRepository ur = new DBUserRepository();
		ur.addBookmarks(userID, it, accessToken);
	}

	public void removeBookmarks(long userID, Iterator<Bookmark> it, String accessToken) throws RepositoryException, SessionException{
		UserRepository ur = new DBUserRepository();
		ur.removeBookmarks(userID, it, accessToken);
	}

	public void setPreferences(long userID, Iterator<Preference> it, String accessToken) throws RepositoryException, SessionException{
		UserRepository ur = new DBUserRepository();
		ur.setPreferences(userID, it, accessToken);
	}

	public Iterator<Event> getNotifications(long userID, GPS coord, String accessToken) throws RepositoryException, SessionException{
		UserRepository ur = new DBUserRepository();
		return ur.getNotifications(userID, coord, accessToken);
		
	}

	public void resgistConsumer(Consumer consumer) throws RepositoryException{
		UserRepository ur = new DBUserRepository();
		ur.registerConsumer(consumer);
	}


}
