package campustv.repository;

import java.util.Iterator;

import campustv.domain.New;
import campustv.domain.Video;
import campustv.repository.exceptions.RepositoryException;

public interface NewRepository {
	
	public void addNew(New e) throws RepositoryException;
	
	public Iterator<New> listNews() throws RepositoryException;
	
	public void removeNew(long newID) throws RepositoryException;

	public New getNewByID(long newID) throws RepositoryException;

	public Iterator<Video> listVideosOfNews() throws RepositoryException;

	public Iterator<Video> listVideosOfNew(long newID) throws RepositoryException;
}
