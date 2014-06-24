package crawlers.rss;

/*
 * Represents one RSS message
 */
public class FeedMessage {

	private String title;
	private String description;
	private String link;
	private String author;
	private String guid;
	private String pubDate;
	
	/*** GETTERS ***/
	
	public String getTitle() {
		return title;
	}


	public String getDescription() {
		return description;
	}


	public String getLink() {
		return link;
	}


	public String getAuthor() {
		return author;
	}


	public String getPubDate() {
		return pubDate;
	}


	public String getGuid() {
		return guid;
	}

	/*** SETTERS ***/

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}


	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Override
	public String toString() {
	    return "FeedMessage [title=" + title + ", description=" + description
	        + ", link=" + link + ", author=" + author + ", guid=" + guid
	        + "]";
	}
	  
}
