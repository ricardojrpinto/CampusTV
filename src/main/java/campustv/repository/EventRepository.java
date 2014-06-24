package campustv.repository;

import java.util.Iterator;

import campustv.domain.Agenda;
import campustv.domain.Event;
import campustv.domain.Video;
import campustv.repository.exceptions.PermissionException;
import campustv.repository.exceptions.RepositoryException;
import campustv.repository.exceptions.SessionException;

public interface EventRepository {

	public void addEvent(Event e, String accessToken) throws RepositoryException, SessionException, PermissionException;
	
	public Iterator<Event> listEvents() throws RepositoryException;
	
	public void removeEvent(long eventID, long producerID, String accessToken) throws RepositoryException, SessionException, PermissionException;

	public Event getEventByID(long eventID) throws RepositoryException;

	public Iterator<Video> getVideosOfEvents() throws RepositoryException;

	public Iterator<Video> getVideosOfEvent(long eventID) throws RepositoryException;

	public Iterator<Event> getEventsByAuthor(String author) throws RepositoryException;

	public Iterator<Agenda> getEventsDay(String scheduleDay) throws RepositoryException;

}
