package campustv.control;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import campustv.domain.Agenda;
import campustv.domain.Video;
import campustv.repository.DBVideoRepository;
import campustv.repository.VideoRepository;
import campustv.repository.exceptions.PermissionException;
import campustv.repository.exceptions.RepositoryException;
import campustv.repository.exceptions.SessionException;


public class VideoController {
	
	public void addVideo(Video v, String accessToken) throws RepositoryException, SessionException, PermissionException{
		VideoRepository vr = new DBVideoRepository();
		vr.addVideo(v, accessToken);
	}
	
	public Iterator<Video> getVideos() throws RepositoryException{
		VideoRepository vr = new DBVideoRepository();
		return vr.listVideos();
	}
	
	public void removeVideo(long videoID, long producerID, String accessToken) throws RepositoryException, SessionException, PermissionException{
		VideoRepository vr = new DBVideoRepository();
		vr.removeVideo(videoID, producerID, accessToken);
	}

	public Video getVideoByID(long videoID) throws RepositoryException{
		VideoRepository vr = new DBVideoRepository();
		return vr.getVideoByID(videoID);
	}
	
	public byte[] getVideoData(long videoID)
					throws RepositoryException, IOException, FileNotFoundException{
		Video v = this.getVideoByID(videoID);
		
		String path = v.getPath();
		
		File f = new File(path);
		byte[] data = new byte[new Long(f.length()).intValue()];
		DataInputStream din = new DataInputStream(new FileInputStream(f));
		din.readFully(data);
		din.close();
		
		return data;
	}
	
	public Iterator<Video> getVideosByAuthor(String author) throws RepositoryException{
		VideoRepository vr = new DBVideoRepository();
		return vr.getVideosByAuthor(author);
	}

	public Iterator<Agenda> getVideosDay(String scheduleDay) throws RepositoryException{
		VideoRepository vr = new DBVideoRepository();
		return vr.getVideosDay(scheduleDay);
	}

}
