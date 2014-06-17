package campustv.repository;

import java.util.Iterator;

import campustv.domain.Video;
import campustv.repository.exceptions.RepositoryException;

public interface VideoRepository {

public void addVideo(Video e) throws RepositoryException;
	
	public Iterator<Video> listVideos() throws RepositoryException;
	
	public void removeVideo(long videoID) throws RepositoryException;

	public Video getVideoByID(long videoID) throws RepositoryException;
}
