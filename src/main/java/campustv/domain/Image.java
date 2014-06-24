package campustv.domain;

public class Image {
	
	private long imageID;
	private String path;
	private long newID;
	private long eventID;
	
	
	public Image() {
		super();
	}

	public Image(long imageID, String path, long newID, long eventID) {
		this.imageID = imageID;
		this.path = path;
		this.newID = newID;
		this.eventID = eventID;
	}
	
	
	public long getImageID() {
		return imageID;
	}

	public void setImageID(long imageID) {
		this.imageID = imageID;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getNewID() {
		return newID;
	}

	public void setNewID(long newID) {
		this.newID = newID;
	}

	public long getEventID() {
		return eventID;
	}

	public void setEventID(long eventID) {
		this.eventID = eventID;
	}

}
