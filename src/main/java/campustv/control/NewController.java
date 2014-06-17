package campustv.control;

import java.util.Iterator;

import campustv.domain.New;
import campustv.domain.Video;
import campustv.repository.DBNewRepository;
import campustv.repository.NewRepository;
import campustv.repository.exceptions.RepositoryException;

public class NewController {
	
	public void addNew(New n) throws RepositoryException{
		NewRepository nr = new DBNewRepository();
		nr.addNew(n);
	}
	
	public Iterator<New> getNews() throws RepositoryException{
		NewRepository nr = new DBNewRepository();
		return nr.listNews();
	}
	
	public void removeNew(long newID) throws RepositoryException{
		NewRepository nr = new DBNewRepository();
		nr.removeNew(newID);
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

}
