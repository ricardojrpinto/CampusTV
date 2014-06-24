package campustv.control;

import java.util.Iterator;

import campustv.domain.Agenda;
import campustv.domain.New;
import campustv.domain.Video;
import campustv.repository.DBNewRepository;
import campustv.repository.NewRepository;
import campustv.repository.exceptions.PermissionException;
import campustv.repository.exceptions.RepositoryException;
import campustv.repository.exceptions.SessionException;

public class NewController {
	
	public void addNew(New n, String accessToken) throws RepositoryException, SessionException, PermissionException{
		NewRepository nr = new DBNewRepository();
		nr.addNew(n, accessToken);
	}
	
	public Iterator<New> getNews() throws RepositoryException{
		NewRepository nr = new DBNewRepository();
		return nr.listNews();
	}
	
	public void removeNew(long newID, long producerID, String accessToken) throws RepositoryException, SessionException, PermissionException{
		NewRepository nr = new DBNewRepository();
		nr.removeNew(newID, producerID, accessToken);
	}

	public New getNewByID(long newID) throws RepositoryException{
		NewRepository nr = new DBNewRepository();
		return nr.getNewByID(newID);
	}

	public Iterator<Video> getVideosOfNews() throws RepositoryException {
		NewRepository nr = new DBNewRepository();
		return nr.listVideosOfNews();
	}

	public Iterator<Video> getVideosOfNew(long newID) throws RepositoryException {
		NewRepository nr = new DBNewRepository();
		return nr.listVideosOfNew(newID);
	}

	public Iterator<New> getNewsByAuthor(String author) throws RepositoryException{
		NewRepository nr = new DBNewRepository();
		return nr.getNewsByAuthor(author);
	}

	public Iterator<Agenda> getNewsDay(String scheduleDay) throws RepositoryException {
		NewRepository nr = new DBNewRepository();
		return nr.getNewsDay(scheduleDay);
	}

}
