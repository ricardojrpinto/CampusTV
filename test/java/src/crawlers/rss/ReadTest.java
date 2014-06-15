package crawlers.rss;

import java.io.IOException;

import javax.xml.stream.XMLStreamException;

public class ReadTest {
	  public static void main(String[] args) throws IOException, XMLStreamException {
	    RSSFeedParser parser = new RSSFeedParser("http://www.biblioteca.fct.unl.pt/rss.xml");
	    Feed feed = parser.readFeed();
	    System.out.println(feed);
	    for (FeedMessage message : feed.getMessages()) {
	      System.out.println(message);
	    }

	  }
	} 
