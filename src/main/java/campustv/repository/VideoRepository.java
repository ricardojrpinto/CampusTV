package campustv.repository;

import java.util.Iterator;

import campustv.domain.Agenda;
import campustv.domain.Video;
import campustv.repository.exceptions.PermissionException;
import campustv.repository.exceptions.RepositoryException;
import campustv.repository.exceptions.SessionException;

public interface VideoRepository {

public void addVideo(Video e, String accessToken) throws RepositoryException, SessionException, PermissionException;
	
	public Iterator<Video> listVideos() throws RepositoryException;
	
	public void removeVideo(long videoID, long producerID, String accessToken) throws RepositoryException, SessionException, PermissionException;

	public Video getVideoByID(long videoID) throws RepositoryException;

	public Iterator<Video> getVideosByAuthor(String author) throws RepositoryException;

	public Iterator<Agenda> getVideosDay(String scheduleDay) throws RepositoryException;

}
