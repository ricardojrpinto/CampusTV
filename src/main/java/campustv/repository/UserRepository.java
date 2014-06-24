package campustv.repository;

import java.util.Iterator;

import campustv.domain.Bookmark;
import campustv.domain.Consumer;
import campustv.domain.Event;
import campustv.domain.GPS;
import campustv.domain.Preference;
import campustv.repository.exceptions.RepositoryException;
import campustv.repository.exceptions.SessionException;

public interface UserRepository {

	Iterator<Preference> listPreferences(long userID, String accessToken) throws RepositoryException, SessionException;

	Iterator<Bookmark> listBookmarks(long userID, String accessToken) throws RepositoryException, SessionException;

	void addBookmarks(long userID, Iterator<Bookmark> it, String accessToken)throws RepositoryException, SessionException;

	void removeBookmarks(long userID, Iterator<Bookmark> it, String accessToken) throws RepositoryException, SessionException;

	void setPreferences(long userID, Iterator<Preference> it, String accessToken) throws RepositoryException, SessionException;

	Iterator<Event> getNotifications(long userID, GPS coord, String accessToken) throws RepositoryException, SessionException;

	void registerConsumer(Consumer consumer) throws RepositoryException;

}
