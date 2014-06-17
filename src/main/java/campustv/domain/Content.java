package campustv.domain;


public abstract class Content {

	private long contentID;
	private String insertion_date;
	private String initial_issue;
	private String final_issue;
	private String description;
	private String category;
	private String subcategory;
	private long producerID;
	
	@Override
	public String toString() {
		return "Content [initial_issue=" + initial_issue + ", final_issue="
				+ final_issue + ", description=" + description + ", category="
				+ category + ", subcategory=" + subcategory + ", producerID="
				+ producerID + "]";
	}

	public Content(){
		
	}
	
	public Content(long contentID, String insertion_date,String initial_issue, String final_issue,
			String description, String category, String subcategory,
			long producerID) {
		this.contentID = contentID;
		this.insertion_date = insertion_date;
		this.initial_issue = initial_issue;
		this.final_issue = final_issue;
		this.description = description;
		this.category = category;
		this.subcategory = subcategory;
		this.producerID = producerID;
	}
	
	public long getContentID() {
		return contentID;
	}

	public void setContentID(long contentID) {
		this.contentID = contentID;
	}

	public String getInsertion_date() {
		return insertion_date;
	}

	public void setInsertion_date(String insertion_date) {
		this.insertion_date = insertion_date;
	}

	public String getInitial_issue() {
		return initial_issue;
	}

	public void setInitial_issue(String initial_issue) {
		this.initial_issue = initial_issue;
	}

	public String getFinal_issue() {
		return final_issue;
	}

	public void setFinal_issue(String final_issue) {
		this.final_issue = final_issue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}

	public long getProducerID() {
		return producerID;
	}
	
	public void setProducerID(long producerID){
		this.producerID = producerID;
	}
	
}
