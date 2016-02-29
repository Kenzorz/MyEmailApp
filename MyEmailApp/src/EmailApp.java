import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.security.auth.Subject;

import com.sun.mail.pop3.POP3SSLStore;

public class EmailApp {
	
	String host = "pop3.live.com";
	String email = "";
	String pass = "";
	
	Session session;
	Store store;
	URLName url;
	boolean textIsHtml = false;
	
	Message messages[];
	
	public EmailApp (String emailAddress, String password) {
		email = emailAddress;
		pass = password;
		//login();
		//getInbox();
		//getSenderName(messages[1]);
		//getReceivedDate(messages[1]);

	}

	public boolean login() {
		
		boolean success = false;
		
		try {
			String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
			
			Properties props = new Properties();
			props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
			props.setProperty("mail.pop3.socketFactory.fallback", "false");
			props.setProperty("mail.pop3.port",  "995");
			props.setProperty("mail.pop3.socketFactory.port", "995");
	
			url = new URLName("pop3", host, 995, "", email, pass);
			session = Session.getInstance(props, null);
			store = new POP3SSLStore(session, url);
			store.connect();
			success = true; 
		} catch (AuthenticationFailedException e) {
			//e.printStackTrace();
		} catch (MessagingException e) {
			//e.printStackTrace();
		}

		//System.out.println(success);
		return success;
	}
	
	public boolean getInbox() {
		Folder inbox;
		try {
			inbox = store.getFolder("Inbox");
			inbox.open(Folder.READ_ONLY);
			messages = inbox.getMessages();
		} catch (MessagingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (messages.length > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public Message getMessage(int index) {
		return messages[index];
	}
	
	public String getBody(Message m) {
		try {
			return getText(m);
		} catch (MessagingException | IOException e) {
			// TODO Auto-generated catch block
			return "";
		}
	}
	
	/**
	 * Return the primary text content of the message.
	 */
	private String getText(Part p) throws MessagingException, IOException {		
	    if (p.isMimeType("text/*")) {
	        String s = (String)p.getContent();
	        textIsHtml = p.isMimeType("text/html");
	        return s;
	    }
	
	    if (p.isMimeType("multipart/alternative")) {
	        // prefer html text over plain text
	        Multipart mp = (Multipart)p.getContent();
	        String text = null;
	        for (int i = 0; i < mp.getCount(); i++) {
	            Part bp = mp.getBodyPart(i);
	            if (bp.isMimeType("text/plain")) {
	                if (text == null)
	                    text = getText(bp);
	                continue;
	            } else if (bp.isMimeType("text/html")) {
	                String s = getText(bp);
	                if (s != null)
	                    return s;
	            } else {
	                return getText(bp);
	            }
	        }
	        return text;
	    } else if (p.isMimeType("multipart/*")) {
	        Multipart mp = (Multipart)p.getContent();
	        for (int i = 0; i < mp.getCount(); i++) {
	            String s = getText(mp.getBodyPart(i));
	            if (s != null)
	                return s;
	        }
	    }
	
	    return null;
	}
	
	public String getEmailSubject(Message m) {
		String subject = "";
		
		try {
			subject = m.getSubject();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return subject;
	}
	
	public String getReceivedDate(Message m) {
		String dateString = "";
		
		try {
			Date d = m.getSentDate();
			SimpleDateFormat formatter = new SimpleDateFormat("EEE dd MMM yyyy HH:mm");
			dateString = formatter.format(d);
			//System.out.println(dateString);
		} catch (MessagingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		return dateString;
	}
	
	public String getSenderAddress(Message m) {
		String address = "";
		
		try {
			Address[] froms = m.getFrom();
			address = froms == null ? null : ((InternetAddress) froms[0]).getAddress();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return address;
	}
	
	public String getSenderName(Message m) {
		String person = "";
		
		try {
			Address[] froms = m.getFrom();
			person = froms == null ? null : ((InternetAddress) froms[0]).getPersonal();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return person;
	}
	
}