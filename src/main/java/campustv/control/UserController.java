package campustv.control;

import java.util.Iterator;

import campustv.domain.Bookmark;
import campustv.domain.Preference;
import campustv.repository.DBUserRepository;
import campustv.repository.UserRepository;
import campustv.repository.exceptions.RepositoryException;

public class UserController {

	public Iterator<Preference> getPreferences(long userID) throws RepositoryException{
		UserRepository ur = new DBUserRepository();
		return ur.listPreferences(userID);
	}

	public Iterator<Bookmark> getBookmarks(long userID) throws RepositoryException{
		UserRepository ur = new DBUserRepository();
		return ur.listBookmarks(userID);
	}

	public void addBookmarks(long userID, Iterator<Bookmark> it) throws RepositoryException{
		UserRepository ur = new DBUserRepository();
		ur.addBookmarks(userID, it);
	}

	public void removeBookmarks(long userID, Iterator<Bookmark> it) throws RepositoryException{
		UserRepository ur = new DBUserRepository();
		ur.removeBookmarks(userID, it);
	}

	public void setPreferences(long userID, Iterator<Preference> it) throws RepositoryException{
		UserRepository ur = new DBUserRepository();
		ur.setPreferences(userID, it);
	}


}
