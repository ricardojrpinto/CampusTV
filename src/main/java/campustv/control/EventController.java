package campustv.control;

import java.util.Iterator;

import campustv.domain.Agenda;
import campustv.domain.Event;
import campustv.domain.Video;
import campustv.repository.DBEventRepository;
import campustv.repository.EventRepository;
import campustv.repository.exceptions.PermissionException;
import campustv.repository.exceptions.RepositoryException;
import campustv.repository.exceptions.SessionException;

public class EventController {

	public void addEvent(Event e, String accessToken) throws RepositoryException, SessionException, PermissionException{
		EventRepository er = new DBEventRepository();
		er.addEvent(e, accessToken);
	}
	
	public Iterator<Event> getEvents() throws RepositoryException{
		EventRepository er = new DBEventRepository();
		return er.listEvents();
	}
	
	public void removeEvent(long eventID, long producerID, String accessToken) throws RepositoryException, SessionException, PermissionException{
		EventRepository er = new DBEventRepository();
		er.removeEvent(eventID, producerID, accessToken);
	}

	public Event getEventByID(long eventID) throws RepositoryException{
		EventRepository er = new DBEventRepository();
		return er.getEventByID(eventID);
	}

	public Iterator<Video> getVideosOfEvents() throws RepositoryException {
		EventRepository er = new DBEventRepository();
		return er.getVideosOfEvents();
	}

	public Iterator<Video> getVideosOfEvent(long eventID) throws RepositoryException {
		EventRepository er = new DBEventRepository();
		return er.getVideosOfEvent(eventID);
	}

	public Iterator<Event> getEventsByAuthor(String author) throws RepositoryException{
		EventRepository er = new DBEventRepository();
		return er.getEventsByAuthor(author);
	}

	public Iterator<Agenda> getEventsDay(String scheduleDay) throws RepositoryException {
		EventRepository er = new DBEventRepository();
		return er.getEventsDay(scheduleDay);
	}

}
