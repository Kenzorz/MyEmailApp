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
	
	public EmailApp (String emailAddress, String password) {
		email = emailAddress;
		pass = password;
		
		String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		
		Properties props = new Properties();
		props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.pop3.socketFactory.fallback", "false");
		props.setProperty("mail.pop3.port",  "995");
		props.setProperty("mail.pop3.socketFactory.port", "995");

		url = new URLName("pop3", host, 995, "", email, pass);
		session = Session.getInstance(props, null);
		store = new POP3SSLStore(session, url);
		try {
			store.connect();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(store);
		getInbox();

	}
	
	public void getInbox() {
		Folder inbox;
		try {
			inbox = store.getFolder("Inbox");
			inbox.open(Folder.READ_ONLY);
			Message messages[] = inbox.getMessages();
			Message aMessage = messages[1];
			System.out.println(aMessage.getSubject());
			
			try {
				Part p = (Part)aMessage.getContent();
				if (p.isMimeType("text/*")) {
					String s = (String)p.getContent();
				}
				
				else if (p.isMimeType("multipart/alternative")) {
					Multipart mp = (Multipart)p.getContent();
					String text = null;
					
					 for (int i = 0; i < mp.getCount(); i++) {
			                Part bp = mp.getBodyPart(i);
			                if (bp.isMimeType("text/plain")) {
			                    if (text == null)
			                        text = aMessage.getText(bp);
			                
			                } else if (bp.isMimeType("text/html")) {
			                    String s = getText(bp);
			                        
			                } else {
			                   
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
				}
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} catch (MessagingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
}