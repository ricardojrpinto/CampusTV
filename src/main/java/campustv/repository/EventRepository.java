package campustv.repository;

import java.util.Iterator;

import campustv.domain.Event;
import campustv.domain.Video;
import campustv.repository.exceptions.RepositoryException;

public interface EventRepository {

	public void addEvent(Event e) throws RepositoryException;
	
	public Iterator<Event> listEvents() throws RepositoryException;
	
	public void removeEvent(long eventID) throws RepositoryException;

	public Event getEventByID(long eventID) throws RepositoryException;

	public Iterator<Video> getVideosOfEvents() throws RepositoryException;

	public Iterator<Video> getVideosOfEvent(long eventID) throws RepositoryException;

}
