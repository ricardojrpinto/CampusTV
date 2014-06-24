package campustv.domain;


public class Video extends Content{
	
	private String title;
	private String checkPoint_I;
	private String checkPoint_F;
	private String path;
	private long newID;
	private long eventID;


	public Video() {
		super();
	}
	
	public Video(long videoID, String insertion_date, String initial_issue, String final_issue,
			String description, String category, String subcategory,
			long producerID, String title, String checkpoint_I, String checkpoint_F, String path, long newID, long eventID) {
		
		super(videoID, insertion_date, initial_issue, final_issue, description, category,
				subcategory, producerID);
		
		this.title = title;
		this.checkPoint_I = checkpoint_I;
		this.checkPoint_F = checkpoint_F;
		this.path = path;
		this.newID = newID;
		this.eventID = eventID;
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCheckPoint_I() {
		return checkPoint_I;
	}

	public void setCheckPoint_I(String checkPoint_I) {
		this.checkPoint_I = checkPoint_I;
	}

	public String getCheckPoint_F() {
		return checkPoint_F;
	}

	public void setCheckPoint_F(String checkPoint_F) {
		this.checkPoint_F = checkPoint_F;
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
