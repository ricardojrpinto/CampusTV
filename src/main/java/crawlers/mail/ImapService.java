package crawlers.mail;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.search.AddressTerm;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.DateTerm;
import javax.mail.search.FromTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;

import crawlers.ConfigurationException;

public class ImapService {

	static final String DEFAULT_CONF_FILE = "config/mail/gmail_credentials";
	public static final String MAIL_TMP_FILE = ".mail";
	
	private Store store;
	private Folder inbox;
	
	private String server;
	private int port;
	private String username, password;
	private boolean inboxSelected;
	private boolean credentialsLoaded;
	

	public ImapService(String server, int port) {
		this.server = server;
		this.port = port;
		this.inboxSelected = false;
		this.credentialsLoaded = false;
	}
	
	public void loadCredentials(String sourcePath) throws IOException,
			ConfigurationException {
		Properties p = new Properties();
		p.load(new FileReader(sourcePath));
		if((username = p.getProperty("username")) == null) {
			credentialsLoaded = false;
			throw new ConfigurationException("Field \"username\" not found in file \""+sourcePath+"\"");
		}
		if((password = p.getProperty("password")) == null) {
			credentialsLoaded = false;
			throw new ConfigurationException("Field \"password\" not found in file \""+sourcePath+"\"");
		}
		credentialsLoaded = true;
	}
	
	private void loadCredentials() 
			throws IOException, ConfigurationException {
		this.loadCredentials(DEFAULT_CONF_FILE);
	}


	public void connect() throws IOException,
			ConfigurationException {
		if(!credentialsLoaded) {
			this.loadCredentials();
		}
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imaps");
		Session session = Session.getInstance(props, null);
		try {
			store = session.getStore();
			store.connect(server, port, username, password);
		} catch (MessagingException e) {
			throw new IOException(e.getMessage());
		}
	}

	public void logout() throws IOException {
		try {
			store.close();
		} catch (MessagingException e) {
			throw new IOException(e.getMessage());
		}
	}

	public int getNumberOfEmails() throws IOException {
		if(!inboxSelected) {
			this.selectInbox();
		}
		try {
			return inbox.getMessageCount();
		} catch (MessagingException e) {
			throw new IOException(e.getMessage());
		}
	}

	/**
	 * Returns a list of indexes that satisfy the search parameters.
	 * The list is ordered by ascending order, with the biggest index being
	 * the most recent.
	 * If a field is set to null, it is ignored.
	 * @throws ParseException if the address format in <from> is not respected
	 */
	public List<Message> search(String from, Date since, String subject)
			throws IOException, ParseException {
		if(!inboxSelected) {
			this.selectInbox();
		}
		List<SearchTerm> searchTerms = new ArrayList<SearchTerm>();
		if(from != null) {
			try {
				Address address = new InternetAddress(from);
				AddressTerm addrTerm = new FromTerm(address);
				searchTerms.add(addrTerm);
			} catch (AddressException e) {
				throw new IllegalArgumentException("Address format is not correct.");
			} 
		}
		if(since != null) {
			DateTerm dateTerm = new ReceivedDateTerm(ComparisonTerm.GE, since);
			searchTerms.add(dateTerm);
		}
		if(subject != null) {
			SubjectTerm subjTerm = new SubjectTerm(subject);
			searchTerms.add(subjTerm);
		}
		
		SearchTerm[] st = new SearchTerm[searchTerms.size()];
		for(int i = 0; i<st.length; i++) {
			st[i] = searchTerms.get(i);
		}
		SearchTerm search = new AndTerm(st);
		
		List<Message> list = new ArrayList<Message>();
		try {
			Message[] msgs = inbox.search(search);
			for(int i = 0; i < msgs.length; i++) {
				list.add(msgs[i]);
			}
		} catch (MessagingException e) {
			throw new IOException(e.getMessage());
		}
		return list;
	}

	
	private void selectInbox() throws IOException {
		try {
			inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);
			inboxSelected = true;
		} catch (MessagingException e) {
			throw new IOException(e.getMessage());
		}
		
	}
	
	public static Part getTextFromMultipart(Part p) throws MessagingException,
	IOException {
		Part text = null;
		if(p.isMimeType("multipart/*")) {
			System.out.println("rec");
			Multipart mp = (Multipart) p.getContent();
			int count = mp.getCount();
			for (int i = 0; i < count; )
				return getTextFromMultipart(mp.getBodyPart(i));
		} else if (p.isMimeType("text/plain")) {
			System.out.println("text");
			text = p;
		}
		return text;
	}
	

}
