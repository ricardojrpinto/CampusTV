package campustv.repository;

import java.util.Iterator;

import campustv.domain.Agenda;
import campustv.domain.New;
import campustv.domain.Video;
import campustv.repository.exceptions.PermissionException;
import campustv.repository.exceptions.RepositoryException;
import campustv.repository.exceptions.SessionException;

public interface NewRepository {
	
	public void addNew(New e, String accessToken) throws RepositoryException, SessionException, PermissionException;
	
	public Iterator<New> listNews() throws RepositoryException;
	
	public void removeNew(long newID, long producerID, String accessToken) throws RepositoryException, SessionException, PermissionException;

	public New getNewByID(long newID) throws RepositoryException;

	public Iterator<Video> listVideosOfNews() throws RepositoryException;

	public Iterator<Video> listVideosOfNew(long newID) throws RepositoryException;

	public Iterator<New> getNewsByAuthor(String author) throws RepositoryException;

	public Iterator<Agenda> getNewsDay(String scheduleDay) throws RepositoryException;
}
