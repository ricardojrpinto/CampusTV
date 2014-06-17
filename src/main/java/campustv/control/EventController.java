package campustv.control;

import java.util.Iterator;

import campustv.domain.Event;
import campustv.domain.Video;
import campustv.repository.DBEventRepository;
import campustv.repository.EventRepository;
import campustv.repository.exceptions.RepositoryException;

public class EventController {

	public void addEvent(Event e) throws RepositoryException{
		EventRepository er = new DBEventRepository();
		er.addEvent(e);
	}
	
	public Iterator<Event> getEvents() throws RepositoryException{
		EventRepository er = new DBEventRepository();
		return er.listEvents();
	}
	
	public void removeEvent(long eventID) throws RepositoryException{
		EventRepository er = new DBEventRepository();
		er.removeEvent(eventID);
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

}
