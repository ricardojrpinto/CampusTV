package campustv.control;

import java.util.Iterator;

import campustv.domain.Video;
import campustv.repository.DBVideoRepository;
import campustv.repository.VideoRepository;
import campustv.repository.exceptions.RepositoryException;

public class VideoController {
	
	public void addVideo(Video v) throws RepositoryException{
		VideoRepository vr = new DBVideoRepository();
		vr.addVideo(v);
	}
	
	public Iterator<Video> getVideos() throws RepositoryException{
		VideoRepository vr = new DBVideoRepository();
		return vr.listVideos();
	}
	
	public void removeVideo(long videoID) throws RepositoryException{
		VideoRepository vr = new DBVideoRepository();
		vr.removeVideo(videoID);
	}

	public Video getVideoByID(long videoID) throws RepositoryException{
		VideoRepository vr = new DBVideoRepository();
		return vr.getVideoByID(videoID);
	}

}
