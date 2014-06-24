package crawlers.rest.fb;

public class FBQueryTest {

	public static void main(String[] args) {
		FBQuery query = new FBQuery("AEFCT/posts");
		query.addParameter("x","d");
		query.addParameter("l", "m");
		query.build();
		System.out.println(query.toString());
	}

}
