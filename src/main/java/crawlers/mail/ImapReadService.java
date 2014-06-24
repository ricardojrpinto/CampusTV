package crawlers.mail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Properties;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.xml.ws.Response;

import crawlers.ConfigurationException;

public class ImapReadService implements EmailService{
	
	static final String DEFAULT_CONF_FILE = "config/mail/gmail_credentials";
	
	private SSLSocket socket;
	private BufferedReader input;
	private PrintWriter output;
	
	private String server;
	private int port;
	private String username, password;
	

	public ImapReadService(String server, int port) {
		this.server = server;
		this.port = port;
	}
	
	public void loadCredentials() 
			throws IOException, ConfigurationException {
		this.loadCredentials(DEFAULT_CONF_FILE);
	}
	
	public void loadCredentials(String sourcePath) 
			throws IOException, ConfigurationException {
		Properties p = new Properties();
		p.load(new FileReader(DEFAULT_CONF_FILE));
		if((username = p.getProperty("username")) == null) {
			throw new ConfigurationException("Field \"username\" not found in file \""+sourcePath+"\"");
		}
		if((password = p.getProperty("password")) == null) {
			throw new ConfigurationException("Field \"password\" not found in file \""+sourcePath+"\"");
		}
	}
	
	public void initService() throws IOException {
		SSLSocketFactory sslsocketFactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
        socket = (SSLSocket)sslsocketFactory.createSocket(server, port);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        // After each println you MUST flush the buffer, or it won't work properly.
        // The true argument makes an automatic flush after each println.
        output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        
	}
	
	//TODO Server's reply might have more than one line. Fix that! 
	public void connect() throws IOException {
        System.out.print("Greeting message: ");
        String response = input.readLine();;
        System.out.println(response);
        
        // login
        output.println("tag login "+username+" "+password);
        response = input.readLine();
        System.out.println(response);
    }
	
	public int getNumberOfEmails() throws IOException {
		int emails = -1;
		output.println("tag STATUS INBOX (MESSAGES)");
		String response = input.readLine();
		System.out.println(response);
		if(response.matches("\\* STATUS \"INBOX\" \\(MESSAGES \\d+\\)")) {
			emails = Integer.parseInt(response.split(" ")[4].split(")")[0]);
		}
		while(input.ready()) {
			input.readLine();
		}
		
		return emails;
	}
	
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	public InboxEmail getInboxEmail(int index) throws IOException {
		InboxEmail mail = null;
		output.println("tag FETCH "+index+" (BODY[])");
		String response = input.readLine();
		System.out.println(response);
		if(response.matches("\\* "+index+" FETCH \\(BODY\\[\\] \\{\\d+\\}")) {
			mail = new InboxEmail(input);
		}
		
		return mail;
	}
	
	
	
	
}
