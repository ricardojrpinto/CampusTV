package campustv.domain;

public class Bookmark {

	private long contentID;
	private long userID;
	private String timeStamp;
	
	public Bookmark() {
		super();
	}

	public Bookmark(long contentID, long userID, String timeStamp) {
		this.contentID = contentID;
		this.userID = userID;
		this.timeStamp = timeStamp;
	}

	public long getContentID() {
		return contentID;
	}

	public void setContentID(long contentID) {
		this.contentID = contentID;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	
}
