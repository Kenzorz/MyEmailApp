import java.io.IOException;
import java.util.Properties;

import javax.mail.*;

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
		login();
		getInbox();

	}

	private boolean login() {
		
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
	
	public void getInbox() {
		Folder inbox;
		try {
			inbox = store.getFolder("Inbox");
			inbox.open(Folder.READ_ONLY);
			messages = inbox.getMessages();
			Message aMessage = messages[1];
			System.out.println(aMessage.getSubject());	
			try {
				String body = getText(aMessage);
				System.out.println(body);	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (MessagingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
	
}