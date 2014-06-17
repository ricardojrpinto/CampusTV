package campustv.repository;

import java.util.Iterator;

import campustv.domain.Bookmark;
import campustv.domain.Preference;
import campustv.repository.exceptions.RepositoryException;

public interface UserRepository {

	Iterator<Preference> listPreferences(long userID) throws RepositoryException;

	Iterator<Bookmark> listBookmarks(long userID) throws RepositoryException;

	void addBookmarks(long userID, Iterator<Bookmark> it) throws RepositoryException;

	void removeBookmarks(long userID, Iterator<Bookmark> it) throws RepositoryException;

	void setPreferences(long userID, Iterator<Preference> it) throws RepositoryException;

}
