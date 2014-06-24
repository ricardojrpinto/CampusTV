package crawlers.mail;

import java.io.Reader;

public class InboxEmail {

	private String sender;
	private String text;
	private Reader reader;
	
	public InboxEmail(Reader reader) {
		sender = text = null;
		this.reader = reader;
	}
	
	
	
}
