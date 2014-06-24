package campustv.domain;

import java.util.Iterator;
import java.util.List;
public class Event extends Content{

	private String title;
	private String eventDate;
	private String latitude;
	private String longitude;
	private String local;
	private List<Image> images;
	private List<Video> videos;
	
	public Event(){
		super();
	}
	
	public Event(long eventID, String insertion_date, String initial_issue, String final_issue,
			String description, String category, String subcategory,
			long producerID, String title, String eventDate, String latitude, 
			String longitude, String local, List<Image> images, List<Video> videos) {
		
		super(eventID, insertion_date, initial_issue, final_issue, description, category,
				subcategory, producerID);
		
		this.title = title;
		this.eventDate = eventDate;
		this.latitude = latitude;
		this.longitude = longitude;
		this.images = images;
		this.videos = videos;
		this.local = local;
	}

	public Iterator<Video> getVideos() {
		return videos.iterator();
	}

	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}

	public String getTitle() {
		return title;
	}

	public Iterator<Image> getImages() {
		return images.iterator();
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}
	
	

}
