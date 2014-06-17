package crawlers.rss;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

public class ReadTest {
	
	public static void main(String[] args) throws IOException, XMLStreamException {
		RSSFeedParser parser = new RSSFeedParser("http://www.fct.unl.pt/noticias/rss.xml");
		Feed feed = parser.readFeed();
		System.out.println(feed);
		for (FeedMessage message : feed.getMessages()) {
			System.out.println(message);
			String description = message.getDescription();
			writeToXMLFile(description);
		}

	}

	private static void writeToXMLFile(String content) throws IOException {
		FileOutputStream fout = new FileOutputStream("rss.xml");
		byte[] contentBytes = content.getBytes();
		fout.write(contentBytes, 0, contentBytes.length);
		fout.close();
	}
} 
