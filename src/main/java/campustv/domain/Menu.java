package campustv.domain;


public class Menu extends Content{
	
	private String restaurantName;

	public Menu() {
		super();
	}
	
	public Menu(long menuID, String insertion_date, String initial_issue, String final_issue,
			String description, String category, String subcategory,
			long producerID, String restaurantName) {
	
		super(menuID, insertion_date, initial_issue, final_issue, description, category, subcategory, producerID);
		this.restaurantName = restaurantName;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

}
