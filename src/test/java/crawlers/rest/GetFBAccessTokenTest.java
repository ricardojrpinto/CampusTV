package crawlers.rest;

public class GetFBAccessTokenTest {

	public static void main(String[] args) {
		boolean getToken = new FBPageFeedReader().initService();
		if(getToken) {
			System.out.println("Success!");
		} else {
			System.out.println("error...");
		}
	}

}
