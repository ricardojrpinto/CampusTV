package campustv.domain;

public class Area {
	
	private String category;
	private String subcategory;
	private String latitude;
	private String longitude;
	
	public Area() {
		super();
	}
	
	public Area(String category, String subcategory, String latitude,
			String longitude) {
		super();
		this.category = category;
		this.subcategory = subcategory;
		this.latitude = latitude;
		this.longitude = longitude;
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
	

}
