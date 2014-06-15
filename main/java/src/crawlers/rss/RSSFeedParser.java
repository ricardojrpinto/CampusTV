package crawlers.rss;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class RSSFeedParser {
	
	static final String TITLE = "title";
	static final String DESCRIPTION = "description";
	static final String CHANNEL = "channel";
	static final String LANGUAGE = "language";
	static final String COPYRIGHT = "copyright";
	static final String LINK = "link";
	static final String AUTHOR = "dc:creator";
	static final String ITEM = "item";
	static final String PUB_DATE = "pubDate";
	static final String GUID = "guid";
	
	final URL url;

	public RSSFeedParser(String feedUrl) {
		try {
			this.url = new URL(feedUrl);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	public Feed readFeed() throws IOException {
		Document doc = buildDocument();
		
		String description = "";
		String title = "";
		String link = "";
		String language = "";
		String copyright = "";
		String pubdate = "";
		Feed feed = new Feed();
		
		NodeList nl = doc.getElementsByTagName(CHANNEL);
		if(nl.getLength() > 0) {
			NodeList children = nl.item(0).getChildNodes();
			for(int i=0;i < children.getLength(); i++) {
				Node e = children.item(i);
				switch(e.getNodeName()) {
				case TITLE:
					title = e.getTextContent();
					break;
				case LINK:
					link = e.getTextContent();
					break;
				case DESCRIPTION:
					description = e.getTextContent();
					break;
				case LANGUAGE:
					language = e.getTextContent();
					break;
				case COPYRIGHT:
					copyright = e.getTextContent();
					break;
				case PUB_DATE:
					pubdate = e.getTextContent();
					break;
				case ITEM:
					FeedMessage fm = getFeedMessage(e);
					feed.getMessages().add(fm);
					break;
				}
			}
		}
		feed.setTitle(title);
		feed.setLink(link);
		feed.setDescription(description);
		feed.setLanguage(language);
		feed.setCopyright(copyright);
		feed.setPubDate(pubdate);
		
		return feed;
  	}


	protected FeedMessage getFeedMessage(Node e) {
		String description = "";
		String title = "";
		String link = "";
		String author = "";
		String pubdate = "";
		String guid = "";
		FeedMessage msg = new FeedMessage();
		
		
		
		NodeList children = e.getChildNodes();
		for(int i=0;i < children.getLength(); i++) {
			Node c = children.item(i);
			switch(c.getNodeName()) {
			case TITLE:
				title = c.getTextContent();
				break;
			case LINK:
				link = c.getTextContent();
				break;
			case DESCRIPTION:
				description = c.getTextContent();
				break;
			case GUID:
				guid = c.getTextContent();
				break;
			case AUTHOR:
				author = c.getTextContent();
				break;
			case PUB_DATE:
				pubdate = c.getTextContent();
				break;
			}
		}
		msg.setTitle(title);
		msg.setLink(link);
		msg.setDescription(description);
		msg.setGuid(guid);
		msg.setAuthor(author);
		msg.setPubDate(pubdate);
		return msg;
	}


	protected Document buildDocument() throws IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
		DocumentBuilder docbuilder;
		Document doc;
		try {
			docbuilder = dbf.newDocumentBuilder();
			doc = parseDocument(docbuilder);
		} catch (ParserConfigurationException | SAXException e) {
			e.printStackTrace();
			throw new IOException(e.getMessage());
		}
		return doc;
	}

	private Document parseDocument(DocumentBuilder docbuilder)
			throws SAXException, IOException {
		Document doc = docbuilder.parse(url.openStream());
		return doc;
	}


} 
