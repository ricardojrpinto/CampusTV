package campustv.domain;

import java.util.Iterator;
import java.util.List;


public class New extends Content{

	private String title;
	private List<Image> images;
	private List<Video> videos;
	
	
	public New() {
		super();
	}

	public New(long newID, String insertion_date,String initial_issue, String final_issue,
			String description, String category, String subcategory,
			long producerID, String title, List<Image> images, List<Video> videos) {
		
		super(newID, insertion_date, initial_issue, final_issue, description, category, subcategory, producerID);
		this.title = title;
		this.images = images;
		this.videos = videos;
	}

	public Iterator<Image> getImages() {
		return images.iterator();
	}

	public void setImages(List<Image> images) {
		this.images = images;
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

	public void setTitle(String title) {
		this.title = title;
	}

}
