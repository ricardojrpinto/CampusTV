package campustv.domain;

public class Preference {
	
	private long userID;
	private char relevance;
	private String category;
	private String subCategory;
	
	public Preference() {
		super();
	}
	
	
	public Preference(long userID, char relevance, String category,
			String subCategory) {
		
		this.userID = userID;
		this.relevance = relevance;
		this.category = category;
		this.subCategory = subCategory;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public char getRelevance() {
		return relevance;
	}

	public void setRelevance(char relevance) {
		this.relevance = relevance;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	
}
